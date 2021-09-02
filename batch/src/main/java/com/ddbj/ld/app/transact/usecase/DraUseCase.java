package com.ddbj.ld.app.transact.usecase;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.service.dra.*;
import com.ddbj.ld.common.annotation.UseCase;
import com.ddbj.ld.common.constants.FileNameEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;

import java.io.File;
import java.util.*;

/**
 * DRAに関する処理を行うユースケースクラス.
 */
@UseCase
@AllArgsConstructor
@Slf4j
public class DraUseCase {
    private final ConfigSet config;

    private final DraSubmissionService submission;
    private final DraExperimentService experiment;
    private final DraAnalysisService analysis;
    private final DraRunService run;
    private final DraStudyService study;
    private final DraSampleService sample;

    private final SearchModule searchModule;

    public void register(
        final String path,
        final boolean deletable
    ) {
        // XMLのパス群
        var pathMap = this.getPathListMap(path);

        // 各メタデータの登録するデータ
        var studyRequests = new BulkRequest();
        var sampleRequests = new BulkRequest();
        var submissionRequests = new BulkRequest();
        var experimentRequests = new BulkRequest();
        var analysisRequests = new BulkRequest();
        var runRequests = new BulkRequest();

        // 固定値
        var maximumRecord = this.config.other.maximumRecord;
        var indexPrefix = "dra-*";

        if(this.searchModule.existsIndex(indexPrefix) && deletable) {
            this.searchModule.deleteIndex(indexPrefix);
        }

        for (var parentPath : pathMap.keySet()) {
            var targetDirList = pathMap.get(parentPath);

            for(var targetDir: targetDirList) {
                // ディレクトリ名 = submissionのaccession
                var submissionId = targetDir.getName();
                var targetDirPath = parentPath + "/" + submissionId + "/";

                var submissionXML = new File(targetDirPath + submissionId + FileNameEnum.SUBMISSION_XML.fileName);
                var experimentXML = new File(targetDirPath + submissionId + FileNameEnum.EXPERIMENT_XML.fileName);
                var analysisXML = new File(targetDirPath + submissionId + FileNameEnum.ANALYSIS_XML.fileName);
                var runXML = new File(targetDirPath + submissionId + FileNameEnum.RUN_XML.fileName);
                var studyXML = new File(targetDirPath + submissionId + FileNameEnum.STUDY_XML.fileName);
                var sampleXML = new File(targetDirPath + submissionId + FileNameEnum.SAMPLE_XML.fileName);

                if(submissionXML.exists()) {
                    var submissions = this.submission.get(submissionXML.getPath());

                    for(var submission : submissions) {
                        submissionRequests.add(submission);
                    }
                }

                if(experimentXML.exists()) {
                    var experiments = this.experiment.get(experimentXML.getPath());

                    for(var experiment : experiments) {
                        experimentRequests.add(experiment);
                    }
                }

                if(analysisXML.exists()) {
                    var analysises = this.analysis.get(analysisXML.getPath());

                    for(var analysis : analysises) {
                        analysisRequests.add(analysis);
                    }
                }

                if(runXML.exists()) {
                    var runs = this.run.get(runXML.getPath());

                    for(var run : runs) {
                        runRequests.add(run);
                    }
                }

                if(studyXML.exists()) {
                    var studies = this.study.get(studyXML.getPath());

                    for(var study : studies) {
                        studyRequests.add(study);
                    }
                }

                if(sampleXML.exists()) {
                    var samples = this.sample.get(sampleXML.getPath());

                    for(var sample : samples) {
                        sampleRequests.add(sample);
                    }
                }

                if(submissionRequests.numberOfActions() >= maximumRecord) {
                    this.searchModule.bulkInsert(submissionRequests);
                    submissionRequests = new BulkRequest();
                }

                if(experimentRequests.numberOfActions() >= maximumRecord) {
                    this.searchModule.bulkInsert(experimentRequests);
                    experimentRequests = new BulkRequest();
                }

                if(analysisRequests.numberOfActions() >= maximumRecord) {
                    this.searchModule.bulkInsert(analysisRequests);
                    analysisRequests = new BulkRequest();
                }

                if(runRequests.numberOfActions() >= maximumRecord) {
                    this.searchModule.bulkInsert(runRequests);
                    runRequests = new BulkRequest();
                }

                if(studyRequests.numberOfActions() >= maximumRecord) {
                    this.searchModule.bulkInsert(studyRequests);
                    studyRequests = new BulkRequest();
                }

                if(sampleRequests.numberOfActions() >= maximumRecord) {
                    this.searchModule.bulkInsert(sampleRequests);
                    sampleRequests = new BulkRequest();
                }
            }

            if(submissionRequests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(submissionRequests);
            }

            if(experimentRequests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(experimentRequests);
            }

            if(analysisRequests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(analysisRequests);
            }

            if(runRequests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(runRequests);
            }

            if(studyRequests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(studyRequests);
            }

            if(sampleRequests.numberOfActions() > 0) {
                this.searchModule.bulkInsert(sampleRequests);
            }
        }

        // パース失敗の結果を出力する
        this.submission.printErrorInfo();
        this.experiment.printErrorInfo();
        this.analysis.printErrorInfo();
        this.run.printErrorInfo();
        this.study.printErrorInfo();
        this.sample.printErrorInfo();
    }

    private Map<String, List<File>> getPathListMap(final String path) {
        var draDir = new File(path);
        var draChildrenDirList = Arrays.asList(Objects.requireNonNull(draDir.listFiles()));
        var pathMap = new HashMap<String, List<File>>();

        for(var draChildrenDir : draChildrenDirList) {
            var parentPath = draChildrenDir.getAbsolutePath();
            var grandchildDirList = Arrays.asList(Objects.requireNonNull(draChildrenDir.listFiles()));

            pathMap.put(parentPath, grandchildDirList);
        }

        return pathMap;
    }
}
