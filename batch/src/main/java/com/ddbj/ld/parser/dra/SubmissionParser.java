package com.ddbj.ld.parser.dra;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.ddbj.ld.common.helper.ParserHelper;
import com.ddbj.ld.parser.common.AccessionParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ddbj.ld.bean.dra.SubmissionBean;

@Component
@AllArgsConstructor
@Slf4j
public class SubmissionParser {
    private AccessionParser accessionParser;
    private ParserHelper parserHelper;

    public List<SubmissionBean> parse(String xmlFile) {
        XMLStreamReader reader = null;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xmlFile));
            reader = factory.createXMLStreamReader(stream);

            boolean isStarted = false;
            SubmissionBean submissionBean = null;
            List<SubmissionBean> submissionBeanList = new ArrayList<>();

            // TODO nameとdescription
            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();

                if (isStarted == false
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("SUBMISSION")) {
                    isStarted = true;
                    submissionBean = new SubmissionBean();
                    submissionBean.setIdentifier(accessionParser.parseAccession(reader));
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.START_ELEMENT
                        && reader.getName().toString().equals("TITLE")) {
                    submissionBean.setTitle(parserHelper.getElementText((reader)));
                } else if (isStarted == true
                        && eventType == XMLStreamConstants.END_ELEMENT
                        && reader.getName().toString().equals("SUBMISSION")) {
                    isStarted = false;
                    submissionBeanList.add(submissionBean);
                }
            }

            return submissionBeanList;
        } catch (FileNotFoundException | XMLStreamException e) {
            log.debug(e.getMessage());

            return null;
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
    }
}
