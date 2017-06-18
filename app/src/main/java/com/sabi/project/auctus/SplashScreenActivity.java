package com.sabi.project.auctus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Map;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StaticData.setHelper(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, ?> all = settings.getAll();
        Boolean bool = Boolean.parseBoolean(all.get(getString(R.string.show_splash)).toString());
        if (bool){
            setContentView(R.layout.activity_splash_screen);
            int length = Integer.parseInt(all.get(getString(R.string.splash_length)).toString());
            ImageView imageView = (ImageView) findViewById(R.id.imageView1) ;
            final Handler handler = new Handler();

            final Runnable r = new Runnable() {
                public void run() {
                    StartItemsActivity();
                }
            };
            handler.postDelayed(r, length);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.removeCallbacks(r);
                    StartItemsActivity();
                }
            });

        } else{
            StartItemsActivity();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    protected void StartItemsActivity() {
        startActivity(new Intent(SplashScreenActivity.this, ItemsActivity.class));
        SplashScreenActivity.this.finish();
    }

}
