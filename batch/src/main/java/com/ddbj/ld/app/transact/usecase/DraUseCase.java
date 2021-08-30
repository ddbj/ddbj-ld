package com.ddbj.ld.app.transact.usecase;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.app.transact.service.dra.*;
import com.ddbj.ld.common.annotation.UseCase;
import com.ddbj.ld.common.constants.CenterEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DRAに関する処理を行うユースケースクラス.
 */
@UseCase
@AllArgsConstructor
@Slf4j
public class DraUseCase {
    private final ConfigSet config;

    private final DraAnalysisService analysis;
    private final DraExperimentService experiment;
    private final DraRunService run;
    private final DraSubmissionService submission;
    private final DraSampleService sample;
    private final DraStudyService study;

    private final SearchModule searchModule;

    public void register(
        final String path,
        final CenterEnum center,
        final boolean deletable
    ) {
        // TODO
    }
}
