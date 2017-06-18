package com.sabi.project.auctus.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.sabi.project.auctus.ItemsActivity;
import com.sabi.project.auctus.R;

public class ItemFilterDialog extends Dialog implements
        android.view.View.OnClickListener {

    public ItemsActivity ac;
    public Button yes, no;
    public EditText name;
    public EditText desc;

    public ItemFilterDialog(ItemsActivity a) {
        super(a);
        ac = a;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_item_dialog);
        yes = (Button) findViewById(R.id.btn_filter_item_ok);
        no = (Button) findViewById(R.id.btn_filter_item_cancel);
        name = (EditText) findViewById(R.id.filter_name);
        name.setText(ac.name);
        desc = (EditText) findViewById(R.id.filter_desc);
        desc.setText(ac.desc);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filter_item_ok:
                if (this.name.getText()==null || this.name.getText().toString().equals("")) ac.name = "";
                else ac.name = this.name.getText().toString();
                if (this.desc.getText()==null || this.desc.getText().toString().equals("")) ac.desc = "";
                else ac.desc = this.desc.getText().toString();
                this.dismiss();
                break;
            default:
                this.cancel();
                break;

        }
    }
}