package com.ddbj.ld.service;

import com.ddbj.ld.bean.*;
import com.ddbj.ld.common.BulkHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.ddbj.ld.common.FileNameEnum;
import com.ddbj.ld.common.Settings;
import com.ddbj.ld.common.TypeEnum;
import com.ddbj.ld.dao.ElasticsearchDao;
import com.ddbj.ld.dao.SRAAccessionsDao;
import com.ddbj.ld.parser.*;

// TODO
//  - ログ出力
//  - DBのデータを取ってくる
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

        String hostname = settings.getHostname();
        int    port     = settings.getPort();
        String scheme   = settings.getScheme();

        String xmlPath = settings.getXmlPath();
        String bioProjectXml = xmlPath  + FileNameEnum.BIO_PROJECT_XML.getFileName();
        List<BioProjectBean> bioProjectBeanList = bioProjectParser.parse(bioProjectXml);
        String bioProjectSubmissionTable = TypeEnum.BIO_PROJECT.getType() + "_" + TypeEnum.SUBMISSION;
        String bioProjectStudyTable = TypeEnum.BIO_PROJECT.getType() + "_" + TypeEnum.STUDY;
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
            List<String> bioProjectJsonList = jsonParser.parse(_bioProjectBeanList);
            elasticsearchDao.bulkInsert(hostname, port, scheme, bioProjectIndexName, bioProjectJsonList);
        });

        String bioSampleXml                   = settings.getXmlPath()  + FileNameEnum.BIO_SAMPLE_XML.getFileName();
        List<BioSampleBean> bioSampleBeanList = bioSampleParser.parse(bioSampleXml);
        String bioSampleSampleTable           = TypeEnum.BIO_SAMPLE.getType() + "_" + TypeEnum.SAMPLE;
        TypeEnum bioSampleType                = TypeEnum.BIO_SAMPLE;
        TypeEnum sampleType                   = TypeEnum.SAMPLE;

        bioSampleBeanList.forEach(bioSampleBean -> {
            String accession = bioSampleBean.getIdentifier();
            List<DBXrefsBean> sampleDbXrefs = sraAccessionsDao.selRelation(accession, bioSampleSampleTable, bioSampleType, sampleType);
            bioSampleBean.setDbXrefs(sampleDbXrefs);
        });

        String bioSampleIndexName = TypeEnum.BIO_SAMPLE.getType();

        BulkHelper.extract(bioSampleBeanList, maximumRecord, _bioSampleBeanList -> {
            List<String> bioSampleJsonList = jsonParser.parse(_bioSampleBeanList);
            elasticsearchDao.bulkInsert(hostname, port, scheme, bioSampleIndexName, bioSampleJsonList);
        });

        // TODO
        File targetDir = new File(xmlPath + "/dra/");
        List<File> childrenDirList = Arrays.asList(Objects.requireNonNull(targetDir.listFiles()));

        String studyIndexName = TypeEnum.STUDY.getType();
        String sampleIndexName = TypeEnum.SAMPLE.getType();

        String submissionIndexName = TypeEnum.SUBMISSION.getType();
        String experimentIndexName = TypeEnum.EXPERIMENT.getType();
        String analysisIndexName = TypeEnum.ANALYSIS.getType();
        String runIndexName = TypeEnum.RUN.getType();

        BulkHelper.extract(childrenDirList, maximumRecord, _childrenDirList -> {
            List<String> studyJsonList      = new ArrayList<>();
            List<String> sampleJsonList     = new ArrayList<>();
            List<String> submissionJsonList = new ArrayList<>();
            List<String> experimentJsonList = new ArrayList<>();
            List<String> analysisJsonList   = new ArrayList<>();
            List<String> runJsonList        = new ArrayList<>();

            _childrenDirList.forEach(_childrenDir -> {
                String childrenDirName = _childrenDir.getName();
                // TODO
                String xmlDir = xmlPath + "/dra/" + childrenDirName + "/";

                String studyXml = xmlDir + childrenDirName + FileNameEnum.STUDY_XML.getFileName();
                String sampleXml = xmlDir + childrenDirName + FileNameEnum.SAMPLE_XML.getFileName();

                String submissionXml = xmlDir + childrenDirName + FileNameEnum.SUBMISSION_XML.getFileName();
                String experimentXml = xmlDir + childrenDirName + FileNameEnum.EXPERIMENT_XML.getFileName();
                String analysisXml   = xmlDir + childrenDirName + FileNameEnum.ANALYSIS_XML.getFileName();
                String runXml        = xmlDir + childrenDirName + FileNameEnum.RUN_XML.getFileName();

                File studyXmlFile  = new File(studyXml);
                File sampleXmlFile = new File(sampleXml);

                // TODO テーブル情報は後でBeanにまとめる
                String studySubmissionTable = TypeEnum.STUDY.getType() + "_" + TypeEnum.SUBMISSION.getType();

                if(studyXmlFile.exists()) {
                    List<StudyBean> studyBeanList = studyParser.parse(studyXml);

                    studyBeanList.forEach(bean -> {
                        String accession = bean.getIdentifier();
                        List<DBXrefsBean> bioProjectStudyList = sraAccessionsDao.selRelation(accession, bioProjectStudyTable, TypeEnum.STUDY, TypeEnum.BIO_PROJECT);
                        List<DBXrefsBean> studySubmissionList = sraAccessionsDao.selRelation(accession, studySubmissionTable, TypeEnum.STUDY, TypeEnum.SUBMISSION);
                        bioProjectStudyList.addAll(studySubmissionList);

                        bean.setDbXrefs(bioProjectStudyList);
                    });

                    studyJsonList.addAll(jsonParser.parse(studyBeanList));
                }

                if(sampleXmlFile.exists()) {
                    List<SampleBean> sampleBeanList = sampleParser.parse(sampleXml);

                    sampleBeanList.forEach(bean -> {
                        String accession = bean.getIdentifier();
                        List<DBXrefsBean> bioSampleSampleList = sraAccessionsDao.selRelation(accession, bioSampleSampleTable, TypeEnum.SAMPLE, TypeEnum.BIO_SAMPLE);

                        bean.setDbXrefs(bioSampleSampleList);
                    });

                    sampleJsonList.addAll(jsonParser.parse(sampleBeanList));
                }

                List<SubmissionBean> submissionBeanList = submissionParser.parse(submissionXml);
                List<ExperimentBean> experimentBeanList = experimentParser.parse(experimentXml);
                List<AnalysisBean> analysisBeanList     = analysisParser.parse(analysisXml);
                List<RunBean> runBeanList               = runParser.parse(runXml);

                // TODO
                String submissionExperimentTable = TypeEnum.SUBMISSION.getType() + "_" + TypeEnum.EXPERIMENT.getType();
                String submissionAnalysisTable = TypeEnum.SUBMISSION.getType() + "_" + TypeEnum.ANALYSIS.getType();

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
                });

                // TODO
                String experimentRunTable = TypeEnum.EXPERIMENT.getType() + "_" + TypeEnum.RUN.getType();
                String bioSampleExperimentTable = TypeEnum.BIO_SAMPLE.getType() + "_" + TypeEnum.EXPERIMENT.getType();
                String sampleExperimentTable = TypeEnum.SAMPLE.getType() + "_" + TypeEnum.EXPERIMENT.getType();

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
                });

                analysisBeanList.forEach(bean -> {
                    String accession = bean.getIdentifier();
                    List<DBXrefsBean> submissionAnalysisList = sraAccessionsDao.selRelation(accession, submissionAnalysisTable, TypeEnum.ANALYSIS, TypeEnum.SUBMISSION);

                    bean.setDbXrefs(submissionAnalysisList);
                });

                // TODO
                String runBioSampleTable = TypeEnum.RUN.getType() + "_" + TypeEnum.BIO_SAMPLE.getType();

                runBeanList.forEach(bean -> {
                    String accession = bean.getIdentifier();
                    List<DBXrefsBean> experimentRunList = sraAccessionsDao.selRelation(accession, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);
                    List<DBXrefsBean> runBioSampleList  = sraAccessionsDao.selRelation(accession, runBioSampleTable, TypeEnum.RUN, TypeEnum.BIO_SAMPLE);

                    experimentRunList.addAll(runBioSampleList);

                    bean.setDbXrefs(experimentRunList);
                });

                submissionJsonList.addAll(jsonParser.parse(submissionBeanList));
                experimentJsonList.addAll(jsonParser.parse(experimentBeanList));
                analysisJsonList.addAll(jsonParser.parse(analysisBeanList));
                runJsonList.addAll(jsonParser.parse(runBeanList));
            });

            elasticsearchDao.bulkInsert(hostname, port, scheme, studyIndexName, studyJsonList);
            elasticsearchDao.bulkInsert(hostname, port, scheme, sampleIndexName, sampleJsonList);

            elasticsearchDao.bulkInsert(hostname, port, scheme, submissionIndexName, submissionJsonList);
            elasticsearchDao.bulkInsert(hostname, port, scheme, experimentIndexName, experimentJsonList);
            elasticsearchDao.bulkInsert(hostname, port, scheme, analysisIndexName, analysisJsonList);
            elasticsearchDao.bulkInsert(hostname, port, scheme, runIndexName, runJsonList);
        });

        log.info("Elasticsearch登録処理終了");
    }
}
