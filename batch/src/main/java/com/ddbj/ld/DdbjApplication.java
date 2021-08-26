package com.ddbj.ld;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.transact.service.AccessionsService;
import com.ddbj.ld.app.transact.service.BioProjectService;
import com.ddbj.ld.app.transact.service.jga.*;
import com.ddbj.ld.common.constants.CenterEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
// コマンド仕様目的だがCommandLineRunnerは使用しない
// 使用するとテストのときに一緒に実行されてしまうため <https://qiita.com/tag1216/items/898348a7fc3465148bc8>
public class DdbjApplication {

    private final ConfigSet config;

    // JGA
    private final JgaRelationService jgaRelation;
    private final JgaDateService jgaDate;
    private final JgaStudyService jgaStudy;
    private final JgaDataSetService jgaDataSet;
    private final JgaPolicyService jgaPolicy;
    private final JgaDacService jgaDac;

    // SRA Accessions
    private final AccessionsService accessions;

    // BioProject
    private final BioProjectService bioProject;

    /**
     * メインメソッド、実行されるとrunを呼び出す.
     * @param args
     */
    public static void main(final String... args) {
        // 処理実行、処理完了したらSpringのプロセス自体を落とす
        try(var ctx = SpringApplication.run(DdbjApplication.class, args)) {
            var app = ctx.getBean(DdbjApplication.class);
            app.run(args);
        }
    }

    /**
     * DRA、JGAを登録する.
     * @param args
     * @throws IOException
     */
     private void run(final String... args) {
        var targetDb = args.length > 0 ? args[0] : "all";
        // FIXME: 日付未指定の場合は初回起動時、復旧時. 日付指定の場合は2日目以降の場合
        var date = args.length > 1 ? args[1] : "";

        var stopWatch = new StopWatch();
        stopWatch.start();

        if("jga".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering JGA's data...");

            // 関係情報日付情報の登録
            this.jgaRelation.register();
            this.jgaDate.register();

            // メタデータの登録
            this.jgaStudy.register();
            this.jgaDataSet.register();
            this.jgaPolicy.register();
            this.jgaDac.register();

            log.info("Complete registering JGA's data.");
        }

        if("accessions".equals(targetDb) || "all".equals(targetDb)) {
            // SRAAccessions.tabの情報をDBに登録する
            log.info("Start registering relation data...");

            this.accessions.registerSRAAccessions();

            log.info("Complete registering relation data.");
        }

        if("bioproject".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering BioProject's data...");

            this.bioProject.register(this.config.file.bioProject.ncbi, CenterEnum.NCBI, true);
            this.bioProject.register(this.config.file.bioProject.ddbj, CenterEnum.DDBJ, false);

            log.info("Complete registering BioProject's data.");
        }

        if("biosample".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering BioSample's data...");

            // TODO

            log.info("Complete registering BioSample's data.");
        }

        if("dra".equals(targetDb) || "all".equals(targetDb)) {
            log.info("Start registering DRA's data...");

            // TODO

            log.info("Complete registering DRA's data.");
        }

        stopWatch.stop();
        log.info("Spend time(minute):" + BigDecimal.valueOf(stopWatch.getTotalTimeSeconds() / 60).toPlainString());
        log.info(stopWatch.prettyPrint());
    }
}
