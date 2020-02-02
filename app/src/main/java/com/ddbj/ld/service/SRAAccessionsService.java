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
import java.time.format.DateTimeFormatter;
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
        // 値の重複を避けるために作成
        Map<String, String> bioProjectSubmissionRelationMap = new HashMap<>();
        List<Object[]> bioSampleSampleRelationList = new ArrayList<>();
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
            TypeEnum type = TypeEnum.getSraAccessionType(sraAccession[6]);
            Object[] record = getRecord(sraAccession, timeStampFormat);

            if(record == null) {
                continue;
            }

            switch (type) {
                case STUDY:
                    studyRecordList.add(record);
                    bioProjectAccessionSet.add(sraAccession[18]);

                    // TODO suppressd
                    Object[] bioProjectStudyRelation = getRelation(sraAccession[18], sraAccession[0]);
                    Object[] studySubmissionRelation = getRelation(sraAccession[0], sraAccession[1]);
                    bioProjectStudyRelationList.add(bioProjectStudyRelation);
                    studySubmissionRelationList.add(studySubmissionRelation);
                    bioProjectSubmissionRelationMap.put(sraAccession[18], sraAccession[1]);

                    break;
                case SAMPLE:
                    sampleRecordList.add(record);
                    Object [] bioSampleRecord = new Object[5];
                    bioSampleRecord[0] = sraAccession[17];
                    bioSampleRecordList.add(bioSampleRecord);

                    // TODO suppressd
                    Object[] bioSampleSampleRelation = getRelation(sraAccession[17], sraAccession[0]);
                    bioSampleSampleRelationList.add(bioSampleSampleRelation);

                    break;
                case SUBMISSION:
                    submissionRecordList.add(record);

                    break;
                case EXPERIMENT:
                    experimentRecordList.add(record);

                    // TODO suppressd
                    Object[] submissionExperimentRelation = getRelation(sraAccession[1], sraAccession[0]);
                    Object[] bioSampleExperimentRelation = getRelation(sraAccession[17], sraAccession[0]);
                    Object[] sampleExperimentRelation = getRelation(sraAccession[11], sraAccession[0]);
                    submissionExperimentRelationList.add(submissionExperimentRelation);
                    bioSampleExperimentRelationList.add(bioSampleExperimentRelation);
                    sampleExperimentRelationList.add(sampleExperimentRelation);

                    break;
                case ANALYSIS:
                    analysisRecordList.add(record);

                    // TODO suppressd
                    Object[] submissionAnalysisRelation = getRelation(sraAccession[1], sraAccession[0]);
                    submissionAnalysisRelationList.add(submissionAnalysisRelation);

                    break;
                case RUN:
                    runRecordList.add(record);

                    // TODO suppressd
                    Object[] experimentRunRelation = getRelation(sraAccession[10], sraAccession[0]);
                    Object[] runBioSampleRelation = getRelation(sraAccession[0], sraAccession[17]);
                    experimentRunRelationList.add(experimentRunRelation);
                    runBioSampleRelationList.add(runBioSampleRelation);

                    break;
                default:
            }
        }

        bioProjectAccessionSet.forEach(accession -> {
            Object[] record = new Object[5];
            record[0] = accession;

            bioProjectRecordList.add(record);
        });

        for (Map.Entry<String, String> entry : bioProjectSubmissionRelationMap.entrySet()) {
            Object[] bioProjectSubmissionRelation = new Object[2];
            bioProjectSubmissionRelation[0] = entry.getKey();
            bioProjectSubmissionRelation[1] = entry.getValue();
            bioProjectSubmissionRelationList.add(bioProjectSubmissionRelation);
        }

        int maximumRecord = settings.getMaximumRecord();

        bulkInsertRecord(bioProjectRecordList, maximumRecord, TypeEnum.BIO_PROJECT);
        bulkInsertRecord(bioSampleRecordList, maximumRecord, TypeEnum.BIO_SAMPLE);
        bulkInsertRecord(studyRecordList, maximumRecord, TypeEnum.STUDY);
        bulkInsertRecord(sampleRecordList, maximumRecord, TypeEnum.SAMPLE);
        bulkInsertRecord(submissionRecordList, maximumRecord, TypeEnum.SUBMISSION);
        bulkInsertRecord(analysisRecordList, maximumRecord, TypeEnum.ANALYSIS);
        bulkInsertRecord(experimentRecordList, maximumRecord, TypeEnum.EXPERIMENT);
        bulkInsertRecord(runRecordList, maximumRecord, TypeEnum.RUN);

        bulkInsertRelation(bioProjectSubmissionRelationList, maximumRecord, TypeEnum.BIO_PROJECT, TypeEnum.SUBMISSION);
        bulkInsertRelation(bioProjectStudyRelationList, maximumRecord, TypeEnum.BIO_PROJECT, TypeEnum.STUDY);
        bulkInsertRelation(submissionAnalysisRelationList, maximumRecord, TypeEnum.SUBMISSION, TypeEnum.ANALYSIS);
        bulkInsertRelation(submissionExperimentRelationList, maximumRecord, TypeEnum.SUBMISSION, TypeEnum.EXPERIMENT);
        bulkInsertRelation(experimentRunRelationList, maximumRecord, TypeEnum.EXPERIMENT, TypeEnum.RUN);
        bulkInsertRelation(bioSampleSampleRelationList, maximumRecord, TypeEnum.BIO_SAMPLE, TypeEnum.SAMPLE);
        bulkInsertRelation(bioSampleExperimentRelationList, maximumRecord, TypeEnum.BIO_SAMPLE, TypeEnum.EXPERIMENT);
        bulkInsertRelation(runBioSampleRelationList, maximumRecord, TypeEnum.RUN, TypeEnum.BIO_SAMPLE);
        bulkInsertRelation(sampleExperimentRelationList, maximumRecord, TypeEnum.SAMPLE, TypeEnum.EXPERIMENT);
        bulkInsertRelation(studySubmissionRelationList, maximumRecord, TypeEnum.STUDY, TypeEnum.SUBMISSION);

        log.info("SRAAccessions.tab登録処理が完了");
    }

    private Object[] getRecord(String[] sraAccession, String timeStampFormat) {
        Object[] record = new Object[6];

        try {
            String updatedStr = "-".equals(sraAccession[3]) ? null : sraAccession[3];
            String publishedStr = "-".equals(sraAccession[4]) ? null : sraAccession[4];
            String receivedStr = "-".equals(sraAccession[5]) ? null : sraAccession[5];

            String accession    = sraAccession[0];
            String status       = sraAccession[2];
            Timestamp updated   = new Timestamp(new SimpleDateFormat(timeStampFormat).parse(updatedStr).getTime());
            Timestamp published = new Timestamp(new SimpleDateFormat(timeStampFormat).parse(publishedStr).getTime());
            Timestamp received  = new Timestamp(new SimpleDateFormat(timeStampFormat).parse(receivedStr).getTime());
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
