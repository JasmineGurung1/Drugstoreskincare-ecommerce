package com.example.drugstoreskincare.checkOut.orderComplete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.drugstoreskincare.Home.MainActivity;
import com.example.drugstoreskincare.R;

public class OrderCompleteActivity extends AppCompatActivity {
    TextView backTV,shopMoreTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        backTV = findViewById(R.id.backTV);
        shopMoreTv = findViewById(R.id.shopMoreTv);

        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        shopMoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCompleteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}