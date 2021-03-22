package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.transact.dao.jga.JgaDateDao;
import com.ddbj.ld.app.transact.dao.jga.JgaRelationDao;
import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.OrganismEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.common.helper.DateHelper;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.common.DBXrefsBean;
import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.data.beans.common.SameAsBean;
import com.ddbj.ld.data.beans.dra.analysis.Analysis;
import com.ddbj.ld.data.beans.dra.analysis.AnalysisConverter;
import com.ddbj.ld.data.beans.dra.experiment.Experiment;
import com.ddbj.ld.data.beans.dra.experiment.ExperimentConverter;
import com.ddbj.ld.data.beans.dra.run.Run;
import com.ddbj.ld.data.beans.dra.run.RunConverter;
import com.ddbj.ld.data.beans.dra.sample.Sample;
import com.ddbj.ld.data.beans.dra.sample.SampleConverter;
import com.ddbj.ld.data.beans.dra.study.Study;
import com.ddbj.ld.data.beans.dra.study.StudyConverter;
import com.ddbj.ld.data.beans.dra.submission.Submission;
import com.ddbj.ld.data.beans.dra.submission.SubmissionConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DraService {

    private final ParserHelper parserHelper;
    private final DateHelper dateHelper;
    private final UrlHelper urlHelper;
    private final JgaRelationDao jgaRelationDao;
    private final JgaDateDao jgaDateDao;
    private SRAAccessionsDao sraAccessionsDao;

    // データの関係を取得するためのテーブル群
    private final String bioProjectSubmissionTable = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.SUBMISSION.toString();
    private final String bioProjectStudyTable      = TypeEnum.BIOPROJECT.toString() + "_" + TypeEnum.STUDY.toString();
    private final String bioSampleSampleTable      = TypeEnum.BIOSAMPLE.toString()  + "_" + TypeEnum.SAMPLE.toString();
    private final String studySubmissionTable      = TypeEnum.STUDY.toString()      + "_" + TypeEnum.SUBMISSION.toString();
    private final String submissionExperimentTable = TypeEnum.SUBMISSION.toString() + "_" + TypeEnum.EXPERIMENT.toString();
    private final String submissionAnalysisTable   = TypeEnum.SUBMISSION.toString() + "_" + TypeEnum.ANALYSIS.toString();
    private final String experimentRunTable        = TypeEnum.EXPERIMENT.toString() + "_" + TypeEnum.RUN.toString();
    private final String bioSampleExperimentTable  = TypeEnum.BIOSAMPLE.toString()  + "_" + TypeEnum.EXPERIMENT.toString();
    private final String sampleExperimentTable     = TypeEnum.SAMPLE.toString()     + "_" + TypeEnum.EXPERIMENT.toString();
    private final String runBioSampleTable         = TypeEnum.RUN.toString()        + "_" + TypeEnum.BIOSAMPLE.toString();

    public List<JsonBean> getAnalysis(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_ANALYSIS_START.getItem();
            var endTag    = XmlTagEnum.DRA_ANALYSIS_END.getItem();

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入る可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag)) {
                    var obj = XML.toJSONObject(sb.toString());

                    // 一部のプロパティを配列にするために増やしたタグ由来のブランクの項目を削除
                    var json = replaceJson(obj);

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    Analysis properties = this.getAnalysisProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // JsonBean設定項目の取得
                    var analysis = properties.getAnalysis();

                    // accesion取得
                    var identifier = analysis.getAccession();

                    // Title取得
                    var title = analysis.getTitle();

                    // Description 取得
                    var description = analysis.getDescription();

                    // FIXME nameのマッピング
                    String name = null;

                    // typeの設定
                    var type = TypeEnum.ANALYSIS.getType();

                    // dra-analysis/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // FIXME Mapping
                    List<SameAsBean> sameAs = null;
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // FIXME Organismとする項目の確認が必要
                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();
                    var organism     = this.parserHelper.getOrganism(organismName, organismIdentifier);

                    // FIXME BioSampleとの関係も明らかにする
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var analysisXrefs = this.sraAccessionsDao.selRelation(identifier, submissionAnalysisTable, TypeEnum.ANALYSIS, TypeEnum.SUBMISSION);
                    dbXrefs.addAll(analysisXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    // FIXME 日付のデータの取得元を明らかにし、日付のデータを取得できるようにする
                    String dateCreated = null;
                    String dateModified = null;
                    String datePublished = null;

                    var bean = new JsonBean(
                            identifier,
                            title,
                            description,
                            name,
                            type,
                            url,
                            sameAs,
                            isPartOf,
                            organism,
                            dbXrefs,
                            properties,
                            distribution,
                            dateCreated,
                            dateModified,
                            datePublished
                    );

                    jsonList.add(bean);
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    public List<JsonBean> getExperiment(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_EXPERIMENT_START.getItem();
            var endTag    = XmlTagEnum.DRA_EXPERIMENT_END.getItem();

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入る可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag)) {
                    var obj = XML.toJSONObject(sb.toString());

                    // 一部のプロパティを配列にするために増やしたタグ由来のブランクの項目を削除
                    var json = replaceJson(obj);

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    Experiment properties = this.getExperimentProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // JsonBean設定項目の取得
                    var experiment = properties.getExperiment();

                    // accesion取得
                    var identifier = experiment.getAccession();

                    // Title取得
                    var title = experiment.getTitle();

                    // Description 取得
                    var design = experiment.getDesign();
                    var librarydescriptor = design.getLibraryDescriptor();
                    var targetloci = librarydescriptor.getTargetedLoci();


                    String description = "";
                    if (targetloci != null) {
                        var locus = targetloci.getLocus();
                        description = locus.get(0).getDescription();
                    }

                    // FIXME nameのマッピング
                    String name = null;

                    // typeの設定
                    var type = TypeEnum.EXPERIMENT.getType();

                    // dra-experiment/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // FIXME Mapping
                    List<SameAsBean> sameAs = null;
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // FIXME Organismとする項目の確認が必要
                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();
                    var organism     = this.parserHelper.getOrganism(organismName, organismIdentifier);

                    // FIXME BioSampleとの関係も明らかにする
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var submissionExperimentXrefs = this.sraAccessionsDao.selRelation(identifier, submissionExperimentTable, TypeEnum.EXPERIMENT, TypeEnum.SUBMISSION);
                    var bioSampleExperimentXrefs  = this.sraAccessionsDao.selRelation(identifier, bioSampleExperimentTable, TypeEnum.EXPERIMENT, TypeEnum.BIOSAMPLE);
                    var sampleExperimentXrefs     = this.sraAccessionsDao.selRelation(identifier, sampleExperimentTable, TypeEnum.EXPERIMENT, TypeEnum.SAMPLE);
                    var experimentRunXrefs        = this.sraAccessionsDao.selRelation(identifier, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);

                    dbXrefs.addAll(submissionExperimentXrefs);
                    dbXrefs.addAll(bioSampleExperimentXrefs);
                    dbXrefs.addAll(sampleExperimentXrefs);
                    dbXrefs.addAll(experimentRunXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    // FIXME 日付のデータの取得元を明らかにし、日付のデータを取得できるようにする
                    String dateCreated = null;
                    String dateModified = null;
                    String datePublished = null;

                    var bean = new JsonBean(
                            identifier,
                            title,
                            description,
                            name,
                            type,
                            url,
                            sameAs,
                            isPartOf,
                            organism,
                            dbXrefs,
                            properties,
                            distribution,
                            dateCreated,
                            dateModified,
                            datePublished
                    );

                    jsonList.add(bean);
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    public List<JsonBean> getRun(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_RUN_START.getItem();
            var endTag    = XmlTagEnum.DRA_RUN_END.getItem();

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入る可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag)) {
                    var obj = XML.toJSONObject(sb.toString());

                    // 一部のプロパティを配列にするために増やしたタグ由来のブランクの項目を削除
                    var json = replaceJson(obj);

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    Run properties = this.getRunProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // JsonBean設定項目の取得
                    var run = properties.getRun();

                    // accesion取得
                    var identifier = run.getAccession();

                    // Title取得
                    var title = run.getTitle();

                    // FIXME:Description 取得
                    String description = null;

                    // FIXME nameのマッピング
                    String name = null;
                    var type = TypeEnum.RUN.getType();

                    // dra-run/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // FIXME Mapping
                    List<SameAsBean> sameAs = null;
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // FIXME Organismとする項目の確認が必要
                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();
                    var organism     = this.parserHelper.getOrganism(organismName, organismIdentifier);

                    // FIXME BioSampleとの関係も明らかにする
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var experimentRunXrefs = this.sraAccessionsDao.selRelation(identifier, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);
                    var runBioSampleXrefs  = this.sraAccessionsDao.selRelation(identifier, runBioSampleTable, TypeEnum.RUN, TypeEnum.BIOSAMPLE);
                    dbXrefs.addAll(experimentRunXrefs);
                    dbXrefs.addAll(runBioSampleXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    // FIXME 日付のデータの取得元を明らかにし、日付のデータを取得できるようにする
                    String dateCreated = null;
                    String dateModified = null;
                    String datePublished = null;

                    var bean = new JsonBean(
                            identifier,
                            title,
                            description,
                            name,
                            type,
                            url,
                            sameAs,
                            isPartOf,
                            organism,
                            dbXrefs,
                            properties,
                            distribution,
                            dateCreated,
                            dateModified,
                            datePublished
                    );

                    jsonList.add(bean);
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    public List<JsonBean> getSubmission(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_SUBMISSION_START.getItem();
            var endTag    = XmlTagEnum.DRA_SUBMISSION_END.getItem();

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入る可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag) || line.matches("^(<SUBMISSION).*(/>)$")) {
                    var obj = XML.toJSONObject(sb.toString());

                    // 一部のプロパティを配列にするために増やしたタグ由来のブランクの項目を削除
                    var json = replaceJson(obj);

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    Submission properties = this.getSubmissionProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // JsonBean設定項目の取得
                    var submission = properties.getSubmission();

                    // accesion取得
                    var identifier = submission.getAccession();

                    // Title取得
                    var title = submission.getTitle();

                    // FIXME:Description 取得
                    String description = null;

                    // FIXME nameのマッピング
                    String name = null;
                    var type = TypeEnum.SUBMISSION.getType();

                    // dra-submission/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // FIXME Mapping
                    List<SameAsBean> sameAs = null;
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // FIXME Organismとする項目の確認が必要
                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();
                    var organism     = this.parserHelper.getOrganism(organismName, organismIdentifier);

                    // FIXME BioSampleとの関係も明らかにする
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var bioProjectSubmissionXrefs = this.sraAccessionsDao.selRelation(identifier, bioProjectSubmissionTable, TypeEnum.SUBMISSION, TypeEnum.BIOPROJECT);
                    var studySubmissionXrefs      = this.sraAccessionsDao.selRelation(identifier, studySubmissionTable, TypeEnum.SUBMISSION, TypeEnum.STUDY);
                    var submissionExperimentXrefs = this.sraAccessionsDao.selRelation(identifier, submissionExperimentTable, TypeEnum.SUBMISSION, TypeEnum.EXPERIMENT);
                    var submissionAnalysisXrefs   = this.sraAccessionsDao.selRelation(identifier, submissionAnalysisTable, TypeEnum.SUBMISSION, TypeEnum.ANALYSIS);

                    dbXrefs.addAll(bioProjectSubmissionXrefs);
                    dbXrefs.addAll(studySubmissionXrefs);
                    dbXrefs.addAll(submissionExperimentXrefs);
                    dbXrefs.addAll(submissionAnalysisXrefs);

                    var distribution = this.parserHelper.getDistribution(type, identifier);
                    // FIXME 日付のデータの取得元を明らかにし、日付のデータを取得できるようにする
                    String dateCreated = null;
                    String dateModified = null;
                    String datePublished = null;

                    var bean = new JsonBean(
                            identifier,
                            title,
                            description,
                            name,
                            type,
                            url,
                            sameAs,
                            isPartOf,
                            organism,
                            dbXrefs,
                            properties,
                            distribution,
                            dateCreated,
                            dateModified,
                            datePublished
                    );

                    jsonList.add(bean);
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    public List<JsonBean> getSample(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_SAMPLE_START.getItem();
            var endTag    = XmlTagEnum.DRA_SAMPLE_END.getItem();

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入る可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag)) {
                    var obj = XML.toJSONObject(sb.toString());

                    // 一部のプロパティを配列にするために増やしたタグ由来のブランクの項目を削除
                    var json = getJson(obj);

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    Sample properties = this.getSampleProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // JsonBean設定項目の取得
                    var sample = properties.getSample();

                    // accesion取得
                    var identifier = sample.getAccession();

                    // Title取得
                    var title = sample.getTitle();

                    // Description 取得
                    var description = sample.getDescription();

                    // FIXME nameのマッピング
                    String name = null;

                    // typeの設定
                    var type = TypeEnum.SAMPLE.getType();

                    // dra-sample/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // FIXME Mapping
                    List<SameAsBean> sameAs = null;
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // FIXME Organismとする項目の確認が必要
                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();
                    var organism     = this.parserHelper.getOrganism(organismName, organismIdentifier);

                    // FIXME BioSampleとの関係も明らかにする
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var bioSampleSampleXrefs = this.sraAccessionsDao.selRelation(identifier, bioSampleSampleTable, TypeEnum.SAMPLE, TypeEnum.BIOSAMPLE);

                    dbXrefs.addAll(bioSampleSampleXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    // FIXME 日付のデータの取得元を明らかにし、日付のデータを取得できるようにする
                    String dateCreated = null;
                    String dateModified = null;
                    String datePublished = null;

                    var bean = new JsonBean(
                            identifier,
                            title,
                            description,
                            name,
                            type,
                            url,
                            sameAs,
                            isPartOf,
                            organism,
                            dbXrefs,
                            properties,
                            distribution,
                            dateCreated,
                            dateModified,
                            datePublished
                    );

                    jsonList.add(bean);
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    private String getJson(JSONObject obj) {
        return replaceJson(obj);
    }

    public List<JsonBean> getStudy(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_STUDY_START.getItem();
            var endTag    = XmlTagEnum.DRA_STUDY_END.getItem();

            while((line = br.readLine()) != null) {
                // 開始要素を判断する
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2つ以上入る可能性がある項目は2つ以上タグが存在するようにし、Json化したときにプロパティが配列になるようにする
                if(line.contains(endTag)) {
                    var obj = XML.toJSONObject(sb.toString());

                    // 一部のプロパティを配列にするために増やしたタグ由来のブランクの項目を削除
                    var json = replaceJson(obj);

                    // Json文字列を項目取得用、バリデーション用にBean化する
                    // Beanにない項目がある場合はエラーを出力する
                    Study properties = this.getStudyProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // JsonBean設定項目の取得
                    var study = properties.getStudy();

                    // accesion取得
                    var identifier = study.getAccession();

                    // Title取得
                    var descriptor = study.getDescriptor();
                    var title = descriptor.getStudyTitle();

                    // Description 取得
                    var description = descriptor.getStudyDescription();

                    // FIXME nameのマッピング
                    String name = null;
                    var type = TypeEnum.STUDY.getType();

                    // dra-study/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // FIXME Mapping
                    List<SameAsBean> sameAs = null;
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // FIXME Organismとする項目の確認が必要
                    var organismName       = OrganismEnum.HOMO_SAPIENS_NAME.getItem();
                    var organismIdentifier = OrganismEnum.HOMO_SAPIENS_IDENTIFIER.getItem();
                    var organism     = this.parserHelper.getOrganism(organismName, organismIdentifier);

                    // FIXME BioSampleとの関係も明らかにする
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var bioProjectStudyXrefs = this.sraAccessionsDao.selRelation(identifier, bioProjectStudyTable, TypeEnum.STUDY, TypeEnum.BIOPROJECT);
                    var studySubmissionXrefs = this.sraAccessionsDao.selRelation(identifier, studySubmissionTable, TypeEnum.STUDY, TypeEnum.SUBMISSION);

                    dbXrefs.addAll(bioProjectStudyXrefs);
                    dbXrefs.addAll(studySubmissionXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    // FIXME 日付のデータの取得元を明らかにし、日付のデータを取得できるようにする
                    String dateCreated = null;
                    String dateModified = null;
                    String datePublished = null;

                    var bean = new JsonBean(
                            identifier,
                            title,
                            description,
                            name,
                            type,
                            url,
                            sameAs,
                            isPartOf,
                            organism,
                            dbXrefs,
                            properties,
                            distribution,
                            dateCreated,
                            dateModified,
                            datePublished
                    );

                    jsonList.add(bean);
                }
            }

            return jsonList;

        } catch (IOException e) {
            log.error("Not exists file:" + xmlPath);

            return null;
        }
    }

    private String replaceJson(JSONObject obj) {
        return obj.toString()
                .replaceAll("/\"\",{2,}/ ", "")
                .replaceAll("\\[\"\",", "\\[")
                .replaceAll(",\"\",", ",")
                .replaceAll("\\[\"\"]", "\\[]")
                .replaceAll("\"\",\\{", "{")
                .replaceAll(",\"Data\":\\[]", "")
                .replaceAll(",\"Data\":\\[\"\",\"\"]", "")
                .replaceAll(",\"Data\":\"\"", "");
    }

    private Analysis getAnalysisProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            return AnalysisConverter.fromJsonString(json);
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }

    private Experiment getExperimentProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            return ExperimentConverter.fromJsonString(json);
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }

    private Run getRunProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            return RunConverter.fromJsonString(json);
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }

    private Sample getSampleProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            return SampleConverter.fromJsonString(json);
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }

    private Study getStudyProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            return StudyConverter.fromJsonString(json);
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }

    private Submission getSubmissionProperties(
            final String json,
            final String xmlPath
    ) {
        try {
            return SubmissionConverter.fromJsonString(json);
        } catch (IOException e) {
            log.error("convert json to bean:" + json);
            log.error("xml file path:" + xmlPath);
            log.error(e.getLocalizedMessage());

            return null;
        }
    }
}
