package com.homebudget.dto;

public class TransactionTransferRequest {

    private long userId;

    private long sourceRegisterId;

    private long destinationRegisterId;

    private float amount;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSourceRegisterId() {
        return sourceRegisterId;
    }

    public void setSourceRegisterId(long sourceRegisterId) {
        this.sourceRegisterId = sourceRegisterId;
    }

    public long getDestinationRegisterId() {
        return destinationRegisterId;
    }

    public void setDestinationRegisterId(long destinationRegisterId) {
        this.destinationRegisterId = destinationRegisterId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
