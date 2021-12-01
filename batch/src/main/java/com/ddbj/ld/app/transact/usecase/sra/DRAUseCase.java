package com.ddbj.ld.app.transact.usecase.sra;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.dao.primary.sra.DRAAccessionDao;
import com.ddbj.ld.app.transact.dao.primary.sra.DRALiveListDao;
import com.ddbj.ld.app.transact.service.sra.*;
import com.ddbj.ld.common.annotation.UseCase;
import com.ddbj.ld.common.constants.FileNameEnum;
import com.ddbj.ld.data.beans.common.AccessionsBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@UseCase
@AllArgsConstructor
@Slf4j
public class DRAUseCase {

    private final ConfigSet config;

    private final DRALiveListDao draLiveListDao;
    private final DRAAccessionDao draAccessionDao;

    private final DRALiveListService draLiveList;
    private final SRASubmissionService submission;
    private final SRAExperimentService experiment;
    private final SRAAnalysisService analysis;
    private final SRARunService run;
    private final SRAStudyService study;
    private final SRASampleService sample;

    private final SearchModule searchModule;

    public void registerAccessions() {
        log.info("Start registering DRA's relation to PostgreSQL");

        this.draLiveList.registerLiveList();

        var rootDir = this.config.file.path.sra.ddbj;
        var submissionList = this.draLiveListDao.selSubmissionList();
        var recordList = new ArrayList<Object[]>();

        // 重複チェック用
        // たまにファイルが壊れレコードが重複しているため
        var duplicateCheck = new HashSet<String>();
        var duplicateAccessions = new HashSet<String>();

        this.draAccessionDao.deleteAll();

        for(var submission : submissionList) {
            var submissionId = submission.getAccession();
            var prefix = submissionId.substring(0, 6);
            var submissionDir = rootDir + "/" + prefix + "/" + submissionId + "/";

            var submissionXML = new File(submissionDir + submissionId + FileNameEnum.SUBMISSION_XML.fileName);
            var experimentXML = new File(submissionDir + submissionId + FileNameEnum.EXPERIMENT_XML.fileName);
            var analysisXML = new File(submissionDir + submissionId + FileNameEnum.ANALYSIS_XML.fileName);
            var runXML = new File(submissionDir + submissionId + FileNameEnum.RUN_XML.fileName);
            var studyXML = new File(submissionDir + submissionId + FileNameEnum.STUDY_XML.fileName);
            var sampleXML = new File(submissionDir + submissionId + FileNameEnum.SAMPLE_XML.fileName);

            // experimentだけrunに情報を追加するときにsubmissionIdで引きたいためMap
            var experimentAccessionMap = new HashMap<String, AccessionsBean>();

            LocalDateTime received = null;
            String studyId = null;

            if(submissionXML.exists()) {
                var value = this.submission.getDraAccession(submissionXML.getPath(), submissionId);
                received = value.getReceived();

                var submissionIdPair = value.getAccession() + "," + value.getSubmission();

                if(duplicateCheck.contains(submissionIdPair)) {
                    log.warn("Duplicate submissionId:{}", submissionIdPair);
                    duplicateAccessions.add(submissionIdPair);
                } else {
                    duplicateCheck.add(submissionIdPair);
                    recordList.add(this.beanToRecord(value));
                }
            }

            if(experimentXML.exists()) {
                experimentAccessionMap = this.experiment.getDraAccessionMap(experimentXML.getPath(), submissionId);

                for (Map.Entry<String, AccessionsBean> entry : experimentAccessionMap.entrySet()) {
                    var value = entry.getValue();
                    value.setReceived(received);
                    studyId = value.getStudy();

                    // mapのほうにはreceivedを追加する必要はない

                    var submissionIdPair = value.getAccession() + "," + value.getSubmission();

                    if(duplicateCheck.contains(submissionIdPair)) {
                        log.warn("Duplicate submissionId:{}", submissionIdPair);
                        duplicateAccessions.add(submissionIdPair);
                    } else {
                        duplicateCheck.add(submissionIdPair);
                        recordList.add(this.beanToRecord(value));
                    }
                }
            }

            if(analysisXML.exists()) {
                var submissionIdList = this.analysis.getDraAccessionList(analysisXML.getPath(), submissionId);

                for(var value : submissionIdList) {
                    value.setReceived(received);
                    value.setStudy(studyId);

                    var submissionIdPair = value.getAccession() + "," + value.getSubmission();

                    if(duplicateCheck.contains(submissionIdPair)) {
                        log.warn("Duplicate submissionId:{}", submissionIdPair);
                        duplicateAccessions.add(submissionIdPair);
                    } else {
                        duplicateCheck.add(submissionIdPair);
                        recordList.add(this.beanToRecord(value));
                    }
                }
            }

            if(runXML.exists()) {
                var submissionIdList = this.run.getDraAccessionList(runXML.getPath(), submissionId);

                for(var value : submissionIdList) {
                    var experiment = experimentAccessionMap.get(value.getExperiment());
                    var sampleId = null == experiment ? null : experiment.getSample();
                    var bioSampleId = null == experiment ? null : experiment.getBioSample();
                    var bioProjectId = null == experiment ? null : experiment.getBioProject();

                    value.setReceived(received);
                    value.setStudy(studyId);
                    value.setSample(sampleId);
                    value.setBioSample(bioSampleId);
                    value.setBioProject(bioProjectId);

                    var submissionIdPair = value.getAccession() + "," + value.getSubmission();

                    if(duplicateCheck.contains(submissionIdPair)) {
                        log.warn("Duplicate submissionId:{}", submissionIdPair);
                        duplicateAccessions.add(submissionIdPair);
                    } else {
                        duplicateCheck.add(submissionIdPair);
                        recordList.add(this.beanToRecord(value));
                    }
                }
            }

            if(studyXML.exists()) {
                var submissionIdList = this.study.getDraAccessionList(studyXML.getPath(), submissionId);

                for(var value : submissionIdList) {
                    value.setReceived(received);

                    var submissionIdPair = value.getAccession() + "," + value.getSubmission();

                    if(duplicateCheck.contains(submissionIdPair)) {
                        log.warn("Duplicate submissionId:{}", submissionIdPair);
                        duplicateAccessions.add(submissionIdPair);
                    } else {
                        duplicateCheck.add(submissionIdPair);
                        recordList.add(this.beanToRecord(value));
                    }
                }
            }

            if(sampleXML.exists()) {
                var submissionIdList = this.sample.getDraAccessionList(sampleXML.getPath(), submissionId);

                for(var value : submissionIdList) {
                    value.setReceived(received);

                    var submissionIdPair = value.getAccession() + "," + value.getSubmission();

                    if(duplicateCheck.contains(submissionIdPair)) {
                        log.warn("Duplicate submissionId:{}", submissionIdPair);
                        duplicateAccessions.add(submissionIdPair);
                    } else {
                        duplicateCheck.add(submissionIdPair);
                        recordList.add(this.beanToRecord(value));
                    }
                }
            }

            if(recordList.size() >= this.config.other.maximumRecord) {
                this.draAccessionDao.bulkInsert(recordList);
                // リセット
                recordList = new ArrayList<>();
            }
        }

        if(recordList.size() > 0) {
            this.draAccessionDao.bulkInsert(recordList);
        }

        log.info("Complete registering DRA's relation to PostgreSQL.");
    }

    public void register() {
        // DRA由来のレコードを削除
        var query = QueryBuilders.regexpQuery("identifier", "DR.*").rewrite("constant_score").caseInsensitive(true);
        var request = new DeleteByQueryRequest("sra-*").setQuery(query);
        this.searchModule.deleteByQuery(request);

        var rootDir = this.config.file.path.sra.ddbj;
        var submissionList = this.draLiveListDao.selSubmissionList();

        var requestList = new BulkRequest();

        // 固定値
        var maximumRecord = this.config.search.maximumRecord;

        for(var submission : submissionList) {
            var submissionId = submission.getAccession();
            var prefix = submissionId.substring(0, 6);
            var submissionDir = rootDir + "/" + prefix + "/" + submissionId + "/";

            var submissionXML = new File(submissionDir + submissionId + FileNameEnum.SUBMISSION_XML.fileName);
            var experimentXML = new File(submissionDir + submissionId + FileNameEnum.EXPERIMENT_XML.fileName);
            var analysisXML = new File(submissionDir + submissionId + FileNameEnum.ANALYSIS_XML.fileName);
            var runXML = new File(submissionDir + submissionId + FileNameEnum.RUN_XML.fileName);
            var studyXML = new File(submissionDir + submissionId + FileNameEnum.STUDY_XML.fileName);
            var sampleXML = new File(submissionDir + submissionId + FileNameEnum.SAMPLE_XML.fileName);

            if(submissionXML.exists()) {
                var submissions = this.submission.get(submissionXML.getPath());

                for(var value : submissions) {
                    requestList.add(value);
                }
            }

            if(experimentXML.exists()) {
                var experiments = this.experiment.get(experimentXML.getPath());

                for(var value : experiments) {
                    requestList.add(value);
                }
            }

            if(analysisXML.exists()) {
                var analysises = this.analysis.get(analysisXML.getPath());

                for(var value : analysises) {
                    requestList.add(value);
                }
            }

            if(runXML.exists()) {
                var runs = this.run.get(runXML.getPath());

                for(var value : runs) {
                    requestList.add(value);
                }
            }

            if(studyXML.exists()) {
                var studies = this.study.get(studyXML.getPath());

                for(var value : studies) {
                    requestList.add(value);
                }
            }

            if(sampleXML.exists()) {
                var samples = this.sample.get(sampleXML.getPath());

                for(var value : samples) {
                    requestList.add(value);
                }
            }

            if(requestList.numberOfActions() >= maximumRecord) {
                this.searchModule.bulkInsert(requestList);
                requestList = new BulkRequest();
            }
        }

        if(requestList.numberOfActions() > 0) {
            this.searchModule.bulkInsert(requestList);
        }
    }

    private Object[] beanToRecord(AccessionsBean bean) {
        return new Object[] {
                bean.getAccession(),
                bean.getSubmission(),
                bean.getStatus(),
                null == bean.getUpdated() ? null : Timestamp.valueOf(bean.getUpdated()),
                null == bean.getPublished() ? null : Timestamp.valueOf(bean.getPublished()),
                null == bean.getReceived() ? null : Timestamp.valueOf(bean.getReceived()),
                bean.getType(),
                bean.getCenter(),
                bean.getVisibility(),
                bean.getAlias(),
                bean.getExperiment(),
                bean.getSample(),
                bean.getStudy(),
                bean.getLoaded(),
                bean.getSpots(),
                bean.getBases(),
                bean.getMd5sum(),
                bean.getBioSample(),
                bean.getBioProject(),
                bean.getRePlacedBy()
        };
    }
}
