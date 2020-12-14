package com.ddbj.ld;

import com.ddbj.ld.service.RegisterService;
import com.ddbj.ld.service.RelationService;
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
        var targetDb = args[0];

        if(null == targetDb) {
            log.error("TARGET_DB is null, please write TARGET_DB in .env");
            System.exit(255);
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        if("jga".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering JGA's data...");

            if (relationService.registerJgaRelation()) {
                if (relationService.registerJgaDate()) {
                    registerService.registerJGA();
                }
            }

            log.info("Complete registering JGA's data.");
        }

        if("dra".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering DRA's data...");

            if (relationService.registerDraRelation()) {
                registerService.registerDRA();
            }

            log.info("Complete registering DRA's data.");
        }

        stopWatch.stop();
        log.info("Spend time(minute):" + BigDecimal.valueOf(stopWatch.getTotalTimeSeconds() / 60).toPlainString());
        log.info(stopWatch.prettyPrint());
    }
}
