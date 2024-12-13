package com.jpmc.midascore.entity;

import com.jpmc.midascore.entity.UserRecord;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TransactionRecord{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private float amount;

    @ManyToOne
    @JoinColumn(name="sender_id", nullable = false)
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private UserRecord recipient;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean isValid;

    @Column(nullable = false)
    private float incentiveAmount;

    public TransactionRecord()
    {

    }

    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, float incentiveAmount,      boolean isValid) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.isValid = isValid;
        this.incentiveAmount = incentiveAmount;

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public long getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public UserRecord getSender() {
        return sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isValid() {
        return isValid;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "id=" + id +
                ", amount=" + amount +
                ", sender=" + sender.getName() +
                ", recipient=" + recipient.getName() +
                ", timestamp=" + timestamp +
                ", isValid=" + isValid +
                '}';
    }
}