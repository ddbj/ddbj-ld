package com.ddbj.ld.app.core.parser.dra;

import com.ddbj.ld.data.beans.dra.ExperimentBean;
import com.ddbj.ld.common.annotation.Parser;
import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.app.core.parser.common.AccessionParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Parser
@AllArgsConstructor
@Slf4j
public class ExperimentParser {
    private AccessionParser accessionParser;
    private ParserHelper parserHelper;

    public List<ExperimentBean> parse(String xmlFile) {
        XMLStreamReader reader = null;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
            reader = factory.createXMLStreamReader(stream);

            boolean isStarted = false;
            ExperimentBean experimentBean = null;
            List<ExperimentBean> experimentBeanList = new ArrayList<>();

            // TODO nameとdescription
            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();

                if (!isStarted
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("EXPERIMENT")) {
                    isStarted = true;
                    experimentBean = new ExperimentBean();
                    experimentBean.setIdentifier(accessionParser.parseAccession(reader));
                } else if (isStarted
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("TITLE")) {
                    experimentBean.setTitle(parserHelper.getElementText((reader)));
                } else if (isStarted
                        && eventType == XMLStreamConstants.END_ELEMENT
                        && reader.getName().toString().equals("EXPERIMENT")) {
                    isStarted = false;
                    experimentBeanList.add(experimentBean);
                }
            }

            return experimentBeanList;
        } catch (FileNotFoundException | XMLStreamException e){
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
