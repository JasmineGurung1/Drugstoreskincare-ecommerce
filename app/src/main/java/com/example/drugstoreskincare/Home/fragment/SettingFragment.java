package com.example.drugstoreskincare.Home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.drugstoreskincare.Profile.ProfileActivity;
import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.userAccount.UserAccountActivity;
import com.example.drugstoreskincare.utils.SharedPrefUtils;


public class SettingFragment extends Fragment {
    Button logOutTV;
    TextView profileTV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logOutTV = view.findViewById(R.id.logOutB);
        profileTV = view.findViewById(R.id.profileTV);
        setClickListeners();
        ProfileOnClick();

    }

    private void ProfileOnClick() {
        profileTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

    }




    private void setClickListeners() {
        logOutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefUtils.clear(getContext());
                Intent userAccount = new Intent(getContext(), UserAccountActivity.class);
                startActivity(userAccount);
                getActivity().finish();
            }
        });

    }
}