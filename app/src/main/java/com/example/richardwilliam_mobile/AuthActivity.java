package com.example.richardwilliam_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportFragmentManager()
                .beginTransaction().add(R.id.frame_container, new LoginFragment())
                .commit();
    }
}