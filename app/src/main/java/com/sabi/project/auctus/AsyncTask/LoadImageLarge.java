package com.sabi.project.auctus.AsyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.sabi.project.auctus.R;

/**
 * Created by Sabi on 3.6.2017..
 */

public class LoadImageLarge extends AsyncTask<Object, Void, Bitmap> {

    private ImageView icon;


    @Override
    protected Bitmap doInBackground(Object... params) {
        icon = (ImageView)   params[0];

        Bitmap bMap = BitmapFactory.decodeFile((String) params[1]);
        return bMap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        if (result!=null)
            icon.setImageBitmap(result);
        else
            icon.setImageResource(R.drawable.icon);
    }
}