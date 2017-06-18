package com.sabi.project.auctus.AsyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.sabi.project.auctus.Other.ImageTools;
import com.sabi.project.auctus.R;

/**
 * Created by Sabi on 3.6.2017..
 */

public class LoadImageUser extends AsyncTask<Object, Void, Bitmap> {

    private ImageView icon;


    @Override
    protected Bitmap doInBackground(Object... params) {
        icon = (ImageView)   params[0];
        if (ImageTools.cache.containsKey((String) params[1])) return (Bitmap) ImageTools.cache.get((String) params[1]);
        Bitmap bMap = BitmapFactory.decodeFile((String) params[1]);
        if (bMap!=null) {
            Bitmap resized = ImageTools.resizeUserBmp(bMap);
            if (resized==null){
                ImageTools.cache.put((String) params[1],bMap);
                return bMap;
            }else
                ImageTools.cache.put((String) params[1],resized);
                return resized;
        }
        else
            return null;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        if (result!=null)
            icon.setImageBitmap(result);
    }
}