package com.lazydevs.tinylens.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.lazydevs.tinylens.R;


public class SplashActivity extends Activity {
    private Handler mWaitHandler = new Handler();
    TextView slogan, company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        slogan = (TextView) findViewById(R.id.slogan);
        company = (TextView) findViewById(R.id.company);


        slogan.setText("PUT YOUR SLOGAN HERE");


        mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                //The following code will execute after the 5 seconds.

                try {

                    //Let's Finish Splash Activity since we don't want to show this when user press back showOnMap.
                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 2000);  // Give a 5 seconds delay.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}
