package com.mlucky.coin.app.impl;


import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by m.iakymchuk on 05.11.2014.
 */
public class CoinApplication {
    private static CoinApplication coinApplication = new CoinApplication();
    private static final String localCurrency = "UAH";
    private final Date installDate;
    private Date currentDate;

    List<InCome> inComeSources;
    List<Account> accounts;
    List<Spend> spends;
    List<Goal> goals;

    private Money currentBalance;
    private Money currentSpend;
    private Money plannedSpend;
    private Money spendBudget;
    private Money planedInCome;

    private CoinApplication() {
        super();
        this.installDate = new Date();
        this.currentDate = new Date();
        this.inComeSources =  new ArrayList<InCome>();
        this.accounts =  new ArrayList<Account>();
        this.spends = new ArrayList<Spend>();
        this.goals = new ArrayList<Goal>();
        CurrencyUnit local = CurrencyUnit.getInstance(localCurrency);
        this.currentBalance = Money.zero(local);
        this.currentSpend = Money.zero(local);
        this.plannedSpend = Money.zero(local);
        this.spendBudget = Money.zero(local);
        this.planedInCome = Money.zero(local);
    }

    public static CoinApplication getCoinApplication() {
        return coinApplication;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public InCome addIncome(String title) {
       InCome income = new InCome(title, localCurrency);
       this.inComeSources.add(income);
       return income;
    }

    public Account addAccount(String title) {
        Account account = new Account(title, localCurrency);
        this.accounts.add(account);
        return account;
    }

    public Spend addSpend(String title) {
        Spend spend = new Spend(title, localCurrency);
        this.spends.add(spend);
        return spend;
    }

    public Goal addGoal(String title) {
        Goal goal = new Goal(title, localCurrency);
        this.goals.add(goal);
        return goal;
    }
    public void addAccountTransaction(InCome from, Account to, String sMoney) {
        from.addTransaction(to, sMoney);
    }

    public void addGoalTransaction(InCome from, Goal to,  String sMoney) {
        from.addTransaction(to, sMoney);
    }

    public void addSpendTransaction(Account from, Spend to, String sMoney) {
        from.addTransaction(to, sMoney);
    }


}
