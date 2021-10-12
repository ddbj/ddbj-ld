package com.ddbj.ld.app.core.module;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.common.annotation.Module;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.files.FilesUploadRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Slackに対し通知、ファイルアップロードなどトランザクションを伴わない処理を行うクラス.
 */
@Module
@AllArgsConstructor
@Slf4j
public class MessageModule {

    private final MethodsClient methodsClient;

    private final ConfigSet config;

    public void postMessage(
            final String channelId,
            final String comment
    ) {
        try {
            var request = ChatPostMessageRequest.builder()
                    .channel(channelId)
                    .text(comment)
                    .build();

            this.methodsClient.chatPostMessage(request);

        } catch (IOException | SlackApiException e) {
            log.error("Sending message to slack is failed.", e);
        }
    }

    public void uploadFile(
            final String channelId,
            final String comment,
            final String filePath
    ) {
        try {
            var file = new File(filePath);

            var request = FilesUploadRequest.builder()
                    .channels(Arrays.asList(channelId))
                    .initialComment(comment)
                    .filename(file.getName())
                    .file(file)
                    .build();

            this.methodsClient.filesUpload(request);

        } catch (IOException | SlackApiException e) {
            log.error("Sending message to slack is failed.", e);
        }
    }

    public void uploadFile(
            final String channelId,
            final String comment,
            final String fileName,
            final String content
    ) {
        try {
            var request = FilesUploadRequest.builder()
                    .channels(Arrays.asList(channelId))
                    .initialComment(comment)
                    .filename(fileName)
                    .content(content)
                    .build();

            this.methodsClient.filesUpload(request);

        } catch (IOException | SlackApiException e) {
            log.error("Sending message to slack is failed.", e);
        }
    }

    public void noticeErrorInfo(
            final String type,
            final HashMap<String, List<String>> errorInfo
    ) {
        var comment = String.format(
                "%s\n%s validation failed.",
                this.config.message.mention,
                type
        );

        var sdf = new SimpleDateFormat("yyyyMMdd");
        var today = sdf.format(new Date());
        var fileName = String.format("%s_error_%s.tsv", type, today);

        log.warn("{} has error record. see {}.", type, fileName);

        var esb = new StringBuilder("No\tsummary\tcount\texample\n");
        var no = 1;

        for(var summary: errorInfo.keySet()) {
            var values = errorInfo.get(summary);

            esb.append(no);
            esb.append("\t");
            esb.append(summary);
            esb.append("\t");
            esb.append(values.size());
            esb.append("\t");
            esb.append(values.get(0));
            esb.append("\n");

            no++;
        }

        var content = esb.toString();

        this.uploadFile(this.config.message.channelId, comment, fileName, content);

        try (var writer = new FileWriter("logs/" + fileName)) {
            writer.write(content);

        } catch (IOException e) {
            log.error("Writing file is failed.", e);
        }
    }

    public void noticeEsErrorInfo(final String errorInfo) {
        var comment = String.format(
                "%s\nRegistering elasticsearch is failed.",
                this.config.message.mention
        );

        var sdf = new SimpleDateFormat("yyyyMMdd");
        var today = sdf.format(new Date());
        var fileName = String.format("elasticsearch_error_%s.tsv", today);

        this.uploadFile(this.config.message.channelId, comment, fileName, errorInfo);
    }
}
