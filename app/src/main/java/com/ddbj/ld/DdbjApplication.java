package com.ddbj.ld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import lombok.AllArgsConstructor;
import java.io.IOException;

import com.ddbj.ld.service.ElasticsearchService;
import com.ddbj.ld.service.SRAAccessionsService;

/**
 * converting XML of DRA metadata to JSON batch class.
 */
@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class DdbjApplication implements CommandLineRunner {
    private final SRAAccessionsService sraAccessionsService;
    private final ElasticsearchService elasticsearchService;

    /**
     * main method.
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DdbjApplication.class, args);
        // TODO エラーコードとエラーハンドリング
        System.exit(0);
    }

    /**
     * exec from main.
     *
     * @param args
     * @throws IOException
     */
    @Override
    public void run(String... args) {
        log.info("DRAメタデータ登録処理開始");

        sraAccessionsService.registerSRAAccessions();
        elasticsearchService.registerElasticsearch();

        log.info("DRAメタデータ登録処理終了");
    }
}
