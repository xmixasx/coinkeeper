package com.mlucky.coin.app.gui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.mlucky.coin.app.impl.*;

public class ApplicationActivity extends Activity {
    final CoinApplication coinApplication = CoinApplication.getCoinApplication();
    private static final String INCOME_VIEW_TAG = "income_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
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

    public void inComeAddButtonClick(View view) {
        NewItemDialog newDial = new NewItemDialog(this,R.id.incomeadd_button);
        newDial.show();

    }

    public void inAccountAddButtonClick(View view) {
        NewItemDialog newDial = new NewItemDialog(this,R.id.accountadd_button);
        newDial.show();
//

    }
    public void spendAddButtonClick(View view) {
        NewItemDialog newDial = new NewItemDialog(this, R.id.spendadd_button);
        newDial.show();
    }
    public void goalAddButtonClick(View view) {
        NewItemDialog newDial = new NewItemDialog(this, R.id.goaladd_button);
        newDial.show();
    }
}
