package com.mlucky.coin.app.gui;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.mlucky.coin.app.impl.*;

/**
 * Created by m.golimbiiev on 11/Nov/2014.
 */
public class NewItemDialog extends Dialog implements android.view.View.OnClickListener{
    public Dialog mDialog;
    public Button addButton;
    public Button noButton;
    public Activity mActivity;
    public EditText mEditText;
    public int ButtonId;
    public LayoutInflater layoutInflater = getLayoutInflater();

    final CoinApplication coinApplication = CoinApplication.getCoinApplication();
    private static final String INCOME_VIEW_TAG = "income_index";
    public NewItemDialog(Activity activity, int ButtonId) {
        super(activity);
        this.mActivity = activity;
        this.ButtonId = ButtonId;
    }
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_item_dialog);
        addButton = (Button) findViewById(R.id.yes);
        noButton = (Button) findViewById(R.id.no);
        mEditText = (EditText) findViewById(R.id.editText1);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);        addButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes :
                final LinearLayout horizontalLayout;
                final MoneyFlow moneyFlowItem;
                switch (ButtonId) {
                    case R.id.incomeadd_button:
                        horizontalLayout = (LinearLayout)mActivity.findViewById(R.id.income_linear_layout);
                        moneyFlowItem =  coinApplication.addIncome(mEditText.getText().toString());
                        break;
                    case R.id.accountadd_button:
                        horizontalLayout = (LinearLayout)mActivity.findViewById(R.id.account_linear_layout);
                        moneyFlowItem = coinApplication.addAccount(mEditText.getText().toString());
                        break;
                    case R.id.spendadd_button:
                        horizontalLayout = (LinearLayout)mActivity.findViewById(R.id.spend_linear_layout);
                        moneyFlowItem = coinApplication.addSpend(mEditText.getText().toString());
                        break;
                    default:
                        horizontalLayout = (LinearLayout)mActivity.findViewById(R.id.goal_linear_layout);
                        moneyFlowItem =  coinApplication.addGoal(mEditText.getText().toString());
                        break;
                }
                createItem(horizontalLayout, moneyFlowItem);
                break;
            case R.id.no:
                dismiss();
                break;
            default: break;
        }
        dismiss();
    }

    private void createItem(LinearLayout horLayout,final MoneyFlow moneyFlowItem) {
        //LayoutInflater layoutInflater = getLayoutInflater();


        LinearLayout itemLayout =
                (LinearLayout)layoutInflater.inflate(R.layout.item, horLayout, false);

        TextView titleText = (TextView)itemLayout.findViewById(R.id.item_title);
        titleText.setText(moneyFlowItem.getTitle().toString());
        TextView totalText = (TextView)itemLayout.findViewById(R.id.item_total);
        totalText.setText(moneyFlowItem.getTotal().toString());

        itemLayout.setTag(INCOME_VIEW_TAG);
        final ImageView imageView;
        imageView = (ImageView)itemLayout.findViewById(R.id.item_image);

        Drawable mIcon = getContext().getResources().getDrawable(R.drawable.ic_launcher);
        imageView.setBackground(mIcon);

        horLayout.addView(itemLayout);
        final int index;
        switch (horLayout.getId()){
            case R.id.account_linear_layout:
                index = coinApplication.getAccounts().indexOf(moneyFlowItem);
                break;
            case R.id.income_linear_layout:
                index = coinApplication.getInComeSources().indexOf(moneyFlowItem);
                break;
            case R.id.spend_linear_layout:
                index = coinApplication.getSpends().indexOf(moneyFlowItem);
                break;
            default:
                index = coinApplication.getGoals().indexOf(moneyFlowItem);
                break;
        }
        //final int index = coinApplication.getInComeSources().indexOf(moneyFlowItem);

        itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData dragData = ClipData.newPlainText((CharSequence) view.getTag(), Integer.toString(index));
                ClipData.Item item = new ClipData.Item(moneyFlowItem.getClass().toString());
                View.DragShadowBuilder inComeShadow = new View.DragShadowBuilder(imageView);
                dragData.addItem(item);
                view.startDrag(dragData,  // the data to be dragged
                        inComeShadow,  // the drag shadow builder
                        null,      // no need to use local data
                        0          // flags (not currently used, set to 0)
                );
                return false;
            }
        });
        dragAction(itemLayout,moneyFlowItem,horLayout);

    }
    private void dragAction(final LinearLayout itemLayout,final MoneyFlow moneyFlowItem, final LinearLayout horLayout){
        ImageView imageView;
        imageView = (ImageView)itemLayout.findViewById(R.id.item_image);
        Drawable mIcon = getContext().getResources().getDrawable(R.drawable.ic_launcher);
        imageView.setBackground(mIcon);

        imageView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                final int action = dragEvent.getAction();
                switch(action) {
                    case DragEvent.ACTION_DRAG_STARTED:

                        if(moneyFlowItem instanceof InCome)
                            return false;
                        else return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        int incomeIndex = Integer.parseInt((String) dragEvent.getClipData().getItemAt(0).getText());
                        String Str = view.getParent().getParent().toString();
                        String className = dragEvent.getClipData().getItemAt(1).getText().toString();
                        AmountDialog newAmount = new AmountDialog(mActivity, incomeIndex, moneyFlowItem, className, horLayout, itemLayout, Str);
                        newAmount.show();
                        return true;
                }
                return false;
            }


        });
    }
}
