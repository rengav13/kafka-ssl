package com.example.kafkassl;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.properties.security.protocol}")
    private String protocol;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        configs.put("security.protocol", "SSL");
        configs.put("ssl.truststore.location", "/Users/VSILVA/Documents/pocs/kafka/ssl/_secrets/kafka.client.truststore.jks");
        configs.put("ssl.truststore.password", "123456");
        configs.put("ssl.keystore.location", "/Users/VSILVA/Documents/pocs/kafka/ssl/_secrets/kafka.client.keystore.jks");
        configs.put("ssl.keystore.password", "123456");
        configs.put("ssl.key.password", "123456");
        configs.put("ssl.enabled.protocols", "TLSv1.2,TLSv1.1,TLSv1");
        configs.put("ssl.client.auth", "required");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic myKafkaTopic() {
        return new NewTopic("kafka.topic.my", 1, (short) 1);
    }
}
