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
        LayoutInflater layoutInflater = getLayoutInflater();

        LinearLayout inComeLayout = (LinearLayout)findViewById(R.id.income_linear_layout);
        LinearLayout itemLayout =
                // Add the LinearLayout layout to the parent income_linear_layout
                (LinearLayout)layoutInflater.inflate(R.layout.item, inComeLayout, false);

        // In order to get the view we have to use the new view with item_title in it
        InCome income = coinApplication.addIncome("Salary");

        TextView titleText = (TextView)itemLayout.findViewById(R.id.item_title);
        titleText.setText(income.getTitle().toString());
        TextView totalText = (TextView)itemLayout.findViewById(R.id.item_total);
        totalText.setText(income.getTotal().toString());

        itemLayout.setTag(INCOME_VIEW_TAG);
        final ImageView imageView;
        imageView = (ImageView)itemLayout.findViewById(R.id.item_image);

        Drawable mIcon = getResources().getDrawable(R.drawable.ic_launcher);
        imageView.setBackground(mIcon);

        inComeLayout.addView(itemLayout);

        final int index = coinApplication.getInComeSources().indexOf(income);

        itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                //String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = ClipData.newPlainText((CharSequence) view.getTag(), Integer.toString(index));
                //ClipData.Item item = new ClipData.Item(Integer.toString(totalText.getId()));
                View.DragShadowBuilder inComeShadow = new View.DragShadowBuilder(imageView);
                //dragData.addItem(item);
                view.startDrag(dragData,  // the data to be dragged
                        inComeShadow,  // the drag shadow builder
                        null,      // no need to use local data
                        0          // flags (not currently used, set to 0)
                );
                return false;
            }
        });
    }

    public void inAccountAddButtonClick(View view) {
        LayoutInflater layoutInflater = getLayoutInflater();

        LinearLayout accountLayout = (LinearLayout)findViewById(R.id.account_linear_layout);
        final LinearLayout itemLayout =
                (LinearLayout)layoutInflater.inflate(R.layout.item, accountLayout, false);

        Account account = coinApplication.addAccount("Agricole");

        TextView textTitle = (TextView)itemLayout.findViewById(R.id.item_title);
        textTitle.setText(account.getTitle().toString());
        TextView textTotal = (TextView)itemLayout.findViewById(R.id.item_total);
        textTotal.setText(account.getTotal().toString());

        ImageView imageView;
        imageView = (ImageView)itemLayout.findViewById(R.id.item_image);
        Drawable mIcon = getResources().getDrawable(R.drawable.ic_launcher);
        imageView.setBackground(mIcon);

        accountLayout.addView(itemLayout);

        final int accountIndex = coinApplication.getAccounts().indexOf(account);
        imageView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                final int action = dragEvent.getAction();
                switch(action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        int incomeIndex = Integer.parseInt((String) dragEvent.getClipData().getItemAt(0).getText());

                        InCome from = coinApplication.getInComeSources().get(incomeIndex);
                        Account to = coinApplication.getAccounts().get(accountIndex);
                        coinApplication.addAccountTransaction(from, to, "500");

                        LinearLayout incomeLayout =(LinearLayout)findViewById(R.id.income_linear_layout);
                        LinearLayout incomeItemLayout = (LinearLayout)incomeLayout.getChildAt(incomeIndex+1);

                        ((TextView)incomeItemLayout.findViewById(R.id.item_total)).setText(from.getTotal().toString());
                        ((TextView)itemLayout.getChildAt(2)).setText(to.getTotal().toString());

                        Toast.makeText(getApplicationContext(), "Dragged data is " + incomeIndex, Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }


        });

    }
}
