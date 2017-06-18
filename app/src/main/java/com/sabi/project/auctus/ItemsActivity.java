package com.sabi.project.auctus;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.sabi.project.auctus.Adapter.DrawerListAdapter;
import com.sabi.project.auctus.Adapter.ItemListAdapter;
import com.sabi.project.auctus.AsyncTask.FillItemsAsyncTask;
import com.sabi.project.auctus.AsyncTask.LoadImageLarge;
import com.sabi.project.auctus.Dialog.ItemFilterDialog;
import com.sabi.project.auctus.Model.Item;
import com.sabi.project.auctus.Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<String> options;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ArrayList<Item> items;
    private ItemListAdapter adapter;
    private ListView listView;
    private TextView noItems;
    public String name = "";
    public String desc = "";
    private ImageView image;
    private TextView uName,address,phone,email;
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    StaticData.Initialize();
                } else
                {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (ActivityCompat.checkSelfPermission(ItemsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ItemsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        }

        setContentView(R.layout.activity_items);
        options = new ArrayList<String>();
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

        noItems = (TextView) findViewById(R.id.lblNoUnsoldItems);
        items = new ArrayList<Item>();
        adapter = new ItemListAdapter(this, items);
        listView = (ListView) findViewById(R.id.items_list);
        listView.setAdapter(adapter);
        try {
            new FillItemsAsyncTask(items,adapter,listView,noItems, StaticData.helper.getItemDao()).execute(name,desc);
        } catch (SQLException e) {
            Log.e("MYAPP", "exception", e);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                Intent itemIntent = new Intent(ItemsActivity.this, ItemActivity.class);
                itemIntent.putExtra("itemId",item.getId());
                startActivity(itemIntent);

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
            case "Auctions": {
                startActivity(new Intent(ItemsActivity.this, AuctionsActivity.class));
                //finish();
                break;
            }
            case "Settings": {
                startActivity(new Intent(ItemsActivity.this, SettingsActivity.class));
                //finish();
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        }

    @Override
    protected void onResume() {
        super.onResume();
        //fillDatabase();

    }

    public void refresh(){
        try {
            items.clear();
            adapter = new ItemListAdapter(this, items);
            listView.setAdapter(adapter);
            new FillItemsAsyncTask(items,adapter,listView,noItems,StaticData.helper.getItemDao()).execute(name,desc);
        } catch (SQLException e) {
            Log.e("MYAPP", "exception", e);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(ItemsActivity.this, SettingsActivity.class));
            return true;
        }else if (id == R.id.action_filter) {
            ItemFilterDialog cdd = new ItemFilterDialog(this);
            cdd.show();
            cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    refresh();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}
