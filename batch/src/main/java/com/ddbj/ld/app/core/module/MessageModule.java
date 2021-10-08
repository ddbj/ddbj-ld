package com.ddbj.ld.app.core.module;

import com.ddbj.ld.common.annotation.Module;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.files.FilesUploadRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Slackに対し通知、ファイルアップロードなどトランザクションを伴わない処理を行うクラス.
 */
@Module
@AllArgsConstructor
@Slf4j
public class MessageModule {

    private final MethodsClient methodsClient;

    public void postMessage(
            final String channelId,
            final String text
    ) {
        try {
            var request = ChatPostMessageRequest.builder()
                    .channel(channelId)
                    .text(text)
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
}
