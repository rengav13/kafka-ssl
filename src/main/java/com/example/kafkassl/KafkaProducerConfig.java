package com.example.kafkassl;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        configProps.put("security.protocol", "SSL");
        configProps.put("ssl.truststore.location", "/Users/VSILVA/Documents/pocs/kafka/ssl/_secrets/kafka.client.truststore.jks");
        configProps.put("ssl.truststore.password", "123456");
        configProps.put("ssl.keystore.location", "/Users/VSILVA/Documents/pocs/kafka/ssl/_secrets/kafka.client.keystore.jks");
        configProps.put("ssl.keystore.password", "123456");
        configProps.put("ssl.key.password", "123456");
        configProps.put("ssl.enabled.protocols", "TLSv1.2,TLSv1.1,TLSv1");
        configProps.put("ssl.client.auth", "required");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}