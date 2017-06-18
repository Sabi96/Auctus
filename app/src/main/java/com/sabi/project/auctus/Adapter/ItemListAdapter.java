package com.sabi.project.auctus.Adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sabi.project.auctus.Model.Item;
import com.sabi.project.auctus.Other.StringTools;
import com.sabi.project.auctus.AsyncTask.LoadImage;
import com.sabi.project.auctus.R;

import java.util.ArrayList;

/**
 * Created by Sabi on 22.5.2017..
 */

public class ItemListAdapter extends ArrayAdapter<Item> {

    Context mContext;

    public ItemListAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.item_list_item, parent, false);

        TextView label=(TextView)row.findViewById(R.id.item_list_name);
        label.setText(StringTools.getShorter(item.getName()));

        TextView label1 = (TextView)row.findViewById(R.id.item_list_description);
        label1.setText(StringTools.getShort(item.getDescription()));

        ImageView icon=(ImageView)row.findViewById(R.id.item_list_picture);

        new LoadImage().execute(icon,Environment.getExternalStorageDirectory().getPath()+item.getPicture());

        return row;
    }
}
