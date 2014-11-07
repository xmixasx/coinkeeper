package com.mlucky.coin.app.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mlucky.coin.app.impl.*;

public class ApplicationActivity extends Activity {
    final CoinApplication coinApplication = CoinApplication.getCoinApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        Button inComeAddButton = (Button)findViewById(R.id.incomeadd_button);
        inComeAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout inComeLinearLayout = (LinearLayout)findViewById(R.id.income_linear_layout);
                // Layout inflater
                LayoutInflater layoutInflater = getLayoutInflater();
                LinearLayout itemLinearLayout =
                // Add the LinearLayout layout to the parent income_linear_layout
                (LinearLayout)layoutInflater.inflate(R.layout.item, inComeLinearLayout, false);

                // In order to get the view we have to use the new view with item_title in it
                InCome income = coinApplication.addIncome("Salary");
                Account agricole = coinApplication.addAccount("Agricole");
                TextView textTitle = (TextView)itemLinearLayout.findViewById(R.id.item_title);
                textTitle.setText(income.getTitle().toString());
                coinApplication.addAccountTransaction(income, agricole, "840");
                TextView textTotal = (TextView)itemLinearLayout.findViewById(R.id.item_total);
                textTotal.setText(income.getTotal().toString());

                    // Add the text view to the parent layout
                inComeLinearLayout.addView(itemLinearLayout);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.application, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void inComeAddButtonClick(View view) {
//
//    }
}
