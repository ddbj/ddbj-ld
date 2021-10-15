package com.ddbj.ld.app.transact.usecase;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.FileModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.service.sra.*;
import com.ddbj.ld.common.annotation.UseCase;
import com.ddbj.ld.common.constants.FileNameEnum;
import com.ddbj.ld.common.exception.DdbjException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

/**
 * DRAに関する処理を行うユースケースクラス.
 */
@UseCase
@AllArgsConstructor
@Slf4j
public class SraUseCase {
    private final ConfigSet config;

    private final SraSubmissionService submission;
    private final SraExperimentService experiment;
    private final SraAnalysisService analysis;
    private final SraRunService run;
    private final SraStudyService study;
    private final SraSampleService sample;

    private final SearchModule searchModule;
    private final FileModule fileModule;

    public void delete() {
        if(this.searchModule.existsIndex("sra-*")) {
            this.searchModule.deleteIndex("sra-*");
        }
    }

    public void register(
        final String path
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

        // パース失敗の結果を通知
        this.submission.noticeErrorInfo();
        this.experiment.noticeErrorInfo();
        this.analysis.noticeErrorInfo();
        this.run.noticeErrorInfo();
        this.study.noticeErrorInfo();
        this.sample.noticeErrorInfo();
    }

    public void validate(final String path) {
        // XMLのパス群
        var pathMap = this.getPathListMap(path);

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
                    this.submission.validate(submissionXML.getPath());
                }

                if(experimentXML.exists()) {
                    this.experiment.validate(experimentXML.getPath());
                }

                if(analysisXML.exists()) {
                    this.analysis.validate(analysisXML.getPath());
                }

                if(runXML.exists()) {
                    this.run.validate(runXML.getPath());
                }

                if(studyXML.exists()) {
                    this.study.validate(studyXML.getPath());
                }

                if(sampleXML.exists()) {
                    this.sample.validate(sampleXML.getPath());
                }
            }
        }

        // パース失敗の結果を通知
        this.submission.noticeErrorInfo();
        this.experiment.noticeErrorInfo();
        this.analysis.noticeErrorInfo();
        this.run.noticeErrorInfo();
        this.study.noticeErrorInfo();
        this.sample.noticeErrorInfo();
    }

    public void getMetadata(final String date) {

        if(null == date) {
            var message = "Date is null.";
            log.error(message);

            throw new DdbjException(message);
        }

        var fileList = this.fileModule.listFiles(this.config.file.ftp.ncbi, "/sra/reports/Metadata/");
        var startFile = String.format("NCBI_SRA_Metadata_Full_%s.tar.gz", date);
        var pattern = Pattern.compile("NCBI_SRA_Metadata_\\d{8,}.tar.gz");
        var isStart = false;
        var targetFileList = new ArrayList<String>();

        for(var file: fileList) {
            var fileName = file.getName();

            if(startFile.equals(fileName)) {
                isStart = true;
                targetFileList.add(fileName);
            }

            var m = pattern.matcher(fileName);

            if(isStart && m.find()) {
                targetFileList.add(file.getName());
            }
        }

        var latestTarget = targetFileList.get(targetFileList.size() - 1);
        var lp = Pattern.compile("\\d{8,}");
        var lm =  lp.matcher(latestTarget);

        if(lm.find()) {
            var latestDate  = lm.group();
            this.fileModule.overwrite(this.config.file.path.sra.execDatePath, latestDate);
        }

        for(var targetFile: targetFileList) {
            var retrieveTarget = "/sra/reports/Metadata/" + targetFile;
            var retrieveDist = this.config.file.path.outDir + "/" + targetFile;

            log.info("Download {}.", retrieveTarget);

            this.fileModule.retrieveFile(this.config.file.ftp.ncbi, retrieveTarget, retrieveDist);

            var extractDist = this.config.file.path.sra.fullXMLPath;

            this.fileModule.extractSRA(retrieveDist, extractDist);
            this.fileModule.delete(retrieveDist);

            log.info("Complete {}.", retrieveTarget);
        }

        var accessionsTarget = "/sra/reports/Metadata/SRA_Accessions.tab";

        this.fileModule.retrieveFile(this.config.file.ftp.ncbi, accessionsTarget, this.config.file.path.sra.accessions);
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
