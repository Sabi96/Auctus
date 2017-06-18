package com.sabi.project.auctus.AsyncTask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.sabi.project.auctus.Adapter.AuctionListAdapter;
import com.sabi.project.auctus.Model.Auction;
import com.sabi.project.auctus.Model.Bid;

import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Sabi on 5.6.2017..
 */

public class FillAuctionsAsyncTask extends AsyncTask<Object,Void,ArrayList<Auction>> {

    private ArrayList<Auction> auctions;
    private AuctionListAdapter adapter;
    private TextView noPart;
    private ListView listView;
    private Dao<Auction, Long> auctionDao;
    private Dao<Bid, Long> bidDao;

    public FillAuctionsAsyncTask( ArrayList<Auction> aList, AuctionListAdapter al, ListView lv, TextView np,Dao<Auction, Long> auctionDao,Dao<Bid, Long> bidDao){
        this.auctions = aList;
        this.adapter = al;
        this.listView = lv;
        this.noPart = np;
        this.auctionDao = auctionDao;
        this.bidDao = bidDao;
    }


    @Override
    protected ArrayList<Auction> doInBackground(Object... params) {
        String name = (String) params[0];
        String desc = (String) params[1];
        int filterStatus = (int) params[2];
        ArrayList<Auction> as = new ArrayList<Auction>();
        try {
            for (Auction auction : auctionDao) {
                if ((auction.getUser().getId()==1 || !bidDao.queryBuilder().where().eq("auction_id",auction.getId()).and().eq("user_id",1).query().isEmpty())
                        && auction.getItem().getName().contains(name) && auction.getItem().getDescription().contains(desc)){
                    //AKO IMA SOLD==true ILI AKO JE SOLD==false I NEMA DATUM KRAJA ILI DATUM KRAJA POSLE TRENUTNOG
                    if (filterStatus==0 || (filterStatus==2 && auction.getItem().isSold()) || (filterStatus==1 && !auction.getItem().isSold()) && (auction.getEndDate()==null || auction.getEndDate().after(new Date())))
                    as.add(auction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return as;
    }

    @Override
    protected void onPostExecute(ArrayList<Auction> as) {
        super.onPostExecute(as);
        auctions.addAll(as);
        if (auctions.isEmpty()){
            noPart.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else{
            noPart.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }
}