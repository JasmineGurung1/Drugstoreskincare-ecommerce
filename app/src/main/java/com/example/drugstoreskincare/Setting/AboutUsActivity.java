package com.example.drugstoreskincare.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.drugstoreskincare.R;

public class AboutUsActivity extends AppCompatActivity {
    ImageView aboutusbackIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        aboutusbackIV = findViewById(R.id.aboutusbackIV);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        aboutusbackIV.setOnClickListener( v -> finish());
    }
}