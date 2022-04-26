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

import com.example.drugstoreskincare.Admin.AdminActivity;
import com.example.drugstoreskincare.Setting.AboutUsActivity;
import com.example.drugstoreskincare.Setting.ContactUsActivity;
import com.example.drugstoreskincare.Setting.ProfileActivity;
import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.Setting.TermsAndConditionActivity;
import com.example.drugstoreskincare.checkOut.orderHsitory.OrderActivity;
import com.example.drugstoreskincare.checkOut.orderHsitory.OrderHistosyActivity;
import com.example.drugstoreskincare.userAccount.UserAccountActivity;
import com.example.drugstoreskincare.utils.SharedPrefUtils;


public class SettingFragment extends Fragment {
    Button logOutTV;
    TextView profileTV, adminAreaTV, AboutUsTV, TermsTV,contactusTV, OrderHistoryTV;


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
        adminAreaTV = view.findViewById(R.id.adminAreaTV);
        AboutUsTV = view.findViewById(R.id.AboutUsTV);
        TermsTV = view.findViewById(R.id.TermsTV);
        contactusTV = view.findViewById(R.id.contactusTV);
        OrderHistoryTV = view.findViewById(R.id.OrderHistoryTV);
        setClickListeners();
        OnClick();
        checkAdmin();

    }

    private void checkAdmin() {

        boolean is_staff = SharedPrefUtils.getBool(getActivity(),"sfk",false );
        if (is_staff)
            adminAreaTV.setVisibility(View.VISIBLE);
    }

    private void OnClick() {
        profileTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        AboutUsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });

        TermsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TermsAndConditionActivity.class);
                startActivity(intent);
            }
        });
        contactusTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });
        OrderHistoryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
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
        adminAreaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AdminActivity.class);
                startActivity(intent);
            }
        });



    }
}