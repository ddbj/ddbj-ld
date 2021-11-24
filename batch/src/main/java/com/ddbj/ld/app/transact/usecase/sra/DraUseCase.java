package com.ddbj.ld.app.transact.usecase.sra;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.transact.dao.sra.DraAccessionDao;
import com.ddbj.ld.app.transact.dao.sra.DraLiveListDao;
import com.ddbj.ld.app.transact.service.sra.*;
import com.ddbj.ld.common.annotation.UseCase;
import com.ddbj.ld.common.constants.FileNameEnum;
import com.ddbj.ld.data.beans.common.AccessionsBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
public class DraUseCase {

    private final ConfigSet config;

    private final DraLiveListDao draLiveListDao;
    private final DraAccessionDao draAccessionDao;

    private final SraSubmissionService submission;
    private final SraExperimentService experiment;
    private final SraAnalysisService analysis;
    private final SraRunService run;
    private final SraStudyService study;
    private final SraSampleService sample;

    public void registerAccessions() {
        log.info("Start registering DRA's relation to PostgreSQL");

        var rootDir = this.config.file.path.sra.ddbj;
        var submissionList = this.draLiveListDao.selSubmissionList();
        var recordList = new ArrayList<Object[]>();

        // 重複チェック用
        // たまにファイルが壊れレコードが重複しているため
        var duplicateCheck = new HashSet<String>();
        var duplicateAccessions = new HashSet<String>();

        this.draAccessionDao.deleteAll();

        for(var submission : submissionList) {
            var accession = submission.getAccession();
            var prefix = accession.substring(0, 6);
            var submissionDir = rootDir + "/" + prefix + "/" + accession + "/";

            var submissionXML = new File(submissionDir + accession + FileNameEnum.SUBMISSION_XML.fileName);
            var experimentXML = new File(submissionDir + accession + FileNameEnum.EXPERIMENT_XML.fileName);
            var analysisXML = new File(submissionDir + accession + FileNameEnum.ANALYSIS_XML.fileName);
            var runXML = new File(submissionDir + accession + FileNameEnum.RUN_XML.fileName);
            var studyXML = new File(submissionDir + accession + FileNameEnum.STUDY_XML.fileName);
            var sampleXML = new File(submissionDir + accession + FileNameEnum.SAMPLE_XML.fileName);

            // experimentだけrunに情報を追加するときにaccessionで引きたいためMap
            var experimentAccessionMap = new HashMap<String, AccessionsBean>();

            LocalDateTime received = null;
            String studyId = null;

            if(submissionXML.exists()) {
                var value = this.submission.getDraAccession(submissionXML.getPath());
                received = value.getReceived();

                var accessionPair = value.getAccession() + "," + accession;

                if(duplicateCheck.contains(accessionPair)) {
                    log.warn("Duplicate accession:{}", accessionPair);
                    duplicateAccessions.add(accessionPair);
                } else {
                    duplicateCheck.add(accessionPair);
                    recordList.add(this.beanToRecord(value));
                }
            }

            if(experimentXML.exists()) {
                experimentAccessionMap = this.experiment.getDraAccessionList(experimentXML.getPath());

                for (Map.Entry<String, AccessionsBean> entry : experimentAccessionMap.entrySet()) {
                    var value = entry.getValue();
                    value.setReceived(received);
                    studyId = value.getStudy();

                    // mapのほうにはreceivedを追加する必要はない

                    var accessionPair = value.getAccession() + "," + accession;

                    if(duplicateCheck.contains(accessionPair)) {
                        log.warn("Duplicate accession:{}", accessionPair);
                        duplicateAccessions.add(accessionPair);
                    } else {
                        duplicateCheck.add(accessionPair);
                        recordList.add(this.beanToRecord(value));
                    }
                }
            }

            if(analysisXML.exists()) {
                var accessionList = this.analysis.getDraAccessionList(analysisXML.getPath());

                for(var value : accessionList) {
                    value.setReceived(received);
                    value.setStudy(studyId);

                    var accessionPair = value.getAccession() + "," + accession;

                    if(duplicateCheck.contains(accessionPair)) {
                        log.warn("Duplicate accession:{}", accessionPair);
                        duplicateAccessions.add(accessionPair);
                    } else {
                        duplicateCheck.add(accessionPair);
                        recordList.add(this.beanToRecord(value));
                    }
                }
            }

            if(runXML.exists()) {
                var accessionList = this.run.getDraAccessionList(runXML.getPath());

                for(var value : accessionList) {
                    var experiment = experimentAccessionMap.get(value.getExperiment());
                    var sampleId = null == experiment ? null : experiment.getSample();
                    var bioSampleId = null == experiment ? null : experiment.getBioSample();
                    var bioProjectId = null == experiment ? null : experiment.getBioProject();

                    value.setReceived(received);
                    value.setStudy(studyId);
                    value.setSample(sampleId);
                    value.setBioSample(bioSampleId);
                    value.setBioProject(bioProjectId);

                    var accessionPair = value.getAccession() + "," + accession;

                    if(duplicateCheck.contains(accessionPair)) {
                        log.warn("Duplicate accession:{}", accessionPair);
                        duplicateAccessions.add(accessionPair);
                    } else {
                        duplicateCheck.add(accessionPair);
                        recordList.add(this.beanToRecord(value));
                    }
                }
            }

            if(studyXML.exists()) {
                var accessionList = this.study.getDraAccessionList(studyXML.getPath());

                for(var value : accessionList) {
                    value.setReceived(received);

                    var accessionPair = value.getAccession() + "," + accession;

                    if(duplicateCheck.contains(accessionPair)) {
                        log.warn("Duplicate accession:{}", accessionPair);
                        duplicateAccessions.add(accessionPair);
                    } else {
                        duplicateCheck.add(accessionPair);
                        recordList.add(this.beanToRecord(value));
                    }
                }
            }

            if(sampleXML.exists()) {
                var accessionList = this.sample.getDraAccessionList(sampleXML.getPath());

                for(var value : accessionList) {
                    value.setReceived(received);

                    var accessionPair = value.getAccession() + "," + accession;

                    if(duplicateCheck.contains(accessionPair)) {
                        log.warn("Duplicate accession:{}", accessionPair);
                        duplicateAccessions.add(accessionPair);
                    } else {
                        duplicateCheck.add(accessionPair);
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
