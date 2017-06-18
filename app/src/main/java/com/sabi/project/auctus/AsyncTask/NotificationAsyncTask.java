package com.sabi.project.auctus.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by Sabi on 28.5.2017..
 */

public class NotificationAsyncTask extends AsyncTask<Long,Void,Long> {

    private Context context;

    public NotificationAsyncTask(Context c){
        this.context = c;
    }


    @Override
    protected Long doInBackground(Long... params) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return params[0];
    }

    @Override
    protected void onPostExecute(Long item) {
        super.onPostExecute(item);
        Intent intent = new Intent();
        intent.putExtra("item",item);
        intent.setAction("HIGHEST_BIDDER");
        context.sendBroadcast(intent);
    }
}
