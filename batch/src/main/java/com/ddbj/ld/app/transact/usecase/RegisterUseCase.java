package com.ddbj.ld.app.transact.usecase;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.service.BioProjectService;
import com.ddbj.ld.app.transact.service.BioSampleService;
import com.ddbj.ld.app.transact.service.dra.*;
import com.ddbj.ld.common.annotation.UseCase;
import com.ddbj.ld.common.constants.FileNameEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.utility.BulkUtil;
import com.ddbj.ld.data.beans.common.JsonBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

/**
 * Elasticsearchに関する処理を行うユースケースクラス.
 */
@UseCase
@AllArgsConstructor
@Slf4j
@Deprecated
public class RegisterUseCase {
    private final ConfigSet config;

    private final BioProjectService bioProjectService;
    private final BioSampleService bioSampleService;

    // DRA service
    private final DraAnalysisService analysisService;
    private final DraExperimentService experimentservice;
    private final DraRunService runService;
    private final DraSubmissionService submissionService;
    private final DraSampleService sampleService;
    private final DraStudyService studyService;

    private final SearchModule searchModule;

    /**
     * ElasticsearchにBioSampleのデータを登録する.
     */
    public void registerBioSample(String date) {
        var index = TypeEnum.BIOSAMPLE.getType();
        if(this.searchModule.existsIndex(index)) {
            // データが既にあるなら、全削除して入れ直す
            this.searchModule.deleteIndex(index);
        }

        var path = !date.equals("") ? config.file.path.bioSample + "." + date : config.file.path.bioSample;
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
    public void registerDRA (String data) {
        // XMLのパス群
        Map<String, List<File>> pathMap = getPathListMap(data);
        for (String parentPath : pathMap.keySet()) {
            List<File> targetDirList = pathMap.get(parentPath);

            BulkUtil.extract(
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
                        studyList.addAll(this.studyService.getStudy(studyXmlFile.getPath()));
                    }

                    File sampleXmlFile = new File(targetDirPath + submission + FileNameEnum.SAMPLE_XML.getFileName());
                    if(sampleXmlFile.exists()) {
                        sampleList.addAll(this.sampleService.getSample(sampleXmlFile.getPath()));
                    }

                    File submissionXmlFile = new File(targetDirPath + submission + FileNameEnum.SUBMISSION_XML.getFileName());
                    if(submissionXmlFile.exists()) {
                        submissionList.addAll(this.submissionService.getSubmission(submissionXmlFile.getPath()));
                    }

                    File experimentXmlFile = new File(targetDirPath + submission + FileNameEnum.EXPERIMENT_XML.getFileName());
                    if(experimentXmlFile.exists()) {
                        experimentList.addAll(this.experimentservice.getExperiment(experimentXmlFile.getPath()));
                    }

                    File analysisXmlFile = new File(targetDirPath + submission + FileNameEnum.ANALYSIS_XML.getFileName());
                    if(analysisXmlFile.exists()) {
                        analysisList.addAll(this.analysisService.getAnalysis(analysisXmlFile.getPath()));
                    }

                    File runXmlFile = new File(targetDirPath + submission + FileNameEnum.RUN_XML.getFileName());
                    if(runXmlFile.exists()) {
                        runList.addAll(this.runService.getRun(runXmlFile.getPath()));
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

    private Map<String, List<File>> getPathListMap(String date) {
        // XMLのパス群
        String path = !date.equals("") ? config.file.path.dra + "." + date : config.file.path.dra;
        File draDir = new File(path);
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
