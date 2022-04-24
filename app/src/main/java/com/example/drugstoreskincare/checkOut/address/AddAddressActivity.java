package com.example.drugstoreskincare.checkOut.address;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.ApiClient;
import com.example.drugstoreskincare.api.response.Address;
import com.example.drugstoreskincare.api.response.AddressResponse;
import com.example.drugstoreskincare.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {
    EditText CityET, StreetET, ProvinceET, DescriptionET;
    LinearLayout addAddressLL, cancelAddressLL;
    static String ADDED_KEY = "ad";
    static String ADDED_DATA_KEY = "adk";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        CityET = findViewById(R.id.CityET);
        StreetET = findViewById(R.id.StreetET);
        ProvinceET = findViewById(R.id.ProvinceET);
        DescriptionET = findViewById(R.id.DescriptionET);
        addAddressLL = findViewById(R.id.addAddressLL);
        cancelAddressLL = findViewById(R.id.cancelAddressLL);
        setOnClickListener();
    }

    private void setOnClickListener() {
        addAddressLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    String key = SharedPrefUtils.getSting(AddAddressActivity.this, getString(R.string.api_key));
                    Call<AddressResponse>addressResponseCall = ApiClient.getClient().addAddress(key, CityET.getText().toString(),
                            StreetET.getText().toString(), ProvinceET.getText().toString(),DescriptionET.getText().toString());

                    addressResponseCall.enqueue(new Callback<AddressResponse>() {
                        @Override
                        public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                            AddressResponse addressResponse = response.body();
                            if (response.isSuccessful()) {
                                if (!addressResponse.getError()) {
                                    Address address = new Address();
                                    address.setCity(CityET.getText().toString());
                                    address.setStreet(StreetET.getText().toString());
                                    address.setProvince(ProvinceET.getText().toString());
                                    address.setDescription(DescriptionET.getText().toString());
                                    address.setId(address.getId());
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra(ADDED_KEY, true);
                                    resultIntent.putExtra(ADDED_DATA_KEY, address);
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AddressResponse> call, Throwable t) {


                        }
                    });

                }
            }
        });

        cancelAddressLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


    public boolean validate() {
        boolean validate = true;
        if (CityET.getText().toString().isEmpty()
                || StreetET.getText().toString().isEmpty()
                || ProvinceET.getText().toString().isEmpty()
                || DescriptionET.getText().toString().isEmpty()) {
            Toast.makeText(AddAddressActivity.this, "None of the above fields can be empty", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;
    }

}