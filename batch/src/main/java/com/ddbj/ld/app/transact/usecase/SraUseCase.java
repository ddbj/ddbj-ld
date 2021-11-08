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
        var maximumRecord = this.config.search.maximumRecord;

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

        var target = String.format("NCBI_SRA_Metadata_Full_%s.tar.gz", date);
        var targetPath     = "/sra/reports/Metadata/" + target;
        var targetDist     = this.config.file.path.outDir + "/" + target;

        log.info("Download {}.", targetPath);
        // ダウンロード先ディレクトリがなければ作る
        this.fileModule.createDirectory(this.config.file.path.outDir);
        // ダウンロード
        this.fileModule.retrieveFile(this.config.file.ftp.ncbi, targetPath, targetDist);
        log.info("Complete download {}.", targetPath);

        log.info("Extract {}.", this.config.file.path.sra.fullXMLPath);

        // 既に解凍先ディレクトリがあるなら全部削除し作り直す
        this.fileModule.deleteRecursively(this.config.file.path.sra.fullXMLPath);
        this.fileModule.deleteRecursively(this.config.file.path.sra.accessionsPath);
        this.fileModule.createDirectory(this.config.file.path.sra.fullXMLPath);
        this.fileModule.createDirectory(this.config.file.path.sra.accessionsPath);
        // 解凍し、ダウンロードしたファイルの日付（引数のdate）をファイルに書き込む
        this.fileModule.extractSRA(targetDist, this.config.file.path.sra.fullXMLPath);
        this.fileModule.overwrite(this.config.file.path.sra.execDatePath, date);

        log.info("Complete extract {}.", this.config.file.path.sra.fullXMLPath);

        // ダウンロードしたファイルを削除
        this.fileModule.delete(targetDist);
    }

    public void getUpdatedMetadata(final String date) {

        if(null == date) {
            var message = "Date is null.";
            log.error(message);

            throw new DdbjException(message);
        }

        var target = String.format("NCBI_SRA_Metadata_%s.tar.gz", date);
        var targetDir      = "/sra/reports/Metadata/";
        var targetPath     = targetDir + target;
        var targetDist     = this.config.file.path.outDir + "/" + target;

        if(!this.fileModule.exists(this.config.file.ftp.ncbi, targetDir, target)) {
            // 対象のファイルがない場合、ログ（エラーではない）を出力して終了、処理空振り
            log.info("Not exists file: {}, today not updated", target);

            return;
        }

        log.info("Download {}.", targetPath);
        // ダウンロード先ディレクトリがなければ作る
        this.fileModule.createDirectory(this.config.file.path.outDir);
        // ダウンロード
        this.fileModule.retrieveFile(this.config.file.ftp.ncbi, targetPath, targetDist);
        log.info("Complete download {}.", targetPath);

        var yearDir = date.substring(0, 4);
        var monthDir = date.substring(4, 6);
        var dateDir= date.substring(6, 8);
        var updatedXMLDir = this.config.file.path.sra.basePath + "/" + yearDir + "/" + monthDir + "/" + dateDir + "/xml";
        log.info("Extract {}.", updatedXMLDir);

        // 既に解凍先ディレクトリがあるなら全部削除し作り直す
        this.fileModule.deleteRecursively(updatedXMLDir);
        this.fileModule.deleteRecursively(this.config.file.path.sra.accessionsPath);
        this.fileModule.createDirectory(updatedXMLDir);
        this.fileModule.createDirectory(this.config.file.path.sra.accessionsPath);
        // 解凍し、ダウンロードしたファイルの日付（引数のdate）をファイルに書き込む
        this.fileModule.extractSRA(targetDist, updatedXMLDir);
        this.fileModule.overwrite(this.config.file.path.sra.execDatePath, date);

        log.info("Complete extract {}.", updatedXMLDir);

        // ダウンロードしたファイルを削除
        this.fileModule.delete(targetDist);
    }

    public void update(final String date) {
        if(null == date) {
            var message = "Date is null.";
            log.error(message);

            throw new DdbjException(message);
        }

        // TODO
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
