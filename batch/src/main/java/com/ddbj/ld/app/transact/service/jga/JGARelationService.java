package com.ddbj.ld.app.transact.service.jga;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.transact.dao.jga.*;
import com.ddbj.ld.common.exception.DdbjException;
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
public class JGARelationService {

    private final ConfigSet config;

    // JGAのDao
    private final JGAAnalysisStudyDao analysisStudyDao;
    private final JGADataExperimentDao dataExperimentDao;
    private final JGADataSetAnalysisDao dataSetAnalysisDao;
    private final JGADataSetDataDao dataSetDataDao;
    private final JGADataSetPolicyDao dataSetPolicyDao;
    private final JGAExperimentStudyDao experimentStudyDao;
    private final JGAPolicyDacDao policyDacDao;

    /**
     * JGAの関係情報を登録する.
     */
    public void register() {
        log.info("Start registering JGA's relation data to PostgreSQL");

        // analysis-experiment-relation.csvはカラムがないため取り込まない
        // policy-dac-relation.csvはDACが固定値で1つだけ(JGAC000001)なので取り込まない
        // 将来的に上記2点が変更された場合、処理とテーブルを増やすこと
        this.registerJgaData(this.config.file.path.jga.analysisStudy, this.analysisStudyDao);
        this.registerJgaData(this.config.file.path.jga.dataExperiment, this.dataExperimentDao);
        this.registerJgaData(this.config.file.path.jga.dataSetAnalysis, this.dataSetAnalysisDao);
        this.registerJgaData(this.config.file.path.jga.dataSetData, this.dataSetDataDao);
        this.registerJgaData(this.config.file.path.jga.dataSetPolicy, this.dataSetPolicyDao);
        this.registerJgaData(this.config.file.path.jga.experimentStudy, this.experimentStudyDao);
        this.registerJgaData(this.config.file.path.jga.policyDac, this.policyDacDao);

        log.info("Complete registering JGA's relation data to PostgreSQL");
    }

    private void registerJgaData(final String path, final JGADao dao) {
        try (var br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            dao.dropIndex();
            dao.deleteAll();

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

                var key = row[1] + "," + row[2];

                if(duplicateCheck.contains(key)) {
                    // 本当はWarnが望ましいと思うが、重複が多すぎるし検知して問い合わせることもないためDEBUG
                    log.debug("Duplicate record:{}", key);
                    continue;
                } else {
                    duplicateCheck.add(key);
                }

                recordList.add(new Object[]{ row[1], row[2] });

                if(recordList.size() == maximumRecord) {
                    dao.bulkInsert(recordList);
                    // リセット
                    recordList = new ArrayList<>();
                }
            }

            if(recordList.size() > 0) {
                dao.bulkInsert(recordList);
            }

        } catch (IOException e) {
            var message = String.format("Not exists file:%s", path);
            log.error(message, e);

            throw new DdbjException(message);
        } finally {
            dao.createIndex();
        }
    }
}
