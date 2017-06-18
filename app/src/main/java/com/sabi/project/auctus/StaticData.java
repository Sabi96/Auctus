package com.sabi.project.auctus;

import android.content.Context;
import android.os.Environment;
import com.sabi.project.auctus.Dao.DatabaseHelper;
import java.io.File;

/**
 * Created by Sabi on 22.5.2017..
 */

public class StaticData {
    public static DatabaseHelper helper;

    public static void setHelper(Context c){
        helper = DatabaseHelper.getHelper(c);
    }

    public static void Initialize(){

        File folder = new File(Environment.getExternalStorageDirectory() + "/Android/data/","com.sabi.project.auctus");
        folder.mkdir();

        folder = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.sabi.project.auctus/","images_users");
        folder.mkdir();

        folder = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.sabi.project.auctus/","images_items");
        folder.mkdir();
    }
}
