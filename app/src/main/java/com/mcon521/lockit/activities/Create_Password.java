package com.mcon521.lockit.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.mcon521.lockit.R;
import com.mcon521.lockit.classes.Password;
import com.mcon521.lockit.databinding.ActivityCreatePasswordBinding;

public class Create_Password extends AppCompatActivity {

    private Password mPass;
    private AppBarConfiguration appBarConfiguration;
    private ActivityCreatePasswordBinding binding;
    private TextView mdisplay_text;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        setupBindingAndToolbar();
        setupViews();

        setupFab();

    }

    private void setupViews() {
        mdisplay_text = binding.passwordContent.displayText;
        /*mdisplay_text.setText(getResources().getString(R.string.welcome));*/
    }


    private void generatePassword(){
        mPass = new Password();

        mdisplay_text.setText(mPass.newPassword(mPass));

    }


    private void setupFab() {
        ExtendedFloatingActionButton fab = findViewById(R.id.generate_password_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePassword();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    private void setupBindingAndToolbar() {
        binding = ActivityCreatePasswordBinding.inflate(getLayoutInflater());
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}