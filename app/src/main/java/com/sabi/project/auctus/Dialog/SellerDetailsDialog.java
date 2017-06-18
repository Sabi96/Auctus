package com.sabi.project.auctus.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sabi.project.auctus.Model.User;
import com.sabi.project.auctus.R;

import org.w3c.dom.Text;

import static android.R.attr.name;

public class SellerDetailsDialog extends Dialog implements
        android.view.View.OnClickListener {

    User user;
    Button close;

    public SellerDetailsDialog(Context context, User user) {
        super(context);
        this.user = user;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_details_dialog);
        close = (Button) findViewById(R.id.btn_dialog_close);
        TextView name = (TextView) findViewById(R.id.seller_name);
        TextView address = (TextView) findViewById(R.id.seller_address);
        TextView email = (TextView) findViewById(R.id.seller_email);
        TextView phone = (TextView) findViewById(R.id.seller_phone);
        RelativeLayout addressLayout = (RelativeLayout) findViewById(R.id.seller_address_layout);
        RelativeLayout phoneLayout = (RelativeLayout) findViewById(R.id.seller_phone_layout);

        name.setText(user.getName());
        email.setText(user.getEmail());
        if (user.getAddress()!=null && !user.getAddress().equals("")){
            address.setText(user.getAddress());
        }else{
            addressLayout.setVisibility(View.GONE);
        }
        if (user.getPhone()!=null && !user.getPicture().equals("")){
            phone.setText(user.getPhone());
        }else{
            phoneLayout.setVisibility(View.GONE);
        }
        close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}