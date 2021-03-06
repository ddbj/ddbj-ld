package com.ddbj.ld.app.core.parser.dra;

import com.ddbj.ld.common.annotation.Parser;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Parser
@AllArgsConstructor
@Slf4j
public class SRAAccessionsParser {
    public List<String[]> parser(String file) {
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(file), Charset.forName("UTF-8"))) {
            TsvParserSettings settings = new TsvParserSettings();
            settings.getFormat().setLineSeparator("\n");
            settings.setHeaderExtractionEnabled(true);

            TsvParser parser = new TsvParser(settings);

            return parser.parseAll(reader);
        } catch (IOException e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
