package com.ddbj.ld.parser;

import com.ddbj.ld.bean.DBXrefsBean;
import com.ddbj.ld.bean.DataSetBean;
import com.ddbj.ld.bean.JgaStudyBean;
import com.ddbj.ld.common.ParserHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class DataSetParser {
    private AccessionParser accessionParser;
    private ParserHelper parserHelper;

    public List<DataSetBean> parse(String xmlFile) {
        XMLStreamReader reader = null;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
            reader = factory.createXMLStreamReader(stream);

            boolean isStarted = false;
            DataSetBean dataSetBean = null;
            List<DataSetBean> dataSetBeanList = new ArrayList<>();

            // TODO name
            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();

                if (isStarted == false
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("DATASET")) {
                    isStarted = true;
                    dataSetBean = new DataSetBean();
                    dataSetBean.setIdentifier(accessionParser.parseAccession(reader));
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("TITLE")) {
                    dataSetBean.setTitle(parserHelper.getElementText((reader)));
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("DESCRIPTION")) {
                    dataSetBean.setDescription(parserHelper.getElementText((reader)));
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("POLICY_REF")) {
                    List<DBXrefsBean> dbXrefsBeanList = new ArrayList<>();
                    DBXrefsBean dbXrefsBean = new DBXrefsBean();

                    // TODO 明日、ここから

                    dataSetBean.setDbXrefs(dbXrefsBeanList);
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.END_ELEMENT
                        && reader.getName().toString().equals("DATASET")) {
                    isStarted = false;
                    dataSetBeanList.add(dataSetBean);
                }
            }

            return dataSetBeanList;
        } catch (FileNotFoundException | XMLStreamException e) {
            log.debug(e.getMessage());

            return null;
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (XMLStreamException e) {
                log.debug(e.getMessage());
            }
        }
    }
}
