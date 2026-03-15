package com.kbtu.kafka.feedservice.config;

import com.kbtu.kafka.events.PostCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, PostCreatedEvent> postCreatedEventConsumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> properties = new HashMap<>(kafkaProperties.buildConsumerProperties());
        properties.putIfAbsent(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.putIfAbsent(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.putIfAbsent(JsonDeserializer.TRUSTED_PACKAGES, "*");
        properties.putIfAbsent(JsonDeserializer.VALUE_DEFAULT_TYPE, PostCreatedEvent.class.getName());
        return new DefaultKafkaConsumerFactory<>(
                properties,
                new StringDeserializer(),
                new JsonDeserializer<>(PostCreatedEvent.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostCreatedEvent> postCreatedEventKafkaListenerContainerFactory(
            ConsumerFactory<String, PostCreatedEvent> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, PostCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
