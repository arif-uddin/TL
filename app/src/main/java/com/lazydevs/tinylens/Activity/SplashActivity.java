package com.lazydevs.tinylens.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lazydevs.tinylens.R;


public class SplashActivity extends Activity {
    private Handler mWaitHandler = new Handler();
    ImageView slogan,company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        company = (ImageView) findViewById(R.id.company);
        slogan = (ImageView) findViewById(R.id.slogan);



        mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                //The following code will execute after the 5 seconds.

                try {

                    Intent intent;
                    //Let's Finish Splash Activity since we don't want to show this when user press back showOnMap.
                    if (user!=null){
                         intent = new Intent(SplashActivity.this,MainActivity.class);
                         startActivity(intent);
                    } else
                    {
                        intent = new Intent(SplashActivity.this,LoginActivity.class);
                         startActivity(intent);
                    }
                    //Let's Finish Splash Activity since we don't want to show this when user press back showOnMap.
                    finish();
                    //startActivity(intent);

                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 2000);  // Give a 2 seconds delay.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}
