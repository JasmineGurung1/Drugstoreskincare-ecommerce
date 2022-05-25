package com.example.drugstoreskincare.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.Setting.changepassword.ChangePasswordActivity;
import com.example.drugstoreskincare.api.ApiClient;
import com.example.drugstoreskincare.api.response.RegisterResponse;
import com.example.drugstoreskincare.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilebackIV;
    EditText fullnameET, emailET;
    LinearLayout changePasswordLL;
    ProgressDialog progress;
    TextView editTV, profileNameTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilebackIV = findViewById(R.id.ProfileBackIV);
        fullnameET = findViewById(R.id.fullNameET);
        emailET = findViewById(R.id.EmailET);
        changePasswordLL = findViewById(R.id.changePasswordLL);
        editTV = findViewById(R.id.editTV);
        profileNameTV = findViewById(R.id.profileNameTV);

        fullnameET.setText(SharedPrefUtils.getSting(this, getString(R.string.name_key)));
        emailET.setText(SharedPrefUtils.getSting(this, getString(R.string.email_id)));
        profileNameTV.setText(SharedPrefUtils.getSting(this, getString(R.string.name_key)));
//
//    }
//
//    private void setOnClickListeners() {
//        profilebackIV.setOnClickListener(v -> finish());
//    }
        profilebackIV.setOnClickListener(v -> finish());

        editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callResponse(fullnameET.getText().toString(), emailET.getText().toString());
                fullnameET.setText("");
                emailET.setText("");
            }

            private void callResponse(String names, String email) {
                String key = SharedPrefUtils.getSting(ProfileActivity.this, getString(R.string.api_key));
                Call<RegisterResponse> registerResponseCall = ApiClient.getClient().updateProfile(key, names, email);

                registerResponseCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            if (!response.body().getError()) {
                                Toast.makeText(ProfileActivity.this, "successfully updated", Toast.LENGTH_LONG).show();
                                Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                SharedPrefUtils.setString(ProfileActivity.this, getString(R.string.name_key), names);
                                SharedPrefUtils.setString(ProfileActivity.this, getString(R.string.email_id), email);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });
            }

        });

        setOnclickListeners();
    }

    public void message(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setOnclickListeners() {

        profilebackIV.setOnClickListener(v -> finish());

        changePasswordLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }


}
