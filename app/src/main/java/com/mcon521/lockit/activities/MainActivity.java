package com.mcon521.lockit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.snackbar.Snackbar;
import com.mcon521.lockit.R;
import com.mcon521.lockit.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Button mPasswordGenerator, mMyPasswords;
    private Snackbar mSnackBar;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBindingAndToolbar();
        setupButtonsAndHandleButtonClicks();
    }

    private void setupButtonsAndHandleButtonClicks() {
        Button mPasswordGenerator = findViewById(R.id.gen_pass_button);

        Button mMyPasswords = findViewById(R.id.my_pass);

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

    /*private void dismissSnackBarIfShown() {
        if (mSnackBar.isShown()) {
            mSnackBar.dismiss();
        }
    }*/

    private void showPasswordGenerator() {
        /*dismissSnackBarIfShown();*/
        Intent intent = new Intent(getApplicationContext(), Create_Password.class);
        /*intent.putExtra("GAME", mGame.getJSONFromCurrentGame());*/
        startActivity(intent);
    }

    private void showMyPasswords() {
        /*dismissSnackBarIfShown();*/
        Intent intent = new Intent(getApplicationContext(), My_Passwords.class);
        /*intent.putExtra("GAME", mGame.getJSONFromCurrentGame());*/
        startActivity(intent);
    }


}