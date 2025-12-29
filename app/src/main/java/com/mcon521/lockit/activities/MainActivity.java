package com.mcon521.lockit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.snackbar.Snackbar;
import com.mcon521.lockit.R;
import com.mcon521.lockit.databinding.ActivityMainBinding;
import com.mcon521.lockit.lib.Utils;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private Button mPasswordGenerator, mMyPasswords ,mLogout;
    private Snackbar mSnackBar;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            sendYouOnYourWay();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setupBindingAndToolbar();
        setupButtonsAndHandleButtonClicks();
    }

    private void setupButtonsAndHandleButtonClicks() {
        mPasswordGenerator = findViewById(R.id.gen_pass_button);
        mMyPasswords = findViewById(R.id.my_pass);
        mLogout = findViewById(R.id.logout);

        mMyPasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyPasswords();
            }
        });


        mPasswordGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordGenerator();
            }
        });

        mLogout.setOnClickListener(view -> {
            try {
                logOut();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void setupBindingAndToolbar() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.includeToolbar.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.removeItem(R.id.export);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showPasswordGenerator() {
        /*dismissSnackBarIfShown();*/
        Intent intent = new Intent(getApplicationContext(), Create_Password.class);
        /*intent.putExtra("GAME", mGame.getJSONFromCurrentGame());*/
        startActivity(intent);
    }

    private void showMyPasswords() {
        Intent intent = new Intent(getApplicationContext(), My_Passwords.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            sendYouOnYourWay();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if(!Utils.getLoginStatus(getApplicationContext())){
                sendYouOnYourWay();
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendYouOnYourWay() throws GeneralSecurityException, IOException {
//        Toast.makeText(this, String.valueOf(Utils.doesRequireLogin(getApplicationContext(), getString(R.string.requireLoginKey))), Toast.LENGTH_SHORT).show();
        if(Utils.doesRequireLogin(getApplicationContext(), getString(R.string.requireLoginKey))
                && !Utils.getLoginStatus(getApplicationContext()) && Utils.passwordIsSet(getApplicationContext()).length() == 0 )
        {
            goToSetLoginPassword();
        }
        else if ((Utils.doesRequireLogin(getApplicationContext(), getString(R.string.requireLoginKey))
                && !Utils.getLoginStatus(getApplicationContext()) && Utils.passwordIsSet(getApplicationContext()).length() > 0 )){
            goToLogin();
        }
    }

    private void logOut() throws GeneralSecurityException, IOException {
//        Utils.setRequireLogin(true);
        if(Utils.getLoginStatus(this) && Utils.doesRequireLogin(getApplicationContext(), getString(R.string.requireLoginKey))){
            Utils.setToLoggedOut(this);
//            finish();
            Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show();
            goToLogin();
        }
        Toast.makeText(this, String.valueOf(Utils.getLoginStatus(getApplicationContext())), Toast.LENGTH_SHORT).show();
    }


    private void goToSetLoginPassword(){
        startActivity(new Intent(this, SetLoginPassword.class));
    }

    private void goToLogin(){

        startActivity(new Intent(this, LoginActivity.class));
    }


}