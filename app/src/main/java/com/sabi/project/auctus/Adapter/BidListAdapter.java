package com.sabi.project.auctus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sabi.project.auctus.Model.Auction;
import com.sabi.project.auctus.Model.Bid;
import com.sabi.project.auctus.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabi on 22.5.2017..
 */

public class BidListAdapter extends ArrayAdapter<Bid> {

    Context mContext;

    public BidListAdapter(Context context, List<Bid> bids) {
        super(context, 0, bids);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bid bid = getItem(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.auction_bid_list_item, parent, false);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        TextView label = (TextView)row.findViewById(R.id.auction_bid_user_name);
        label.setText("User: " + bid.getUser().getName());

        TextView label1 = (TextView)row.findViewById(R.id.auction_bid_price);
        label1.setText(String.valueOf(bid.getPrice()));

        TextView label2 = (TextView)row.findViewById(R.id.auction_bid_datetime);
        label2.setText(dateFormat.format(bid.getDateTime()));

        return row;
    }
}
