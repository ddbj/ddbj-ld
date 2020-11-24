package com.ddbj.ld.app.transact.usecase;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.parser.jga.JgaDateParser;
import com.ddbj.ld.app.core.parser.jga.JgaRelationParser;
import com.ddbj.ld.app.transact.dao.jga.JgaDateDao;
import com.ddbj.ld.app.transact.dao.jga.JgaRelationDao;
import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.annotation.UseCase;
import com.ddbj.ld.common.constants.FileNameEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.helper.BulkHelper;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * PostgreSQLに関する処理を行うユースケースクラス.
 */
@UseCase
@AllArgsConstructor
@Slf4j
public class RelationUseCase {
    private final ConfigSet config;

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

        // 重複回避用
        HashSet<String> bioProjectAccessionSet = new HashSet<>();

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

        // 重複回避用
        Map<String, Object[]> bioSampleSampleRelationMap = new HashMap<>();
        Map<String, Object[]> submissionAnalysisRelationMap = new HashMap<>();
        Map<String, Object[]> submissionExperimentRelationMap = new HashMap<>();
        Map<String, Object[]> experimentRunRelationMap = new HashMap<>();
        Map<String, Object[]> bioSampleExperimentRelationMap = new HashMap<>();
        Map<String, Object[]> runBioSampleRelationMap = new HashMap<>();
        Map<String, Object[]> sampleExperimentRelationMap = new HashMap<>();
        Map<String, Object[]> studySubmissionRelationMap = new HashMap<>();

        String timeStampFormat = this.config.other.timestampFormat;
        String sraAccessionsTab = this.config.file.path.sra + FileNameEnum.SRA_ACCESSIONS.getFileName();

        try(Stream<String> stream = Files.lines(Paths.get(sraAccessionsTab))) {
            stream.forEach(line -> {
                if (line.matches("^(Accession\t).*")) {
                    return;
                }

                TsvParserSettings settings = new TsvParserSettings();
                TsvParser parser = new TsvParser(settings);
                String[] sraAccession = parser.parseLine(line);

                Object[] record = getRecord(sraAccession, timeStampFormat);
                if(record == null) {
                    return;
                }

                String targetStatus = "live";
                TypeEnum type   = TypeEnum.getType(sraAccession[6]);
                switch (type) {
                    case STUDY:
                        studyRecordList.add(record);

                        if(!targetStatus.equals(record[1])) {
                            return;
                        }

                        // BioProject
                        bioProjectAccessionSet.add(sraAccession[18]);

                        // BioProject Study
                        Object[] bioProjectStudyRelation = getRelation(sraAccession[18], sraAccession[0]);
                        bioProjectStudyRelationList.add(bioProjectStudyRelation);

                        // Study Submissison
                        Object[] studySubmissionRelation = getRelation(sraAccession[0], sraAccession[1]);
                        studySubmissionRelationMap.put(sraAccession[0], studySubmissionRelation);

                        // BioProject Submission
                        Object[] bioProjectSubmissionRelation = getRelation(sraAccession[18], sraAccession[1]);;
                        bioProjectSubmissionRelationList.add(bioProjectSubmissionRelation);

                        break;
                    case SAMPLE:
                        sampleRecordList.add(record);

                        if(!targetStatus.equals(record[1])) {
                            return;
                        }

                        // BioSample
                        Object [] bioSampleRecord = new Object[6];
                        bioSampleRecord[0] = sraAccession[17];
                        bioSampleRecordMap.put(sraAccession[17], bioSampleRecord);

                        // BioSample Sample
                        Object[] bioSampleSampleRelation = getRelation(sraAccession[17], sraAccession[0]);
                        bioSampleSampleRelationMap.put(sraAccession[17], bioSampleSampleRelation);

                        break;
                    case SUBMISSION:
                        submissionRecordList.add(record);

                        break;
                    case EXPERIMENT:
                        experimentRecordList.add(record);

                        if(!targetStatus.equals(record[1])) {
                            return;
                        }

                        // Submission Experiment
                        Object[] submissionExperimentRelation = getRelation(sraAccession[1], sraAccession[0]);
                        submissionExperimentRelationMap.put(sraAccession[1], submissionExperimentRelation);

                        // BioSample Experiment
                        Object[] bioSampleExperimentRelation = getRelation(sraAccession[17], sraAccession[0]);
                        bioSampleExperimentRelationMap.put(sraAccession[17], bioSampleExperimentRelation);

                        // Sample Experiment
                        Object[] sampleExperimentRelation = getRelation(sraAccession[11], sraAccession[0]);
                        sampleExperimentRelationMap.put(sraAccession[11], sampleExperimentRelation);

                        break;
                    case ANALYSIS:
                        analysisRecordList.add(record);

                        if(!targetStatus.equals(record[1])) {
                            return;
                        }

                        // Submission Analysis
                        Object[] submissionAnalysisRelation = getRelation(sraAccession[1], sraAccession[0]);
                        submissionAnalysisRelationMap.put(sraAccession[1], submissionAnalysisRelation);

                        break;
                    case RUN:
                        runRecordList.add(record);

                        if(!targetStatus.equals(record[1])) {
                            return;
                        }

                        // Experiment Run
                        Object[] experimentRunRelation = getRelation(sraAccession[10], sraAccession[0]);
                        experimentRunRelationMap.put(sraAccession[10], experimentRunRelation);

                        // Run BioSample
                        if(sraAccession.length > 17) {
                            Object[] runBioSampleRelation  = getRelation(sraAccession[0], sraAccession[17]);
                            runBioSampleRelationMap.put(sraAccession[0], runBioSampleRelation);
                        }

                        break;
                    default:
                }
            });

        } catch (IOException e) {
            log.debug(e.getMessage());
            return;
        }

        // TBLE : bioproject
        List<Object[]> bioProjectRecordList = new ArrayList<>();
        bioProjectAccessionSet.forEach(accession -> {
            Object[] record = new Object[6];
            record[0] = accession;

            bioProjectRecordList.add(record);
        });

        int maximumRecord = this.config.other.maximumRecord;
        bulkInsertRecord(bioProjectRecordList, maximumRecord, TypeEnum.BIOPROJECT);
        log.info("Complete bioproject:" + bioProjectRecordList.size());

        // TBLE : biosample
        List<Object[]> bioSampleRecordList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : bioSampleRecordMap.entrySet()) {
            bioSampleRecordList.add(entry.getValue());
        }
        bulkInsertRecord(bioSampleRecordList, maximumRecord, TypeEnum.BIOSAMPLE);
        log.info("Complete biosample:" + bioSampleRecordList.size());

        // TBLE : study
        bulkInsertRecord(studyRecordList, maximumRecord, TypeEnum.STUDY);
        log.info("Complete study:" + studyRecordList.size());

        // TBLE : sample
        bulkInsertRecord(sampleRecordList, maximumRecord, TypeEnum.SAMPLE);
        log.info("Complete sample:" + sampleRecordList.size());

        // TBLE : submission
        bulkInsertRecord(submissionRecordList, maximumRecord, TypeEnum.SUBMISSION);
        log.info("Complete submission:" + submissionRecordList.size());

        // TBLE : analysis
        bulkInsertRecord(analysisRecordList, maximumRecord, TypeEnum.ANALYSIS);
        log.info("Complete analysis:" + analysisRecordList.size());

        // TBLE : experiment
        bulkInsertRecord(experimentRecordList, maximumRecord, TypeEnum.EXPERIMENT);
        log.info("Complete experiment:" + experimentRecordList.size());

        // TBLE : run
        bulkInsertRecord(runRecordList, maximumRecord, TypeEnum.RUN);
        log.info("Complete run:" + runRecordList.size());

        // TBLE : bioproject_submission
        bulkInsertRelation(bioProjectSubmissionRelationList, maximumRecord, TypeEnum.BIOPROJECT, TypeEnum.SUBMISSION);
        log.info("Complete bioproject_submission:" + bioProjectSubmissionRelationList.size());

        // TBLE : bioproject_study
        bulkInsertRelation(bioProjectStudyRelationList, maximumRecord, TypeEnum.BIOPROJECT, TypeEnum.STUDY);
        log.info("Complete bioproject_study:" + bioProjectStudyRelationList.size());

        // TBLE : submission_analysis
        List<Object[]> submissionAnalysisRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : submissionAnalysisRelationMap.entrySet()) {
            submissionAnalysisRelationList.add(entry.getValue());
        }
        bulkInsertRelation(submissionAnalysisRelationList, maximumRecord, TypeEnum.SUBMISSION, TypeEnum.ANALYSIS);
        log.info("Complete submission_analysis:" + submissionAnalysisRelationList.size());

        // TBLE : submission_experiment
        List<Object[]> submissionExperimentRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : submissionExperimentRelationMap.entrySet()) {
            submissionExperimentRelationList.add(entry.getValue());
        }
        bulkInsertRelation(submissionExperimentRelationList, maximumRecord, TypeEnum.SUBMISSION, TypeEnum.EXPERIMENT);
        log.info("Complete submission_experiment:" + submissionExperimentRelationList.size());

        // TBLE : experiment_run
        List<Object[]> experimentRunRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : experimentRunRelationMap.entrySet()) {
            experimentRunRelationList.add(entry.getValue());
        }
        bulkInsertRelation(experimentRunRelationList, maximumRecord, TypeEnum.EXPERIMENT, TypeEnum.RUN);
        log.info("Complete experiment_run:" + experimentRunRelationList.size());

        // TBLE : biosample_sample
        List<Object[]> bioSampleSampleRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : bioSampleSampleRelationMap.entrySet()) {
            bioSampleSampleRelationList.add(entry.getValue());
        }
        bulkInsertRelation(bioSampleSampleRelationList, maximumRecord, TypeEnum.BIOSAMPLE, TypeEnum.SAMPLE);
        log.info("Complete biosample_sample:" + bioSampleSampleRelationList.size());

        // TBLE : biosample_experiment
        List<Object[]> bioSampleExperimentRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : bioSampleExperimentRelationMap.entrySet()) {
            bioSampleExperimentRelationList.add(entry.getValue());
        }
        bulkInsertRelation(bioSampleExperimentRelationList, maximumRecord, TypeEnum.BIOSAMPLE, TypeEnum.EXPERIMENT);
        log.info("Complete biosample_experiment:" + bioSampleExperimentRelationList.size());

        // TBLE : run_biosample
        List<Object[]> runBioSampleRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : runBioSampleRelationMap.entrySet()) {
            runBioSampleRelationList.add(entry.getValue());
        }
        bulkInsertRelation(runBioSampleRelationList, maximumRecord, TypeEnum.RUN, TypeEnum.BIOSAMPLE);
        log.info("Complete run_biosample:" + runBioSampleRelationList.size());

        // TBLE : sample_experiment
        List<Object[]> sampleExperimentRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : sampleExperimentRelationMap.entrySet()) {
            sampleExperimentRelationList.add(entry.getValue());
        }
        bulkInsertRelation(sampleExperimentRelationList, maximumRecord, TypeEnum.SAMPLE, TypeEnum.EXPERIMENT);
        log.info("Complete sample_experiment:" + sampleExperimentRelationList.size());

        // TBLE : study_submission
        List<Object[]> studySubmissionRelationList = new ArrayList<>();
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

        this.jgaRelationDao.deleteAll();

        String path = !date.equals("") ? config.file.path.jga + "." + date : config.file.path.jga;
        var analysisExperimentRelation = path + FileNameEnum.ANALYSIS_EXPERIMENT_RELATION.getFileName();
        var analysisStudyRelation      = path + FileNameEnum.ANALYSIS_STUDY_RELATION.getFileName();
        var dataExperimentRelation     = path + FileNameEnum.DATA_EXPERIMENT_RELATION.getFileName();
        var datasetAnalysisRelation    = path + FileNameEnum.DATASET_ANALYSIS_RELATION.getFileName();
        var datasetDataRelation        = path + FileNameEnum.DATASET_DATA_RELATION.getFileName();
        var datasetPolicyRelation      = path + FileNameEnum.DATASET_POLICY_RELATION.getFileName();
        var experimentStudyRelation    = path + FileNameEnum.EXPERIMENT_STUDY_RELATION.getFileName();
        var policyDacRelation          = path + FileNameEnum.POLICY_DAC_RELATION.getFileName();

        var analysisExperimentRecords = this.jgaRelationParser.parse(analysisExperimentRelation, TypeEnum.JGA_ANALYSIS.getType(), TypeEnum.JGA_EXPERIMENT.getType());

        if(null == analysisExperimentRecords) {
            log.error("analysisExperimentRelation file is not exist.");
            System.exit(255);
        }

        this.jgaRelationDao.bulkInsert(analysisExperimentRecords);

        var analysisStudyRecords = this.jgaRelationParser.parse(analysisStudyRelation, TypeEnum.JGA_ANALYSIS.getType(), TypeEnum.JGA_STUDY.getType());

        if(null == analysisStudyRecords) {
            log.error("analysisStudyRelation file is not exist.");
            System.exit(255);
        }

        this.jgaRelationDao.bulkInsert(analysisStudyRecords);

        var dataExperimentRecords = this.jgaRelationParser.parse(dataExperimentRelation, TypeEnum.JGA_DATA.getType(), TypeEnum.JGA_EXPERIMENT.getType());

        if(null == dataExperimentRecords) {
            log.error("dataExperimentRelation file is not exist.");
            System.exit(255);
        }

        this.jgaRelationDao.bulkInsert(dataExperimentRecords);

        var datasetAnalysisRecords = this.jgaRelationParser.parse(datasetAnalysisRelation, TypeEnum.JGA_DATASET.getType(), TypeEnum.JGA_ANALYSIS.getType());

        if(null == datasetAnalysisRecords) {
            log.error("datasetAnalysisRelation file is not exist.");
            System.exit(255);
        }

        this.jgaRelationDao.bulkInsert(datasetAnalysisRecords);

        var datasetDataRecords = this.jgaRelationParser.parse(datasetDataRelation, TypeEnum.JGA_DATASET.getType(), TypeEnum.JGA_DATA.getType());

        if(null == datasetDataRecords) {
            log.error("datasetDataRelation file is not exist.");
            System.exit(255);
        }

        this.jgaRelationDao.bulkInsert(datasetDataRecords);

        var datasetPolicyRecords = this.jgaRelationParser.parse(datasetPolicyRelation, TypeEnum.JGA_DATASET.getType(), TypeEnum.JGA_POLICY.getType());

        if(null == datasetPolicyRecords) {
            log.error("datasetPolicyRelation file is not exist.");
            System.exit(255);
        }

        this.jgaRelationDao.bulkInsert(datasetPolicyRecords);

        var experimentStudyRecords = this.jgaRelationParser.parse(experimentStudyRelation, TypeEnum.JGA_EXPERIMENT.getType(), TypeEnum.JGA_STUDY.getType());

        if(null == experimentStudyRecords) {
            log.error("experimentStudyRelation file is not exist.");
            System.exit(255);
        }

        this.jgaRelationDao.bulkInsert(experimentStudyRecords);

        var policyDacRecords = this.jgaRelationParser.parse(policyDacRelation, TypeEnum.JGA_POLICY.getType(), TypeEnum.JGA_DAC.getType());

        if(null == policyDacRecords) {
            log.error("policyDacRelation file is not exist.");
            System.exit(255);
        }

        this.jgaRelationDao.bulkInsert(policyDacRecords);

        log.info("Complete registering JGA's relation data to PostgreSQL");
    }

    /**
     * JGAの日付情報を登録する.
     */
    public void registerJgaDate() {
        log.info("Start registering JGA's date data to PostgreSQL");

        this.jgaDateDao.deleteAll();

        var file       = this.config.file.path.jga + FileNameEnum.JGA_DATE.getFileName();
        var recordList = this.jgaDateParser.parse(file);

        var maximumRecord = this.config.other.maximumRecord;

        BulkHelper.extract(recordList, maximumRecord, _recordList -> {
            this.jgaDateDao.bulkInsert(_recordList);
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
