package com.example.storebookservice.kafka_config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BookConsumerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> consumerConfig() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TYPE_MAPPINGS,
                "verify:com.example.storebookservice.kafka_config.StoreBookEvent");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, StoreBookEvent.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "repositoryBooks");
        return properties;
    }

    @Bean
    public ConsumerFactory<String, StoreBookEvent> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<
                ConcurrentMessageListenerContainer<String,
                        StoreBookEvent>> kafkaListenerContainerFactory (
            ConsumerFactory<String, StoreBookEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, StoreBookEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
