package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.transact.dao.dra.*;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

// TODO DDBJ出力のデータをラグを少なくして反映するためにはDDBJが出力したファイルを登録する処理が必要
@Service
@AllArgsConstructor
@Slf4j
public class AccessionsService {

    private final ConfigSet config;

    private final DraSubmissionDao submissionDao;
    private final DraExperimentDao experimentDao;
    private final DraAnalysisDao analysisDao;
    private final DraRunDao runDao;
    private final DraStudyDao studyDao;
    private final DraSampleDao sampleDao;

    /**
     * SRA, ERA, DRAの関係情報をSRA_Accessions.tabから取得しDBに登録する.
     */
    public void registerSRAAccessions() {
        log.info("Start registering SRAAccessions.tab to PostgreSQL");

        this.submissionDao.dropIndex();
        this.experimentDao.dropIndex();
        this.analysisDao.dropIndex();
        this.runDao.dropIndex();
        this.studyDao.dropIndex();
        this.sampleDao.dropIndex();

        this.submissionDao.deleteAll();
        this.experimentDao.deleteAll();
        this.analysisDao.deleteAll();
        this.runDao.deleteAll();
        this.studyDao.deleteAll();
        this.sampleDao.deleteAll();

        try (var br = Files.newBufferedReader(Paths.get(this.config.file.path.sraAccessions), StandardCharsets.UTF_8)) {
            var parser = new TsvParser(new TsvParserSettings());
            String line;
            br.readLine();

            var submissionList = new ArrayList<Object[]>();
            var experimentList = new ArrayList<Object[]>();
            var analysisList = new ArrayList<Object[]>();
            var runList = new ArrayList<Object[]>();
            var studyList = new ArrayList<Object[]>();
            var sampleList = new ArrayList<Object[]>();

            // 重複チェック用
            // たまにファイルが壊れレコードが重複しているため
            var duplicateCheck = new HashSet<String>();

            int cnt = 0;
            var maximumRecord = this.config.other.maximumRecord;

            while((line = br.readLine()) != null) {
                var row = parser.parseLine(line);

                if(row.length == 0) {
                    // 最終行は処理をスキップ
                    continue;
                }

                var accession = row[0];

                if(duplicateCheck.contains(accession)) {
                    log.warn("Duplicate accession:{}", accession);
                    continue;
                } else {
                    duplicateCheck.add(accession);
                }

                // レコードをDBに格納しやすい方に変更する
                var record = this.getSRARecord(row);

                if(null == record) {
                    log.error("converting record failed.{}", line);

                    continue;
                }

                switch (row[6]) {
                    case "SUBMISSION":
                        submissionList.add(record);

                        if(submissionList.size() == maximumRecord) {
                            this.submissionDao.bulkInsert(submissionList);
                            // リセット
                            submissionList = new ArrayList<>();
                        }

                        break;

                    case "EXPERIMENT":
                        experimentList.add(record);

                        if(experimentList.size() == maximumRecord) {
                            this.experimentDao.bulkInsert(experimentList);
                            // リセット
                            experimentList = new ArrayList<>();
                        }

                        break;

                    case "ANALYSIS":
                        analysisList.add(record);

                        if(analysisList.size() == maximumRecord) {
                            this.analysisDao.bulkInsert(analysisList);
                            // リセット
                            analysisList = new ArrayList<>();
                        }

                        break;

                    case "RUN":
                        runList.add(record);

                        if(runList.size() == maximumRecord) {
                            this.runDao.bulkInsert(runList);
                            // リセット
                            runList = new ArrayList<>();
                        }

                        break;

                    case "STUDY":
                        studyList.add(record);

                        if(studyList.size() == maximumRecord) {
                            this.studyDao.bulkInsert(studyList);
                            // リセット
                            studyList = new ArrayList<>();
                        }

                        break;

                    case "SAMPLE":
                        sampleList.add(record);

                        if(sampleList.size() == maximumRecord) {
                            this.sampleDao.bulkInsert(sampleList);
                            // リセット
                            sampleList = new ArrayList<>();
                        }

                        break;
                }

                cnt++;

                if(cnt % maximumRecord == 0) {
                    log.info("count:{}", cnt);
                }
            }

            if(submissionList.size() > 0) {
                this.submissionDao.bulkInsert(submissionList);
            }

            if(experimentList.size() > 0) {
                this.experimentDao.bulkInsert(experimentList);
            }

            if(analysisList.size() > 0) {
                this.analysisDao.bulkInsert(analysisList);
            }

            if(runList.size() > 0) {
                this.runDao.bulkInsert(runList);
            }

            if(studyList.size() > 0) {
                this.studyDao.bulkInsert(studyList);
            }

            if(sampleList.size() > 0) {
                this.sampleDao.bulkInsert(sampleList);
            }

        } catch (IOException e) {
            log.error("Opening SRAAccessions.tab is failed.", e);
        } finally {
            this.submissionDao.createIndex();
            this.experimentDao.createIndex();
            this.analysisDao.createIndex();
            this.runDao.createIndex();
            this.studyDao.createIndex();
            this.sampleDao.createIndex();
        }

        log.info("Complete registering SRAAccessions.tab to PostgreSQL");
    }

    /**
     * SRAAccessions.tabのレコードから各メタデータの情報を登録するレコードに変換する.
     */
    private Object[] getSRARecord(String[] row) {
        var timeStampFormat = this.config.other.timestampFormat;
        var hyphen = "-";

        try {
            var updated   = hyphen.equals(row[3])
                    ? null
                    : new Timestamp(new SimpleDateFormat(timeStampFormat).parse(row[3]).getTime());
            var published = hyphen.equals(row[4])
                    ? null
                    : new Timestamp(new SimpleDateFormat(timeStampFormat).parse(row[4]).getTime());
            var received  = hyphen.equals(row[5])
                    ? null
                    : new Timestamp(new SimpleDateFormat(timeStampFormat).parse(row[5]).getTime());

            Object[] record = new Object[20];

            record[0] = row[0];
            record[1] = row[1];
            record[2] = row[2];
            record[3] = updated;
            record[4] = published;
            record[5] = received;
            record[6] = hyphen.equals(row[6]) ? null : row[6];
            record[7] = hyphen.equals(row[7]) ? null : row[7];
            record[8] = hyphen.equals(row[8]) ? null : row[8];
            record[9] = hyphen.equals(row[9]) ? null : row[9];
            record[10] = hyphen.equals(row[10]) ? null : row[10];
            record[11] = hyphen.equals(row[11]) ? null : row[11];
            record[12] = hyphen.equals(row[12]) ? null : row[12];
            record[13] = hyphen.equals(row[13]) ? null : Integer.parseInt(row[13]);
            record[14] = hyphen.equals(row[14]) ? null : row[14];
            record[15] = hyphen.equals(row[15]) ? null : row[15];
            record[16] = hyphen.equals(row[16]) ? null : row[16];
            record[17] = hyphen.equals(row[17]) ? null : row[17];
            record[18] = hyphen.equals(row[18]) ? null : row[18];
            record[19] = hyphen.equals(row[19]) ? null : row[19];

            return record;

        } catch (ParseException e) {
            log.error("parsing record is failed.", e);

            return null;
        }
    }
}
