package com.example.richardwilliam_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView nameProfile, emailProfile;
        Button backButton;
        SharedPreferences sp = sp = getSharedPreferences("sp", MODE_PRIVATE);
        View.OnClickListener goBack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.finish();
            }
        };

        nameProfile = findViewById(R.id.yourName_tv);
        emailProfile = findViewById(R.id.yourEmail_tv);
        backButton = findViewById(R.id.profile_back);

        nameProfile.setText(sp.getString("name", "null"));
        emailProfile.setText(sp.getString("email", "null"));

        backButton.setOnClickListener(goBack);

    }
}