package com.example.drugstoreskincare.checkOut.orderHsitory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.response.Bag;
import com.example.drugstoreskincare.checkOut.orderHsitory.Adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderHistosyActivity extends AppCompatActivity {
    RecyclerView orderDetailsRv;
    ImageView backIvo;
    List<Bag> data = new ArrayList<Bag>();
    HistoryAdapter detailsAdapter;
    Bag bag;

    public static String ORDER_DETAILS_KEY="odk";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_histosy);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        orderDetailsRv=findViewById(R.id.orderdetailsRV);
        backIvo = findViewById(R.id.backIvO);

        backIvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().getSerializableExtra(ORDER_DETAILS_KEY) == null)
            finish();

        bag=(Bag) getIntent().getSerializableExtra(ORDER_DETAILS_KEY);
        data.add(bag);
        setOrderDetailsRv(data);
    }

    private void setOrderDetailsRv(List<Bag> bagList){
        data=bagList;
        orderDetailsRv.setHasFixedSize(true);
        orderDetailsRv.setLayoutManager(new LinearLayoutManager(this));
        detailsAdapter=new HistoryAdapter(bagList,this);
        orderDetailsRv.setAdapter(detailsAdapter);
    }
}
