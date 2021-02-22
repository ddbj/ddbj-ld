package com.ddbj.ld.app.core.parser.dra;

import com.ddbj.ld.app.core.parser.common.AccessionParser;
import com.ddbj.ld.data.beans.dra.AnalysisBean;
import com.ddbj.ld.common.annotation.Parser;
import com.ddbj.ld.common.helper.ParserHelper;
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
public class AnalysisParser {
    private AccessionParser accessionParser;
    private ParserHelper    parserHelper;

    public List<AnalysisBean> parse(String xmlFile) {
        XMLStreamReader reader = null;

        try {
            XMLInputFactory factory    = XMLInputFactory.newInstance();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
            reader                     = factory.createXMLStreamReader(stream);

            boolean isStarted                   = false;
            AnalysisBean analysisBean           = null;
            List<AnalysisBean> analysisBeanList = new ArrayList<>();

            // TODO name
            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();

                if (isStarted == false
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("ANALYSIS")) {
                    isStarted = true;
                    analysisBean = new AnalysisBean();
                    analysisBean.setIdentifier(accessionParser.parseAccession(reader));
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("TITLE")) {
                    analysisBean.setTitle(parserHelper.getElementText((reader)));
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("DESCRIPTION")) {
                    analysisBean.setDescription(parserHelper.getElementText((reader)));
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.END_ELEMENT
                        && reader.getName().toString().equals("ANALYSIS")) {
                    isStarted = false;
                    analysisBeanList.add(analysisBean);
                }
            }

            return analysisBeanList;
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
