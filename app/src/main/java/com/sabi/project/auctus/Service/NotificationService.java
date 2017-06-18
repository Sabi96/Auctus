package com.sabi.project.auctus.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sabi.project.auctus.AsyncTask.NotificationAsyncTask;

/**
 * Created by Sabi on 28.5.2017..
 */

public class NotificationService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Long id = (Long) intent.getExtras().get("item");
        NotificationAsyncTask nat = new NotificationAsyncTask(getApplicationContext());
        nat.execute(id);
        stopSelfResult(startId);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
