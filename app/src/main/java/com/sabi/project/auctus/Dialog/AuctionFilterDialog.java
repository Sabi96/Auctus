package com.sabi.project.auctus.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.sabi.project.auctus.AuctionsActivity;
import com.sabi.project.auctus.R;

public class AuctionFilterDialog extends Dialog implements
        android.view.View.OnClickListener {

    public AuctionsActivity ac;
    public Button yes, no;
    public EditText name;
    public EditText desc;
    public RadioButton rbtnAll, rbtnOngoing, rbtnFinished;

    public AuctionFilterDialog(AuctionsActivity a) {
        super(a);
        ac = a;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_auction_dialog);
        yes = (Button) findViewById(R.id.btn_filter_auction_ok);
        no = (Button) findViewById(R.id.btn_filter_auction_cancel);
        name = (EditText) findViewById(R.id.filter_auction_name);
        name.setText(ac.name);
        desc = (EditText) findViewById(R.id.filter_auction_desc);
        desc.setText(ac.desc);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        rbtnAll = (RadioButton) findViewById(R.id.filter_all);
        rbtnOngoing = (RadioButton) findViewById(R.id.filter_ongoing);
        rbtnFinished = (RadioButton) findViewById(R.id.filter_finished);
        if (ac.filterStatus == 0) rbtnAll.setChecked(true);
        else if (ac.filterStatus == 1) rbtnOngoing.setChecked(true);
        else if (ac.filterStatus == 2) rbtnFinished.setChecked(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filter_auction_ok:
                if (this.name.getText()==null || this.name.getText().toString().equals("")) ac.name = "";
                else ac.name = this.name.getText().toString();
                if (this.desc.getText()==null || this.desc.getText().toString().equals("")) ac.desc = "";
                else ac.desc = this.desc.getText().toString();
                if (rbtnAll.isChecked()) ac.filterStatus = 0;
                else if (rbtnFinished.isChecked()) ac.filterStatus = 2;
                else if (rbtnOngoing.isChecked()) ac.filterStatus = 1;
                this.dismiss();
                break;
            default:
                this.cancel();
                break;

        }
    }
}