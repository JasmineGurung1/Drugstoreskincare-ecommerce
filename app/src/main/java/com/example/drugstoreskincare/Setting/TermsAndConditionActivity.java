package com.example.drugstoreskincare.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.drugstoreskincare.R;

public class TermsAndConditionActivity extends AppCompatActivity {
    ImageView termsAndConditionIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        termsAndConditionIV = findViewById(R.id.termsAndConditionbackIV);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        termsAndConditionIV.setOnClickListener( v -> finish());
    }
}