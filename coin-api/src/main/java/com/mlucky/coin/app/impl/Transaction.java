package com.mlucky.coin.app.impl;

import org.joda.money.Money;

import java.util.Date;

/**
 * Created by m.iakymchuk on 03.11.14.
 */
public class Transaction {

    private static long idCounter = 0;

    private String id;
    private MoneyFlow from;
    private MoneyFlow to;
    private Date transactionDate;
    private Money moneyCount;

    public Transaction(MoneyFlow from, MoneyFlow to, Money sumOfTransaction) {
        this.id = createdId();
        this.from = from;
        this.to = to;
        this.moneyCount = sumOfTransaction;
        this.transactionDate = new Date();
    }

    public static synchronized String createdId() {
        return String.valueOf(idCounter++);
    }

    public Money getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(Money moneyCount) {
        this.moneyCount = moneyCount;
    }

    public String getId() {
        return id;
    }

    public MoneyFlow getFrom() {
        return from;
    }

    public MoneyFlow getTo() {
        return to;
    }
}
