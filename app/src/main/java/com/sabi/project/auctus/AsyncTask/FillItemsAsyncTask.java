package com.sabi.project.auctus.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.sabi.project.auctus.Adapter.ItemListAdapter;
import com.sabi.project.auctus.Model.Item;
import java.util.ArrayList;

/**
 * Created by Sabi on 5.6.2017..
 */

public class FillItemsAsyncTask extends AsyncTask<String,Void,ArrayList<Item>> {

    private Context context;
    private ArrayList<Item> items;
    private ItemListAdapter adapter;
    private TextView noItems;
    private ListView listView;
    Dao<Item,Long> itemDao;

    public FillItemsAsyncTask(ArrayList<Item> iList, ItemListAdapter al, ListView lv, TextView np, Dao<Item,Long> itemDao){
        this.items = iList;
        this.adapter = al;
        this.listView = lv;
        this.noItems = np;
        this.itemDao = itemDao;
    }


    @Override
    protected ArrayList<Item> doInBackground(String... params) {
        String name = params[0];
        String desc = params[1];
        ArrayList<Item> is = new ArrayList<Item>();
        try {
            for (Item item : itemDao.queryForAll()) {
                if (!item.isSold() && ( name.equals("") || item.getName().contains(name)) && ( desc.equals("") || item.getDescription().contains(desc))){
                    is.add(item);
                    //adapter.notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    @Override
    protected void onPostExecute(ArrayList<Item> is) {
        super.onPostExecute(is);
        items.addAll(is);
        adapter.notifyDataSetChanged();
        if (items.isEmpty()){
            noItems.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else{
            noItems.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }
}
