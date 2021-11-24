package com.ddbj.ld.app.transact.service.sra;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.transact.dao.sra.*;
import com.ddbj.ld.common.constants.AccessionTypeEnum;
import com.ddbj.ld.common.constants.StatusEnum;
import com.ddbj.ld.common.constants.VisibilityEnum;
import com.ddbj.ld.common.exception.DdbjException;
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

@Service
@AllArgsConstructor
@Slf4j
public class SraAccessionsService {

    private final ConfigSet config;

    private final SraSubmissionDao submissionDao;
    private final SraExperimentDao experimentDao;
    private final SraAnalysisDao analysisDao;
    private final SraRunDao runDao;
    private final SraStudyDao studyDao;
    private final SraSampleDao sampleDao;

    private final MessageModule messageModule;

    /**
     * SRA, ERAの関係情報をSRA_Accessions.tabから取得しDBに登録する.
     */
    public void registerAccessions() {
        log.info("Start registering SRAAccessions.tab to PostgreSQL");

        this.resetTables();

        try (var br = Files.newBufferedReader(Paths.get(this.config.file.path.sra.accessions), StandardCharsets.UTF_8)) {
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
            var duplicateAccessions = new HashSet<String>();

            int cnt = 0;
            var maximumRecord = this.config.search.maximumRecord;

            while((line = br.readLine()) != null) {
                var row = parser.parseLine(line);

                if(row.length == 0) {
                    // 最終行は処理をスキップ
                    continue;
                }

                var accession = row[0];

                if(accession.startsWith("DR")) {
                    // DDBJ出力分は別途登録するため処理をスキップ
                    continue;
                }

                if(duplicateCheck.contains(accession)) {
                    log.warn("Duplicate accession:{}", accession);
                    duplicateAccessions.add(accession);

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

                switch (AccessionTypeEnum.getType(row[6])) {
                    case SUBMISSION:
                        submissionList.add(record);

                        if(submissionList.size() == maximumRecord) {
                            this.submissionDao.bulkInsert(submissionList);
                            // リセット
                            submissionList = new ArrayList<>();
                        }

                        break;

                    case EXPERIMENT:
                        experimentList.add(record);

                        if(experimentList.size() == maximumRecord) {
                            this.experimentDao.bulkInsert(experimentList);
                            // リセット
                            experimentList = new ArrayList<>();
                        }

                        break;

                    case ANALYSIS:
                        analysisList.add(record);

                        if(analysisList.size() == maximumRecord) {
                            this.analysisDao.bulkInsert(analysisList);
                            // リセット
                            analysisList = new ArrayList<>();
                        }

                        break;

                    case RUN:
                        runList.add(record);

                        if(runList.size() == maximumRecord) {
                            this.runDao.bulkInsert(runList);
                            // リセット
                            runList = new ArrayList<>();
                        }

                        break;

                    case STUDY:
                        studyList.add(record);

                        if(studyList.size() == maximumRecord) {
                            this.studyDao.bulkInsert(studyList);
                            // リセット
                            studyList = new ArrayList<>();
                        }

                        break;

                    case SAMPLE:
                        sampleList.add(record);

                        if(sampleList.size() == maximumRecord) {
                            this.sampleDao.bulkInsert(sampleList);
                            // リセット
                            sampleList = new ArrayList<>();
                        }

                        break;
                }

                cnt++;
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

            log.info("total:{}", cnt);

            if(duplicateAccessions.size() > 0) {
                this.messageModule.noticeDuplicateRecord(duplicateAccessions);

            } else {
                var comment = String.format(
                        "%s\nSRA_Accessions.tab register success.",
                        this.config.message.mention
                );

                this.messageModule.postMessage(this.config.message.channelId, comment);
            }


        } catch (IOException e) {
            var message = "Opening SRAAccessions.tab is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        } finally {
            this.createIndexes();
        }

        log.info("Complete registering SRAAccessions.tab to PostgreSQL");
    }

    /**
     * 更新されたSRA, ERAの関係情報をSRA_Accessions.tabから取得しDBに登録する.
     */
    public void createUpdatedData(final String date) {
        log.info("Start registering SRAAccessions.tab to PostgreSQL and create update diff SRA_Accessions.tab");

        if(null == date) {
            var message = "Date is null.";
            log.error(message);

            throw new DdbjException(message);
        }

        this.createTempTables(date);

        try (var br = Files.newBufferedReader(Paths.get(this.config.file.path.sra.accessions), StandardCharsets.UTF_8)) {
            var parser = new TsvParser(new TsvParserSettings());
            String line;
            br.readLine();

            // 重複チェック用
            // たまにファイルが壊れレコードが重複しているため
            var duplicateCheck = new HashSet<String>();
            var duplicateAccessions = new HashSet<String>();

            int cnt = 0;
            var maximumRecord = this.config.other.maximumRecord;

            var submissionList = new ArrayList<Object[]>();
            var experimentList = new ArrayList<Object[]>();
            var analysisList = new ArrayList<Object[]>();
            var runList = new ArrayList<Object[]>();
            var studyList = new ArrayList<Object[]>();
            var sampleList = new ArrayList<Object[]>();

            while((line = br.readLine()) != null) {
                var row = parser.parseLine(line);

                if(row.length == 0) {
                    // 最終行は処理をスキップ
                    continue;
                }

                var accession = row[0];

                if(accession.startsWith("DR")) {
                    // DDBJ出力分は別途登録するため処理をスキップ
                    continue;
                }

                if(duplicateCheck.contains(accession)) {
                    log.warn("Duplicate accession:{}", accession);
                    duplicateAccessions.add(accession);

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

                switch (AccessionTypeEnum.getType(row[6])) {
                    case SUBMISSION:
                        submissionList.add(record);

                        if(submissionList.size() == maximumRecord) {
                            this.submissionDao.bulkInsertTemp(date, submissionList);
                            // リセット
                            submissionList = new ArrayList<>();
                        }

                        break;

                    case EXPERIMENT:
                        experimentList.add(record);

                        if(experimentList.size() == maximumRecord) {
                            this.experimentDao.bulkInsertTemp(date, experimentList);
                            // リセット
                            experimentList = new ArrayList<>();
                        }

                        break;

                    case ANALYSIS:
                        analysisList.add(record);

                        if(analysisList.size() == maximumRecord) {
                            this.analysisDao.bulkInsertTemp(date, analysisList);
                            // リセット
                            analysisList = new ArrayList<>();
                        }

                        break;

                    case RUN:
                        runList.add(record);

                        if(runList.size() == maximumRecord) {
                            this.runDao.bulkInsertTemp(date, runList);
                            // リセット
                            runList = new ArrayList<>();
                        }

                        break;

                    case STUDY:
                        studyList.add(record);

                        if(studyList.size() == maximumRecord) {
                            this.studyDao.bulkInsertTemp(date, studyList);
                            // リセット
                            studyList = new ArrayList<>();
                        }

                        break;

                    case SAMPLE:
                        sampleList.add(record);

                        if(sampleList.size() == maximumRecord) {
                            this.sampleDao.bulkInsertTemp(date, sampleList);
                            // リセット
                            sampleList = new ArrayList<>();
                        }

                        break;
                }

                cnt++;
            }

            if(submissionList.size() > 0) {
                this.submissionDao.bulkInsertTemp(date, submissionList);
            }

            if(experimentList.size() > 0) {
                this.experimentDao.bulkInsertTemp(date, experimentList);
            }

            if(analysisList.size() > 0) {
                this.analysisDao.bulkInsertTemp(date, analysisList);
            }

            if(runList.size() > 0) {
                this.runDao.bulkInsertTemp(date, runList);
            }

            if(studyList.size() > 0) {
                this.studyDao.bulkInsertTemp(date, studyList);
            }

            if(sampleList.size() > 0) {
                this.sampleDao.bulkInsertTemp(date, sampleList);
            }

            this.createTempIndexes(date);

            log.info("total:{}", cnt);

            if(duplicateAccessions.size() > 0) {
                this.messageModule.noticeDuplicateRecord(duplicateAccessions);

            } else {
                var comment = String.format(
                        "%s\nSRA_Accessions.tab register success.",
                        this.config.message.mention
                );

                this.messageModule.postMessage(this.config.message.channelId, comment);
            }

        } catch (IOException e) {
            var message = "Opening SRAAccessions.tab is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }

        log.info("Complete registering SRAAccessions.tab to PostgreSQL and create update diff SRA_Accessions.tab");
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
            // liveはpublicとする
            record[2] = StatusEnum.LIVE.status.equals(row[2]) ? StatusEnum.PUBLIC.status : row[2];
            record[3] = updated;
            record[4] = published;
            record[5] = received;
            record[6] = hyphen.equals(row[6]) ? null : row[6];
            record[7] = hyphen.equals(row[7]) ? null : row[7];
            record[8] = hyphen.equals(row[8]) ? null : "public".equals(row[8]) ? VisibilityEnum.UNRESTRICTED_ACCESS.visibility : VisibilityEnum.CONTROLLED_ACCESS.visibility;
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

    private void resetTables() {
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
    }

    private void createIndexes() {
        this.submissionDao.createIndex();
        this.experimentDao.createIndex();
        this.analysisDao.createIndex();
        this.runDao.createIndex();
        this.studyDao.createIndex();
        this.sampleDao.createIndex();
    }

    private void createTempTables(final String date) {
        this.submissionDao.createTempTable(date);
        this.experimentDao.createTempTable(date);
        this.analysisDao.createTempTable(date);
        this.runDao.createTempTable(date);
        this.studyDao.createTempTable(date);
        this.sampleDao.createTempTable(date);
    }

    private void createTempIndexes(final String date) {
        this.submissionDao.createTempIndex(date);
        this.experimentDao.createTempIndex(date);
        this.analysisDao.createTempIndex(date);
        this.runDao.createTempIndex(date);
        this.studyDao.createTempIndex(date);
        this.sampleDao.createTempIndex(date);
    }
}
