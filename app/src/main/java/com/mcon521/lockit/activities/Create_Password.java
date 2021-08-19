package com.mcon521.lockit.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mcon521.lockit.R;
import com.mcon521.lockit.classes.Password;
import com.mcon521.lockit.databinding.ActivityCreatePasswordBinding;

public class Create_Password extends AppCompatActivity {

    private Password mPass;
    private AppBarConfiguration appBarConfiguration;
    private ActivityCreatePasswordBinding binding;
    private TextView mdisplay_text;
    private Button mButtonCopyToClip, mButtonGoToEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        setupBindingAndToolbar();
        setupViews();
        setupFabAndGeneratePassword();
        copyPasswordToClipboard();
        goToCreateEntryActivity();

    }

    protected void copyPasswordToClipboard(){
        mButtonCopyToClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupClipboard(getPasswordFromTextView());
                 Snackbar.make(view, "Password copied to clipboard", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    /*gets the password from the text view*/
    protected String getPasswordFromTextView(){
        return mdisplay_text.getText().toString();
    }

    protected void setupClipboard(String text){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        CharSequence label = "copiedText";
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }

    private void setupViews() {
        mdisplay_text = binding.passwordContent.displayText;
        mButtonCopyToClip = binding.passwordContent.copyToClipboard;
        mButtonGoToEntry = binding.passwordContent.saveAndGoToSaveEntry;
        /*mdisplay_text.setText(getResources().getString(R.string.welcome));*/
    }


    /*this method calls generates the new password, it is called with in the setup fab method*/
    private void generatePassword(){
        mPass = new Password();
        mdisplay_text.setText(mPass.newPassword(mPass));
    }


    private void setupFabAndGeneratePassword() {

        ExtendedFloatingActionButton fab = findViewById(R.id.generate_password_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePassword();
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

    private void goToCreateEntryActivity(){
        mButtonGoToEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordGenerator();
                /* Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

    }

    /* creates the intent to navigate to the create entry page*/
    private void showPasswordGenerator() {
        /*dismissSnackBarIfShown();*/
        Intent intent = new Intent(this, Create_Entry.class);
        intent.putExtra("PASSWORD", getPasswordFromTextView());
        startActivity(intent);
    }
}