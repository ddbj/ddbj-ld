package com.ddbj.ld.app.transact.usecase;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.core.parser.common.JsonParser;
import com.ddbj.ld.app.core.parser.dra.*;
import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.app.transact.service.BioProjectService;
import com.ddbj.ld.app.transact.service.JgaService;
import com.ddbj.ld.common.annotation.UseCase;
import com.ddbj.ld.common.constants.FileNameEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.helper.BulkHelper;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.dra.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

// FIXME
//  - 繰り返し処理の回数を減らす
//  - 同じ記述を減らす
/**
 * Elasticsearchに関する処理を行うユースケースクラス.
 */
@UseCase
@AllArgsConstructor
@Slf4j
public class RegisterUseCase {
    private final ConfigSet config;

    private final JsonParser jsonParser;
    private final BioProjectService bioProjectService;
    private final BioSampleParser bioSampleParser;
    private final StudyParser studyParser;
    private final SampleParser sampleParser;
    private final SubmissionParser submissionParser;
    private final ExperimentParser experimentParser;
    private final AnalysisParser analysisParser;
    private final RunParser runParser;

    private final JgaService jgaService;

    private final SearchModule searchModule;
    private final SRAAccessionsDao sraAccessionsDao;

    /**
     * ElasticsearchにBioProjectのデータを登録する.
     */
    public void registerBioProject() {
        var path  = this.config.file.path.bioProject;
        var index = TypeEnum.BIOPROJECT.getType();
        //  一度に登録するレコード数
        //  アプリケーションとElasticsearchの挙動を確認し適宜調整すること
        var maximumRecord = this.config.other.maximumRecord;

        var dir      = new File(path);
        var fileList = Arrays.asList(Objects.requireNonNull(dir.listFiles()));

        if(this.searchModule.existsIndex(index)) {
            // データが既にあるなら、全削除して入れ直す
            this.searchModule.deleteIndex(index);
        }

        for(File file: fileList) {
            var jsonList = this.bioProjectService.getBioProject(file.getAbsolutePath());

            BulkHelper.extract(jsonList, maximumRecord, _jsonList -> {
                this.searchModule.bulkInsert(index, _jsonList);
            });
        }
    }

    /**
     * ElasticsearchにBioSampleのデータを登録する.
     */
    public void registerBioSample() {
        // Elasticsearchの設定
        String hostname = this.config.elasticsearch.hostname;
        int    port     = this.config.elasticsearch.port;
        String scheme   = this.config.elasticsearch.scheme;

        String bioSampleSampleTable      = TypeEnum.BIOSAMPLE.toString() + "_" + TypeEnum.SAMPLE.toString();
        String bioSampleExperimentTable  = TypeEnum.BIOSAMPLE.toString() + "_" + TypeEnum.EXPERIMENT.toString();

        // 使用するObjectMapper
        ObjectMapper mapper = jsonParser.getMapper();

        String bioSamplePath = this.config.file.path.bioSample;
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
                List<DBXrefsBean> sampleDbXrefs     = sraAccessionsDao.selRelation(accession, bioSampleSampleTable, TypeEnum.BIOSAMPLE, TypeEnum.SAMPLE);
                List<DBXrefsBean> experimentDbXrefs = sraAccessionsDao.selRelation(accession, bioSampleExperimentTable, TypeEnum.BIOSAMPLE, TypeEnum.EXPERIMENT);
                sampleDbXrefs.addAll(experimentDbXrefs);

                bean.setDbXrefs(sampleDbXrefs);
                bioSampleJsonMap.put(accession, jsonParser.parse(bean, mapper));
            });

            // TODO ここがボトルネックっぽい
            // TODO Elasticsearchのスキーマ定義も事前に定義してあげる必要がある（かも
            // TODO 全てのIndexのmappingを事前に定義しておくようにして試す
            // TODO 本番の自動生成されたbiosampleのmappingを使う
            searchModule.bulkInsert(hostname, port, scheme, bioSampleIndexName, bioSampleJsonMap);

            bioSampleCnt = bioSampleCnt + bioSampleBeanList.size();
        }
    }

    /**
     * ElasticsearchにDRAのデータを登録する.
     */
    public void registerDRA () {
        // Elasticsearchの設定
        String hostname = this.config.elasticsearch.hostname;
        int    port     = this.config.elasticsearch.port;
        String scheme   = this.config.elasticsearch.scheme;

        // データの関係を取得するためのテーブル群
        String bioProjectSubmissionTable = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.SUBMISSION.toString();
        String bioProjectStudyTable      = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.STUDY.toString();
        String bioSampleSampleTable      = TypeEnum.BIOSAMPLE.toString() + "_" + TypeEnum.SAMPLE.toString();
        String studySubmissionTable      = TypeEnum.STUDY.toString() + "_" + TypeEnum.SUBMISSION.toString();
        String submissionExperimentTable = TypeEnum.SUBMISSION.toString() + "_" + TypeEnum.EXPERIMENT.toString();
        String submissionAnalysisTable   = TypeEnum.SUBMISSION.toString() + "_" + TypeEnum.ANALYSIS.toString();
        String experimentRunTable        = TypeEnum.EXPERIMENT.toString() + "_" + TypeEnum.RUN.toString();
        String bioSampleExperimentTable  = TypeEnum.BIOSAMPLE.toString() + "_" + TypeEnum.EXPERIMENT.toString();
        String sampleExperimentTable     = TypeEnum.SAMPLE.toString() + "_" + TypeEnum.EXPERIMENT.toString();
        String runBioSampleTable         = TypeEnum.RUN.toString() + "_" + TypeEnum.BIOSAMPLE.toString();

        // XMLのパス群
        String draPath = this.config.file.path.dra;

        //  一度に登録するレコード数
        int maximumRecord = this.config.other.maximumRecord;

        // 使用するObjectMapper
        ObjectMapper mapper = jsonParser.getMapper();

        File draDir = new File(draPath);
        List<File> draChildrenDirList = Arrays.asList(Objects.requireNonNull(draDir.listFiles()));

        Map<String, List<File>> pathMap = new HashMap<>();

        for(File draChildrenDir : draChildrenDirList) {
            String parentPath = draChildrenDir.getAbsolutePath();
            List<File> grandchildDirList = Arrays.asList(Objects.requireNonNull(draChildrenDir.listFiles()));

            pathMap.put(parentPath, grandchildDirList);
        }

        String studyIndexName      = TypeEnum.STUDY.getType();
        String sampleIndexName     = TypeEnum.SAMPLE.getType();

        String submissionIndexName = TypeEnum.SUBMISSION.getType();
        String experimentIndexName = TypeEnum.EXPERIMENT.getType();
        String analysisIndexName   = TypeEnum.ANALYSIS.getType();
        String runIndexName        = TypeEnum.RUN.getType();

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
                    File submissionXmlFile = new File(submissionXml);
                    File experimentXmlFile = new File(experimentXml);
                    File analysisXmlFile = new File(analysisXml);
                    File runXmlFile = new File(runXml);

                    if(studyXmlFile.exists()) {
                        List<StudyBean> studyBeanList = studyParser.parse(studyXml);

                        studyBeanList.forEach(bean -> {
                            String accession = bean.getIdentifier();
                            List<DBXrefsBean> bioProjectStudyList = sraAccessionsDao.selRelation(accession, bioProjectStudyTable, TypeEnum.STUDY, TypeEnum.BIOPROJECT);
                            List<DBXrefsBean> studySubmissionList = sraAccessionsDao.selRelation(accession, studySubmissionTable, TypeEnum.STUDY, TypeEnum.SUBMISSION);
                            bioProjectStudyList.addAll(studySubmissionList);

                            bean.setDbXrefs(bioProjectStudyList);
                            studyJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean , mapper));
                        });
                    }

                    if(sampleXmlFile.exists()) {
                        List<SampleBean> sampleBeanList = sampleParser.parse(sampleXml);

                        sampleBeanList.forEach(bean -> {
                            String accession = bean.getIdentifier();
                            List<DBXrefsBean> bioSampleSampleList = sraAccessionsDao.selRelation(accession, bioSampleSampleTable, TypeEnum.SAMPLE, TypeEnum.BIOSAMPLE);

                            bean.setDbXrefs(bioSampleSampleList);
                            sampleJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
                        });
                    }

                    List<SubmissionBean> submissionBeanList = new ArrayList<>();

                    if(submissionXmlFile.exists()) {
                        submissionBeanList = submissionParser.parse(submissionXml);
                    }

                    List<ExperimentBean> experimentBeanList  = new ArrayList<>();

                    if(experimentXmlFile.exists()) {
                        experimentBeanList = experimentParser.parse(experimentXml);
                    }

                    List<AnalysisBean> analysisBeanList = new ArrayList<>();
                    if(analysisXmlFile.exists()) {
                        analysisBeanList = analysisParser.parse(analysisXml);
                    }

                    List<RunBean> runBeanList = new ArrayList<>();
                    if(runXmlFile.exists()) {
                        runBeanList = runParser.parse(runXml);
                    }

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

                    analysisBeanList.forEach(bean -> {
                        String accession = bean.getIdentifier();
                        List<DBXrefsBean> submissionAnalysisList = sraAccessionsDao.selRelation(accession, submissionAnalysisTable, TypeEnum.ANALYSIS, TypeEnum.SUBMISSION);

                        bean.setDbXrefs(submissionAnalysisList);
                        analysisJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
                    });

                    runBeanList.forEach(bean -> {
                        String accession = bean.getIdentifier();
                        List<DBXrefsBean> experimentRunList = sraAccessionsDao.selRelation(accession, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);
                        List<DBXrefsBean> runBioSampleList  = sraAccessionsDao.selRelation(accession, runBioSampleTable, TypeEnum.RUN, TypeEnum.BIOSAMPLE);

                        experimentRunList.addAll(runBioSampleList);

                        bean.setDbXrefs(experimentRunList);
                        runJsonMap.put(bean.getIdentifier(), jsonParser.parse(bean, mapper));
                    });
                });

                if(studyJsonMap.size() > 0 ) {
                    searchModule.bulkInsert(hostname, port, scheme, studyIndexName, studyJsonMap);
                }

                if(sampleJsonMap.size() > 0) {
                    searchModule.bulkInsert(hostname, port, scheme, sampleIndexName, sampleJsonMap);
                }

                if(submissionJsonMap.size() > 0) {
                    searchModule.bulkInsert(hostname, port, scheme, submissionIndexName, submissionJsonMap);
                }

                if(experimentJsonMap.size() > 0) {
                    searchModule.bulkInsert(hostname, port, scheme, experimentIndexName, experimentJsonMap);
                }

                if(analysisJsonMap.size() > 0) {
                    searchModule.bulkInsert(hostname, port, scheme, analysisIndexName, analysisJsonMap);
                }

                if(runJsonMap.size() > 0) {
                    searchModule.bulkInsert(hostname, port, scheme, runIndexName, runJsonMap);
                }
            });
        }
    }

    /**
     * ElasticsearchにJGAのデータを登録する.
     */
    public void registerJGA() {
        var studyIndexName   = TypeEnum.JGA_STUDY.getType();
        var dataSetIndexName = TypeEnum.JGA_DATASET.getType();
        var policyIndexName  = TypeEnum.JGA_POLICY.getType();
        var dacIndexName     = TypeEnum.JGA_DAC.getType();

        var xmlPath     = this.config.file.path.jga;
        var studyPath   = xmlPath + FileNameEnum.JGA_STUDY_XML.getFileName();
        var datasetPath = xmlPath + FileNameEnum.DATASET_XML.getFileName();
        var policyPath  = xmlPath + FileNameEnum.POLICY_XML.getFileName();
        var dacPath     = xmlPath + FileNameEnum.DAC_XML.getFileName();

        var studyList   = this.jgaService.getStudy(studyPath);
        var datasetList = this.jgaService.getDataset(datasetPath);
        var policyList  = this.jgaService.getPolicy(policyPath);
        var dacList     = this.jgaService.getDAC(dacPath);

        this.searchModule.deleteIndex("jga-*");

        if(studyList != null
        && studyList.size() > 0) {
            this.searchModule.bulkInsert(studyIndexName, studyList);
        }

        if(datasetList != null
        && datasetList.size() > 0) {
            this.searchModule.bulkInsert(dataSetIndexName, datasetList);
        }

        if(policyList != null
        && policyList.size() > 0) {
            this.searchModule.bulkInsert(policyIndexName, policyList);
        }

        if(dacList != null
        && dacList.size() > 0) {
            this.searchModule.bulkInsert(dacIndexName, dacList);
        }
    }
}
