package com.ddbj.ld;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.MessageModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.usecase.sra.DraUseCase;
import com.ddbj.ld.app.transact.service.sra.SraAccessionsService;
import com.ddbj.ld.app.transact.service.bioproject.BioProjectService;
import com.ddbj.ld.app.transact.service.biosample.BioSampleService;
import com.ddbj.ld.app.transact.service.jga.*;
import com.ddbj.ld.app.transact.usecase.sra.SraUseCase;
import com.ddbj.ld.common.constants.ActionEnum;
import com.ddbj.ld.common.constants.CenterEnum;
import com.ddbj.ld.common.exception.DdbjException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * BioProject, BioSample, DRA, JGAのメタデータをJson形式に変換し関係情報も付加しElasticsearchに登録するバッチ.
 */
@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class DdbjApplication {

    private final ConfigSet config;

    private final SearchModule search;
    private final MessageModule message;

    // JGA
    private final JgaRelationService jgaRelation;
    private final JgaDateService jgaDate;
    private final JgaStudyService jgaStudy;
    private final JgaDataSetService jgaDataSet;
    private final JgaPolicyService jgaPolicy;
    private final JgaDacService jgaDac;

    // BioProject
    private final BioProjectService bioProject;

    // BioSample
    private final BioSampleService bioSample;

    // SRA
    private final SraAccessionsService sraAccessions;
    private final DraUseCase dra;
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
        var action = args.length > 0 ? args[0] : null;
        var date = args.length > 1 ? args[1] : null;

        if(null == action) {
            var message = "Action is required. finish without doing anything";
            log.error(message);

            throw new DdbjException(message);
        }

        var stopWatch = new StopWatch();
        stopWatch.start();

        if(ActionEnum.GET_BIOPROJECT.action.equals(action)) {
            log.info("Start getting BioProject's data...");

            this.bioProject.getMetadata();

            log.info("Complete getting BioProject's data...");
        }

        if(ActionEnum.GET_BIOSAMPLE.action.equals(action)) {
            log.info("Start getting BioSample's data...");

            this.bioSample.getMetadata();

            log.info("Complete getting BioSample's data...");
        }

        if(ActionEnum.GET_SRA.action.equals(action)) {
            log.info("Start getting SRA's data...");

            this.sra.getMetadata(date);

            log.info("Complete getting SRA's data...");
        }

         if(ActionEnum.GET_SRA_UPDATED.action.equals(action)) {
             log.info("Start getting SRA's updated data...");

             this.sra.getUpdatedMetadata(date);

             log.info("Complete getting SRA's updated data...");
         }

         if(ActionEnum.REGISTER_SRA_ACCESSIONS.action.equals(action)) {
             log.info("Start registering SRA's relation data...");

             this.sraAccessions.registerAccessions();

             log.info("Complete registering SRA's relation data.");
         }

        if(ActionEnum.REGISTER_DRA_ACCESSIONS.action.equals(action)) {
            log.info("Start registering DRA's relation data...");

            this.dra.registerAccessions();

            log.info("Complete registering DRA's relation data.");
        }

        if(ActionEnum.REGISTER_JGA.action.equals(action)) {
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

        if(ActionEnum.REGISTER_BIOPROJECT.action.equals(action)) {
            log.info("Start registering BioProject's data...");

            this.bioProject.registerNCBI();

            log.info("Complete registering BioProject's data.");
        }

        if(ActionEnum.REGISTER_DDBJ_BIOPROJECT.action.equals(action)) {
            log.info("Start registering DDBJ's BioProject's data...");

            this.bioProject.registerDDBJ();

            log.info("Complete registering DDBJ's BioProject's data.");
        }

        if(ActionEnum.REGISTER_BIOSAMPLE.action.equals(action)) {
            log.info("Start registering BioSample's data...");

            // TODO register内でDeleteできるようにする
            this.bioSample.register(this.config.file.path.bioSample.ncbi, CenterEnum.NCBI);

            log.info("Complete registering BioSample's data.");
        }

        if(ActionEnum.REGISTER_DDBJ_BIOSAMPLE.action.equals(action)) {
            log.info("Start registering DDBJ's BioSample's data...");

            // TODO register内でDeleteできるようにする
            this.bioSample.register(this.config.file.path.bioSample.ddbj, CenterEnum.DDBJ);

            log.info("Complete registering DDBJ's BioSample's data.");
        }

        if(ActionEnum.REGISTER_SRA.action.equals(action)) {
            log.info("Start registering SRA's data...");

            // TODO register内でDeleteできるようにする
            this.sra.register(this.config.file.path.sra.ebi, CenterEnum.EBI);
            this.sra.register(this.config.file.path.sra.ncbi, CenterEnum.NCBI);

            log.info("Complete registering SRA's data.");
        }

        if(ActionEnum.REGISTER_DRA.action.equals(action)) {
            log.info("Start registering DRA's data...");

            this.dra.register();

            log.info("Complete registering DRA's data.");
        }

         if(ActionEnum.UPDATE_SRA_ACCESSIONS.action.equals(action)) {
             // SRAAccessions.tabの情報のうち、更新差分をDBに登録する
             log.info("Start registering updating SRA's relation data...");

             this.sraAccessions.createUpdatedData(date);

             log.info("Complete registering updating SRA's relation data.");
         }

         if(ActionEnum.UPDATE_BIOPROJECT.action.equals(action)) {
             log.info("Start updating BioProject's data...");

             // TODO メソッドをひとまとめにする
             this.bioProject.createUpdatedData(date, this.config.file.path.bioProject.ncbi, CenterEnum.NCBI);
             this.bioProject.update(date);

             log.info("Complete updating BioProject's data.");
         }

        if(ActionEnum.UPDATE_DDBJ_BIOPROJECT.action.equals(action)) {
            log.info("Start updating DDBJ's BioProject's data...");

            // TODO メソッドをひとまとめにする
            this.bioProject.createUpdatedData(date, this.config.file.path.bioProject.ddbj, CenterEnum.DDBJ);
            this.bioProject.update(date);

            log.info("Complete updating DDBJ's BioProject's data.");
        }

         if(ActionEnum.UPDATE_BIOSAMPLE.action.equals(action)) {
             log.info("Start updating BioSample's data...");

             // TODO メソッドをひとまとめにする
             this.bioSample.createUpdatedData(date, this.config.file.path.bioSample.ncbi, CenterEnum.NCBI);
             this.bioSample.update(date);

             log.info("Complete updating BioSample's data.");
         }

        if(ActionEnum.UPDATE_DDBJ_BIOSAMPLE.action.equals(action)) {
            log.info("Start updating DDBJ's BioSample's data...");

            // TODO メソッドをひとまとめにする
            this.bioSample.createUpdatedData(date, this.config.file.path.bioSample.ddbj, CenterEnum.DDBJ);
            this.bioSample.update(date);

            log.info("Complete updating DDBJ's BioSample's data.");
        }

         if(ActionEnum.UPDATE_SRA.action.equals(action)) {
             log.info("Start updating SRA's data...");

             this.sra.update(date);

             log.info("Complete updating SRA's data.");
         }

         if(ActionEnum.VALIDATE_JGA.action.equals(action)) {
             log.info("Start validating JGA's data...");

             // メタデータのバリデート
             this.jgaStudy.validate();
             this.jgaDataSet.validate();
             this.jgaPolicy.validate();
             this.jgaDac.validate();

             log.info("Complete validating JGA's data.");
         }

        if(ActionEnum.VALIDATE_BIOPROJECT.action.equals(action)) {
            log.info("Start validating BioProject's data...");

            this.bioProject.validate(this.config.file.path.bioProject.ncbi);

            log.info("Complete validating BioProject's data.");
        }

        if(ActionEnum.VALIDATE_DDBJ_BIOPROJECT.action.equals(action)) {
            log.info("Start validating DDBJ's BioProject's data...");

            this.bioProject.validate(this.config.file.path.bioProject.ddbj);

            log.info("Complete validating DDBJ's BioProject's data.");
        }

        if(ActionEnum.VALIDATE_BIOSAMPLE.action.equals(action)) {
            log.info("Start validating BioSample's data...");

            this.bioSample.validate(this.config.file.path.bioSample.ncbi);

            log.info("Complete validating BioSample's data.");
        }

        if(ActionEnum.VALIDATE_DDBJ_BIOSAMPLE.action.equals(action)) {
            log.info("Start validating DDBJ's BioSample's data...");

            this.bioSample.validate(this.config.file.path.bioSample.ddbj);

            log.info("Complete validating DDBJ's BioSample's data.");
        }

        if(ActionEnum.VALIDATE_SRA.action.equals(action)) {
            log.info("Start validating SRA's data...");

            this.sra.validate(this.config.file.path.sra.ddbj);
            this.sra.validate(this.config.file.path.sra.ebi);
            this.sra.validate(this.config.file.path.sra.ncbi);

            log.info("Complete validating SRA's data.");
        }

        var esErrorInfo = this.search.getErrorInfo();

        if(esErrorInfo.length() > 0) {
            this.message.noticeEsErrorInfo(esErrorInfo);
        }

        stopWatch.stop();
        log.info("Spend time(minute):" + BigDecimal.valueOf(stopWatch.getTotalTimeSeconds() / 60).toPlainString());
        log.info(stopWatch.prettyPrint());
    }
}
