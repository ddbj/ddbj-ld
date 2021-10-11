package com.ddbj.ld;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.transact.service.AccessionsService;
import com.ddbj.ld.app.transact.service.BioProjectService;
import com.ddbj.ld.app.transact.service.BioSampleService;
import com.ddbj.ld.app.transact.service.jga.*;
import com.ddbj.ld.app.transact.usecase.SraUseCase;
import com.ddbj.ld.common.constants.ActionEnum;
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

    // BioSample
    private final BioSampleService bioSample;

    // DRA
    private final SraUseCase sra;

    /**
     * メインメソッド、実行されるとrunを呼び出す.
     * @param args
     */
    public static void main(final String... args) {
        // 処理実行、処理完了したらSpringのプロセス自体を落とす
        // コマンド仕様目的だがCommandLineRunnerは使用しない
        // 使用するとテストのときに一緒に実行されてしまうため <https://qiita.com/tag1216/items/898348a7fc3465148bc8>
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
        var action = args.length > 0 ? args[0] : ActionEnum.REGISTER_ALL.action;

        var stopWatch = new StopWatch();
        stopWatch.start();

        if(ActionEnum.REGISTER_JGA.action.equals(action) || ActionEnum.REGISTER_ALL.action.equals(action)) {
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

        if(ActionEnum.REGISTER_ACCESSIONS.action.equals(action) || ActionEnum.REGISTER_ALL.action.equals(action)) {
            // SRAAccessions.tabの情報をDBに登録する
            log.info("Start registering relation data...");

            this.accessions.registerSRAAccessions();

            log.info("Complete registering relation data.");
        }

        if(ActionEnum.REGISTER_BIOPROJECT.action.equals(action) || ActionEnum.REGISTER_ALL.action.equals(action)) {
            log.info("Start registering BioProject's data...");

            this.bioProject.delete();
            this.bioProject.register(this.config.file.bioProject.ncbi, CenterEnum.NCBI);
            this.bioProject.register(this.config.file.bioProject.ddbj, CenterEnum.DDBJ);

            log.info("Complete registering BioProject's data.");
        }

        if(ActionEnum.REGISTER_BIOSAMPLE.action.equals(action) || ActionEnum.REGISTER_ALL.action.equals(action)) {
            log.info("Start registering BioSample's data...");

            this.bioSample.delete();
            this.bioSample.register(this.config.file.bioSample.ncbi, CenterEnum.NCBI);
            this.bioSample.register(this.config.file.bioSample.ddbj, CenterEnum.DDBJ);

            log.info("Complete registering BioSample's data.");
        }

        if(ActionEnum.REGISTER_SRA.action.equals(action) || ActionEnum.REGISTER_ALL.action.equals(action)) {
            log.info("Start registering SRA's data...");

            this.sra.delete();
            this.sra.register(this.config.file.sra.ddbj);
            this.sra.register(this.config.file.sra.ebi);
            this.sra.register(this.config.file.sra.ncbi);

            log.info("Complete registering SRA's data.");
        }

        // TODO 各DB更新処理

         if(ActionEnum.VALIDATE_JGA.action.equals(action) || ActionEnum.VALIDATE_ALL.action.equals(action)) {
             log.info("Start validating JGA's data...");

             // メタデータのバリデート
             this.jgaStudy.validate();
             this.jgaDataSet.validate();
             this.jgaPolicy.validate();
             this.jgaDac.validate();

             log.info("Complete validating JGA's data.");
         }

         if(ActionEnum.VALIDATE_BIOPROJECT.action.equals(action) || ActionEnum.VALIDATE_ALL.action.equals(action)) {
             log.info("Start validating BioProject's data...");

             this.bioProject.validate(this.config.file.bioProject.ddbj);
             this.bioProject.validate(this.config.file.bioProject.ncbi);

             log.info("Complete validating BioProject's data.");
         }

         if(ActionEnum.VALIDATE_BIOSAMPLE.action.equals(action) || ActionEnum.VALIDATE_ALL.action.equals(action)) {
             log.info("Start validating BioSample's data...");

             this.bioSample.validate(this.config.file.bioSample.ddbj);
             this.bioSample.validate(this.config.file.bioSample.ncbi);

             log.info("Complete validating BioSample's data.");
         }

         if(ActionEnum.VALIDATE_SRA.action.equals(action) || ActionEnum.VALIDATE_ALL.action.equals(action)) {
             log.info("Start validating SRA's data...");

             this.sra.validate(this.config.file.sra.ddbj);
             this.sra.validate(this.config.file.sra.ebi);
             this.sra.validate(this.config.file.sra.ncbi);

             log.info("Complete validating SRA's data.");
         }

        stopWatch.stop();
        log.info("Spend time(minute):" + BigDecimal.valueOf(stopWatch.getTotalTimeSeconds() / 60).toPlainString());
        log.info(stopWatch.prettyPrint());
    }
}
