package com.ddbj.ld.app.transact.usecase;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.service.BioProjectService;
import com.ddbj.ld.app.transact.service.BioSampleService;
import com.ddbj.ld.app.transact.service.JgaService;
import com.ddbj.ld.app.transact.service.DraService;
import com.ddbj.ld.common.annotation.UseCase;
import com.ddbj.ld.common.constants.FileNameEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.helper.BulkHelper;
import com.ddbj.ld.data.beans.common.JsonBean;
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

    private final BioProjectService bioProjectService;
    private final BioSampleService bioSampleService;

    private final JgaService jgaService;
    private final DraService draService;

    private final SearchModule searchModule;

    /**
     * ElasticsearchにBioProjectのデータを登録する.
     */
    public void registerBioProject() {
        var index = TypeEnum.BIOPROJECT.getType();
        if(this.searchModule.existsIndex(index)) {
            // データが既にあるなら、全削除して入れ直す
            this.searchModule.deleteIndex(index);
        }

        //  一度に登録するレコード数
        var maximumRecord = this.config.other.maximumRecord;

        var path  = this.config.file.path.bioProject;

        var dir = new File(path);
        var fileList = Arrays.asList(Objects.requireNonNull(dir.listFiles()));
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
        var index = TypeEnum.BIOSAMPLE.getType();
        if(this.searchModule.existsIndex(index)) {
            // データが既にあるなら、全削除して入れ直す
            // FIXME:日付指定昨日いれたら削除処理を消す
            this.searchModule.deleteIndex(index);
        }

        var path = config.file.path.bioSample;
        bioSampleService.splitBioSample(path + FileNameEnum.BIOSAMPLE_XML.getFileName());

        var splitDir = new File(path + "/split/");
        var fileList = Arrays.asList(Objects.requireNonNull(splitDir.listFiles()));

        for(File file: fileList) {
            bioSampleService.getBioSample(file.getAbsolutePath());
            bioSampleService.printErrorInfo(file.getAbsolutePath());
        }
    }

    /**
     * ElasticsearchにDRAのデータを登録する.
     */
    public void registerDRA () {
        // XMLのパス群
        Map<String, List<File>> pathMap = getPathListMap();
        for (String parentPath : pathMap.keySet()) {
            List<File> targetDirList = pathMap.get(parentPath);

            BulkHelper.extract(
                    targetDirList,
                    this.config.other.maximumRecord, // 一度に登録するレコード数
                    _targetDirList -> {

                List<JsonBean> studyList = new ArrayList<>();
                List<JsonBean> sampleList = new ArrayList<>();
                List<JsonBean> submissionList = new ArrayList<>();
                List<JsonBean> experimentList = new ArrayList<>();
                List<JsonBean> analysisList = new ArrayList<>();
                List<JsonBean> runList = new ArrayList<>();

                _targetDirList.forEach(_targetDir -> {
                    String submission = _targetDir.getName();
                    String targetDirPath = parentPath + "/" + submission + "/";

                    File studyXmlFile = new File(targetDirPath + submission + FileNameEnum.STUDY_XML.getFileName());
                    if(studyXmlFile.exists()) {
                        studyList.addAll(this.draService.getStudy(studyXmlFile.getPath()));
                    }

                    File sampleXmlFile = new File(targetDirPath + submission + FileNameEnum.SAMPLE_XML.getFileName());
                    if(sampleXmlFile.exists()) {
                        sampleList.addAll(this.draService.getSample(sampleXmlFile.getPath()));
                    }

                    File submissionXmlFile = new File(targetDirPath + submission + FileNameEnum.SUBMISSION_XML.getFileName());
                    if(submissionXmlFile.exists()) {
                        submissionList.addAll(this.draService.getSubmission(submissionXmlFile.getPath()));
                    }

                    File experimentXmlFile = new File(targetDirPath + submission + FileNameEnum.EXPERIMENT_XML.getFileName());
                    if(experimentXmlFile.exists()) {
                        experimentList.addAll(this.draService.getExperiment(experimentXmlFile.getPath()));
                    }

                    File analysisXmlFile = new File(targetDirPath + submission + FileNameEnum.ANALYSIS_XML.getFileName());
                    if(analysisXmlFile.exists()) {
                        analysisList.addAll(this.draService.getAnalysis(analysisXmlFile.getPath()));
                    }

                    File runXmlFile = new File(targetDirPath + submission + FileNameEnum.RUN_XML.getFileName());
                    if(runXmlFile.exists()) {
                        runList.addAll(this.draService.getRun(runXmlFile.getPath()));
                    }
                });

                if(studyList.size() > 0) {
                    this.searchModule.bulkInsert(TypeEnum.STUDY.getType(), studyList);
                }

                if(sampleList.size() > 0) {
                    this.searchModule.bulkInsert(TypeEnum.SAMPLE.getType(), sampleList);
                }

                if(submissionList.size() > 0) {
                    this.searchModule.bulkInsert(TypeEnum.SUBMISSION.getType(), submissionList);
                }

                if(experimentList.size() > 0) {
                    this.searchModule.bulkInsert(TypeEnum.EXPERIMENT.getType(), experimentList);
                }

                if(analysisList.size() > 0) {
                    this.searchModule.bulkInsert(TypeEnum.ANALYSIS.getType(), analysisList);
                }

                if(runList.size() > 0) {
                    this.searchModule.bulkInsert(TypeEnum.RUN.getType(), runList);
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

    private Map<String, List<File>> getPathListMap() {
        // XMLのパス群
        String draPath = this.config.file.path.dra;
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
}
