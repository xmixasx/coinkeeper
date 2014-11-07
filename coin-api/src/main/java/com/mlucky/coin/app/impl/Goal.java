package com.mlucky.coin.app.impl;

import org.joda.money.Money;

/**
 * Created by m.iakymchuk on 04.11.2014.
 */
public class Goal extends MoneyFlow {

    private boolean isClosed;
    private Budget budget;

    @Override
    public void addTransaction(MoneyFlow to, String money) {
        super.addTransaction(to, money);
        if(this.budget.isOverBudget(this)) {
            this.budget.setBudget(this.getTotal());
        }
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;

    }

    public boolean isClosed() { return isClosed; }

    public Goal(String title, String currency) {
        super(title, currency);
    }

}
