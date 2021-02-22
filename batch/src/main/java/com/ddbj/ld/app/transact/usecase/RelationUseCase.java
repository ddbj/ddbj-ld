package com.ddbj.ld.app.transact.usecase;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.parser.dra.SRAAccessionsParser;
import com.ddbj.ld.app.core.parser.jga.JgaDateParser;
import com.ddbj.ld.app.core.parser.jga.JgaRelationParser;
import com.ddbj.ld.app.transact.dao.jga.JgaDateDao;
import com.ddbj.ld.app.transact.dao.jga.JgaRelationDao;
import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.annotation.UseCase;
import com.ddbj.ld.common.constants.FileNameEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.helper.BulkHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * PostgreSQLに関する処理を行うユースケースクラス.
 */
@UseCase
@AllArgsConstructor
@Slf4j
public class RelationUseCase {
    private final ConfigSet config;

    private final SRAAccessionsParser sraAccessionsParser;
    private final JgaRelationParser jgaRelationParser;
    private final JgaDateParser jgaDateParser;

    private final SRAAccessionsDao sraAccessionsDao;
    private final JgaRelationDao jgaRelationDao;
    private final JgaDateDao jgaDateDao;

    /**
     * BioProject, BioSample, DRAの関係情報をSRAAccessions.tabから登録する.
     */
    public void registerSRARelation() {
        log.info("Start registering BioProject And BioSamle, DRA's relation data to PostgreSQL");

        String sraAccessionsTab = this.config.file.path.sra + FileNameEnum.SRA_ACCESSIONS.getFileName();

        List<String[]> sraAccessions = sraAccessionsParser.parser(sraAccessionsTab);

        List<Object[]> bioProjectRecordList = new ArrayList<>();
        // 重複回避用
        HashSet<String> bioProjectAccessionSet = new HashSet<>();

        List<Object[]> bioSampleRecordList = new ArrayList<>();
        // 重複回避用
        Map<String, Object[]> bioSampleRecordMap = new HashMap<>();

        List<Object[]> studyRecordList = new ArrayList<>();
        List<Object[]> sampleRecordList = new ArrayList<>();

        List<Object[]> submissionRecordList = new ArrayList<>();
        List<Object[]> analysisRecordList = new ArrayList<>();
        List<Object[]> experimentRecordList = new ArrayList<>();
        List<Object[]> runRecordList = new ArrayList<>();

        // TODO Experimentを経由して取得
        List<Object[]> bioProjectSubmissionRelationList = new ArrayList<>();
        // TODO Studyのレコードから取得
        List<Object[]> bioProjectStudyRelationList = new ArrayList<>();

        List<Object[]> bioSampleSampleRelationList = new ArrayList<>();
        // 重複回避用
        Map<String, Object[]> bioSampleSampleRelationMap = new HashMap<>();
        List<Object[]> submissionAnalysisRelationList = new ArrayList<>();
        // 重複回避用
        Map<String, Object[]> submissionAnalysisRelationMap = new HashMap<>();
        List<Object[]> submissionExperimentRelationList = new ArrayList<>();
        // 重複回避用
        Map<String, Object[]> submissionExperimentRelationMap = new HashMap<>();
        List<Object[]> experimentRunRelationList = new ArrayList<>();
        // 重複回避用
        Map<String, Object[]> experimentRunRelationMap = new HashMap<>();
        List<Object[]> bioSampleExperimentRelationList = new ArrayList<>();
        // 重複回避用
        Map<String, Object[]> bioSampleExperimentRelationMap = new HashMap<>();
        List<Object[]> runBioSampleRelationList = new ArrayList<>();
        // 重複回避用
        Map<String, Object[]> runBioSampleRelationMap = new HashMap<>();
        List<Object[]> sampleExperimentRelationList = new ArrayList<>();
        // 重複回避用
        Map<String, Object[]> sampleExperimentRelationMap = new HashMap<>();
        List<Object[]> studySubmissionRelationList = new ArrayList<>();
        // 重複回避用
        Map<String, Object[]> studySubmissionRelationMap = new HashMap<>();

        String timeStampFormat = this.config.other.timestampFormat;

        for(int i = 0; i < sraAccessions.size(); i++) {
            String[] sraAccession = sraAccessions.get(i);
            // TODO ここのEnumを呼ぶ部分を整理
            TypeEnum type   = TypeEnum.getType(sraAccession[6]);
            Object[] record = getRecord(sraAccession, timeStampFormat);

            if(record == null) {
                continue;
            }

            // TODO ステータス関係が整理されたらEnum化
            String targetStatus = "live";

            switch (type) {
                case STUDY:
                    studyRecordList.add(record);

                    if(!targetStatus.equals(record[1])) {
                        continue;
                    }

                    bioProjectAccessionSet.add(sraAccession[18]);

                    Object[] bioProjectStudyRelation = getRelation(sraAccession[18], sraAccession[0]);
                    Object[] studySubmissionRelation = getRelation(sraAccession[0], sraAccession[1]);
                    bioProjectStudyRelationList.add(bioProjectStudyRelation);

                    studySubmissionRelationMap.put(sraAccession[0], studySubmissionRelation);

                    Object[] bioProjectSubmissionRelation = new Object[2];
                    bioProjectSubmissionRelation[0] = sraAccession[18];
                    bioProjectSubmissionRelation[1] = sraAccession[1];
                    bioProjectSubmissionRelationList.add(bioProjectSubmissionRelation);

                    break;
                case SAMPLE:
                    sampleRecordList.add(record);

                    if(!targetStatus.equals(record[1])) {
                        continue;
                    }

                    Object [] bioSampleRecord = new Object[6];
                    bioSampleRecord[0] = sraAccession[17];

                    bioSampleRecordMap.put(sraAccession[17], bioSampleRecord);

                    Object[] bioSampleSampleRelation = getRelation(sraAccession[17], sraAccession[0]);

                    bioSampleSampleRelationMap.put(sraAccession[17], bioSampleSampleRelation);
                    break;
                case SUBMISSION:
                    submissionRecordList.add(record);

                    break;
                case EXPERIMENT:
                    experimentRecordList.add(record);

                    if(!targetStatus.equals(record[1])) {
                        continue;
                    }

                    Object[] submissionExperimentRelation = getRelation(sraAccession[1], sraAccession[0]);

                    submissionExperimentRelationMap.put(sraAccession[1], submissionExperimentRelation);

                    Object[] bioSampleExperimentRelation = getRelation(sraAccession[17], sraAccession[0]);
                    Object[] sampleExperimentRelation = getRelation(sraAccession[11], sraAccession[0]);

                    bioSampleExperimentRelationMap.put(sraAccession[17], bioSampleExperimentRelation);
                    sampleExperimentRelationMap.put(sraAccession[11], sampleExperimentRelation);

                    break;
                case ANALYSIS:
                    analysisRecordList.add(record);

                    if(!targetStatus.equals(record[1])) {
                        continue;
                    }

                    Object[] submissionAnalysisRelation = getRelation(sraAccession[1], sraAccession[0]);
                    submissionAnalysisRelationMap.put(sraAccession[1], submissionAnalysisRelation);

                    break;
                case RUN:
                    runRecordList.add(record);

                    if(!targetStatus.equals(record[1])) {
                        continue;
                    }

                    Object[] experimentRunRelation = getRelation(sraAccession[10], sraAccession[0]);

                    experimentRunRelationMap.put(sraAccession[10], experimentRunRelation);

                    if(sraAccession.length > 17) {
                        Object[] runBioSampleRelation  = getRelation(sraAccession[0], sraAccession[17]);
                        runBioSampleRelationMap.put(sraAccession[0], runBioSampleRelation);
                    }

                    break;
                default:
            }
        }

        bioProjectAccessionSet.forEach(accession -> {
            Object[] record = new Object[6];
            record[0] = accession;

            bioProjectRecordList.add(record);
        });

        int maximumRecord = this.config.other.maximumRecord;

        bulkInsertRecord(bioProjectRecordList, maximumRecord, TypeEnum.BIOPROJECT);

        log.info("Complete bioproject:" + bioProjectRecordList.size());

        for(Map.Entry<String, Object[]> entry : bioSampleRecordMap.entrySet()) {
            bioSampleRecordList.add(entry.getValue());
        }

        bulkInsertRecord(bioSampleRecordList, maximumRecord, TypeEnum.BIOSAMPLE);

        log.info("Complete biosample:" + bioSampleRecordList.size());

        bulkInsertRecord(studyRecordList, maximumRecord, TypeEnum.STUDY);

        log.info("Complete study:" + studyRecordList.size());

        bulkInsertRecord(sampleRecordList, maximumRecord, TypeEnum.SAMPLE);

        log.info("Complete sample:" + sampleRecordList.size());

        bulkInsertRecord(submissionRecordList, maximumRecord, TypeEnum.SUBMISSION);

        log.info("Complete submission:" + submissionRecordList.size());

        bulkInsertRecord(analysisRecordList, maximumRecord, TypeEnum.ANALYSIS);

        log.info("Complete analysis:" + analysisRecordList.size());

        bulkInsertRecord(experimentRecordList, maximumRecord, TypeEnum.EXPERIMENT);

        log.info("Complete experiment:" + analysisRecordList.size());

        bulkInsertRecord(runRecordList, maximumRecord, TypeEnum.RUN);

        log.info("Complete run:" + runRecordList.size());

        bulkInsertRelation(bioProjectSubmissionRelationList, maximumRecord, TypeEnum.BIOPROJECT, TypeEnum.SUBMISSION);

        log.info("Complete bioproject_submission:" + bioProjectSubmissionRelationList.size());

        bulkInsertRelation(bioProjectStudyRelationList, maximumRecord, TypeEnum.BIOPROJECT, TypeEnum.STUDY);

        log.info("Complete bioproject_study:" + bioProjectStudyRelationList.size());

        for(Map.Entry<String, Object[]> entry : submissionAnalysisRelationMap.entrySet()) {
            submissionAnalysisRelationList.add(entry.getValue());
        }

        bulkInsertRelation(submissionAnalysisRelationList, maximumRecord, TypeEnum.SUBMISSION, TypeEnum.ANALYSIS);

        log.info("Complete submission_analysis:" + submissionAnalysisRelationList.size());

        for(Map.Entry<String, Object[]> entry : submissionExperimentRelationMap.entrySet()) {
            submissionExperimentRelationList.add(entry.getValue());
        }

        bulkInsertRelation(submissionExperimentRelationList, maximumRecord, TypeEnum.SUBMISSION, TypeEnum.EXPERIMENT);

        log.info("Complete submission_experiment:" + submissionExperimentRelationList.size());

        for(Map.Entry<String, Object[]> entry : experimentRunRelationMap.entrySet()) {
            experimentRunRelationList.add(entry.getValue());
        }

        bulkInsertRelation(experimentRunRelationList, maximumRecord, TypeEnum.EXPERIMENT, TypeEnum.RUN);

        log.info("Complete experiment_run:" + experimentRunRelationList.size());

        for(Map.Entry<String, Object[]> entry : bioSampleSampleRelationMap.entrySet()) {
            bioSampleSampleRelationList.add(entry.getValue());
        }

        bulkInsertRelation(bioSampleSampleRelationList, maximumRecord, TypeEnum.BIOSAMPLE, TypeEnum.SAMPLE);

        log.info("Complete biosample_sample:" + bioSampleSampleRelationList.size());

        for(Map.Entry<String, Object[]> entry : bioSampleExperimentRelationMap.entrySet()) {
            bioSampleExperimentRelationList.add(entry.getValue());
        }

        bulkInsertRelation(bioSampleExperimentRelationList, maximumRecord, TypeEnum.BIOSAMPLE, TypeEnum.EXPERIMENT);

        log.info("Complete biosample_experiment:" + bioSampleSampleRelationList.size());

        for(Map.Entry<String, Object[]> entry : runBioSampleRelationMap.entrySet()) {
            runBioSampleRelationList.add(entry.getValue());
        }

        bulkInsertRelation(runBioSampleRelationList, maximumRecord, TypeEnum.RUN, TypeEnum.BIOSAMPLE);

        log.info("Complete run_biosample:" + bioSampleSampleRelationList.size());

        for(Map.Entry<String, Object[]> entry : sampleExperimentRelationMap.entrySet()) {
            sampleExperimentRelationList.add(entry.getValue());
        }

        bulkInsertRelation(sampleExperimentRelationList, maximumRecord, TypeEnum.SAMPLE, TypeEnum.EXPERIMENT);

        log.info("Complete sample_experiment:" + bioSampleSampleRelationList.size());

        for(Map.Entry<String, Object[]> entry : studySubmissionRelationMap.entrySet()) {
            studySubmissionRelationList.add(entry.getValue());
        }

        bulkInsertRelation(studySubmissionRelationList, maximumRecord, TypeEnum.STUDY, TypeEnum.SUBMISSION);

        log.info("Complete study_submission:" + studySubmissionRelationList.size());

        log.info("Complete registering BioProject And BioSamle, DRA's relation data to PostgreSQL");
    }

    /**
     * JGAの関係情報を登録する.
     */
    public void registerJgaRelation() {
        log.info("Start registering JGA's relation data to PostgreSQL");

        jgaRelationDao.deleteAll();

        String analysisExperimentRelation = this.config.file.path.jga + FileNameEnum.ANALYSIS_EXPERIMENT_RELATION.getFileName();
        String analysisStudyRelation      = this.config.file.path.jga + FileNameEnum.ANALYSIS_STUDY_RELATION.getFileName();
        String dataExperimentRelation     = this.config.file.path.jga + FileNameEnum.DATA_EXPERIMENT_RELATION.getFileName();
        String datasetAnalysisRelation    = this.config.file.path.jga + FileNameEnum.DATASET_ANALYSIS_RELATION.getFileName();
        String datasetDataRelation        = this.config.file.path.jga + FileNameEnum.DATASET_DATA_RELATION.getFileName();
        String datasetPolicyRelation      = this.config.file.path.jga + FileNameEnum.DATASET_POLICY_RELATION.getFileName();
        String experimentStudyRelation    = this.config.file.path.jga + FileNameEnum.EXPERIMENT_STUDY_RELATION.getFileName();
        String policyDacRelation          = this.config.file.path.jga + FileNameEnum.POLICY_DAC_RELATION.getFileName();

        List<Object[]> analysisExperimentRecords = jgaRelationParser.parser(analysisExperimentRelation, TypeEnum.JGA_ANALYSIS.getType(), TypeEnum.JGA_EXPERIMENT.getType());

        if(null == analysisExperimentRecords) {
            log.error("analysisExperimentRelation file is not exist.");
            System.exit(255);
        }

        jgaRelationDao.bulkInsert(analysisExperimentRecords);

        List<Object[]> analysisStudyRecords = jgaRelationParser.parser(analysisStudyRelation, TypeEnum.JGA_ANALYSIS.getType(), TypeEnum.JGA_STUDY.getType());

        if(null == analysisStudyRecords) {
            log.error("analysisStudyRelation file is not exist.");
            System.exit(255);
        }

        jgaRelationDao.bulkInsert(analysisStudyRecords);

        List<Object[]> dataExperimentRecords = jgaRelationParser.parser(dataExperimentRelation, TypeEnum.DATA.getType(), TypeEnum.JGA_EXPERIMENT.getType());

        if(null == dataExperimentRecords) {
            log.error("dataExperimentRelation file is not exist.");
            System.exit(255);
        }

        jgaRelationDao.bulkInsert(dataExperimentRecords);

        List<Object[]> datasetAnalysisRecords = jgaRelationParser.parser(datasetAnalysisRelation, TypeEnum.DATASET.getType(), TypeEnum.JGA_ANALYSIS.getType());

        if(null == datasetAnalysisRecords) {
            log.error("datasetAnalysisRelation file is not exist.");
            System.exit(255);
        }

        jgaRelationDao.bulkInsert(datasetAnalysisRecords);

        List<Object[]> datasetDataRecords = jgaRelationParser.parser(datasetDataRelation, TypeEnum.DATASET.getType(), TypeEnum.DATA.getType());

        if(null == datasetDataRecords) {
            log.error("datasetDataRelation file is not exist.");
            System.exit(255);
        }

        jgaRelationDao.bulkInsert(datasetDataRecords);

        List<Object[]> datasetPolicyRecords = jgaRelationParser.parser(datasetPolicyRelation, TypeEnum.DATASET.getType(), TypeEnum.POLICY.getType());

        if(null == datasetPolicyRecords) {
            log.error("datasetPolicyRelation file is not exist.");
            System.exit(255);
        }

        jgaRelationDao.bulkInsert(datasetPolicyRecords);

        List<Object[]> experimentStudyRecords = jgaRelationParser.parser(experimentStudyRelation, TypeEnum.JGA_EXPERIMENT.getType(), TypeEnum.JGA_STUDY.getType());

        if(null == experimentStudyRecords) {
            log.error("experimentStudyRelation file is not exist.");
            System.exit(255);
        }

        jgaRelationDao.bulkInsert(experimentStudyRecords);

        List<Object[]> policyDacRecords = jgaRelationParser.parser(policyDacRelation, TypeEnum.POLICY.getType(), TypeEnum.DAC.getType());

        if(null == policyDacRecords) {
            log.error("policyDacRelation file is not exist.");
            System.exit(255);
        }

        jgaRelationDao.bulkInsert(policyDacRecords);

        log.info("Complete registering JGA's relation data to PostgreSQL");
    }

    /**
     * JGAの日付情報を登録する.
     */
    public void registerJgaDate() {
        log.info("Start registering JGA's date data to PostgreSQL");

        jgaDateDao.deleteAll();

        String file = this.config.file.path.jga + FileNameEnum.JGA_DATE.getFileName();
        List<Object[]> recordList = jgaDateParser.parser(file);

        int maximumRecord = this.config.other.maximumRecord;

        BulkHelper.extract(recordList, maximumRecord, _recordList -> {
            jgaDateDao.bulkInsert(_recordList);
        });

        log.info("Complete registering JGA's date data to PostgreSQL");
    }

    /**
     * SRAAccessions.tabのレコードからDRAの各メタデータの情報を登録するレコードに変換する.
     */
    private Object[] getRecord(String[] sraAccession, String timeStampFormat) {
        Object[] record = new Object[6];

        String hyphen = "-";

        try {
            String accession    = sraAccession[0];
            String status       = sraAccession[2];
            Timestamp updated   = hyphen.equals(sraAccession[3])
                    ? null
                    : new Timestamp(new SimpleDateFormat(timeStampFormat).parse(sraAccession[3]).getTime());
            Timestamp published = hyphen.equals(sraAccession[4])
                    ? null
                    : new Timestamp(new SimpleDateFormat(timeStampFormat).parse(sraAccession[4]).getTime());
            Timestamp received  = hyphen.equals(sraAccession[5])
                    ? null
                    : new Timestamp(new SimpleDateFormat(timeStampFormat).parse(sraAccession[5]).getTime());
            String visibility = sraAccession[8];

            record[0] = accession;
            record[1] = status;
            record[2] = updated;
            record[3] = published;
            record[4] = received;
            record[5] = visibility;
        } catch (ParseException e) {
            log.debug(e.getMessage());
            log.debug(Arrays.toString(sraAccession));

            return null;
        }

        return record;
    }

    /**
     * 関係情報を登録する形式に変換する.
     */
    private Object[] getRelation(String baseAccession, String targetAccession) {
        Object[] relation = new Object[2];
        relation[0] = baseAccession;
        relation[1] = targetAccession;

        return relation;
    }

    /**
     * DRAのメタデータの情報を登録する.
     */
    private void bulkInsertRecord(List<Object[]> recordList, int maximumRecord, TypeEnum type) {
        BulkHelper.extract(recordList, maximumRecord, _recordList -> {
            sraAccessionsDao.bulkInsert(type.toString(), _recordList);
        });
    }

    /**
     * DRAの関係情報を登録する.
     */
    private void bulkInsertRelation(List<Object[]> relationList, int maximumRecord, TypeEnum baseType, TypeEnum targetType) {
        BulkHelper.extract(relationList, maximumRecord, _relationList -> {
            sraAccessionsDao.bulkInsertRelation(baseType.toString(), targetType.toString(), _relationList);
        });
    }
}
