package com.example.drugstoreskincare.checkOut.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.drugstoreskincare.Home.fragment.home.adapters.AddressAdapter;
import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.ApiClient;
import com.example.drugstoreskincare.api.response.Address;
import com.example.drugstoreskincare.api.response.AddressResponse;
import com.example.drugstoreskincare.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {
    RecyclerView addressRV;
    TextView AddAddressTV;
    public  static  String ADDRESS_SELECTED_KEY = "DFa";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        addressRV = findViewById(R.id.addressRV);
        AddAddressTV = findViewById(R.id.AddAddressTV);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Address");
        getAddressOnline();
        SetClickListener();

    }

    private void SetClickListener() {
        AddAddressTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });


    }


    private void getAddressOnline() {
        String Key = SharedPrefUtils.getSting(this, getString(R.string.api_key));
        Call<AddressResponse> addressResponseCall =  ApiClient.getClient().getMyAddresses(Key);
        addressResponseCall.enqueue(new Callback<AddressResponse>() {

            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if (response.isSuccessful()){
                    ListAddress(response.body().getAdresses());
                }
            }


            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {

            }
        });
    }


    private void ListAddress(List<Address> adresses) {
        addressRV.setHasFixedSize(true);
        addressRV.setLayoutManager(new LinearLayoutManager(this));
        AddressAdapter addressAdapter = new AddressAdapter(adresses, this);
        addressAdapter.setOnAddressItemClickListener(new AddressAdapter.OnAddressItemClickListener() {
            @Override
            public void onAddressClick(int position, Address adress) {
                Intent resultIntent =  new Intent();
                resultIntent.putExtra(ADDRESS_SELECTED_KEY, adress);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        addressRV.setAdapter(addressAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressOnline();
    }


}