package com.ddbj.ld;

import com.ddbj.ld.app.transact.usecase.RegisterUseCase;
import com.ddbj.ld.app.transact.usecase.RelationUseCase;
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

    private final RelationUseCase relationUseCase;
    private final RegisterUseCase registerUseCase;

    /**
     * メインメソッド、実行されるとrunを呼び出す.
     * @param args
     */
    public static void main(final String... args) {
        // 処理実行、処理完了したらSpringのプロセス自体を落とす
        SpringApplication.exit(SpringApplication.run(DdbjApplication.class, args));
    }

    /**
     * DRA、JGAを登録する.
     * @param args
     * @throws IOException
     */
    @Override
    public void run(final String... args) {
        var targetDb = args.length > 0 ? args[0] : "all";
        // FIXME: 日付未指定の場合は初回起動時、復旧時. 日付指定の場合は2日目以降の場合
        var date = args.length > 1 ? args[1] : "";

        var stopWatch = new StopWatch();
        stopWatch.start();

        if("jga".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering JGA's data...");

            this.relationUseCase.registerJgaRelation(date);
            this.relationUseCase.registerJgaDate(date);
            this.registerUseCase.registerJGA(date);

            log.info("Complete registering JGA's data.");
        }

        if(false == "jga".equals(targetDb)) {
            // JGA以外の場合、関係情報をPostgresに登録する
            log.info("Start registering relation data...");

            this.relationUseCase.registerSRARelation(date);

            log.info("Complete registering relation data.");
        }

        if("bioproject".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering BioProject's data...");

            this.registerUseCase.registerBioProject(date);

            log.info("Complete registering BioProject's data.");
        }

        if("biosample".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering BioSample's data...");

            this.registerUseCase.registerBioSample(date);

            log.info("Complete registering BioSample's data.");
        }

        if("dra".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering DRA's data...");

            this.registerUseCase.registerDRA(date);

            log.info("Complete registering DRA's data.");
        }

        stopWatch.stop();
        log.info("Spend time(minute):" + BigDecimal.valueOf(stopWatch.getTotalTimeSeconds() / 60).toPlainString());
        log.info(stopWatch.prettyPrint());
    }
}
