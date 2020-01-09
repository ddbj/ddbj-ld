package com.ddbj.ld.parser;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;

import com.ddbj.ld.bean.BioProjectBean;
import com.ddbj.ld.common.FileNameEnum;

// TODO 後で削除
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

public class BioProjectParser {
    // TODO 後で削除
    public static void main(String[] argv) throws XMLStreamException, IOException { ;

        BioProjectParser obj = new BioProjectParser();
        String bioProjectXml = "/Users/the024and025/ddbj/ddbj-ld/app/src/main/resources/xml/" + FileNameEnum.BIO_PROJECT_XML.getFileName();
        List<BioProjectBean> bioProjectBeanList = obj.parse(bioProjectXml);

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        // TODO
        BulkProcessor processor =  BulkProcessor.builder((request, bulkListener) ->
                client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), new BulkProcessor.Listener() {

            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {
                System.out.println("bulkRequest = " + bulkRequest.numberOfActions());
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                System.out.println(
                        "bulkResponse = " + bulkResponse.hasFailures() + " " + bulkResponse.buildFailureMessage());
            }
        }).setBulkActions(20)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(0)
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

        bioProjectBeanList.forEach(bean -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);

                String json = mapper.writeValueAsString(bean);

                System.out.println(json);

                processor.add(new IndexRequest("bio_project").source(json, XContentType.JSON));
            } catch (IOException e) {
                // nothing
            }
        });
    }

    public List<BioProjectBean> parse(String xmlFile) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
        XMLStreamReader reader = factory.createXMLStreamReader(stream);
        int packageFlg = 0;
        int descFlg = 0;

        List<BioProjectBean> bioProjectBeanList = new ArrayList<>();
        BioProjectBean bioProjectBean = null;

        for (; reader.hasNext(); reader.next()) {
            int eventType = reader.getEventType();

            if (packageFlg == 0
            && eventType == XMLStreamConstants.START_ELEMENT
            && reader.getName().toString().equals("Package")) {
                packageFlg = 1;
                bioProjectBean = new BioProjectBean();
            } else if (packageFlg == 1
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("ArchiveID")) {
                bioProjectBean.setAccession(parseArchiveID(reader));
            } else if (packageFlg == 1
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("ProjectDescr")) {
                descFlg = 1;
            } else if (descFlg == 1
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Name")) {
                bioProjectBean.setName(reader.getElementText());
            } else if (descFlg == 1
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Title")) {
                bioProjectBean.setTitle(reader.getElementText());
            } else if (descFlg == 1
                    && eventType == XMLStreamConstants.START_ELEMENT
                    && reader.getName().toString().equals("Description")) {
                bioProjectBean.setDescription(reader.getElementText());
                descFlg = 0;
            } else if (packageFlg == 1
                    && eventType == XMLStreamConstants.END_ELEMENT
                    && reader.getName().toString().equals("Package")) {
                packageFlg = 0;
                bioProjectBeanList.add(bioProjectBean);
            }
        }

        reader.close();

        return bioProjectBeanList;
    }

    public String parseArchiveID(XMLStreamReader reader) {

        String accession = null;
        int count = reader.getAttributeCount();
        for (int i=0; i<count; i++) {
            if (reader.getAttributeName(i).toString().equals("accession")) {
                accession = reader.getAttributeValue(i);
                break;
            }
        }

        return accession;
    }
}
