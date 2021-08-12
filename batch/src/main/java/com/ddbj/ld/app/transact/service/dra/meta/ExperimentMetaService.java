package com.ddbj.ld.app.transact.service.dra.meta;

import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.dra.experiment.Experiment;
import com.ddbj.ld.data.beans.dra.experiment.ExperimentConverter;
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
public class ExperimentMetaService {
    private final ParserHelper parserHelper;
    private final UrlHelper urlHelper;
    private SRAAccessionsDao sraAccessionsDao;

    private final String submissionExperimentTable = TypeEnum.SUBMISSION + "_" + TypeEnum.EXPERIMENT;
    private final String bioSampleExperimentTable  = TypeEnum.BIOSAMPLE  + "_" + TypeEnum.EXPERIMENT;
    private final String sampleExperimentTable     = TypeEnum.SAMPLE     + "_" + TypeEnum.EXPERIMENT;
    private final String experimentRunTable        = TypeEnum.EXPERIMENT + "_" + TypeEnum.RUN;

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
                    Experiment properties = this.getProperties(json, xmlPath);

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

    private Experiment getProperties(
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
}