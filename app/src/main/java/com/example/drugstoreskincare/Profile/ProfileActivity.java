package com.example.drugstoreskincare.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.drugstoreskincare.R;

public class ProfileActivity extends AppCompatActivity {
    ImageView profileBackIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileBackIV = findViewById(R.id.ProfileBackIV);
        setOnclickListeners();

    }

    private void setOnclickListeners() {
        profileBackIV.setOnClickListener(v -> finish());
    }
}