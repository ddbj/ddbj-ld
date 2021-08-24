package com.ddbj.ld.app.transact.service.jga;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.transact.dao.jga.DateDao;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

@Service
@AllArgsConstructor
@Slf4j
public class JgaDateService {

    private final ConfigSet config;

    private final DateDao dateDao;

    /**
     * JGAの日付情報を登録する.
     */
    public void register() {
        log.info("Start registering JGA's date data to PostgreSQL");

        try (var br = Files.newBufferedReader(Paths.get(this.config.file.jga.date), StandardCharsets.UTF_8)) {
            this.dateDao.dropIndex();
            this.dateDao.deleteAll();

            var recordList = new ArrayList<Object[]>();
            var duplicateCheck = new HashSet<>();

            var maximumRecord = this.config.other.maximumRecord;
            var parser = new CsvParser(new CsvParserSettings());

            String line;

            // ヘッダは飛ばす
            br.readLine();

            while((line = br.readLine()) != null) {
                var row = parser.parseLine(line);

                if(row.length == 0) {
                    // 最終行は処理をスキップ
                    continue;
                }

                var key = row[2];

                if(duplicateCheck.contains(key)) {
                    // 本当はWarnが望ましいと思うが、重複が多すぎるし検知して問い合わせることもないためDEBUG
                    log.debug("Duplicate record:{}", key);
                    continue;
                } else {
                    duplicateCheck.add(key);
                }

                var record = row;

                recordList.add(record);

                if(recordList.size() == maximumRecord) {
                    this.dateDao.bulkInsert(recordList);
                    // リセット
                    recordList = new ArrayList<>();
                }
            }

            if(recordList.size() > 0) {
                this.dateDao.bulkInsert(recordList);
            }

        } catch (IOException e) {
            log.error("Opening date.csv is failed.", e);
        } finally {
            this.dateDao.createIndex();
        }

        log.info("Complete registering JGA's date data to PostgreSQL");
    }
}
