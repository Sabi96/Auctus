package com.sabi.project.auctus;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.j256.ormlite.dao.Dao;
import com.sabi.project.auctus.Dao.DatabaseHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

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
