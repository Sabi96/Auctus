package com.sabi.project.auctus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sabi.project.auctus.Model.Auction;
import com.sabi.project.auctus.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Sabi on 22.5.2017..
 */

public class ItemAuctionItemAdapter extends ArrayAdapter<Auction> {

    Context mContext;

    public ItemAuctionItemAdapter(Context context, List<Auction> auctions) {
        super(context, 0, auctions);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Auction auction = getItem(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.item_auction_item, parent, false);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        TextView label = (TextView)row.findViewById(R.id.item_auction_user_name);
        label.setText("User: " + auction.getUser().getName());

        TextView label1 = (TextView)row.findViewById(R.id.item_auction_start_date);
        label1.setText("Listed on: " + dateFormat.format(auction.getStartDate()));

        TextView label2 = (TextView)row.findViewById(R.id.item_auction_end_date);
        label2.setText("Ends on: " + dateFormat.format(auction.getEndDate()));

        TextView label3 = (TextView)row.findViewById(R.id.item_auction_latest_bid);
        label3.setText("Highest bid: " + auction.getBids().get(auction.getBids().size()-1).getPrice());


        return row;
    }
}
