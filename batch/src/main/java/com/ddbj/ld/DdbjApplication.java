package com.ddbj.ld;

import com.ddbj.ld.app.transact.service.RegisterService;
import com.ddbj.ld.app.transact.service.RelationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.math.BigDecimal;

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
        var targetDb = args.length > 0 ? args[0] : "all";

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        if("jga".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering JGA's data...");

            relationService.registerJgaRelation();
            relationService.registerJgaDate();
            registerService.registerJGA();

            log.info("Complete registering JGA's data.");
        }

        if(false == "jga".equals(targetDb)) {
            // JGA以外の場合、関係情報をPostgresに登録する
            log.info("Start registering relation data...");

            relationService.registerSRARelation();

            log.info("Complete registering relation data.");
        }

        if("bioproject".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering BioProject's data...");

            registerService.registerBioProject();

            log.info("Complete registering BioProject's data.");
        }

        if("biosample".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering BioProject's data...");

            registerService.registerBioSample();

            log.info("Complete registering BioProject's data.");
        }

        if("dra".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering DRA's data...");

            registerService.registerDRA();

            log.info("Complete registering DRA's data.");
        }

        stopWatch.stop();
        log.info("Spend time(minute):" + BigDecimal.valueOf(stopWatch.getTotalTimeSeconds() / 60).toPlainString());
        log.info(stopWatch.prettyPrint());
    }
}
