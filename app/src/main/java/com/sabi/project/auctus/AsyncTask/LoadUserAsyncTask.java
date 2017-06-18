package com.sabi.project.auctus.AsyncTask;

import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.sabi.project.auctus.Model.User;

import java.sql.SQLException;

/**
 * Created by Sabi on 6.6.2017..
 */

public class LoadUserAsyncTask extends AsyncTask<Void,Void,User> {

    private ImageView userImage;
    private TextView name, email, address, phone;
    private Dao<User, Long> userDao;

    public LoadUserAsyncTask(ImageView userImage, TextView name, TextView email, TextView address, TextView phone, Dao<User, Long> userDao){
        this.userImage = userImage;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.userDao = userDao;
    }


    @Override
    protected User doInBackground(Void... params) {
        try {
            return userDao.queryForId((long) 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        if (user!=null){
            name.setText(user.getName());
            email.setText(user.getEmail());
            if (user.getAddress()!=null && !user.getAddress().equals("")){
                address.setText(user.getAddress());
            }else{
                address.setVisibility(View.GONE);
            }
            if (user.getPhone()!=null && !user.getPhone().equals("")){
                phone.setText(user.getPhone());
            }else{
                phone.setVisibility(View.GONE);
            }
            new LoadImageLarge().execute(userImage, Environment.getExternalStorageDirectory().getPath()+user.getPicture());
        }
    }
}
