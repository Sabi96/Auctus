package com.sabi.project.auctus.Fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.sabi.project.auctus.Model.Item;
import com.sabi.project.auctus.AsyncTask.LoadImageLarge;
import com.sabi.project.auctus.StaticData;
import com.sabi.project.auctus.R;

import java.sql.SQLException;

public class ItemFragment extends Fragment {
    private Long id;

    public static ItemFragment newInstance(Long id) {
        ItemFragment fragment = new ItemFragment();
        fragment.init(id);
        return fragment;
    }

    public void init(Long id){
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        try {
            Dao<Item, Long> itemDao = StaticData.helper.getItemDao();
            Item item = itemDao.queryForId(id);

            TextView label=(TextView) view.findViewById(R.id.lblItemName);
            label.setText(item.getName());

            TextView label1=(TextView) view.findViewById(R.id.lblItemDescription);
            label1.setText(item.getDescription());

            TextView label2=(TextView) view.findViewById(R.id.lblitemSold);
            if (item.isSold()) label2.setText("ITEM IS SOLD");
            else label2.setText("ITEM IS NOT SOLD");

            ImageView iv = (ImageView) view.findViewById(R.id.itemPicture);
            new LoadImageLarge().execute(iv, Environment.getExternalStorageDirectory().getPath()+item.getPicture());
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return view;
    }

}