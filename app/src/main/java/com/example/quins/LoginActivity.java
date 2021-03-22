package com.example.quins;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 7117;
    FirebaseUser user;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
        showsigninOptions();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this
                    , MainActivity.class);
            Toast.makeText(this, "" + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

    }

    private void showsigninOptions() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.Theme_Quins)
                .setIsSmartLockEnabled(false)
                .build(), MY_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK) {

            Intent intent = new Intent(this
                    , MainActivity.class);
            Toast.makeText(this, "" + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            startActivity(intent);

        }


    }
}
