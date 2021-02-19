package com.ddbj.ld.parser.jga;

import com.ddbj.ld.common.annotation.Parser;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Parser
@AllArgsConstructor
@Slf4j
public class JgaRelationParser {
    public List<Object[]> parser(String file, String selfType, String parentType) {
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(file), Charset.forName("UTF-8"))) {
            CsvParserSettings settings = new CsvParserSettings();
            settings.getFormat().setLineSeparator("\n");
            settings.setHeaderExtractionEnabled(true);

            CsvParser parser = new CsvParser(settings);

            List<String[]> records = parser.parseAll(reader);
            Map<String, Object[]> jgaRelationMap = new HashMap<>();

            for(String[] record: records) {
                Object[] jgaRelation = new Object[4];

                jgaRelation[0] = record[1];
                jgaRelation[1] = record[2];
                jgaRelation[2] = selfType;
                jgaRelation[3] = parentType;

                jgaRelationMap.put(record[1] + "," + record[2], jgaRelation);
            }

            return new ArrayList<>(jgaRelationMap.values());
        } catch (IOException e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}