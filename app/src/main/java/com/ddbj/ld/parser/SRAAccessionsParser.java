package com.ddbj.ld.parser;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class SRAAccessionsParser {
    public static List<String[]> parser(String file) {
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get(file), Charset.forName("UTF-8"));

            TsvParserSettings settings = new TsvParserSettings();
            settings.getFormat().setLineSeparator("\n");
            settings.setHeaderExtractionEnabled(true);

            TsvParser parser = new TsvParser(settings);

            return parser.parseAll(br);

        } catch (IOException e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
