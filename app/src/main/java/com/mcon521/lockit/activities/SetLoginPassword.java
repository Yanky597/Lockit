package com.mcon521.lockit.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;
import com.mcon521.lockit.databinding.ActivitySetLoginPasswordBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SetLoginPassword extends AppCompatActivity {

    EditText newPassword, confirmPassword;

    private final String mLOGINPASSWORD =  "LoginPassword";
    private final String mISLOGGEDIN =  "IsLoggedIn";

    private AppBarConfiguration appBarConfiguration;
    private ActivitySetLoginPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySetLoginPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        setUpEditTextViews();

        setupFab();
    }

    private void setupFab()  {
        binding.setContentSetLoginPassword.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createNewPassword(view);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setUpEditTextViews() {
        newPassword = binding.setContentSetLoginPassword.newPassword;
        confirmPassword = binding.setContentSetLoginPassword.confirmNewPassword;
    }

    private void createNewPassword(View view) throws GeneralSecurityException, IOException {
        if(newPassword.getText().toString().equals(confirmPassword.getText().toString()) && newPassword.getText().length() > 3){
            setLoginPasswordInsharedPreferences(getApplicationContext(),newPassword.getText().toString(), mLOGINPASSWORD);
            Snackbar.make(view, "Your Password Has Been Set", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            GoToMainActivity();

        }
        else{
            Snackbar.make(view, "Invalid Password", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void GoToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //TODO this has to be updated to encrypt the users pin
    private void setLoginPasswordInsharedPreferences(Context context, String password, String placeToSave) throws GeneralSecurityException, IOException {
//        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
//
//
//        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
//                placeToSave,
//                masterKeyAlias,
//                context,
//                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        );

        // use the shared preferences and editor as you normally would
//        SharedPreferences.Editor editor = sharedPreferences.edit();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
        editor.putString(placeToSave, password);
        editor.putBoolean(mISLOGGEDIN, true);
        editor.commit();
    }

}