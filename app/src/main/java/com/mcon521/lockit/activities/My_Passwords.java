package com.mcon521.lockit.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mcon521.lockit.R;
import com.mcon521.lockit.classes.Entries;
import com.mcon521.lockit.classes.Entry;
import com.mcon521.lockit.classes.PasswordAdapater;
import com.mcon521.lockit.databinding.ActivityMyPasswordsBinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class My_Passwords extends AppCompatActivity implements PasswordAdapater.ItemClickListener {

    private SearchView msearchView;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMyPasswordsBinding binding;
    public Entries mPassWordList;
    public Entries mEmptyList;
    ImageButton mimageButtonHome, mimageButtonCreatePassword,mimageButtonMyPasswordList;

    //    private final String mMyList = "MYLIST";
    private final String mMyList = "PASSLIST";
    private final String mKeyPrefsName = "MYPASSWORDLIST";

    PasswordAdapater adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getPasswordListFromSharedPreferences();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setupBindingAndToolbarAndSearchBar();

        setupRecyclerView();
        wireUpTheBottomNavBar();
        setupFabAndGeneratePassword();
        searchList();
    }

    /*this is where I would setup a preliminary search bar*/
    private void setupFabAndGeneratePassword() {

        FloatingActionButton fab = findViewById(R.id.go_to_generate_password_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Create_Password.class);
                startActivity(intent);
//                setupRecyclerView();
            }
        });
    }

    // returns a search list
    private void searchList(){
        msearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener (){


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setupRecyclerView();
                return false;
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.myPasswordsListContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(mPassWordList == null){
            mEmptyList = new Entries();
            Entry placeHolderForEmptyList = new Entry("The list is empty", "", "");
            mEmptyList.addEntryToList(placeHolderForEmptyList);
            adapter = new PasswordAdapater(this, mEmptyList.getPasswordList());
        }
        else if(msearchView.getQuery().length() != 0) {

            Entries filterdList = new Entries();

            for(Entry pass : mPassWordList.getPasswordList()){
                if(pass.getSite().contains(msearchView.getQuery().toString().toUpperCase(Locale.ROOT))){
                    filterdList.addEntryToList(pass);
                }
            }

            adapter = new PasswordAdapater(this, filterdList.getPasswordList());
            adapter.setClickListener(this);

            /*
            * make = new password list;
            * for i in password list
            * if the value in the search bar contains(searchBar.text)
            * add to new password list
            *
            *  adapter = new PasswordAdapater(this, new password list);
            *  adapter.setClickListener(this);
            * */
        }
        else{
            adapter = new PasswordAdapater(this, mPassWordList.getPasswordList());
            adapter.setClickListener(this);
        }
        recyclerView.setAdapter(adapter);
    }



    private void setupBindingAndToolbarAndSearchBar() {
        binding = ActivityMyPasswordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.passToolbar.toolbar);
        msearchView = binding.searchView;
    }


    public void getPasswordListFromSharedPreferences() throws GeneralSecurityException, IOException {
//        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);

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

    public void exportClicked() {
        String Title = "Export";
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(Title)
                .setMessage(" WARNING Are you sure you want to export your passwords?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    try {
                        getPasswordListFromSharedPreferences();
                        Gson gson = new Gson();
                        String jsonResult = gson.toJson(mPassWordList);
                        sendData(jsonResult);
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(R.drawable.ic_baseline_lock_24)
                .show();


    }

    private void sendData(String result) {
        try {
            // Write JSON to a file in cache directory
            File cacheDir = getCacheDir();
            File exportFile = new File(cacheDir, "lockit_export.json");
            FileWriter writer = new FileWriter(exportFile);
            writer.write(result);
            writer.close();

            // Get URI via FileProvider
            Uri fileUri = FileProvider.getUriForFile(this,
                    getPackageName() + ".fileprovider", exportFile);

            // Share the file
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("application/json");
            sendIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent shareIntent = Intent.createChooser(sendIntent, "Export Passwords");
            startActivity(shareIntent);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Export failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.export:
                exportClicked();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}