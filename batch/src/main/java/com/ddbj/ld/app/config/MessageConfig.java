package com.ddbj.ld.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-batch.properties", encoding = "UTF-8")
public class MessageConfig {

	public String apiToken;
	public String channelId;
	public String mention;

	public MessageConfig(
			@Value("${slack.api-token}") String apiToken,
			@Value("${slack.channel-id}") String channelId,
			@Value("${slack.mention-id}") String mentionId
	) {
		this.apiToken = apiToken;
		this.channelId = channelId;
		// メンションを本文で送る形式にする
		this.mention = "<@" + mentionId + ">";
	}
}
