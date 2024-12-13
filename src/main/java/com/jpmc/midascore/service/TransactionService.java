package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    private final RestTemplate restTemplate;

    public TransactionService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Transactional
    public Transaction processTransaction(Transaction transaction)
    {


        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        float transactionAmount = transaction.getAmount();

        float incentiveAmount;
        boolean isTransactionValid = validateTransaction(sender, recipient, transactionAmount);

        if(isTransactionValid)
        {


            incentiveAmount = getIncentiveAmount(transaction);

            TransactionRecord transactionRecord = new TransactionRecord(sender,recipient, transactionAmount, incentiveAmount,isTransactionValid);

            sender.setBalance(sender.getBalance()-transactionAmount);
            recipient.setBalance(recipient.getBalance()+transactionAmount + incentiveAmount);




            userRepository.save(sender);
            userRepository.save(recipient);


            transactionRepository.save(transactionRecord);

        }



        return transaction;

    }

    private float getIncentiveAmount(Transaction transaction) {
        try {
            ResponseEntity<Incentive> response = restTemplate.postForEntity(
                    "http://localhost:8080/incentive",
                    transaction,
                    Incentive.class
            );

            Incentive incentive = response.getBody();

            if (incentive != null) {
                return incentive.getAmount();
            } else {
                System.err.println("Incentive API returned null response.");
                return 0;
            }
        } catch (Exception e) {
            System.err.println("Error calling Incentive API: " + e.getMessage());
            return 0;
        }
    }



    private boolean validateTransaction(UserRecord sender, UserRecord recipient, float transactionAmount)
    {

    try
    {


        if (sender == null || recipient == null) {
            return false;
        }

        return sender.getBalance() >= transactionAmount;

    }
    catch(Exception exception)
    {
        System.out.println("Exception occurred while validating Transaction : " + exception.getMessage());
        return false;
    }


    }


}
