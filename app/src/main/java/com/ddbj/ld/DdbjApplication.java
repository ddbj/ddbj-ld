package com.ddbj.ld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.util.StopWatch;

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

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        sraAccessionsService.registerSRAAccessions();
        elasticsearchService.registerElasticsearch();

        stopWatch.stop();
        log.info("実行時間(分):" + BigDecimal.valueOf(stopWatch.getTotalTimeSeconds() / 60).toPlainString());
        log.info(stopWatch.prettyPrint());

        log.info("DRAメタデータ登録処理終了");
    }
}
