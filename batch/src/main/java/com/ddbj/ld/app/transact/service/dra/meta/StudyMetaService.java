package com.ddbj.ld.app.transact.service.dra.meta;

import com.ddbj.ld.app.transact.dao.livelist.SRAAccessionsDao;
import com.ddbj.ld.common.constants.IsPartOfEnum;
import com.ddbj.ld.common.constants.TypeEnum;
import com.ddbj.ld.common.constants.XmlTagEnum;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.common.helper.UrlHelper;
import com.ddbj.ld.data.beans.common.*;
import com.ddbj.ld.data.beans.dra.study.Study;
import com.ddbj.ld.data.beans.dra.study.StudyConverter;
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
public class StudyMetaService {
    private final DraMetaCommonService commonService;
    private final ParserHelper parserHelper;
    private final UrlHelper urlHelper;
    private SRAAccessionsDao sraAccessionsDao;

    private final String bioProjectStudyTable      = TypeEnum.BIOPROJECT + "_" + TypeEnum.STUDY;
    private final String studySubmissionTable      = TypeEnum.STUDY      + "_" + TypeEnum.SUBMISSION;

    public List<JsonBean> getStudy(final String xmlPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(xmlPath));) {

            String line;
            StringBuilder sb = new StringBuilder();
            List<JsonBean> jsonList = new ArrayList<>();

            var isStarted = false;
            var startTag  = XmlTagEnum.DRA_STUDY_START.getItem();
            var endTag    = XmlTagEnum.DRA_STUDY_END.getItem();

            while((line = br.readLine()) != null) {
                // �J�n�v�f�𔻒f����
                if(line.contains(startTag)) {
                    isStarted = true;
                    sb = new StringBuilder();
                }

                if(isStarted) {
                    sb.append(line);
                }

                // 2�ȏ����\�������鍀�ڂ�2�ȏ�^�O�����݂���悤�ɂ��AJson�������Ƃ��Ƀv���p�e�B���z��ɂȂ�悤�ɂ���
                if(line.contains(endTag)) {
                    var json = XML.toJSONObject(sb.toString()).toString();

                    // Json����������ڎ擾�p�A�o���f�[�V�����p��Bean������
                    // Bean�ɂȂ����ڂ�����ꍇ�̓G���[���o�͂���
                    Study properties = this.getProperties(json, xmlPath);

                    if(null == properties) {
                        log.error("Skip this metadata.");

                        continue;
                    }

                    // JsonBean�ݒ荀�ڂ̎擾
                    var study = properties.getStudy();

                    // accesion�擾
                    var identifier = study.getAccession();

                    // Title�擾
                    var descriptor = study.getDescriptor();
                    var title = descriptor.getStudyTitle();

                    // Description �擾
                    var description = descriptor.getStudyDescription();

                    // name �擾
                    String name = study.getAlias();
                    var type = TypeEnum.STUDY.getType();

                    // dra-study/[DES]RA??????
                    var url = this.urlHelper.getUrl(type, identifier);

                    // �����Ɠ��l�̏���ێ�����BioProject���w��
                    var externalid = study.getIdentifiers().getExternalID();
                    List<SameAsBean> sameAs = null;
                    if (externalid != null) {
                        sameAs = commonService.getSameAsBeans(externalid, TypeEnum.BIOPROJECT.getType());
                    }

                    // "DRA"�Œ�
                    var isPartOf = IsPartOfEnum.DRA.getIsPartOf();

                    // ��������ID��Sample�݂̂̏��ł��邽�ߋ����ݒ�
                    OrganismBean organism = new OrganismBean();

                    //
                    List<DBXrefsBean> dbXrefs = new ArrayList<>();
                    var bioProjectStudyXrefs = this.sraAccessionsDao.selRelation(identifier, bioProjectStudyTable, TypeEnum.STUDY, TypeEnum.BIOPROJECT);
                    var studySubmissionXrefs = this.sraAccessionsDao.selRelation(identifier, studySubmissionTable, TypeEnum.STUDY, TypeEnum.SUBMISSION);

                    dbXrefs.addAll(bioProjectStudyXrefs);
                    dbXrefs.addAll(studySubmissionXrefs);
                    var distribution = this.parserHelper.getDistribution(type, identifier);

                    /// SRA_Accessions.tab������t�̃f�[�^���擾
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

    private Study getProperties(
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
}
