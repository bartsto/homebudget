package com.homebudget.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float amount;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id")
    private Register source;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id")
    private Register destination;

    public Transaction() {
    }

    public Transaction(float amount, LocalDateTime createdAt, TransactionStatus status, Register source, Register destination) {
        this.amount = amount;
        this.createdAt = createdAt;
        this.status = status;
        this.source = source;
        this.destination = destination;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Register getSource() {
        return source;
    }

    public void setSource(Register source) {
        this.source = source;
    }

    public Register getDestination() {
        return destination;
    }

    public void setDestination(Register destination) {
        this.destination = destination;
    }
}
