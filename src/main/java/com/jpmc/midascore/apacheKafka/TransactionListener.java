package com.jpmc.midascore.apacheKafka;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    @Value("${general.kafka-topic}")
    private String topicName;

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(Transaction transaction)
    {
        Transaction savedTransaction = transactionService.processTransaction(transaction);



    }
}
