package com.ddbj.ld.service;

import com.ddbj.ld.common.BulkHelper;
import com.ddbj.ld.common.FileNameEnum;
import com.ddbj.ld.common.Settings;
import com.ddbj.ld.common.TypeEnum;
import com.ddbj.ld.dao.SRAAccessionsDao;
import com.ddbj.ld.parser.SRAAccessionsParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class SRAAccessionsService {
    private final Settings settings;
    private final SRAAccessionsParser sraAccessionsParser;
    private final SRAAccessionsDao sraAccessionsDao;

    public void registerSRAAccessions() {
        log.info("SRAAccessions.tab登録処理開始");

        String sraAccessionsTab = settings.getTsvPath() + FileNameEnum.SRA_ACCESSIONS.getFileName();

        List<String[]> sraAccessions = sraAccessionsParser.parser(sraAccessionsTab);

        List<Object[]> bioProjectRecordList = new ArrayList<>();
        // 値の重複を避けるために作成
        HashSet<String> bioProjectAccessionSet = new HashSet<>();
        List<Object[]> bioSampleRecordList = new ArrayList<>();

        List<Object[]> studyRecordList = new ArrayList<>();
        List<Object[]> sampleRecordList = new ArrayList<>();

        List<Object[]> submissionRecordList = new ArrayList<>();
        List<Object[]> analysisRecordList = new ArrayList<>();
        List<Object[]> experimentRecordList = new ArrayList<>();
        List<Object[]> runRecordList = new ArrayList<>();

        List<Object[]> bioProjectSubmissionRelationList = new ArrayList<>();
        List<Object[]> bioProjectStudyRelationList = new ArrayList<>();

        List<Object[]> bioSampleSampleRelationList = new ArrayList<>();
        // 重複回避用
        Map<String, Object[]> bioSampleSampleRelationMap = new HashMap<>();
        List<Object[]> submissionAnalysisRelationList = new ArrayList<>();
        List<Object[]> submissionExperimentRelationList = new ArrayList<>();
        List<Object[]> experimentRunRelationList = new ArrayList<>();
        List<Object[]> bioSampleExperimentRelationList = new ArrayList<>();
        List<Object[]> runBioSampleRelationList = new ArrayList<>();
        List<Object[]> sampleExperimentRelationList = new ArrayList<>();
        List<Object[]> studySubmissionRelationList = new ArrayList<>();

        String timeStampFormat = settings.getTimeStampFormat();


        int recordSize = sraAccessions.size() - 1;
        String mode = settings.getMode();

        if(mode.equals("Development")) {
            recordSize = sraAccessions.size() > settings.getDevelopmentRecordNumber()
                    ? settings.getDevelopmentRecordNumber()
                    : sraAccessions.size();
        }

        for(int i = 0; i < recordSize; i++) {
            String[] sraAccession = sraAccessions.get(i);
            TypeEnum type   = TypeEnum.getSraAccessionType(sraAccession[6]);
            Object[] record = getRecord(sraAccession, timeStampFormat);

            if(record == null) {
                continue;
            }

            switch (type) {
                case STUDY:
                    studyRecordList.add(record);

                    if(!"live".equals(record[1])) {
                        continue;
                    }

                    bioProjectAccessionSet.add(sraAccession[18]);

                    Object[] bioProjectStudyRelation = getRelation(sraAccession[18], sraAccession[0]);
                    Object[] studySubmissionRelation = getRelation(sraAccession[0], sraAccession[1]);
                    bioProjectStudyRelationList.add(bioProjectStudyRelation);
                    studySubmissionRelationList.add(studySubmissionRelation);

                    Object[] bioProjectSubmissionRelation = new Object[2];
                    bioProjectSubmissionRelation[0] = sraAccession[18];
                    bioProjectSubmissionRelation[1] = sraAccession[1];
                    bioProjectSubmissionRelationList.add(bioProjectSubmissionRelation);

                    break;
                case SAMPLE:
                    sampleRecordList.add(record);

                    if(!"live".equals(record[1])) {
                        continue;
                    }

                    Object [] bioSampleRecord = new Object[6];
                    bioSampleRecord[0] = sraAccession[17];
                    bioSampleRecordList.add(bioSampleRecord);

                    Object[] bioSampleSampleRelation = getRelation(sraAccession[17], sraAccession[0]);

                    bioSampleSampleRelationMap.put(sraAccession[17], bioSampleSampleRelation);
                    break;
                case SUBMISSION:
                    submissionRecordList.add(record);

                    break;
                case EXPERIMENT:
                    experimentRecordList.add(record);

                    if(!"live".equals(record[1])) {
                        continue;
                    }

                    Object[] submissionExperimentRelation = getRelation(sraAccession[1], sraAccession[0]);
                    submissionExperimentRelationList.add(submissionExperimentRelation);

                    Object[] bioSampleExperimentRelation = getRelation(sraAccession[17], sraAccession[0]);
                    Object[] sampleExperimentRelation = getRelation(sraAccession[11], sraAccession[0]);

                    bioSampleExperimentRelationList.add(bioSampleExperimentRelation);
                    sampleExperimentRelationList.add(sampleExperimentRelation);

                    break;
                case ANALYSIS:
                    analysisRecordList.add(record);

                    if(!"live".equals(record[1])) {
                        continue;
                    }

                    Object[] submissionAnalysisRelation = getRelation(sraAccession[1], sraAccession[0]);
                    submissionAnalysisRelationList.add(submissionAnalysisRelation);

                    break;
                case RUN:
                    runRecordList.add(record);

                    if(!"live".equals(record[1])) {
                        continue;
                    }

                    Object[] experimentRunRelation = getRelation(sraAccession[10], sraAccession[0]);
                    Object[] runBioSampleRelation = getRelation(sraAccession[0], sraAccession[17]);
                    experimentRunRelationList.add(experimentRunRelation);
                    runBioSampleRelationList.add(runBioSampleRelation);

                    break;
                default:
            }
        }

        bioProjectAccessionSet.forEach(accession -> {
            Object[] record = new Object[6];
            record[0] = accession;

            bioProjectRecordList.add(record);
        });

        int maximumRecord = settings.getMaximumRecord();

        bulkInsertRecord(bioProjectRecordList, maximumRecord, TypeEnum.BIO_PROJECT);

        log.info("bioproject登録完了:" + bioProjectRecordList.size() + "件");

        for(Map.Entry<String, Object[]> entry : bioSampleSampleRelationMap.entrySet()) {
            bioSampleSampleRelationList.add(entry.getValue());
        }

        bulkInsertRecord(bioSampleRecordList, maximumRecord, TypeEnum.BIO_SAMPLE);

        log.info("biosample登録完了:" + bioSampleRecordList.size() + "件");

        bulkInsertRecord(studyRecordList, maximumRecord, TypeEnum.STUDY);

        log.info("study登録完了:" + studyRecordList.size() + "件");

        bulkInsertRecord(sampleRecordList, maximumRecord, TypeEnum.SAMPLE);

        log.info("sample登録完了:" + sampleRecordList.size() + "件");

        bulkInsertRecord(submissionRecordList, maximumRecord, TypeEnum.SUBMISSION);

        log.info("submission登録完了:" + submissionRecordList.size() + "件");

        bulkInsertRecord(analysisRecordList, maximumRecord, TypeEnum.ANALYSIS);

        log.info("analysis登録完了:" + analysisRecordList.size() + "件");

        bulkInsertRecord(experimentRecordList, maximumRecord, TypeEnum.EXPERIMENT);

        log.info("experiment登録完了:" + analysisRecordList.size() + "件");

        bulkInsertRecord(runRecordList, maximumRecord, TypeEnum.RUN);

        log.info("run登録完了:" + runRecordList.size() + "件");

        bulkInsertRelation(bioProjectSubmissionRelationList, maximumRecord, TypeEnum.BIO_PROJECT, TypeEnum.SUBMISSION);

        log.info("bioproject_submission登録完了:" + bioProjectSubmissionRelationList.size() + "件");

        bulkInsertRelation(bioProjectStudyRelationList, maximumRecord, TypeEnum.BIO_PROJECT, TypeEnum.STUDY);

        log.info("bioproject_study登録完了:" + bioProjectStudyRelationList.size() + "件");

        bulkInsertRelation(submissionAnalysisRelationList, maximumRecord, TypeEnum.SUBMISSION, TypeEnum.ANALYSIS);

        log.info("submission_analysis登録完了:" + submissionAnalysisRelationList.size() + "件");

        bulkInsertRelation(submissionExperimentRelationList, maximumRecord, TypeEnum.SUBMISSION, TypeEnum.EXPERIMENT);

        log.info("submission_experiment登録完了:" + submissionExperimentRelationList.size() + "件");

        bulkInsertRelation(experimentRunRelationList, maximumRecord, TypeEnum.EXPERIMENT, TypeEnum.RUN);

        log.info("experiment_run登録完了:" + experimentRunRelationList.size() + "件");

        bulkInsertRelation(bioSampleSampleRelationList, maximumRecord, TypeEnum.BIO_SAMPLE, TypeEnum.SAMPLE);

        log.info("biosample_sample登録完了:" + bioSampleSampleRelationList.size() + "件");

        bulkInsertRelation(bioSampleExperimentRelationList, maximumRecord, TypeEnum.BIO_SAMPLE, TypeEnum.EXPERIMENT);

        log.info("biosample_experiment登録完了:" + bioSampleSampleRelationList.size() + "件");

        bulkInsertRelation(runBioSampleRelationList, maximumRecord, TypeEnum.RUN, TypeEnum.BIO_SAMPLE);

        log.info("run_biosample登録完了:" + bioSampleSampleRelationList.size() + "件");

        bulkInsertRelation(sampleExperimentRelationList, maximumRecord, TypeEnum.SAMPLE, TypeEnum.EXPERIMENT);

        log.info("sample_experiment登録完了:" + bioSampleSampleRelationList.size() + "件");

        bulkInsertRelation(studySubmissionRelationList, maximumRecord, TypeEnum.STUDY, TypeEnum.SUBMISSION);

        log.info("study_submission登録完了:" + bioSampleSampleRelationList.size() + "件");

        log.info("SRAAccessions.tab登録処理が完了");
    }

    private Object[] getRecord(String[] sraAccession, String timeStampFormat) {
        Object[] record = new Object[6];

        try {
            String accession    = sraAccession[0];
            String status       = sraAccession[2];
            Timestamp updated   = "-".equals(sraAccession[3])
                    ? null
                    : new Timestamp(new SimpleDateFormat(timeStampFormat).parse(sraAccession[3]).getTime());
            Timestamp published = "-".equals(sraAccession[4])
                    ? null
                    : new Timestamp(new SimpleDateFormat(timeStampFormat).parse(sraAccession[4]).getTime());
            Timestamp received  = "-".equals(sraAccession[5])
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

    private Object[] getRelation(String baseAccession, String targetAccession) {
        Object[] relation = new Object[2];
        relation[0] = baseAccession;
        relation[1] = targetAccession;

        return relation;
    }

    private void bulkInsertRecord(List<Object[]> recordList, int maximumRecord, TypeEnum type) {
        BulkHelper.extract(recordList, maximumRecord, _recordList -> {
            sraAccessionsDao.bulkInsert(type.getType(), _recordList);
        });
    }

    private void bulkInsertRelation(List<Object[]> relationList, int maximumRecord, TypeEnum baseType, TypeEnum targetType) {
        BulkHelper.extract(relationList, maximumRecord, _relationList -> {
            sraAccessionsDao.bulkInsertRelation(baseType.getType(), targetType.getType(), _relationList);
        });
    }
}
