package com.sabi.project.auctus;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.sabi.project.auctus.Adapter.BidListAdapter;
import com.sabi.project.auctus.Adapter.DrawerListAdapter;
import com.sabi.project.auctus.AsyncTask.LoadImageLarge;
import com.sabi.project.auctus.Dialog.SellerDetailsDialog;
import com.sabi.project.auctus.Model.Auction;
import com.sabi.project.auctus.Model.Bid;
import com.sabi.project.auctus.Model.User;
import com.sabi.project.auctus.Other.StringTools;
import com.sabi.project.auctus.AsyncTask.LoadImage;
import com.sabi.project.auctus.AsyncTask.LoadImageUser;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AuctionActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<String> options;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private Long id;
    private ImageView image;
    private TextView uName,address,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);
        Intent intent = getIntent();
        id = (Long) intent.getExtras().get("auctionId");

        options = new ArrayList<String>();
        options.add("Items");
        options.add("Auctions");
        options.add("Settings");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.drawerList);
        drawerList.setAdapter(new DrawerListAdapter(this, options));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarItems);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        image = (ImageView) findViewById(R.id.drawer_user_image);
        uName = (TextView) findViewById(R.id.drawer_user_name);
        email = (TextView) findViewById(R.id.drawer_user_email);
        address = (TextView) findViewById(R.id.drawer_user_address);
        phone = (TextView) findViewById(R.id.drawer_user_phone);

        setUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Dao<Auction, Long> auctionDao = StaticData.helper.getAuctionDao();
            final Auction auction = auctionDao.queryForId(id);

            TextView label = (TextView) findViewById(R.id.lblAuction_user_name);
            label.setText(auction.getUser().getName());

            TextView label1 = (TextView) findViewById(R.id.auction_user_email);
            label1.setText(auction.getUser().getEmail());

            TextView label2 = (TextView) findViewById(R.id.lblAuction_item_name);
            label2.setText(StringTools.getShorter(auction.getItem().getName()));

            TextView label3 = (TextView) findViewById(R.id.lblAuction_item_description);
            label3.setText(StringTools.getShort(auction.getItem().getDescription()));

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            TextView label4 = (TextView) findViewById(R.id.lblAuction_start_date);
            label4.setText(getString(R.string.auction_startdate_placeholder) + format.format(auction.getStartDate()));

            TextView label5 = (TextView) findViewById(R.id.lblAuction_end_date);
            label5.setText(getString(R.string.auction_enddate_placeholder) + format.format(auction.getEndDate()));

            ImageView iconUser = (ImageView) findViewById(R.id.auction_user_image);
            new LoadImageUser().execute(iconUser, Environment.getExternalStorageDirectory().getPath()+auction.getUser().getPicture());

            ImageView iconItem = (ImageView) findViewById(R.id.auction_item_image);

            new LoadImage().execute(iconItem, Environment.getExternalStorageDirectory().getPath()+auction.getItem().getPicture());

            Dao<Bid, Long> bidDao= StaticData.helper.getBidDao();

            List<Bid> bids = bidDao.queryBuilder().where().eq("auction_id",auction.getId()).query();

            ListView listBids = (ListView) findViewById(R.id.auction_bids_list);
            BidListAdapter bl = new BidListAdapter(this,bids);
            listBids.setAdapter(bl);

            Button btnSellerInfo = (Button) findViewById(R.id.open_seller_dialog);
            if (auction.getItem().isSold() && !bids.isEmpty() && bids.get(bids.size()-1).getUser().getId()==(long)1)
                btnSellerInfo.setVisibility(View.VISIBLE);

            btnSellerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SellerDetailsDialog sdd = new SellerDetailsDialog(AuctionActivity.this, auction.getUser());
                    sdd.show();
                }
            });

            RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_item);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AuctionActivity.this, ItemActivity.class);
                    intent.putExtra("itemId",auction.getItem().getId());
                    startActivity(intent);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        setUser();
    }

    private void setUser(){
        User user = null;
        try {
            user = StaticData.helper.getUserDao().queryForId((long) 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user!=null){
            uName.setText(user.getName());
            email.setText(user.getEmail());
            if (user.getAddress()!=null && !user.getAddress().equals("")){
                address.setText(user.getAddress());
            }else{
                address.setVisibility(View.GONE);
            }
            if (user.getPhone()!=null && !user.getPhone().equals("")){
                phone.setText(user.getPhone());
            }else{
                phone.setVisibility(View.GONE);
            }
            new LoadImageLarge().execute(image, Environment.getExternalStorageDirectory().getPath()+user.getPicture());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id){
            selectItem(position);
        }

    }

    private void selectItem(int position){
        switch (options.get(position)){
            case "Auctions": {
                startActivity(new Intent(AuctionActivity.this, AuctionsActivity.class));
                //finish();
                break;
            }
            case "Items": {
                startActivity(new Intent(AuctionActivity.this, ItemsActivity.class));
                //finish();
                break;
            }
            case "Settings": {
                startActivity(new Intent(AuctionActivity.this, SettingsActivity.class));
                //finish();
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(AuctionActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
