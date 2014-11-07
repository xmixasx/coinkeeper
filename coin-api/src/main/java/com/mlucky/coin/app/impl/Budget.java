package com.mlucky.coin.app.impl;

import org.joda.money.Money;

/**
 * Created by m.iakymchuk on 05.11.2014.
 */
public class Budget {
    private Money budget;

    public void setBudget(Money budget) {
        this.budget = budget;
    }

    public Money getBudget() {
        return budget;
    }

    public boolean isOverBudget(MoneyFlow moneyFlow) {
        return this.budget.isGreaterThan(moneyFlow.getTotal());
    }
}
