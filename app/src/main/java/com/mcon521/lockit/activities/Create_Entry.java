package com.mcon521.lockit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.mcon521.lockit.R;
import com.mcon521.lockit.classes.Entries;
import com.mcon521.lockit.classes.Entry;
import com.mcon521.lockit.databinding.ActivityCreateEntryBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class Create_Entry extends AppCompatActivity {

    public Entries mPassWordList;
    public Entry entry;

    ImageButton mimageButtonHome, mimageButtonCreatePassword,mimageButtonMyPasswordList;

    EditText mTitle, mUser, mPassword;
    String password;
    private AppBarConfiguration appBarConfiguration;
    private ActivityCreateEntryBinding binding;

    //    private final String mMyList = "MYLIST";
    private final String mMyList = "PASSLIST";

    private final String mKeyPrefsName = "MYPASSWORDLIST";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPassWordList == null){
            mPassWordList = new Entries();
        }
        setupBindingAndToolbar();
        try {
            getPasswordListFromSharedPreferences();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getPasswordFromCreatePasswordClass();
        setupViews();
        setupFab();
        wireUpTheBottomNavBar();
        setupBackPress();
    }

    private void setupBackPress() {
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            getPasswordListFromSharedPreferences();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            saveToSharedPref();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull  Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            saveToSharedPref();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wireUpTheBottomNavBar() {

        mimageButtonHome = binding.bottomBarNav.buttonNavToHome;
        mimageButtonCreatePassword = binding.bottomBarNav.buttonNavToNewPassword;
        mimageButtonCreatePassword.setBackgroundColor(ContextCompat.getColor(this, R.color.primaryDarkColor));
        mimageButtonMyPasswordList = binding.bottomBarNav.buttonNavToMyPassword;

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

        mimageButtonMyPasswordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), My_Passwords.class);
                startActivity(intent);
            }
        });

    }


    private void makeAToast() {
        String size = String.valueOf(mPassWordList.getSizeOfPassWordList());
        Toast.makeText(this, size, Toast.LENGTH_SHORT).show();
    }

    /*This is for when the activity is over*/
    private void saveToSharedPref() throws GeneralSecurityException, IOException {
        // Create a SP reference to the prefs file on the device whose name matches mKeyPrefsName
        // If the file on the device does not yet exist, then it will be created

        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);


        SharedPreferences preferences = EncryptedSharedPreferences.create(
                mKeyPrefsName,
                masterKeyAlias,
                getApplicationContext(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );

        // used this when it was not encrypted
//        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);

        // Create an Editor object to write changes to the preferences object above
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        if (mPassWordList != null){
            String json = gson.toJson(mPassWordList);
            editor.putString(mMyList, json);
            editor.apply();
        }
    }

    public void getPasswordListFromSharedPreferences() throws GeneralSecurityException, IOException {
       /* SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);
        Gson gson = new Gson();
        if(preferences.contains(mMyList)){
            String json = preferences.getString(mMyList, mKeyPrefsName );
            mPassWordList = gson.fromJson(json, Entries.class);
        }*/

        /*makeAToast();*/

        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);


        SharedPreferences preferences = EncryptedSharedPreferences.create(
                mKeyPrefsName,
                masterKeyAlias,
                getApplicationContext(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );

        Gson gson = new Gson();
        if(preferences.contains(mMyList)){
            String json = preferences.getString(mMyList, mKeyPrefsName );
            mPassWordList = gson.fromJson(json, Entries.class);
        }
    }

    private void setupFab() {
        ExtendedFloatingActionButton fab = findViewById(R.id.save_entry_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTextFields()){
                    try {
                        saveToSharedPref();
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Snackbar.make(view, "Entry Added!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    goToPasswordList();

                }
                /*else {
                    Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }*/
            }
        });
    }

    private void setupBindingAndToolbar() {
        binding = ActivityCreateEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.entryToolbar.toolbar);
    }

    private void getPasswordFromCreatePasswordClass() {
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            password = extra.getString("PASSWORD");
            //The key argument here must match that used in the other activity
        }
        else{
            password = "Something went wrong";
        }
    }

    private void setupViews() {
        mTitle = binding.createEntry.createEntryNameEditTextView;
        mUser = binding.createEntry.user;
        mPassword = binding.createEntry.entryPass;
        if (password.toLowerCase().equals("password")){
            password = "";
        }
        mPassword.setText(password);
        /*mdisplay_text.setText(getResources().getString(R.string.welcome));*/
    }

    private boolean checkTextFields(){

        String title = mTitle.getText().toString();
        String user = mUser.getText().toString();
        String pass = mPassword.getText().toString();

        if (title.equals("")){
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
        }
        else if(user.equals("")){
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
        }
        else if(pass.equals("")){
            Toast.makeText(this, "Please generate a password", Toast.LENGTH_SHORT).show();
        }

        if (!title.equals("") && !user.equals("") && !pass.equals("")){
           /* Toast.makeText(this, "Entry is ready", Toast.LENGTH_SHORT).show();*/
            entry = new Entry(title, user, pass);
            mPassWordList.addEntryToList(entry);
            return true;
        }
        else{
            return false;
        }

    }
    private void goToPasswordList() {
        Intent intent = new Intent(getApplicationContext(), My_Passwords.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home) {
            onBackPressed();
            return true;

        }
        else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }

    }

}

