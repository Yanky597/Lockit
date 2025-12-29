package com.mcon521.lockit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mcon521.lockit.R;
import com.mcon521.lockit.lib.Utils;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SplashActivity extends Activity {
    private static boolean splashLoaded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Utils.setLoginOnOffFromPreferenceValue(getApplicationContext(), getString(R.string.requireLoginKey));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        splashScreenRouter();

    }


    private void goToHome(){
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }


    private void splashScreenRouter() {
        if (!splashLoaded) {
            setContentView(R.layout.activity_splash);
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    goToHome();
                    finish();
                }
            }, secondsDelayed * 500);

            splashLoaded = true;
        } else {
            Intent goToMainActivity = new Intent(SplashActivity.this, MainActivity.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }
    }

}