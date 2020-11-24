package com.ddbj.ld.app.core.parser.jga;

<<<<<<< HEAD:batch/src/main/java/com/ddbj/ld/app/core/parser/jga/JgaDateParser.java
import com.ddbj.ld.common.annotation.Parser;
=======
>>>>>>> 変更分取り込み:batch/src/main/java/com/ddbj/ld/parser/jga/JgaDateParser.java
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
<<<<<<< HEAD:batch/src/main/java/com/ddbj/ld/app/core/parser/jga/JgaDateParser.java
=======
import org.springframework.stereotype.Component;
>>>>>>> 変更分取り込み:batch/src/main/java/com/ddbj/ld/parser/jga/JgaDateParser.java

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
public class JgaDateParser {
    public List<Object[]> parse(String file) {
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(file), Charset.forName("UTF-8"))) {
            CsvParserSettings settings = new CsvParserSettings();
            settings.getFormat().setLineSeparator("\n");
            settings.setHeaderExtractionEnabled(true);

            CsvParser parser = new CsvParser(settings);

            List<String[]> records = parser.parseAll(reader);
            Map<String, Object[]> jgaDateMap = new HashMap<>();

            List<Object[]> jgaDateList = new ArrayList<>();

            for (String[] record: records) {
<<<<<<< HEAD:batch/src/main/java/com/ddbj/ld/app/core/parser/jga/JgaDateParser.java
=======
                if(record.length < 2) {
                    continue;
                }

>>>>>>> 変更分取り込み:batch/src/main/java/com/ddbj/ld/parser/jga/JgaDateParser.java
                jgaDateMap.put(record[0], record);
            }

            for (Object[] record : jgaDateMap.values()) {
                jgaDateList.add(record);
            }

            return jgaDateList;
        } catch (IOException e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
