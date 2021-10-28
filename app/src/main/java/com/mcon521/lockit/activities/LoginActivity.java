package com.mcon521.lockit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.snackbar.Snackbar;
import com.mcon521.lockit.databinding.ActivityLoginBinding;
import com.mcon521.lockit.lib.Utils;

public class LoginActivity extends AppCompatActivity {

    EditText mLoginPassword;


    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginBinding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = ActivityLoginBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            mLoginPassword = binding.loginContent.loginPassword;

            binding.loginContent.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendToMainActivity(checkPassword(), view);
                };
            });
        }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    private void sendToMainActivity(boolean enteredPassword, View view) {
            if (enteredPassword) {
                Utils.LoggedStatusTrue(this);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
//                super.onBackPressed();
            }
            else{
                Snackbar.make(view, "Password is incorrect", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        }

        private boolean checkPassword() {
            String password = mLoginPassword.getText().toString();
            String storedPassword = Utils.passwordIsSet(getApplicationContext());

            if (password.equals(storedPassword)) {
                return true;
            } else {
                return false;
            }
        }


}