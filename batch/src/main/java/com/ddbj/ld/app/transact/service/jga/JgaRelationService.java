package com.ddbj.ld.app.transact.service.jga;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.transact.dao.jga.*;
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
public class JgaRelationService {

    private final ConfigSet config;

    // JGAのDao
    private final AnalysisStudyDao analysisStudyDao;
    private final DataExperimentDao dataExperimentDao;
    private final DataSetAnalysisDao dataSetAnalysisDao;
    private final DataSetDataDao dataSetDataDao;
    private final DataSetPolicyDao dataSetPolicyDao;
    private final ExperimentStudyDao experimentStudyDao;

    /**
     * JGAの関係情報を登録する.
     */
    public void register() {
        log.info("Start registering JGA's relation data to PostgreSQL");

        // analysis-experiment-relation.csvはカラムがないため取り込まない
        // policy-dac-relation.csvはDACが固定値で1つだけ(JGAC000001)なので取り込まない
        // 将来的に上記2点が変更された場合、処理とテーブルを増やすこと
        this.registerJgaData(this.config.file.jga.analysisStudy, this.analysisStudyDao);
        this.registerJgaData(this.config.file.jga.dataExperiment, this.dataExperimentDao);
        this.registerJgaData(this.config.file.jga.dataSetAnalysis, this.dataSetAnalysisDao);
        this.registerJgaData(this.config.file.jga.dataSetData, this.dataSetDataDao);
        this.registerJgaData(this.config.file.jga.dataSetPolicy, this.dataSetPolicyDao);
        this.registerJgaData(this.config.file.jga.experimentStudy, this.experimentStudyDao);

        log.info("Complete registering JGA's relation data to PostgreSQL");
    }

    private void registerJgaData(final String path, final JgaDao dao) {
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
            log.error("Opening file is failed.", e);
        } finally {
            dao.createIndex();
        }
    }
}