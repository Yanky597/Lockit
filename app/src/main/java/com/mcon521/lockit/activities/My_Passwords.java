package com.mcon521.lockit.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mcon521.lockit.R;
import com.mcon521.lockit.classes.Entries;
import com.mcon521.lockit.classes.PasswordAdapater;
import com.mcon521.lockit.databinding.ActivityMyPasswordsBinding;

import java.util.Collections;

public class My_Passwords extends AppCompatActivity implements PasswordAdapater.ItemClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMyPasswordsBinding binding;
    public Entries mPassWordList;
    ImageButton mimageButtonHome, mimageButtonCreatePassword,mimageButtonMyPasswordList;

    private final String mMyList = "MYLIST";
    private final String mKeyPrefsName = "MYPASSWORDLIST";

    PasswordAdapater adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPasswordListFromSharedPreferences();
        setupBindingAndToolbar();

        setupRecyclerView();
        wireUpTheBottomNavBar();
        setupFabAndGeneratePassword();
    }

    private void setupFabAndGeneratePassword() {

        FloatingActionButton fab = findViewById(R.id.go_to_generate_password_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Create_Password.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.myPasswordsListContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PasswordAdapater(this, mPassWordList.getPasswordList());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void setupBindingAndToolbar() {
        binding = ActivityMyPasswordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.passToolbar.toolbar);
    }



    public void getPasswordListFromSharedPreferences(){
        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);
        Gson gson = new Gson();
        if(preferences.contains(mMyList)){
            String json = preferences.getString(mMyList, mKeyPrefsName );
            mPassWordList = gson.fromJson(json, Entries.class);
            sortTheList(mPassWordList);
        }
        /*makeAToast();*/
    }




    public void sortTheList(Entries list){
        if(list != null){
            Collections.sort(list.getPasswordList(), (a, b) -> {
                return a.getSite().compareTo(b.getSite());
            });
        }
    }

    protected void setupClipboard(String text){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        CharSequence label = "copiedText";
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }

    public void wireUpTheBottomNavBar() {

        mimageButtonHome = binding.bottomBarNav.buttonNavToHome;
        mimageButtonCreatePassword = binding.bottomBarNav.buttonNavToNewPassword;
        mimageButtonMyPasswordList = binding.bottomBarNav.buttonNavToMyPassword;
        mimageButtonMyPasswordList.setBackgroundColor(mimageButtonMyPasswordList.getContext().getResources().getColor(R.color.secondaryDarkColor));

        mimageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mimageButtonCreatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Create_Password.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(this, EdityEntry.class);
        intent.putExtra("CURRENTTITLE", adapter.getItem(position));
        intent.putExtra("CURRENTUSERNAME", adapter.getUSer(position));
        intent.putExtra("CURRENTPASSWORD", adapter.getPass(position));
        intent.putExtra("CURRENTPOSITION", position);

        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
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