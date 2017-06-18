package com.sabi.project.auctus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sabi.project.auctus.Adapter.AuctionListAdapter;
import com.sabi.project.auctus.Adapter.DrawerListAdapter;
import com.sabi.project.auctus.AsyncTask.FillAuctionsAsyncTask;
import com.sabi.project.auctus.AsyncTask.LoadImageLarge;
import com.sabi.project.auctus.Dialog.AuctionFilterDialog;
import com.sabi.project.auctus.Model.Auction;
import com.sabi.project.auctus.Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class AuctionsActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<String> options;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ArrayList<Auction> auctions;
    private AuctionListAdapter adapter;
    private ListView listView;
    private TextView noParticipate;
    public String name = "";
    public String desc = "";
    public int filterStatus = 0;
    private ImageView image;
    private TextView uName,address,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auctions);

        options = new ArrayList<String>();
        options.add("Items");
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

        auctions = new ArrayList<Auction>();
        adapter = new AuctionListAdapter(this, auctions);
        listView = (ListView) findViewById(R.id.auctions_list);
        listView.setAdapter(adapter);

        noParticipate = (TextView) findViewById(R.id.lblNoAuctionsForUser);
        try {
            new FillAuctionsAsyncTask(auctions,adapter,listView,noParticipate, StaticData.helper.getAuctionDao(), StaticData.helper.getBidDao()).execute(name,desc,filterStatus);
        } catch (SQLException e) {
            Log.e("MYAPP", "exception", e);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Auction auction = (Auction) parent.getItemAtPosition(position);
                Intent auctionIntent = new Intent(AuctionsActivity.this, AuctionActivity.class);
                auctionIntent.putExtra("auctionId", auction.getId());
                startActivity(auctionIntent);

            }

        });

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



    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id){
            selectItem(position);
        }

    }


    private void selectItem(int position){
        switch (options.get(position)){
            case "Items": {
                startActivity(new Intent(AuctionsActivity.this, ItemsActivity.class));
                //finish();
                break;
            }
            case "Settings": {
                startActivity(new Intent(AuctionsActivity.this, SettingsActivity.class));
                //finish();
                break;
            }
            default:{
                break;
            }
        }
    }

    public void refresh(){
        try {
            auctions.clear();
            adapter = new AuctionListAdapter(this, auctions);
            listView.setAdapter(adapter);
            new FillAuctionsAsyncTask(auctions,adapter,listView,noParticipate,StaticData.helper.getAuctionDao(),StaticData.helper.getBidDao()).execute(name,desc,filterStatus);
        } catch (SQLException e) {
            Log.e("MYAPP", "exception", e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

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

    protected void btnStartAuctionActivity(View v){
        startActivity(new Intent(AuctionsActivity.this, AuctionActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(AuctionsActivity.this, SettingsActivity.class));
            return true;
        }else if (id == R.id.action_filter){
            AuctionFilterDialog afd = new AuctionFilterDialog(this);
            afd.show();
            afd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    refresh();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
