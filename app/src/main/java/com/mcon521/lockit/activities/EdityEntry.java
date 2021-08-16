package com.mcon521.lockit.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.mcon521.lockit.R;
import com.mcon521.lockit.classes.Entries;
import com.mcon521.lockit.classes.Entry;
import com.mcon521.lockit.databinding.ActivityEdityEntryBinding;

public class EdityEntry extends AppCompatActivity {

    int currentPosition;
    public Entry entry;
    String Title, Username, Password;
    ImageButton mbackButton, mCopyTitle,mCopyUser, mCopyPass, mDeleteEntry, mSaveEdit;
    EditText mEditTitle, mEditUser, mEditPassword;
    public Entries mPassWordList;
    private final String mMyList = "MYLIST";
    private final String mKeyPrefsName = "MYPASSWORDLIST";

    ActivityEdityEntryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edity_entry);
        binding = ActivityEdityEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getPasswordListFromSharedPreferences();
        setUpButtonsAndViews();
        getContentFromEntry();
        setEditTextViews();
        setupCopyContentButtons();
        updateEntry();
        deleteAnEntry();
        goBacToPasswordListButton();

    }

    public void getPasswordListFromSharedPreferences(){
        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(mMyList, mKeyPrefsName );
        mPassWordList = gson.fromJson(json, Entries.class);
//        sortTheList(mPassWordList);

    }

    public void setUpButtonsAndViews(){
        mbackButton = binding.editEntryContent.backButton;
        mDeleteEntry = binding.editEntryContent.deleteEntry;
        mSaveEdit = binding.editEntryContent.updateContent;
        mCopyTitle = binding.editEntryContent.copyTitle;
        mCopyUser = binding.editEntryContent.copyUser;
        mCopyPass = binding.editEntryContent.copyPass;
        mEditTitle = binding.editEntryContent.editTitle;
        mEditUser = binding.editEntryContent.editUser;
        mEditPassword = binding.editEntryContent.editPass;
    }

    private void goBacToPasswordListButton(){
        mbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToSharedPref();
                goToPasswordList();
            }
        });
    }

    public void deleteAnEntry(){
        mDeleteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EdityEntry.this);
                builder
                        .setTitle(Title)
                        .setMessage("Are you sure you want to delete the information for " + Title)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               deleteTheEntry();
                               saveToSharedPref();
                               goToPasswordList();

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setIcon(R.drawable.ic_baseline_lock_24)
                        .show();
            }
        });
    }

    public void deleteTheEntry(){
        mPassWordList.delteAPassword(Title);
    }


    private void updateEntry(){
        mSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EdityEntry.this);
                        builder
                        .setTitle(Title)
                        .setMessage("Are you sure you want to overwrite the information for " + Title)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                checkTextFields();


                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setIcon(R.drawable.ic_baseline_lock_24)
                        .show();
            }
        });
    }

    public void setupCopyContentButtons(){
        mCopyTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setupClipboard(mEditTitle.getText().toString());
                Snackbar.make(view, "Copied Title to clipboard", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        mCopyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupClipboard(mEditUser.getText().toString());
                Snackbar.make(view, "Copied username to clipboard", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        mCopyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupClipboard(mEditPassword.getText().toString());
                Snackbar.make(view, "Copied password to clipboard", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

    }
    protected void setupClipboard(String text){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        CharSequence label = "copiedText";
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }



    private void setEditTextViews(){
        mEditTitle.setText(Title);
        mEditUser.setText(Username);
        mEditPassword.setText(Password);
    }


    /*a method for editing the current users info*/
    private boolean checkTextFields(){

        String title = mEditTitle.getText().toString().toUpperCase();
        String user = mEditUser.getText().toString();
        String pass = mEditPassword.getText().toString();

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
           /* mPassWordList.getPasswordList().get(currentPosition).setSite(title);
            mPassWordList.getPasswordList().get(currentPosition).setUsername(user);
            mPassWordList.getPasswordList().get(currentPosition).setPassword(pass);*/
            mPassWordList.editAnEntry(Title, title, user, pass);
            saveToSharedPref();

            goToPasswordList();
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
    protected void onStart() {
        super.onStart();
        getPasswordListFromSharedPreferences();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveToSharedPref();
    }


    private void getContentFromEntry() {
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            Title = extra.getString("CURRENTTITLE");
            Username = extra.getString("CURRENTUSERNAME");
            Password = extra.getString("CURRENTPASSWORD");
            currentPosition = extra.getInt("CURRENTPOSITION");
            //The key argument here must match that used in the other activity
        }
        else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToSharedPref() {
        // Create a SP reference to the prefs file on the device whose name matches mKeyPrefsName
        // If the file on the device does not yet exist, then it will be created
        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);

        // Create an Editor object to write changes to the preferences object above
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mPassWordList);
        editor.putString(mMyList, json);
        editor.apply();

    }

   /* public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), My_Passwords.class);
        startActivity(myIntent);
        return true;
    }*/
}