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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * PostgreSQLに関する処理を行うユースケースクラス.
 */
@UseCase
@RequiredArgsConstructor
@Slf4j
public class RelationUseCase {
    private final ConfigSet config;

    private final JgaRelationParser jgaRelationParser;
    private final JgaDateParser jgaDateParser;

    private final SRAAccessionsDao sraAccessionsDao;
    private final JgaRelationDao jgaRelationDao;
    private final JgaDateDao jgaDateDao;

    // META データ　ID LIST用
    private List<Object[]> bioProjectRecordList = new ArrayList<>();
    private List<Object[]> bioSampleRecordList = new ArrayList<>();
    private List<Object[]> studyRecordList = new ArrayList<>();
    private List<Object[]> sampleRecordList = new ArrayList<>();
    private List<Object[]> submissionRecordList = new ArrayList<>();
    private List<Object[]> analysisRecordList = new ArrayList<>();
    private List<Object[]> experimentRecordList = new ArrayList<>();
    private List<Object[]> runRecordList = new ArrayList<>();

    // Relation TABLE用
    private Map<String, Object[]> bioSampleSampleRelationMap = new HashMap<>();
    private Map<String, Object[]> submissionAnalysisRelationMap = new HashMap<>();
    private Map<String, Object[]> submissionExperimentRelationMap = new HashMap<>();
    private Map<String, Object[]> experimentRunRelationMap = new HashMap<>();
    private Map<String, Object[]> bioSampleExperimentRelationMap = new HashMap<>();
    private Map<String, Object[]> runBioSampleRelationMap = new HashMap<>();
    private Map<String, Object[]> sampleExperimentRelationMap = new HashMap<>();
    private Map<String, Object[]> studySubmissionRelationMap = new HashMap<>();

    // insert Counter
    private int bioprojectCnt = 0;
    private int biosampleCnt = 0;
    private int studyCnt = 0;
    private int sampleCnt = 0;
    private int submissionCnt = 0;
    private int analysisCnt = 0;
    private int experimentCnt = 0;
    private int runCnt = 0;
    private int bioprojectSubmissionCnt = 0;
    private int bioprojectStudyCnt = 0;
    private int submissionAnalysisCnt = 0;
    private int submissionExperimentCnt = 0;
    private int experimentRunCnt = 0;
    private int biosampleSampleCnt = 0;
    private int biosampleExperimentCnt = 0;
    private int runBiosampleCnt = 0;
    private int sampleExperimentCnt = 0;
    private int studySubmissionCnt = 0;

    /**
     * BioProject, BioSample, DRAの関係情報をSRAAccessions.tabから登録する.
     */
    public void registerSRARelation(String date) {
        log.info("Start registering BioProject And BioSamle, DRA's relation data to PostgreSQL");

        // TODO Experimentを経由して取得
        List<Object[]> bioProjectSubmissionRelationList = new ArrayList<>();
        // TODO Studyのレコードから取得
        List<Object[]> bioProjectStudyRelationList = new ArrayList<>();

        // 重複回避用
        HashSet<String> bioProjectAccessionSet = new HashSet<>();
        HashSet<String> bioSampleAccessionSet = new HashSet<>();

        var path = !date.equals("") ? config.file.path.sra + "." + date : config.file.path.sra;
        String sraAccessionsTab = path + FileNameEnum.SRA_ACCESSIONS.getFileName();

        try(BufferedReader reader = new BufferedReader(new FileReader(sraAccessionsTab))) {
            int maximumRecord = config.other.maximumRecord;
            String line;
            int insertCnt = 0;
            while ((line = reader.readLine()) != null) {
                if (line.matches("^(Accession\t).*")) {
                    continue;
                }

                TsvParserSettings settings = new TsvParserSettings();
                TsvParser parser = new TsvParser(settings);
                String[] sraAccession = parser.parseLine(line);

                // TODO: Submissionの時はskipしなくていいのか
                String targetStatus = "live";
                if (!targetStatus.equals(sraAccession[2])) {
                    continue;
                }

                if(getRecord(sraAccession) == null) {
                    continue;
                }

                TypeEnum type = TypeEnum.getType(sraAccession[6]);
                switch (type) {
                    case STUDY:
                        createBioProjectData(bioProjectSubmissionRelationList, bioProjectStudyRelationList, bioProjectAccessionSet, sraAccession);

                        break;
                    case SAMPLE:
                        createBioSampleData(bioSampleAccessionSet, sraAccession);

                        break;
                    case SUBMISSION:
                        createSubmissionData(sraAccession);

                        break;
                    case EXPERIMENT:
                        createExperimentData(sraAccession);

                        break;
                    case ANALYSIS:
                        createAnalysisData(sraAccession);

                        break;
                    case RUN:
                        createRunData(sraAccession);

                        break;
                    default:
                }
                insertCnt++;

                // 1000件分処理したらInsertする
                if (insertCnt >= maximumRecord || !reader.ready()) {
                    // insert data
                    insertMetaList(bioProjectAccessionSet, bioSampleAccessionSet);
                    insertRelation(bioProjectSubmissionRelationList, bioProjectStudyRelationList);

                    // clear temporary data
                    clearData();
                    bioProjectSubmissionRelationList = new ArrayList<>();
                    bioProjectStudyRelationList = new ArrayList<>();
                    bioProjectAccessionSet = new HashSet<>();
                    bioSampleAccessionSet = new HashSet<>();
                    insertCnt = 0;
                }

            }
        } catch (IOException e) {
            log.debug(e.getMessage());
            return;
        }

        printInsertCount();

        log.info("Complete registering BioProject And BioSamle, DRA's relation data to PostgreSQL");
    }

    private void clearData() {
        // META データ　ID LIST用
        bioProjectRecordList = new ArrayList<>();
        bioSampleRecordList = new ArrayList<>();
        studyRecordList = new ArrayList<>();
        sampleRecordList = new ArrayList<>();
        submissionRecordList = new ArrayList<>();
        analysisRecordList = new ArrayList<>();
        experimentRecordList = new ArrayList<>();
        runRecordList = new ArrayList<>();

        // Relation TABLE用
        bioSampleSampleRelationMap = new HashMap<>();
        submissionAnalysisRelationMap = new HashMap<>();
        submissionExperimentRelationMap = new HashMap<>();
        experimentRunRelationMap = new HashMap<>();
        bioSampleExperimentRelationMap = new HashMap<>();
        runBioSampleRelationMap = new HashMap<>();
        sampleExperimentRelationMap = new HashMap<>();
        studySubmissionRelationMap = new HashMap<>();
    }

    private void createRunData(String[] sraAccession) {
        runRecordList.add(getRecord(sraAccession));

        // Experiment Run
        Object[] experimentRunRelation = getRelation(sraAccession[10], sraAccession[0]);
        experimentRunRelationMap.put(sraAccession[10], experimentRunRelation);

        // Run BioSample
        if(sraAccession.length > 17) {
            Object[] runBioSampleRelation  = getRelation(sraAccession[0], sraAccession[17]);
            runBioSampleRelationMap.put(sraAccession[0], runBioSampleRelation);
        }
    }

    private void createAnalysisData(String[] sraAccession) {
        analysisRecordList.add(getRecord(sraAccession));

        // Submission Analysis
        Object[] submissionAnalysisRelation = getRelation(sraAccession[1], sraAccession[0]);
        submissionAnalysisRelationMap.put(sraAccession[1], submissionAnalysisRelation);
    }

    private void createExperimentData(String[] sraAccession) {
        experimentRecordList.add(getRecord(sraAccession));

        // Submission Experiment
        Object[] submissionExperimentRelation = getRelation(sraAccession[1], sraAccession[0]);
        submissionExperimentRelationMap.put(sraAccession[1], submissionExperimentRelation);

        // BioSample Experiment
        Object[] bioSampleExperimentRelation = getRelation(sraAccession[17], sraAccession[0]);
        bioSampleExperimentRelationMap.put(sraAccession[17], bioSampleExperimentRelation);

        // Sample Experiment
        Object[] sampleExperimentRelation = getRelation(sraAccession[11], sraAccession[0]);
        sampleExperimentRelationMap.put(sraAccession[11], sampleExperimentRelation);
    }

    private void createSubmissionData(String[] sraAccession) {
        submissionRecordList.add(getRecord(sraAccession));
    }

    private void createBioSampleData(HashSet<String> bioSampleAccessionSet, String[] sraAccession) {
        sampleRecordList.add(getRecord(sraAccession));

        // BioSample
        bioSampleAccessionSet.add(sraAccession[17]);

        // BioSample Sample
        Object[] bioSampleSampleRelation = getRelation(sraAccession[17], sraAccession[0]);
        bioSampleSampleRelationMap.put(sraAccession[17], bioSampleSampleRelation);
    }

    private void createBioProjectData(List<Object[]> bioProjectSubmissionRelationList, List<Object[]> bioProjectStudyRelationList, HashSet<String> bioProjectAccessionSet, String[] sraAccession) {
        studyRecordList.add(getRecord(sraAccession));

        // BioProject
        bioProjectAccessionSet.add(sraAccession[18]);

        // BioProject Study
        Object[] bioProjectStudyRelation = getRelation(sraAccession[18], sraAccession[0]);
        bioProjectStudyRelationList.add(bioProjectStudyRelation);

        // Study Submissison
        Object[] studySubmissionRelation = getRelation(sraAccession[0], sraAccession[1]);
        studySubmissionRelationMap.put(sraAccession[0], studySubmissionRelation);

        // BioProject Submission
        Object[] bioProjectSubmissionRelation = getRelation(sraAccession[18], sraAccession[1]);
        bioProjectSubmissionRelationList.add(bioProjectSubmissionRelation);
    }

    private void printInsertCount() {
        log.info("Complete bioproject:" + bioprojectCnt);
        log.info("Complete biosample:" + biosampleCnt);
        log.info("Complete study:" + studyCnt);
        log.info("Complete sample:" + sampleCnt);
        log.info("Complete submission:" + submissionCnt);
        log.info("Complete analysis:" + analysisCnt);
        log.info("Complete experiment:" + experimentCnt);
        log.info("Complete run:" + runCnt);

        log.info("Complete bioproject_submission:" + bioprojectSubmissionCnt);
        log.info("Complete bioproject_study:" + bioprojectStudyCnt);
        log.info("Complete submission_analysis:" + submissionAnalysisCnt);
        log.info("Complete submission_experiment:" + submissionExperimentCnt);
        log.info("Complete experiment_run:" + experimentRunCnt);
        log.info("Complete biosample_sample:" + biosampleSampleCnt);
        log.info("Complete biosample_experiment:" + biosampleExperimentCnt);
        log.info("Complete run_biosample:" + runBiosampleCnt);
        log.info("Complete sample_experiment:" + sampleExperimentCnt);
        log.info("Complete study_submission:" + studySubmissionCnt);
    }

    private void insertMetaList(HashSet<String> bioProjectAccessionSet, HashSet<String> bioSampleAccessionSet) {
        int maximumRecord = config.other.maximumRecord;

        // TBLE : bioproject
        bioProjectAccessionSet.forEach(accession -> {
            bioProjectRecordList.add(createRecord(accession));
        });
        bulkInsertRecord(bioProjectRecordList, maximumRecord, TypeEnum.BIOPROJECT);
        bioprojectCnt += bioProjectRecordList.size();

        // TBLE : biosample
        bioSampleAccessionSet.forEach(accession -> {
            bioSampleRecordList.add(createRecord(accession));

        });
        bulkInsertRecord(bioSampleRecordList, maximumRecord, TypeEnum.BIOSAMPLE);
        biosampleCnt += bioSampleRecordList.size();

        // TBLE : study
        bulkInsertRecord(studyRecordList, maximumRecord, TypeEnum.STUDY);
        studyCnt += studyRecordList.size();

        // TBLE : sample
        bulkInsertRecord(sampleRecordList, maximumRecord, TypeEnum.SAMPLE);
        sampleCnt += sampleRecordList.size();

        // TBLE : submission
        bulkInsertRecord(submissionRecordList, maximumRecord, TypeEnum.SUBMISSION);
        submissionCnt += submissionRecordList.size();

        // TBLE : analysis
        bulkInsertRecord(analysisRecordList, maximumRecord, TypeEnum.ANALYSIS);
        analysisCnt += analysisRecordList.size();

        // TBLE : experiment
        bulkInsertRecord(experimentRecordList, maximumRecord, TypeEnum.EXPERIMENT);
        experimentCnt += experimentRecordList.size();

        // TBLE : run
        bulkInsertRecord(runRecordList, maximumRecord, TypeEnum.RUN);
        runCnt += runRecordList.size();
    }

    private void insertRelation(List<Object[]> bioProjectSubmissionRelationList, List<Object[]> bioProjectStudyRelationList) {
        int maximumRecord = config.other.maximumRecord;

        // TBLE : bioproject_submission
        bulkInsertRelation(bioProjectSubmissionRelationList, maximumRecord, TypeEnum.BIOPROJECT, TypeEnum.SUBMISSION);
        bioprojectSubmissionCnt += bioProjectSubmissionRelationList.size();

        // TBLE : bioproject_study
        bulkInsertRelation(bioProjectStudyRelationList, maximumRecord, TypeEnum.BIOPROJECT, TypeEnum.STUDY);
        bioprojectStudyCnt += bioProjectStudyRelationList.size();

        // TBLE : submission_analysis
        List<Object[]> submissionAnalysisRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : submissionAnalysisRelationMap.entrySet()) {
            submissionAnalysisRelationList.add(entry.getValue());
        }
        bulkInsertRelation(submissionAnalysisRelationList, maximumRecord, TypeEnum.SUBMISSION, TypeEnum.ANALYSIS);
        submissionAnalysisCnt += submissionAnalysisRelationList.size();

        // TBLE : submission_experiment
        List<Object[]> submissionExperimentRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : submissionExperimentRelationMap.entrySet()) {
            submissionExperimentRelationList.add(entry.getValue());
        }
        bulkInsertRelation(submissionExperimentRelationList, maximumRecord, TypeEnum.SUBMISSION, TypeEnum.EXPERIMENT);
        submissionExperimentCnt += submissionExperimentRelationList.size();

        // TBLE : experiment_run
        List<Object[]> experimentRunRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : experimentRunRelationMap.entrySet()) {
            experimentRunRelationList.add(entry.getValue());
        }
        bulkInsertRelation(experimentRunRelationList, maximumRecord, TypeEnum.EXPERIMENT, TypeEnum.RUN);
        experimentRunCnt += experimentRunRelationList.size();

        // TBLE : biosample_sample
        List<Object[]> bioSampleSampleRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : bioSampleSampleRelationMap.entrySet()) {
            bioSampleSampleRelationList.add(entry.getValue());
        }
        bulkInsertRelation(bioSampleSampleRelationList, maximumRecord, TypeEnum.BIOSAMPLE, TypeEnum.SAMPLE);
        biosampleSampleCnt += bioSampleSampleRelationList.size();

        // TBLE : biosample_experiment
        List<Object[]> bioSampleExperimentRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : bioSampleExperimentRelationMap.entrySet()) {
            bioSampleExperimentRelationList.add(entry.getValue());
        }
        bulkInsertRelation(bioSampleExperimentRelationList, maximumRecord, TypeEnum.BIOSAMPLE, TypeEnum.EXPERIMENT);
        biosampleExperimentCnt += bioSampleExperimentRelationList.size();

        // TBLE : run_biosample
        List<Object[]> runBioSampleRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : runBioSampleRelationMap.entrySet()) {
            runBioSampleRelationList.add(entry.getValue());
        }
        bulkInsertRelation(runBioSampleRelationList, maximumRecord, TypeEnum.RUN, TypeEnum.BIOSAMPLE);
        runBiosampleCnt += runBioSampleRelationList.size();

        // TBLE : sample_experiment
        List<Object[]> sampleExperimentRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : sampleExperimentRelationMap.entrySet()) {
            sampleExperimentRelationList.add(entry.getValue());
        }
        bulkInsertRelation(sampleExperimentRelationList, maximumRecord, TypeEnum.SAMPLE, TypeEnum.EXPERIMENT);
        sampleExperimentCnt += sampleExperimentRelationList.size();

        // TBLE : study_submission
        List<Object[]> studySubmissionRelationList = new ArrayList<>();
        for(Map.Entry<String, Object[]> entry : studySubmissionRelationMap.entrySet()) {
            studySubmissionRelationList.add(entry.getValue());
        }
        bulkInsertRelation(studySubmissionRelationList, maximumRecord, TypeEnum.STUDY, TypeEnum.SUBMISSION);
        studySubmissionCnt += studySubmissionRelationList.size();
    }

    /**
     * JGAの関係情報を登録する.
     */
    public void registerJgaRelation(String date) {
        log.info("Start registering JGA's relation data to PostgreSQL");

        this.jgaRelationDao.deleteAll();

        String path = !date.equals("") ? config.file.path.jga + "." + date : config.file.path.jga;

        // analysis_experiment
        var analysisExperimentRelation = path + FileNameEnum.ANALYSIS_EXPERIMENT_RELATION.getFileName();
        var analysisExperimentRecords = this.jgaRelationParser.parse(analysisExperimentRelation, TypeEnum.JGA_ANALYSIS.getType(), TypeEnum.JGA_EXPERIMENT.getType());
        if(null == analysisExperimentRecords) {
            log.error("analysisExperimentRelation file is not exist.");
            System.exit(255);
        }
        this.jgaRelationDao.bulkInsert(analysisExperimentRecords);

        // analysis_study
        var analysisStudyRelation = path + FileNameEnum.ANALYSIS_STUDY_RELATION.getFileName();
        var analysisStudyRecords = this.jgaRelationParser.parse(analysisStudyRelation, TypeEnum.JGA_ANALYSIS.getType(), TypeEnum.JGA_STUDY.getType());
        if(null == analysisStudyRecords) {
            log.error("analysisStudyRelation file is not exist.");
            System.exit(255);
        }
        this.jgaRelationDao.bulkInsert(analysisStudyRecords);

        // data_experiment
        var dataExperimentRelation = path + FileNameEnum.DATA_EXPERIMENT_RELATION.getFileName();
        var dataExperimentRecords = this.jgaRelationParser.parse(dataExperimentRelation, TypeEnum.JGA_DATA.getType(), TypeEnum.JGA_EXPERIMENT.getType());
        if(null == dataExperimentRecords) {
            log.error("dataExperimentRelation file is not exist.");
            System.exit(255);
        }
        this.jgaRelationDao.bulkInsert(dataExperimentRecords);

        // dataset_analysis
        var datasetAnalysisRelation = path + FileNameEnum.DATASET_ANALYSIS_RELATION.getFileName();
        var datasetAnalysisRecords = this.jgaRelationParser.parse(datasetAnalysisRelation, TypeEnum.JGA_DATASET.getType(), TypeEnum.JGA_ANALYSIS.getType());
        if(null == datasetAnalysisRecords) {
            log.error("datasetAnalysisRelation file is not exist.");
            System.exit(255);
        }
        this.jgaRelationDao.bulkInsert(datasetAnalysisRecords);

        // dataset_data
        var datasetDataRelation = path + FileNameEnum.DATASET_DATA_RELATION.getFileName();
        var datasetDataRecords = this.jgaRelationParser.parse(datasetDataRelation, TypeEnum.JGA_DATASET.getType(), TypeEnum.JGA_DATA.getType());
        if(null == datasetDataRecords) {
            log.error("datasetDataRelation file is not exist.");
            System.exit(255);
        }
        this.jgaRelationDao.bulkInsert(datasetDataRecords);

        // dataset_policy
        var datasetPolicyRelation = path + FileNameEnum.DATASET_POLICY_RELATION.getFileName();
        var datasetPolicyRecords = this.jgaRelationParser.parse(datasetPolicyRelation, TypeEnum.JGA_DATASET.getType(), TypeEnum.JGA_POLICY.getType());
        if(null == datasetPolicyRecords) {
            log.error("datasetPolicyRelation file is not exist.");
            System.exit(255);
        }
        this.jgaRelationDao.bulkInsert(datasetPolicyRecords);

        // experiment_study
        var experimentStudyRelation = path + FileNameEnum.EXPERIMENT_STUDY_RELATION.getFileName();
        var experimentStudyRecords = this.jgaRelationParser.parse(experimentStudyRelation, TypeEnum.JGA_EXPERIMENT.getType(), TypeEnum.JGA_STUDY.getType());
        if(null == experimentStudyRecords) {
            log.error("experimentStudyRelation file is not exist.");
            System.exit(255);
        }
        this.jgaRelationDao.bulkInsert(experimentStudyRecords);

        // policy_dac
        var policyDacRelation = path + FileNameEnum.POLICY_DAC_RELATION.getFileName();
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
    public void registerJgaDate(String date) {
        log.info("Start registering JGA's date data to PostgreSQL");

        int maximumRecord = config.other.maximumRecord;

        this.jgaDateDao.deleteAll();
        var path = !date.equals("") ? config.file.path.jga + "." + date : config.file.path.jga;
        var file       = path + FileNameEnum.JGA_DATE.getFileName();
        var recordList = this.jgaDateParser.parse(file);

        BulkHelper.extract(recordList, maximumRecord, _recordList -> {
            this.jgaDateDao.bulkInsert(_recordList);
        });

        log.info("Complete registering JGA's date data to PostgreSQL");
    }

    /**
     * SRAAccessions.tabのレコードからDRAの各メタデータの情報を登録するレコードに変換する.
     */
    private Object[] getRecord(String[] sraAccession) {
        String timeStampFormat = config.other.timestampFormat;
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

    private Object[] createRecord(String str) {
        Object[] record = new Object[6];
        record[0] = str;

        return record;
    }
}
