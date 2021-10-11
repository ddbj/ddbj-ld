package com.ddbj.ld.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.UUID;

@Configuration
@PropertySource(value = "classpath:ddbj-api.properties", encoding = "UTF-8")
public class SystemConfig {

    public final UUID maintenanceAccountUUID;
    public final UUID secretKey;

    public SystemConfig(
            @Value("${ddbj.system.maintenance.account.uuid}") UUID system_maintenance_account_uuid,
            @Value("${ddbj.system.maintenance.secret.key}") UUID system_maintenance_secret_key) {
        this.maintenanceAccountUUID = system_maintenance_account_uuid;
        this.secretKey = system_maintenance_secret_key;
    }
}
