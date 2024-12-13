package com.jpmc.midascore.apacheKafka;

import com.jpmc.midascore.foundation.Transaction;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class TransactionConsumerConfig {

    @Bean
public ConcurrentKafkaListenerContainerFactory<String, Transaction> transactionListenerFactory()
{
    ConcurrentKafkaListenerContainerFactory<String, Transaction> transactionFactory = new ConcurrentKafkaListenerContainerFactory<>();
    transactionFactory.setConsumerFactory(transactionConsumerFactory());
    return transactionFactory;
}

@Bean
public DefaultKafkaConsumerFactory<String, Transaction> transactionConsumerFactory()
{
    Map<String, Object> configuration = new HashMap<>();
    configuration.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    configuration.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

    return new DefaultKafkaConsumerFactory<>(configuration);
}
}
