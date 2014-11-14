package com.mlucky.coin.app.gui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mlucky.coin.app.impl.*;

import java.util.InputMismatchException;

/**
 * Created by m.golimbiiev on 13/Nov/2014.
 */
public class AmountDialog extends Dialog implements android.view.View.OnClickListener{

    public Dialog mDialog;
    public Button addButton;
    public Button noButton;
    public Activity mActivity;
    public EditText mEditText;
    public int ButtonId;
    public int incomeIndex;
    public MoneyFlow moneyFlowItem;
    public String className;
    public LinearLayout horLayout;
    public String Str;
   public LinearLayout itemLayout;
    final int[] accountIndex = new int[1];
    public LayoutInflater layoutInflater = getLayoutInflater();
    final CoinApplication coinApplication = CoinApplication.getCoinApplication();
    public AmountDialog(Activity activity, int incomeIndex, MoneyFlow moneyFlowItem, String className, LinearLayout horLayout, LinearLayout itemLayout, String str) {
        super(activity);
        this.mActivity = activity;
        this.className = className;
        this.incomeIndex = incomeIndex;
        this.moneyFlowItem = moneyFlowItem;
        this.horLayout  = horLayout;
        this.itemLayout = itemLayout;
        this.Str = str;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.amount_dialog);
        addButton = (Button) findViewById(R.id.yes_button);
        noButton = (Button) findViewById(R.id.no_button);
        mEditText = (EditText) findViewById(R.id.editText2);

        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEditText.setFocusable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
       // mEditText.requestFocus();
        addButton.setOnClickListener(this);
        noButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_button :
                System.out.println("Something " + className);
                LinearLayout fromLayout = choosefromLayout(className);

                MoneyFlow from = chooseFrom(className,incomeIndex, fromLayout);

                MoneyFlow to = chooseTo(Str, moneyFlowItem, accountIndex);
                coinApplication.addTransaction(from, to, mEditText.getText().toString());
                LinearLayout fromItem = (LinearLayout)  fromLayout.getChildAt(incomeIndex+1);
                TextView fromItemTotal = (TextView)fromItem.getChildAt(2);
                fromItemTotal.setText(from.getTotal().toString());
                //horLayout =(LinearLayout)findViewById(R.id.income_linear_layout);
                LinearLayout incomeItemLayout = (LinearLayout)horLayout.getChildAt(accountIndex[0] + 1);
                //System.out.println("Item is NULL or Not null" + incomeItemLayout.toString());
                TextView inemTotal = (TextView)incomeItemLayout.getChildAt(2);
                inemTotal.setText(to.getTotal().toString());
                ((TextView)itemLayout.getChildAt(2)).setText(to.getTotal().toString());
                break;
            case R.id.no_button:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();

    }

    public MoneyFlow chooseFrom(String className, int incomeIndex, LinearLayout linearLayout){
        if(className.matches("(?i).*InCome")){
            linearLayout = (LinearLayout) mActivity.findViewById(R.id.income_linear_layout);
            return  (InCome)coinApplication.getInComeSources().get(incomeIndex);
            // to = coinApplication.getAccounts().get(incomeIndex);
        }
        else if (className.matches("(?i).*Account")){
            linearLayout = (LinearLayout) mActivity.findViewById(R.id.account_linear_layout);
            return (Account)coinApplication.getAccounts().get(incomeIndex);
            //to = coinApplication.getSpends().get(incomeIndex);
        }
        else if (className.matches("(?i).*Spend")){
            linearLayout = (LinearLayout) mActivity.findViewById(R.id.spend_linear_layout);
            return (Spend)coinApplication.getSpends().get(incomeIndex);
        }
        else {
            linearLayout = (LinearLayout) mActivity.findViewById(R.id.goal_linear_layout);
            return (Goal)coinApplication.getGoals().get(incomeIndex);
        }

    }
    public LinearLayout choosefromLayout(String className){
        if(className.matches("(?i).*InCome")){

            return  (LinearLayout) mActivity.findViewById(R.id.income_linear_layout);
            // to = coinApplication.getAccounts().get(incomeIndex);
        }
        else if (className.matches("(?i).*Account")){

            return (LinearLayout) mActivity.findViewById(R.id.account_linear_layout);
            //to = coinApplication.getSpends().get(incomeIndex);
        }
        else if (className.matches("(?i).*Spend")){

            return (LinearLayout) mActivity.findViewById(R.id.spend_linear_layout);
        }
        else {

            return  (LinearLayout) mActivity.findViewById(R.id.goal_linear_layout);
        }

    }
    public MoneyFlow chooseTo(String Str, MoneyFlow moneyFlowItem, int[] accountIndex){
        if(Str.matches("(?i).*account_linear.*")) {
            accountIndex[0] = coinApplication.getAccounts().indexOf(moneyFlowItem);
            return (Account) coinApplication.getAccounts().get(accountIndex[0]);

        }
        else if (Str.matches("(?i).*income_linear.*")) {
            accountIndex[0] = coinApplication.getInComeSources().indexOf(moneyFlowItem);
            return (InCome) coinApplication.getInComeSources().get(accountIndex[0]);

        }
        else if (Str.matches("(?i).*spend_linear.*")){
            accountIndex[0] =  coinApplication.getSpends().indexOf(moneyFlowItem);
            return (Spend) coinApplication.getSpends().get(accountIndex[0]);
        }
        else {
            accountIndex[0] = coinApplication.getGoals().indexOf(moneyFlowItem);
            return (Goal) coinApplication.getGoals().get(accountIndex[0]);
        }
    }

}
