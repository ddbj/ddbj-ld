package com.ddbj.ld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.util.StopWatch;

import com.ddbj.ld.service.RelationService;
import com.ddbj.ld.service.RegisterService;

/**
 * DRA、JGAのメタデータをJson形式に変換し関係情報も付加しElasticsearchに登録するバッチ.
 */
@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class DdbjApplication implements CommandLineRunner {
    private final RelationService relationService;
    private final RegisterService registerService;

    /**
     * メインメソッド、実行されるとrunを呼び出す.
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DdbjApplication.class, args);
        // コンテナが上がったままになるので終了させる
        System.exit(0);
    }

    /**
     * DRA、JGAを登録する.
     * @param args
     * @throws IOException
     */
    @Override
    public void run(String... args) {
        log.info("DRA/JGAメタデータ登録処理開始");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        relationService.registerDraRelation();
        relationService.registerJgaRelation();
        relationService.registerJgaDate();

        registerService.registerDRA();
        registerService.registerJGA();

        stopWatch.stop();
        log.info("実行時間(分):" + BigDecimal.valueOf(stopWatch.getTotalTimeSeconds() / 60).toPlainString());
        log.info(stopWatch.prettyPrint());

        log.info("DRA/JGAメタデータ登録処理終了");
    }
}
