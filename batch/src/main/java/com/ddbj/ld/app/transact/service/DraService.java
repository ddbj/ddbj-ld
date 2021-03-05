package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.dra.analysis.Analysis;
import com.ddbj.ld.data.beans.dra.analysis.AnalysisConverter;
import com.ddbj.ld.data.beans.dra.common.ID;
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
    private final UrlHelper urlHelper;
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
                    var json = XML.toJSONObject(sb.toString()).toString();

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

                    // name 取得
                    String name = analysis.getAlias();

                    // typeの設定
                    var type = TypeEnum.ANALYSIS.getType();

                    // dra-analysis/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
                    List<SameAsBean> sameAs = new ArrayList<>();

                    // "DRA"固定
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // 生物名とIDはSampleのみの情報であるため空情報を設定
                    OrganismBean organism = new OrganismBean();

                    //
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var analysisXrefs = this.sraAccessionsDao.selRelation(identifier, submissionAnalysisTable, TypeEnum.ANALYSIS, TypeEnum.SUBMISSION);
                    dbXrefs.addAll(analysisXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    // SRA_Accessions.tabから日付のデータを取得
                    DatesBean datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.ANALYSIS.toString());
                    String dateCreated = datas.getDateCreated();
                    String dateModified = datas.getDateModified();
                    String datePublished = datas.getDatePublished();

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
                    var json = XML.toJSONObject(sb.toString()).toString();

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

                    // name 取得
                    String name = experiment.getAlias();

                    // typeの設定
                    var type = TypeEnum.EXPERIMENT.getType();

                    // dra-experiment/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
                    List<SameAsBean> sameAs = new ArrayList<>();

                    // "DRA"固定
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // 生物名とIDはSampleのみの情報であるため空情報を設定
                    OrganismBean organism = new OrganismBean();

                    //
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

                    // SRA_Accessions.tabから日付のデータを取得
                    DatesBean datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.EXPERIMENT.toString());
                    String dateCreated = datas.getDateCreated();
                    String dateModified = datas.getDateModified();
                    String datePublished = datas.getDatePublished();

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
                    var json = XML.toJSONObject(sb.toString()).toString();

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

                    // Description に該当するデータは存在しないためrunではnullを設定
                    String description = null;

                    // name 取得
                    String name = run.getAlias();

                    // typeの設定
                    var type = TypeEnum.RUN.getType();

                    // dra-run/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
                    List<SameAsBean> sameAs = new ArrayList<>();

                    // "DRA"固定
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // 生物名とIDはSampleのみの情報であるため空情報を設定
                    OrganismBean organism = new OrganismBean();

                    //
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var experimentRunXrefs = this.sraAccessionsDao.selRelation(identifier, experimentRunTable, TypeEnum.EXPERIMENT, TypeEnum.RUN);
                    var runBioSampleXrefs  = this.sraAccessionsDao.selRelation(identifier, runBioSampleTable, TypeEnum.RUN, TypeEnum.BIOSAMPLE);
                    dbXrefs.addAll(experimentRunXrefs);
                    dbXrefs.addAll(runBioSampleXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    // SRA_Accessions.tabから日付のデータを取得
                    DatesBean datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.RUN.toString());
                    String dateCreated = datas.getDateCreated();
                    String dateModified = datas.getDateModified();
                    String datePublished = datas.getDatePublished();

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
                    var json = XML.toJSONObject(sb.toString()).toString();

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

                    // Description に該当するデータは存在しないためsubmissionではnullを設定
                    String description = null;

                    // name 設定
                    String name = submission.getAlias();

                    // typeの設定
                    var type = TypeEnum.SUBMISSION.getType();

                    // dra-submission/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // sameAs に該当するデータは存在しないためanalysisでは空情報を設定
                    List<SameAsBean> sameAs = new ArrayList<>();

                    // "DRA"固定
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // 生物名とIDはSampleのみの情報であるため空情報を設定
                    OrganismBean organism = new OrganismBean();

                    //
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
                    // SRA_Accessions.tabから日付のデータを取得
                    DatesBean datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.SUBMISSION.toString());
                    String dateCreated = datas.getDateCreated();
                    String dateModified = datas.getDateModified();
                    String datePublished = datas.getDatePublished();

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
                    var json = XML.toJSONObject(sb.toString()).toString();

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

                    // name 取得
                    String name = sample.getAlias();

                    // typeの設定
                    var type = TypeEnum.SAMPLE.getType();

                    // dra-sample/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // 自分と同値の情報を保持するデータを指定
                    var externalid = sample.getIdentifiers().getExternalID();
                    List<SameAsBean> sameAs = null;
                    if (externalid != null) {
                        sameAs = getSameAsBeans(externalid, TypeEnum.BIOSAMPLE.getType());
                    }

                    // "DRA"固定
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // 生物名とIDを設定
                    var samplename = sample.getSampleName();
                    var organismName       = samplename.getScientificName();
                    var organismIdentifier = samplename.getTaxonID();
                    var organism     = this.parserHelper.getOrganism(organismName, organismIdentifier);

                    // dbxrefの設定
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var bioSampleSampleXrefs = this.sraAccessionsDao.selRelation(identifier, bioSampleSampleTable, TypeEnum.SAMPLE, TypeEnum.BIOSAMPLE);

                    dbXrefs.addAll(bioSampleSampleXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    // SRA_Accessions.tabから日付のデータを取得
                    DatesBean datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.SAMPLE.toString());
                    String dateCreated = datas.getDateCreated();
                    String dateModified = datas.getDateModified();
                    String datePublished = datas.getDatePublished();

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
                    var json = XML.toJSONObject(sb.toString()).toString();

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
                    String name = study.getCenterName();
                    var type = TypeEnum.STUDY.getType();

                    // dra-study/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // 自分と同値の情報を保持するBioProjectを指定
                    var externalid = study.getIdentifiers().getExternalID();
                    List<SameAsBean> sameAs = null;
                    if (externalid != null) {
                        sameAs = getSameAsBeans(externalid, TypeEnum.BIOPROJECT.getType());
                    }

                    // "DRA"固定
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // 生物名とIDはSampleのみの情報であるため空情報を設定
                    OrganismBean organism = new OrganismBean();

                    //
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var bioProjectStudyXrefs = this.sraAccessionsDao.selRelation(identifier, bioProjectStudyTable, TypeEnum.STUDY, TypeEnum.BIOPROJECT);
                    var studySubmissionXrefs = this.sraAccessionsDao.selRelation(identifier, studySubmissionTable, TypeEnum.STUDY, TypeEnum.SUBMISSION);

                    dbXrefs.addAll(bioProjectStudyXrefs);
                    dbXrefs.addAll(studySubmissionXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    /// SRA_Accessions.tabから日付のデータを取得
                    DatesBean datas = this.sraAccessionsDao.selDates(identifier, TypeEnum.STUDY.toString());
                    String dateCreated = datas.getDateCreated();
                    String dateModified = datas.getDateModified();
                    String datePublished = datas.getDatePublished();

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

    private List<SameAsBean> getSameAsBeans(List<ID> externalID, String type) {
        List<SameAsBean> sameAs = new ArrayList<>();

        for (int cnt = 0; cnt < externalID.size(); cnt++) {
            var sameAsName = externalID.get(cnt).getNamespace();
            var sameAsId =  externalID.get(cnt).getContent();
            var sameAsUrl = this.urlHelper.getUrl(type, sameAsId);
            SameAsBean sab = new SameAsBean();
            sab.setIdentifier(sameAsName);
            sab.setIdentifier(sameAsId);
            sab.setUrl(sameAsUrl);
            sameAs.add(sab);
        }
        return sameAs;
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
