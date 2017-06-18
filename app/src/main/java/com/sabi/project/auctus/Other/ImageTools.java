package com.sabi.project.auctus.Other;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.TypedValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sabi on 3.6.2017..
 */

public class ImageTools {

    public static Map<String, Object> cache = new HashMap<String, Object>();

    public static Bitmap resizeBmp(Bitmap original){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, Resources.getSystem().getDisplayMetrics());
        Bitmap newBmp = null;
        if (original.getHeight()> px){
            newBmp = Bitmap.createScaledBitmap(original,Math.round(original.getWidth()*px/original.getHeight()),Math.round(original.getHeight()*px/original.getHeight()),false);
        }

        if (original.getWidth()> px){
            newBmp = Bitmap.createScaledBitmap(original,Math.round(original.getWidth()*px/original.getWidth()),Math.round(original.getHeight()*px/original.getWidth()),false);
        }

        return newBmp;
    }

    public static Bitmap resizeUserBmp(Bitmap original){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, Resources.getSystem().getDisplayMetrics());
        Bitmap newBmp = null;
        if (original.getHeight()> px){
            newBmp = Bitmap.createScaledBitmap(original,Math.round(original.getWidth()*px/original.getHeight()),Math.round(original.getHeight()*px/original.getHeight()),false);
        }

        if (original.getWidth()> px){
            newBmp = Bitmap.createScaledBitmap(original,Math.round(original.getWidth()*px/original.getWidth()),Math.round(original.getHeight()*px/original.getWidth()),false);
        }

        return getCircularBitmap(newBmp);
    }

    public static Bitmap resizeLargeBmp(Bitmap original, int wide){
        float px = wide;
        Bitmap newBmp = null;
        if (original.getHeight()> px){
            newBmp = Bitmap.createScaledBitmap(original,Math.round(original.getWidth()*px/original.getHeight()),Math.round(original.getHeight()*px/original.getHeight()),false);
        }

        if (original.getWidth()> px){
            newBmp = Bitmap.createScaledBitmap(original,Math.round(original.getWidth()*px/original.getWidth()),Math.round(original.getHeight()*px/original.getWidth()),false);
        }

        return newBmp;
    }

    public static void refreshCache(String path, Bitmap bitmap){
        cache.put(path,resizeUserBmp(bitmap));
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


}
