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

        newPassword = binding.setContentSetLoginPassword.newPassword;
        confirmPassword = binding.setContentSetLoginPassword.confirmNewPassword;


        binding.setContentSetLoginPassword.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPassword(view);
            }
        });
    }

    private void createNewPassword(View view){
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

    private void setLoginPasswordInsharedPreferences(Context context, String password, String placeToSave) {
        SharedPreferences loginPreference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEdit = loginPreference.edit();

// Storing the key and its value as the data fetched from edittext
        myEdit.putString(placeToSave, password);
        myEdit.putBoolean(mISLOGGEDIN, false);
        myEdit.commit();
    }

}