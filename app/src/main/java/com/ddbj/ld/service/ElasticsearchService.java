package com.ddbj.ld.service;

import com.ddbj.ld.bean.*;
import com.ddbj.ld.common.BulkHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

import com.ddbj.ld.common.FileNameEnum;
import com.ddbj.ld.common.Settings;
import com.ddbj.ld.common.TypeEnum;
import com.ddbj.ld.dao.ElasticsearchDao;
import com.ddbj.ld.dao.SRAAccessionsDao;
import com.ddbj.ld.parser.*;

// TODO
//  - ログ出力
@Service
@AllArgsConstructor
@Slf4j
public class ElasticsearchService {
    private final Settings settings;

    private final JsonParser jsonParser;
    private final BioProjectParser bioProjectParser;
    private final BioSampleParser bioSampleParser;
    private final StudyParser studyParser;
    private final SampleParser sampleParser;
    private final SubmissionParser submissionParser;
    private final ExperimentParser experimentParser;
    private final AnalysisParser analysisParser;
    private final RunParser runParser;

    private final ElasticsearchDao elasticsearchDao;
    private final SRAAccessionsDao sraAccessionsDao;

    public void registerElasticsearch () {
        log.info("Elasticsearch登録処理開始");

        // Elasticsearchの設定
        String hostname = settings.getHostname();
        int    port     = settings.getPort();
        String scheme   = settings.getScheme();

        // データの関係を取得するためのテーブル群
        String bioProjectSubmissionTable = TypeEnum.BIO_PROJECT.getType() + "_" + TypeEnum.SUBMISSION;
        String bioProjectStudyTable = TypeEnum.BIO_PROJECT.getType() + "_" + TypeEnum.STUDY;
        String bioSampleSampleTable           = TypeEnum.BIO_SAMPLE.getType() + "_" + TypeEnum.SAMPLE;
        String studySubmissionTable = TypeEnum.STUDY.getType() + "_" + TypeEnum.SUBMISSION.getType();
        String submissionExperimentTable = TypeEnum.SUBMISSION.getType() + "_" + TypeEnum.EXPERIMENT.getType();
        String submissionAnalysisTable = TypeEnum.SUBMISSION.getType() + "_" + TypeEnum.ANALYSIS.getType();
        String experimentRunTable = TypeEnum.EXPERIMENT.getType() + "_" + TypeEnum.RUN.getType();
        String bioSampleExperimentTable = TypeEnum.BIO_SAMPLE.getType() + "_" + TypeEnum.EXPERIMENT.getType();
        String sampleExperimentTable = TypeEnum.SAMPLE.getType() + "_" + TypeEnum.EXPERIMENT.getType();
        String runBioSampleTable = TypeEnum.RUN.getType() + "_" + TypeEnum.BIO_SAMPLE.getType();

        // XMLのパス群
        String draPath = settings.getXmlPath();

        File draDir = new File(draPath);
        List<File> draChildrenDirList = Arrays.asList(Objects.requireNonNull(draDir.listFiles()));

        Map<String, List<File>> pathMap = new HashMap<>();

        // Debug用
        String mode = settings.getMode();
        int recordLimit = settings.getDevelopmentRecordNumber();
        int recordCnt = 0;

        for(File draChildrenDir : draChildrenDirList) {
            if(mode.equals("Development")
                    && recordLimit < recordCnt) {
                break;
            }

            String parentPath = draChildrenDir.getAbsolutePath();
            List<File> grandchildDirList = Arrays.asList(Objects.requireNonNull(draChildrenDir.listFiles()));

            pathMap.put(parentPath, grandchildDirList);

            recordCnt = recordCnt + grandchildDirList.size();
        }

        String bioProjectXml = settings.getBioProjectXml();
        List<BioProjectBean> bioProjectBeanList = bioProjectParser.parse(bioProjectXml);

        TypeEnum bioProjectType = TypeEnum.BIO_PROJECT;
        TypeEnum submissionType = TypeEnum.SUBMISSION;
        TypeEnum studyType      = TypeEnum.STUDY;

        bioProjectBeanList.forEach(bioProjectBean -> {
            String accession = bioProjectBean.getIdentifier();
            List<DBXrefsBean> studyDbXrefs      = sraAccessionsDao.selRelation(accession, bioProjectStudyTable, bioProjectType, studyType);
            List<DBXrefsBean> submissionDbXrefs = sraAccessionsDao.selRelation(accession, bioProjectSubmissionTable, bioProjectType, submissionType);
            List<DBXrefsBean> dbXrefs = new ArrayList<>();
            dbXrefs.addAll(studyDbXrefs);
            dbXrefs.addAll(submissionDbXrefs);

            bioProjectBean.setDbXrefs(dbXrefs);
        });

        String bioProjectIndexName = TypeEnum.BIO_PROJECT.getType();
        int maximumRecord = settings.getMaximumRecord();

        BulkHelper.extract(bioProjectBeanList, maximumRecord, _bioProjectBeanList -> {
            Map<String, String> bioProjectJsonMap = new HashMap<>();

            _bioProjectBeanList.forEach(bean -> {
                bioProjectJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean));
            });

            elasticsearchDao.bulkInsert(hostname, port, scheme, bioProjectIndexName, bioProjectJsonMap);
        });

        String bioSampleXml                   = settings.getBioSampleXml();
        List<BioSampleBean> bioSampleBeanList = bioSampleParser.parse(bioSampleXml);
        TypeEnum bioSampleType                = TypeEnum.BIO_SAMPLE;
        TypeEnum sampleType                   = TypeEnum.SAMPLE;

        bioSampleBeanList.forEach(bioSampleBean -> {
            String accession = bioSampleBean.getIdentifier();
            List<DBXrefsBean> sampleDbXrefs = sraAccessionsDao.selRelation(accession, bioSampleSampleTable, bioSampleType, sampleType);
            bioSampleBean.setDbXrefs(sampleDbXrefs);
        });

        String bioSampleIndexName = TypeEnum.BIO_SAMPLE.getType();

        BulkHelper.extract(bioSampleBeanList, maximumRecord, _bioSampleBeanList -> {
            Map<String, String> bioSampleJsonMap = new HashMap<>();

            _bioSampleBeanList.forEach(bean -> {
                bioSampleJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean));
            });

            elasticsearchDao.bulkInsert(hostname, port, scheme, bioSampleIndexName, bioSampleJsonMap);
        });

        String studyIndexName = TypeEnum.STUDY.getType();
        String sampleIndexName = TypeEnum.SAMPLE.getType();

        String submissionIndexName = TypeEnum.SUBMISSION.getType();
        String experimentIndexName = TypeEnum.EXPERIMENT.getType();
        String analysisIndexName = TypeEnum.ANALYSIS.getType();
        String runIndexName = TypeEnum.RUN.getType();

        for (String parentPath : pathMap.keySet()) {

            List<File> targetDirList = pathMap.get(parentPath);

            BulkHelper.extract(targetDirList, maximumRecord, _targetDirList -> {
                Map<String,String> studyJsonMap      = new HashMap<>();
                Map<String,String> sampleJsonMap     = new HashMap<>();
                Map<String,String> submissionJsonMap = new HashMap<>();
                Map<String,String> experimentJsonMap = new HashMap<>();
                Map<String,String> analysisJsonMap   = new HashMap<>();
                Map<String,String> runJsonMap        = new HashMap<>();

                _targetDirList.forEach(_targetDir -> {
                    String submission = _targetDir.getName();
                    String targetDirPath = parentPath + "/" + submission + "/";

                    String studyXml = targetDirPath + submission + FileNameEnum.STUDY_XML.getFileName();
                    String sampleXml = targetDirPath + submission + FileNameEnum.SAMPLE_XML.getFileName();

                    String submissionXml = targetDirPath + submission + FileNameEnum.SUBMISSION_XML.getFileName();
                    String experimentXml = targetDirPath + submission + FileNameEnum.EXPERIMENT_XML.getFileName();
                    String analysisXml   = targetDirPath + submission + FileNameEnum.ANALYSIS_XML.getFileName();
                    String runXml        = targetDirPath + submission + FileNameEnum.RUN_XML.getFileName();

                    File studyXmlFile  = new File(studyXml);
                    File sampleXmlFile = new File(sampleXml);
                    File experimentXmlFile = new File(experimentXml);
                    File analysisXmlFile = new File(analysisXml);
                    File runXmlFile = new File(runXml);

                    if(studyXmlFile.exists()) {
                        List<StudyBean> studyBeanList = studyParser.parse(studyXml);

                        studyBeanList.forEach(bean -> {
                            String accession = bean.getIdentifier();
                            List<DBXrefsBean> bioProjectStudyList = sraAccessionsDao.selRelation(accession, bioProjectStudyTable, TypeEnum.STUDY, TypeEnum.BIO_PROJECT);
                            List<DBXrefsBean> studySubmissionList = sraAccessionsDao.selRelation(accession, studySubmissionTable, TypeEnum.STUDY, TypeEnum.SUBMISSION);
                            bioProjectStudyList.addAll(studySubmissionList);

                            bean.setDbXrefs(bioProjectStudyList);
                            studyJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean));
                        });
                    }

                    if(sampleXmlFile.exists()) {
                        List<SampleBean> sampleBeanList = sampleParser.parse(sampleXml);

                        sampleBeanList.forEach(bean -> {
                            String accession = bean.getIdentifier();
                            List<DBXrefsBean> bioSampleSampleList = sraAccessionsDao.selRelation(accession, bioSampleSampleTable, TypeEnum.SAMPLE, TypeEnum.BIO_SAMPLE);

                            bean.setDbXrefs(bioSampleSampleList);
                            sampleJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean));
                        });
                    }

                    List<SubmissionBean> submissionBeanList = submissionParser.parse(submissionXml);

                    List<ExperimentBean> experimentBeanList  = new ArrayList<>();

                    if(experimentXmlFile.exists()) {
                        experimentBeanList = experimentParser.parse(experimentXml);
                    }

                    List<AnalysisBean> analysisBeanList     = new ArrayList<>();
                    if(analysisXmlFile.exists()) {
                        analysisBeanList     = analysisParser.parse(analysisXml);
                    }

                    List<RunBean> runBeanList     = new ArrayList<>();
                    if(runXmlFile.exists()) {
                        runBeanList               = runParser.parse(runXml);
                    }


                    submissionBeanList.forEach(bean -> {
                        String accession = bean.getIdentifier();
                        List<DBXrefsBean> bioProjectSubmissionList = sraAccessionsDao.selRelation(accession, bioProjectSubmissionTable, TypeEnum.SUBMISSION, TypeEnum.BIO_PROJECT);
                        List<DBXrefsBean> studySubmissionList = sraAccessionsDao.selRelation(accession, studySubmissionTable, TypeEnum.SUBMISSION, TypeEnum.STUDY);
                        List<DBXrefsBean> submissionExperimentList = sraAccessionsDao.selRelation(accession, submissionExperimentTable, TypeEnum.SUBMISSION, TypeEnum.EXPERIMENT);
                        List<DBXrefsBean> submissionAnalysisList = sraAccessionsDao.selRelation(accession, submissionAnalysisTable, TypeEnum.SUBMISSION, TypeEnum.ANALYSIS);

                        bioProjectSubmissionList.addAll(studySubmissionList);
                        bioProjectSubmissionList.addAll(submissionExperimentList);
                        bioProjectSubmissionList.addAll(submissionAnalysisList);

                        bean.setDbXrefs(bioProjectSubmissionList);
                        submissionJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean));
                    });

                    experimentBeanList.forEach(bean -> {
                        String accession = bean.getIdentifier();
                        List<DBXrefsBean> submissionExperimentList = sraAccessionsDao.selRelation(accession, submissionExperimentTable, TypeEnum.EXPERIMENT, TypeEnum.SUBMISSION);
                        List<DBXrefsBean> bioSampleExperimentList  = sraAccessionsDao.selRelation(accession, bioSampleExperimentTable, TypeEnum.EXPERIMENT, TypeEnum.BIO_SAMPLE);
                        List<DBXrefsBean> sampleExperimentList     = sraAccessionsDao.selRelation(accession, sampleExperimentTable, TypeEnum.EXPERIMENT, TypeEnum.SAMPLE);
                        List<DBXrefsBean> experimentRunList        = sraAccessionsDao.selRelation(accession, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);

                        submissionExperimentList.addAll(bioSampleExperimentList);
                        submissionExperimentList.addAll(sampleExperimentList);
                        submissionExperimentList.addAll(experimentRunList);

                        bean.setDbXrefs(submissionExperimentList);
                        experimentJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean));
                    });

                    analysisBeanList.forEach(bean -> {
                        String accession = bean.getIdentifier();
                        List<DBXrefsBean> submissionAnalysisList = sraAccessionsDao.selRelation(accession, submissionAnalysisTable, TypeEnum.ANALYSIS, TypeEnum.SUBMISSION);

                        bean.setDbXrefs(submissionAnalysisList);
                        analysisJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean));
                    });

                    runBeanList.forEach(bean -> {
                        String accession = bean.getIdentifier();
                        List<DBXrefsBean> experimentRunList = sraAccessionsDao.selRelation(accession, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);
                        List<DBXrefsBean> runBioSampleList  = sraAccessionsDao.selRelation(accession, runBioSampleTable, TypeEnum.RUN, TypeEnum.BIO_SAMPLE);

                        experimentRunList.addAll(runBioSampleList);

                        bean.setDbXrefs(experimentRunList);
                        runJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean));
                    });
                });

                elasticsearchDao.bulkInsert(hostname, port, scheme, studyIndexName, studyJsonMap);
                elasticsearchDao.bulkInsert(hostname, port, scheme, sampleIndexName, sampleJsonMap);

                elasticsearchDao.bulkInsert(hostname, port, scheme, submissionIndexName, submissionJsonMap);
                elasticsearchDao.bulkInsert(hostname, port, scheme, experimentIndexName, experimentJsonMap);
                elasticsearchDao.bulkInsert(hostname, port, scheme, analysisIndexName, analysisJsonMap);
                elasticsearchDao.bulkInsert(hostname, port, scheme, runIndexName, runJsonMap);
            });
        }

        log.info("Elasticsearch登録処理終了");
    }
}
