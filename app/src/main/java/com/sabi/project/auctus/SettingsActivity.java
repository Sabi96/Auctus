package com.sabi.project.auctus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import com.j256.ormlite.dao.Dao;
import com.sabi.project.auctus.Model.User;
import com.sabi.project.auctus.Other.ImageTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }



    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            try {
                Dao<User, Long> userDao = StaticData.helper.getUserDao();
                User user = userDao.queryForId((long) 1);

                if (key.equals("user_name")) user.setName(sharedPreferences.getString(key,user.getName()));
                else if (key.equals("user_email")) user.setEmail(sharedPreferences.getString(key,user.getEmail()));
                else if (key.equals("user_address")) user.setAddress(sharedPreferences.getString(key,user.getAddress()));
                else if (key.equals("user_password")) user.setPassword(sharedPreferences.getString(key,user.getPassword()));
                else if (key.equals("user_phone")) user.setPhone(sharedPreferences.getString(key,user.getPhone()));

                userDao.update(user);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
            super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

            if(resultCode == RESULT_OK) {
                Uri selectedImage = imageReturnedIntent.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    File file = new File(Environment.getExternalStorageDirectory().toString()+"/Android/data/com.sabi.project.auctus/images_users/", "me.jpg");
                    OutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                    fOut.flush();
                    fOut.close();

                    try {
                        User user = StaticData.helper.getUserDao().queryForId((long)1);
                        user.setPicture("/Android/data/com.sabi.project.auctus/images_users/me.jpg");
                        StaticData.helper.getUserDao().update(user);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    ImageTools.refreshCache(Environment.getExternalStorageDirectory().toString()+"/Android/data/com.sabi.project.auctus/images_users/me.jpg",bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
            try {
                Dao<User, Long> userDao = StaticData.helper.getUserDao();
                User user = userDao.queryForId((long)1);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                editor.putString("user_name",user.getName());
                if (user.getAddress()!=null && !user.getAddress().equals("")) {
                    editor.putString("user_address", user.getAddress());
                }else{
                    editor.putString("user_address","");
                }
                editor.putString("user_email",user.getEmail());
                editor.putString("user_password",user.getPassword());
                if (user.getPhone()!=null && !user.getPhone().equals("")) {
                    editor.putString("user_phone", user.getPhone());
                }else{
                    editor.putString("user_phone","");
                }
                editor.commit();
            }catch (Exception e){
            }
            Preference preference = findPreference("user_image");
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
                public boolean onPreferenceClick(Preference preference){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    int PICK_IMAGE = 1;
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                    return true;
                }
            });

        }


    }

}
