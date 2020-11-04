package com.ddbj.ld.service;

import com.ddbj.ld.bean.common.DBXrefsBean;
import com.ddbj.ld.bean.dra.*;
import com.ddbj.ld.common.constant.FileNameEnum;
import com.ddbj.ld.common.constant.TypeEnum;
import com.ddbj.ld.common.constant.XmlTagEnum;
import com.ddbj.ld.common.helper.BulkHelper;
import com.ddbj.ld.common.setting.Settings;
import com.ddbj.ld.parser.common.JsonParser;
import com.ddbj.ld.parser.dra.*;
import com.ddbj.ld.parser.jga.JgaParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

import com.ddbj.ld.dao.common.ElasticsearchDao;
import com.ddbj.ld.dao.dra.SRAAccessionsDao;

// TODO 繰り返し処理の回数を減らす
/**
 * Elasticsearchに関する処理を行うサービスクラス.
 */
@Service
@AllArgsConstructor
@Slf4j
public class RegisterService {
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

    private final JgaParser jgaParser;

    private final ElasticsearchDao elasticsearchDao;
    private final SRAAccessionsDao sraAccessionsDao;

    // データの関係を取得するためのテーブル群
    private final String bioProjectSubmissionTable = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.SUBMISSION.toString();
    private final String bioProjectStudyTable      = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.STUDY.toString();
    private final String bioSampleSampleTable      = TypeEnum.BIOSAMPLE.toString() + "_" + TypeEnum.SAMPLE.toString();
    private final String studySubmissionTable      = TypeEnum.STUDY.toString() + "_" + TypeEnum.SUBMISSION.toString();
    private final String submissionExperimentTable = TypeEnum.SUBMISSION.toString() + "_" + TypeEnum.EXPERIMENT.toString();
    private final String submissionAnalysisTable   = TypeEnum.SUBMISSION.toString() + "_" + TypeEnum.ANALYSIS.toString();
    private final String experimentRunTable        = TypeEnum.EXPERIMENT.toString() + "_" + TypeEnum.RUN.toString();
    private final String bioSampleExperimentTable  = TypeEnum.BIOSAMPLE.toString() + "_" + TypeEnum.EXPERIMENT.toString();
    private final String sampleExperimentTable     = TypeEnum.SAMPLE.toString() + "_" + TypeEnum.EXPERIMENT.toString();
    private final String runBioSampleTable         = TypeEnum.RUN.toString() + "_" + TypeEnum.BIOSAMPLE.toString();

    /**
     * ElasticsearchにDRAのデータを登録する.
     */
    public void registerDRA () {
        log.info("DRA Elasticsearch登録処理開始");

        // Elasticsearchの設定
        String hostname = settings.getHostname();
        int    port     = settings.getPort();
        String scheme   = settings.getScheme();

        // BioProjectのJson形式データを作成する
        createBioProjectJsonMap();

        // BioSampleのJson形式データを作成する
        createBioSampleJsonMap();

        //  一度に登録するレコード数
        int maximumRecord = settings.getMaximumRecord();

        Map<String, List<File>> pathMap = getPathListMap();
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
                    studyJsonMap.putAll(createStudyJsonMap(studyXml));

                    String sampleXml = targetDirPath + submission + FileNameEnum.SAMPLE_XML.getFileName();
                    sampleJsonMap.putAll(createSampleJsonMap(sampleXml));

                    String submissionXml = targetDirPath + submission + FileNameEnum.SUBMISSION_XML.getFileName();
                    submissionJsonMap.putAll(createSubmissionJsonMap(submissionXml));

                    String experimentXml = targetDirPath + submission + FileNameEnum.EXPERIMENT_XML.getFileName();
                    experimentJsonMap.putAll(createExperimentJsonMap(experimentXml));

                    String analysisXml   = targetDirPath + submission + FileNameEnum.ANALYSIS_XML.getFileName();
                    analysisJsonMap.putAll(createAnalysisJsonMap(analysisXml));

                    String runXml        = targetDirPath + submission + FileNameEnum.RUN_XML.getFileName();
                    runJsonMap.putAll(createRunJsonMap(runXml));
                });

                if(studyJsonMap.size() > 0 ) {
                    elasticsearchDao.bulkInsert(hostname, port, scheme, TypeEnum.STUDY.getType(), studyJsonMap);
                }

                if(sampleJsonMap.size() > 0) {
                    elasticsearchDao.bulkInsert(hostname, port, scheme, TypeEnum.SAMPLE.getType(), sampleJsonMap);
                }

                if(submissionJsonMap.size() > 0) {
                    elasticsearchDao.bulkInsert(hostname, port, scheme, TypeEnum.SUBMISSION.getType(), submissionJsonMap);
                }

                if(experimentJsonMap.size() > 0) {
                    elasticsearchDao.bulkInsert(hostname, port, scheme, TypeEnum.EXPERIMENT.getType(), experimentJsonMap);
                }

                if(analysisJsonMap.size() > 0) {
                    elasticsearchDao.bulkInsert(hostname, port, scheme, TypeEnum.ANALYSIS.getType(), analysisJsonMap);
                }

                if(runJsonMap.size() > 0) {
                    elasticsearchDao.bulkInsert(hostname, port, scheme, TypeEnum.RUN.getType(), runJsonMap);
                }
            });

            log.info("対象ディレクトリ登録完了：" + parentPath);
        }

        log.info("DRA Elasticsearch登録処理終了");
    }

    private Map<String, String> createRunJsonMap(String runXml) {
        File runXmlFile = new File(runXml);
        if(!runXmlFile.exists()) {
            return new HashMap<>();
        }

        List<RunBean> runBeanList = runParser.parse(runXml);
        ObjectMapper mapper = jsonParser.getMapper();
        Map<String, String> runJsonMap = new HashMap<>();
        runBeanList.forEach(bean -> {
            String accession = bean.getIdentifier();
            List<DBXrefsBean> experimentRunList = sraAccessionsDao.selRelation(accession, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);
            List<DBXrefsBean> runBioSampleList  = sraAccessionsDao.selRelation(accession, runBioSampleTable, TypeEnum.RUN, TypeEnum.BIOSAMPLE);

            experimentRunList.addAll(runBioSampleList);

            bean.setDbXrefs(experimentRunList);
            runJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
        });
        return runJsonMap;
    }

    private Map<String, String> createAnalysisJsonMap(String analysisXml) {
        File analysisXmlFile = new File(analysisXml);
        if(!analysisXmlFile.exists()) {
            return new HashMap<>();
        }

        List<AnalysisBean> analysisBeanList = analysisParser.parse(analysisXml);
        ObjectMapper mapper = jsonParser.getMapper();
        Map<String, String> analysisJsonMap = new HashMap<>();
        analysisBeanList.forEach(bean -> {
            String accession = bean.getIdentifier();
            List<DBXrefsBean> submissionAnalysisList = sraAccessionsDao.selRelation(accession, submissionAnalysisTable, TypeEnum.ANALYSIS, TypeEnum.SUBMISSION);

            bean.setDbXrefs(submissionAnalysisList);
            analysisJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
        });
        return analysisJsonMap;
    }

    private Map<String, String> createExperimentJsonMap(String experimentXml) {
        File experimentXmlFile = new File(experimentXml);
        if(experimentXmlFile.exists()) {
            return new HashMap<>();
        }

        List<ExperimentBean> experimentBeanList = experimentParser.parse(experimentXml);
        ObjectMapper mapper = jsonParser.getMapper();
        Map<String, String> experimentJsonMap = new HashMap<>();
        experimentBeanList.forEach(bean -> {
            String accession = bean.getIdentifier();
            List<DBXrefsBean> submissionExperimentList = sraAccessionsDao.selRelation(accession, submissionExperimentTable, TypeEnum.EXPERIMENT, TypeEnum.SUBMISSION);
            List<DBXrefsBean> bioSampleExperimentList  = sraAccessionsDao.selRelation(accession, bioSampleExperimentTable, TypeEnum.EXPERIMENT, TypeEnum.BIOSAMPLE);
            List<DBXrefsBean> sampleExperimentList     = sraAccessionsDao.selRelation(accession, sampleExperimentTable, TypeEnum.EXPERIMENT, TypeEnum.SAMPLE);
            List<DBXrefsBean> experimentRunList        = sraAccessionsDao.selRelation(accession, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);

            submissionExperimentList.addAll(bioSampleExperimentList);
            submissionExperimentList.addAll(sampleExperimentList);
            submissionExperimentList.addAll(experimentRunList);

            bean.setDbXrefs(submissionExperimentList);
            experimentJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
        });
        return experimentJsonMap;
    }

    private Map<String, String> createSubmissionJsonMap(String submissionXml) {
        File submissionXmlFile = new File(submissionXml);
        if(submissionXmlFile.exists()) {
            return new HashMap<>();
        }

        List<SubmissionBean> submissionBeanList = submissionParser.parse(submissionXml);
        ObjectMapper mapper = jsonParser.getMapper();
        Map<String, String> submissionJsonMap = new HashMap<>();
        submissionBeanList.forEach(bean -> {
            String accession = bean.getIdentifier();
            List<DBXrefsBean> bioProjectSubmissionList = sraAccessionsDao.selRelation(accession, bioProjectSubmissionTable, TypeEnum.SUBMISSION, TypeEnum.BIOPROJECT);
            List<DBXrefsBean> studySubmissionList = sraAccessionsDao.selRelation(accession, studySubmissionTable, TypeEnum.SUBMISSION, TypeEnum.STUDY);
            List<DBXrefsBean> submissionExperimentList = sraAccessionsDao.selRelation(accession, submissionExperimentTable, TypeEnum.SUBMISSION, TypeEnum.EXPERIMENT);
            List<DBXrefsBean> submissionAnalysisList = sraAccessionsDao.selRelation(accession, submissionAnalysisTable, TypeEnum.SUBMISSION, TypeEnum.ANALYSIS);

            bioProjectSubmissionList.addAll(studySubmissionList);
            bioProjectSubmissionList.addAll(submissionExperimentList);
            bioProjectSubmissionList.addAll(submissionAnalysisList);

            bean.setDbXrefs(bioProjectSubmissionList);
            submissionJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
        });

        return submissionJsonMap;
    }

    private Map<String, String> createSampleJsonMap(String sampleXml) {
        File sampleXmlFile = new File(sampleXml);
        if(!sampleXmlFile.exists()) {
            return new HashMap<>();
        }

        ObjectMapper mapper = jsonParser.getMapper();
        List<SampleBean> sampleBeanList = sampleParser.parse(sampleXml);

        Map<String, String> sampleJsonMap = new HashMap<>();
        sampleBeanList.forEach(bean -> {
            String accession = bean.getIdentifier();
            List<DBXrefsBean> bioSampleSampleList = sraAccessionsDao.selRelation(accession, bioSampleSampleTable, TypeEnum.SAMPLE, TypeEnum.BIOSAMPLE);

            bean.setDbXrefs(bioSampleSampleList);
            sampleJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
        });
        return sampleJsonMap;
    }

    private Map<String, String> createStudyJsonMap(String studyXml) {
        File studyXmlFile  = new File(studyXml);
        if(!studyXmlFile.exists()) {
            return new HashMap<>();
        }

        ObjectMapper mapper = jsonParser.getMapper();
        List<StudyBean> studyBeanList = studyParser.parse(studyXml);

        Map<String, String> studyJsonMap = new HashMap<>();
        studyBeanList.forEach(bean -> {
            String accession = bean.getIdentifier();
            List<DBXrefsBean> bioProjectStudyList = sraAccessionsDao.selRelation(accession, bioProjectStudyTable, TypeEnum.STUDY, TypeEnum.BIOPROJECT);
            List<DBXrefsBean> studySubmissionList = sraAccessionsDao.selRelation(accession, studySubmissionTable, TypeEnum.STUDY, TypeEnum.SUBMISSION);
            bioProjectStudyList.addAll(studySubmissionList);

            bean.setDbXrefs(bioProjectStudyList);
            studyJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean , mapper));
        });

        return studyJsonMap;
    }

    private void createBioSampleJsonMap() {
        ObjectMapper mapper = jsonParser.getMapper();
        String scheme = settings.getScheme();
        int port = settings.getPort();
        String hostname = settings.getHostname();
        String bioSamplePath = settings.getBioSamplePath();
        String bioSampleIndexName = TypeEnum.BIOSAMPLE.getType();
        int bioSampleCnt = 0;

        File bioSampleDir = new File(bioSamplePath);
        List<File> bioSampleFileList = Arrays.asList(Objects.requireNonNull(bioSampleDir.listFiles()));

        for(File bioSampleFile: bioSampleFileList) {
            // TODO BioSampleのボトルネックは…？？
            // TODO 少なくとも、分割したXMLのサイズのせいではない
            List<BioSampleBean> bioSampleBeanList = bioSampleParser.parse(bioSampleFile.getAbsolutePath());
            Map<String, String> bioSampleJsonMap = new HashMap<>();

            bioSampleBeanList.forEach(bean -> {
                String accession = bean.getIdentifier();
                List<DBXrefsBean> sampleDbXrefs = sraAccessionsDao.selRelation(accession, bioSampleSampleTable, TypeEnum.BIOSAMPLE, TypeEnum.SAMPLE);
                bean.setDbXrefs(sampleDbXrefs);
                bioSampleJsonMap.put(accession, jsonParser.parse(bean, mapper));
            });

            // TODO ここがボトルネックっぽい
            // TODO Elasticsearchのスキーマ定義も事前に定義してあげる必要がある（かも
            // TODO 全てのIndexのmappingを事前に定義しておくようにして試す
            // TODO 本番の自動生成されたbiosampleのmappingを使う
            elasticsearchDao.bulkInsert(hostname, port, scheme, bioSampleIndexName, bioSampleJsonMap);

            bioSampleCnt = bioSampleCnt + bioSampleBeanList.size();
        }

        log.info("biosample、Elasticsearch登録完了：" + bioSampleCnt + "件");
    }

    private void createBioProjectJsonMap() {
        ObjectMapper mapper = jsonParser.getMapper();
        String scheme = settings.getScheme();
        int port = settings.getPort();
        String hostname = settings.getHostname();
        String bioProjectPath = settings.getBioProjectPath();
        TypeEnum bioProjectType = TypeEnum.BIOPROJECT;
        TypeEnum submissionType = TypeEnum.SUBMISSION;
        TypeEnum studyType      = TypeEnum.STUDY;
        String bioProjectIndexName = TypeEnum.BIOPROJECT.getType();
        int bioProjectCnt = 0;

        File bioProjectDir = new File(bioProjectPath);
        List<File> bioProjectFileList = Arrays.asList(Objects.requireNonNull(bioProjectDir.listFiles()));

        for(File bioProjectFile: bioProjectFileList) {
            List<BioProjectBean> bioProjectBeanList = bioProjectParser.parse(bioProjectFile.getAbsolutePath());
            Map<String, String> bioProjectJsonMap = new HashMap<>();

            bioProjectBeanList.forEach(bean -> {
                String accession = bean.getIdentifier();
                List<DBXrefsBean> studyDbXrefs      = sraAccessionsDao.selRelation(accession, bioProjectStudyTable, bioProjectType, studyType);
                List<DBXrefsBean> submissionDbXrefs = sraAccessionsDao.selRelation(accession, bioProjectSubmissionTable, bioProjectType, submissionType);

                studyDbXrefs.addAll(submissionDbXrefs);
                bean.setDbXrefs(studyDbXrefs);
                bioProjectJsonMap.put(accession, jsonParser.parse(bean, mapper));
            });

            elasticsearchDao.bulkInsert(hostname, port, scheme, bioProjectIndexName, bioProjectJsonMap);

            // TODO ログの件数が誤っている
            bioProjectCnt = bioProjectCnt + bioProjectBeanList.size();
        }

        log.info("bioproject、Elasticsearch登録完了：" + bioProjectCnt + "件");
    }

    private Map<String, List<File>> getPathListMap() {
        // XMLのパス群
        String draPath = settings.getDraXmlPath();

        File draDir = new File(draPath);
        List<File> draChildrenDirList = Arrays.asList(Objects.requireNonNull(draDir.listFiles()));

        Map<String, List<File>> pathMap = new HashMap<>();

        for(File draChildrenDir : draChildrenDirList) {
            String parentPath = draChildrenDir.getAbsolutePath();
            List<File> grandchildDirList = Arrays.asList(Objects.requireNonNull(draChildrenDir.listFiles()));

            pathMap.put(parentPath, grandchildDirList);
        }
        return pathMap;
    }

    /**
     * ElasticsearchにJGAのデータを登録する.
     */
    public void registerJGA() {
        log.info("JGA Elasticsearch登録処理開始");

        String hostname = settings.getHostname();
        int    port     = settings.getPort();
        String scheme   = settings.getScheme();

        String studyIndexName   = TypeEnum.JGA_STUDY.getType();
        String dataSetIndexName = TypeEnum.DATASET.getType();
        String policyIndexName  = TypeEnum.POLICY.getType();
        String dacIndexName     = TypeEnum.DAC.getType();

        String xmlPath       = settings.getJgaXmlPath();
        String studyXml      = xmlPath + FileNameEnum.JGA_STUDY_XML.getFileName();
        String dataSetXml    = xmlPath + FileNameEnum.DATASET_XML.getFileName();
        String policyXml     = xmlPath + FileNameEnum.POLICY_XML.getFileName();
        String dacXml        = xmlPath + FileNameEnum.DAC_XML.getFileName();

        String studyType   = TypeEnum.JGA_STUDY.getType();
        String dataSetType = TypeEnum.DATASET.getType();
        String policyType  = TypeEnum.POLICY.getType();
        String dacType     = TypeEnum.DAC.getType();

        String studySetTag   = XmlTagEnum.JGA_STUDY_SET.getItem();
        String dataSetSetTag = XmlTagEnum.DATASET_SET.getItem();
        String policySetTag  = XmlTagEnum.POLICY_SET.getItem();
        String dacSetTag     = XmlTagEnum.DAC_SET.getItem();

        String studyTargetTag   = XmlTagEnum.JGA_STUDY.getItem();
        String dataSetTargetTag = XmlTagEnum.DATASET.getItem();
        String policyTargetTag  = XmlTagEnum.POLICY.getItem();
        String dacTargetTag     = XmlTagEnum.DAC.getItem();

        Map<String,String> studyJsonMap   = jgaParser.parse(studyXml, studyType, studySetTag, studyTargetTag);
        Map<String,String> dataSetJsonMap = jgaParser.parse(dataSetXml, dataSetType, dataSetSetTag, dataSetTargetTag);
        Map<String,String> policyJsonMap  = jgaParser.parse(policyXml, policyType, policySetTag, policyTargetTag);
        Map<String,String> dacJsonMap     = jgaParser.parse(dacXml, dacType, dacSetTag, dacTargetTag);

        if(studyJsonMap != null && studyJsonMap.size() > 0) {
            elasticsearchDao.bulkInsert(hostname, port, scheme, studyIndexName, studyJsonMap);
        }

        if(dataSetJsonMap != null && dataSetJsonMap.size() > 0) {
            elasticsearchDao.bulkInsert(hostname, port, scheme, dataSetIndexName, dataSetJsonMap);
        }

        if(policyJsonMap != null && policyJsonMap.size() > 0) {
            elasticsearchDao.bulkInsert(hostname, port, scheme, policyIndexName, policyJsonMap);
        }

        if(dacJsonMap != null && dacJsonMap.size() > 0) {
            elasticsearchDao.bulkInsert(hostname, port, scheme, dacIndexName, dacJsonMap);
        }

        log.info("JGA Elasticsearch登録処理終了");
    }
}
