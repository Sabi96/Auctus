package com.sabi.project.auctus;

import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sabi.project.auctus.Adapter.DrawerListAdapter;
import com.sabi.project.auctus.AsyncTask.LoadImageLarge;
import com.sabi.project.auctus.Fragment.ItemAuctionsFragment;
import com.sabi.project.auctus.Fragment.ItemFragment;
import com.sabi.project.auctus.Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {
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
        if (StaticData.helper==null) StaticData.setHelper(this);

        setContentView(R.layout.activity_item);

        options = new ArrayList<String>();
        options.add("Auctions");
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
                getSupportActionBar().setTitle("Closed");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Opened");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        image = (ImageView) findViewById(R.id.drawer_user_image);
        uName = (TextView) findViewById(R.id.drawer_user_name);
        email = (TextView) findViewById(R.id.drawer_user_email);
        address = (TextView) findViewById(R.id.drawer_user_address);
        phone = (TextView) findViewById(R.id.drawer_user_phone);

        setUser();

        Intent intent = getIntent();
        id = (Long) intent.getExtras().get("itemId");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        if (intent.getExtras().get("auctionT")!=null){
            viewPager.setCurrentItem(1);
        }

    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ItemFragment.newInstance(id);
                case 1:
                default:
                    return ItemAuctionsFragment.newInstance(id);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Item Details";
                case 1:
                default:
                    return "Bids";
            }
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


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id){
            selectItem(position);
        }

    }

    private void selectItem(int position){
        switch (options.get(position)){
            case "Auctions": {
                startActivity(new Intent(ItemActivity.this, AuctionsActivity.class));
                //finish();
                break;
            }
            case "Items": {
                startActivity(new Intent(ItemActivity.this, ItemsActivity.class));
                //finish();
                break;
            }
            case "Settings": {
                startActivity(new Intent(ItemActivity.this, SettingsActivity.class));
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
            startActivity(new Intent(ItemActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
