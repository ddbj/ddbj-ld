package com.ddbj.ld.service;

import com.ddbj.ld.common.BulkHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import com.ddbj.ld.bean.BioProjectBean;
import com.ddbj.ld.bean.BioSampleBean;
import com.ddbj.ld.bean.DBXrefsBean;
import com.ddbj.ld.common.FileNameEnum;
import com.ddbj.ld.common.Settings;
import com.ddbj.ld.common.TypeEnum;
import com.ddbj.ld.dao.ElasticsearchDao;
import com.ddbj.ld.dao.SRAAccessionsDao;
import com.ddbj.ld.parser.*;

// TODO
//  - ログ出力
//  - パース
//  - リレーションを取ってくる
//  - DBのデータを取ってくる
//  - ElasticSearchのJsonを作る
//  - Elasticsearchにロードする
@Service
@AllArgsConstructor
@Slf4j
public class ElasticsearchService {
    private final Settings settings;

    private final JsonParser jsonParser;
    private final BioProjectParser bioProjectParser;
    private final BioSampleParser bioSampleParser;
    private final StudyParser studyParser;
    private final SampleParser sampleParser;
    private final SubmissionParser submissionParser;
    private final ExperimentParser experimentParser;
    private final AnalysisParser analysisParser;
    private final RunParser runParser;

    private final ElasticsearchDao elasticsearchDao;
    private final SRAAccessionsDao sraAccessionsDao;

    public void registerElasticsearch () {
        log.info("Elasticsearch登録処理開始");

        String hostname = settings.getHostname();
        int    port     = settings.getPort();
        String scheme   = settings.getScheme();

        String bioProjectXml = settings.getXmlPath()  + FileNameEnum.BIO_PROJECT_XML.getFileName();
        List<BioProjectBean> bioProjectBeanList = bioProjectParser.parse(bioProjectXml);
        String bioProjectSubmissionTable = TypeEnum.BIO_PROJECT.getType() + "_" + TypeEnum.SUBMISSION;
        String bioProjectStudyTable = TypeEnum.BIO_PROJECT.getType() + "_" + TypeEnum.STUDY;
        TypeEnum bioProjectType = TypeEnum.BIO_PROJECT;
        TypeEnum submissionType = TypeEnum.SUBMISSION;
        TypeEnum studyType      = TypeEnum.STUDY;

        bioProjectBeanList.forEach(bioProjectBean -> {
            String accession = bioProjectBean.getIdentifier();
            // TODO String accession, String tableName, TypeEnum baseType, TypeEnum targetType
            // TODO select https://tokkan.net/spring/jdbc.html
            List<DBXrefsBean> studyDbXrefs      = sraAccessionsDao.selRelation(accession, bioProjectStudyTable, bioProjectType, studyType);
            List<DBXrefsBean> submissionDbXrefs = sraAccessionsDao.selRelation(accession, bioProjectSubmissionTable, bioProjectType, submissionType);
            List<DBXrefsBean> dbXrefs = new ArrayList<>();
            dbXrefs.addAll(studyDbXrefs);
            dbXrefs.addAll(submissionDbXrefs);

            bioProjectBean.setDbXrefs(dbXrefs);
        });

        String bioProjectIndexName = TypeEnum.BIO_PROJECT.getType();
        String bioProjectJson = jsonParser.parser(bioProjectBeanList);
        int maximumRecord = settings.getMaximumRecord();

        BulkHelper.extract(bioProjectBeanList, maximumRecord, _bioProjectBeanList -> {
            elasticsearchDao.bulkInsert(hostname, port, scheme, bioProjectIndexName, _bioProjectBeanList);
        });

        String bioSampleXml  = settings.getXmlPath()  + FileNameEnum.BIO_SAMPLE_XML.getFileName();
        List<BioSampleBean> bioSampleBeanList = bioSampleParser.parse(bioSampleXml);

        // TODO
        bioSampleBeanList.forEach(bioSampleBean -> {
            // TODO
        });

        log.info("Elasticsearch登録処理終了");
    }
}
