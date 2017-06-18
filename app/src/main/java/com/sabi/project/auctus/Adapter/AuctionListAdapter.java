package com.sabi.project.auctus.Adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.sabi.project.auctus.Model.Auction;
import com.sabi.project.auctus.AsyncTask.LoadImage;
import com.sabi.project.auctus.Model.Bid;
import com.sabi.project.auctus.Other.StringTools;
import com.sabi.project.auctus.R;
import com.sabi.project.auctus.StaticData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Sabi on 22.5.2017..
 */

public class AuctionListAdapter extends ArrayAdapter<Auction> {

    Context mContext;

    public AuctionListAdapter(Context context, List<Auction> auctions) {
        super(context, 0, auctions);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Auction auction = getItem(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.auction_list_item, parent, false);
        TextView label=(TextView)row.findViewById(R.id.auction_list_item_name);
        label.setText(StringTools.getShorter(auction.getItem().getName()));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        TextView label1 = (TextView)row.findViewById(R.id.auction_list_item_start_date);
        label1.setText(mContext.getString(R.string.auction_startdate_placeholder) + dateFormat.format(auction.getStartDate()));

        TextView label2 = (TextView)row.findViewById(R.id.auction_list_item_ends_on);
        if (auction.getEndDate()!=null){
            label2.setText(mContext.getString(R.string.auction_enddate_placeholder) + dateFormat.format(auction.getEndDate()));
        }else{
            label2.setText(mContext.getString(R.string.auction_enddate_placeholder) + R.string.undefined);
        }

        TextView label3 = (TextView)row.findViewById(R.id.auction_list_start_price);

        label3.setText(mContext.getString(R.string.startprice) + " : " + auction.getStartPrice());

        TextView label4 = (TextView)row.findViewById(R.id.auction_list_latest_bid);

        try {
            Dao<Bid, Long> bidDao = StaticData.helper.getBidDao();
            auction.setBids(bidDao.queryBuilder().where().eq("auction_id",auction.getId()).query());
            if (!auction.getBids().isEmpty()){
                label4.setText(mContext.getString(R.string.latest_bid) + ": "+auction.getBids().get(auction.getBids().size()-1).getPrice());
                label4.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){

        }
        ImageView icon=(ImageView)row.findViewById(R.id.auction_list_item_picture);

        new LoadImage().execute(icon,Environment.getExternalStorageDirectory().getPath()+auction.getItem().getPicture());

        return row;
    }
}
