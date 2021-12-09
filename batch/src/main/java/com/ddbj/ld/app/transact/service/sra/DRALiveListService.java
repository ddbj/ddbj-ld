package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.transact.dao.primary.sra.DRALiveListDao;
import com.ddbj.ld.common.exception.DdbjException;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

@Service
@AllArgsConstructor
@Slf4j
public class DRALiveListService {

    private final ConfigSet config;

    private final DRALiveListDao draLiveListDao;

    /**
     * DRAのライブリストをDBに登録する.
     */
    public void registerLiveList() {
        log.info("Start registering DRA's livelist to PostgreSQL");

        try (var br = new BufferedReader(new FileReader(this.config.file.path.sra.livelist))) {
            var parser = new TsvParser(new TsvParserSettings());
            String line;
            br.readLine();

            this.draLiveListDao.deleteAll();

            // 重複チェック用
            // たまにファイルが壊れレコードが重複しているため
            var duplicateCheck = new HashSet<String>();
            var duplicateAccessions = new HashSet<String>();

            var maximumRecord = this.config.other.maximumRecord;

            var recordList = new ArrayList<Object[]>();

            while((line = br.readLine()) != null) {
                var row = parser.parseLine(line);

                if(row.length == 0) {
                    // 最終行は処理をスキップ
                    continue;
                }

                if(row.length != 8) {
                    log.warn("This record is invalid length. {}", line);

                    continue;
                }

                var accession = row[0];
                var submission = row[1];

                var accessionPair = accession + "," + submission;

                if(duplicateCheck.contains(accessionPair)) {
                    log.warn("Duplicate accession:{}", accessionPair);
                    duplicateAccessions.add(accessionPair);

                    continue;
                } else {
                    duplicateCheck.add(accessionPair);
                }

                // レコードをDBに格納しやすい方に変更する
                var record = this.getRecord(row);

                if(null == record) {
                    log.error("converting record failed.{}", line);

                    continue;
                }

                recordList.add(record);

                if(recordList.size() == maximumRecord) {
                    this.draLiveListDao.bulkInsert(recordList);
                    // リセット
                    recordList = new ArrayList<>();
                }
            }

            if(recordList.size() > 0) {
                this.draLiveListDao.bulkInsert(recordList);
            }
        } catch (IOException e) {
            var message = "Opening livelist.tab is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }

        log.info("Complete registering DRA's livelist to PostgreSQL.");
    }

    /**
     * LiveListのレコードから各メタデータの情報を登録するレコードに変換する.
     */
    private Object[] getRecord(String[] row) {
        var timeStampFormat = "yyyy-MM-dd H:mm:ss";

        try {
            var updated   = null == row[3]
                    ? null
                    : new Timestamp(new SimpleDateFormat(timeStampFormat).parse(row[3]).getTime());

            Object[] record = new Object[8];

            record[0] = row[0];
            record[1] = row[1];
            record[2] = row[2];
            record[3] = updated;
            record[4] = row[4];
            record[5] = row[5];
            record[6] = row[6];
            record[7] = row[7];

            return record;

        } catch (ParseException e) {
            log.error("parsing record is failed.", e);

            return null;
        }
    }
}
