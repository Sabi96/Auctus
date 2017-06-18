package com.sabi.project.auctus.Receiver;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.sabi.project.auctus.ItemActivity;
import com.sabi.project.auctus.R;

/**
 * Created by Sabi on 28.5.2017..
 */

public class BidNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Long item = (Long) intent.getExtras().get("item");
        Intent newIntent = new Intent(context, ItemActivity.class);
        newIntent.putExtra("itemId",item);
        newIntent.putExtra("auctionT",true);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, newIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.icon)
                        .setSound(Uri.parse("android.resource://com.sabi.project.auctus/"+R.raw.notification))
                        .setContentIntent(contentIntent)
                        .setContentTitle("Bid notification")
                        .setContentText("Your bid is currently the highest bid!");

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, mBuilder.build());
    }
}
