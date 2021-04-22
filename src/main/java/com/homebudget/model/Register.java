package com.homebudget.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.homebudget.views.Views;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "register")
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Internal.class)
    private long id;

    @JsonView(Views.Summary.class)
    private String name;

    @JsonView(Views.Summary.class)
    private float balance;

    @JsonView(Views.Internal.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonView(Views.Internal.class)
    private User user;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "source")
    @JsonView(Views.Internal.class)
    private List<Transaction> outgoingTransactions;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "destination")
    @JsonView(Views.Internal.class)
    private List<Transaction> incomingTransactions;

    public Register() {
    }

    public Register(String name, float balance, LocalDateTime createdAt, User user, List<Transaction> outgoingTransactions, List<Transaction> incomingTransactions) {
        this.name = name;
        this.balance = balance;
        this.createdAt = createdAt;
        this.user = user;
        this.outgoingTransactions = outgoingTransactions;
        this.incomingTransactions = incomingTransactions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getOutgoingTransactions() {
        return outgoingTransactions;
    }

    public void setOutgoingTransactions(List<Transaction> outgoingTransactions) {
        this.outgoingTransactions = outgoingTransactions;
    }

    public List<Transaction> getIncomingTransactions() {
        return incomingTransactions;
    }

    public void setIncomingTransactions(List<Transaction> incomingTransactions) {
        this.incomingTransactions = incomingTransactions;
    }
}
