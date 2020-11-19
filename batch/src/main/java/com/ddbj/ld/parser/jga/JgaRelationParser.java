package com.ddbj.ld.parser.jga;

import com.ddbj.ld.common.constant.TypeEnum;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class JgaRelationParser {
    public List<Object[]> parser(String file) {
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(file), Charset.forName("UTF-8"))) {
            CsvParserSettings settings = new CsvParserSettings();
            settings.getFormat().setLineSeparator("\n");
            settings.setHeaderExtractionEnabled(true);

            CsvParser parser = new CsvParser(settings);

            List<String[]> records = parser.parseAll(reader);
            List<Object[]> jgaRelationList = new ArrayList<>();

            String dataSetRegex = "^JGAD.*";

            String dataSetAccession = null;

            for(String[] record: records) {
                if(record.length < 2) {
                    continue;
                }

                Object[] jgaRelation = new Object[4];

                jgaRelation[0] = record[1];
                jgaRelation[1] = record[2];

                if(record[1].matches(dataSetRegex)) {
                    dataSetAccession = record[1];
                }

                if(StringUtils.isEmpty(jgaRelation[1])) {
                    if(StringUtils.isEmpty(dataSetAccession)) {
                        continue;
                    }

                    jgaRelation[0] = dataSetAccession;
                    jgaRelation[1] = record[1];
                    dataSetAccession = null;
                }

                if(checkDuplicate(jgaRelation, jgaRelationList)) {
                    continue;
                }

                jgaRelation[2] = getType(jgaRelation[0].toString());
                jgaRelation[3] = getType(jgaRelation[1].toString());

                jgaRelationList.add(jgaRelation);
            }

            return jgaRelationList;
        } catch (IOException e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    private String getType(String accession) {
        String studyRegex   = "^JGAS.*";
        String dataSetRegex = "^JGAD.*";
        String policyRegex  = "^JGAP.*";
        String dacRegex     = "^JGAC.*";

        // accessionがないパターンがDataSetにはあるため
        if(StringUtils.isEmpty(accession)) {
            return TypeEnum.DATASET.getType();
        }

        if(accession.matches(studyRegex)) {
            return TypeEnum.JGA_STUDY.getType();
        } else if(accession.matches(dataSetRegex)) {
            return TypeEnum.DATASET.getType();
        } else if(accession.matches(policyRegex)) {
            return TypeEnum.POLICY.getType();
        } else if(accession.matches(dacRegex)) {
            return TypeEnum.DAC.getType();
        }

        return null;
    }

    private boolean checkDuplicate(Object[] target, List<Object[]> jgaRelationList) {
        for(Object[] jgaRelation: jgaRelationList) {
            if(jgaRelation[0].toString().equals(target[0].toString())
            && jgaRelation[1].toString().equals(target[1].toString())) {
                return true;
            }
        }

        return false;
    }
}
