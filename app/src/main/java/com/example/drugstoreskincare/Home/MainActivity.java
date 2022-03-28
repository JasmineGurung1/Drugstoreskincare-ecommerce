package com.example.drugstoreskincare.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.drugstoreskincare.Home.fragment.CartFragment;
import com.example.drugstoreskincare.Home.fragment.ProfileFragment;
import com.example.drugstoreskincare.Home.fragment.SettingFragment;
import com.example.drugstoreskincare.Home.fragment.WishListFragment;
import com.example.drugstoreskincare.Home.fragment.home.HomeFragment;
import com.example.drugstoreskincare.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    CartFragment cartFragment;
    WishListFragment wishListFragment;
    SettingFragment settingFragment;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.homeBottomNav);
        homeFragment = new HomeFragment();
        currentFragment = homeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.homeFrameContainer,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getTitle().equals(getString(R.string.home))) {
                    if (homeFragment == null)
                        homeFragment = new HomeFragment();
                    changeFragment(homeFragment);
                    return true;

                }else if (item.getTitle().equals(getString(R.string.cart))) {

                    if (cartFragment == null)
                        cartFragment = new CartFragment();
                    changeFragment(cartFragment);
                    return true;

                }
                else if (item.getTitle().equals(getString(R.string.wishlist))) {

                    if (wishListFragment == null)
                        wishListFragment = new WishListFragment();
                    changeFragment(wishListFragment);
                    return true;

                }

                else if (item.getTitle().equals("Setting")) {
                    if (settingFragment == null)
                        settingFragment = new SettingFragment();
                    changeFragment(settingFragment);
                    return true;
                }
                return false;

            }


        });
    }

    public  void  onSearchClicked(View v){
        Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
    }

    void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();

        if (fragment.isAdded()) {

            getSupportFragmentManager().beginTransaction().show(fragment).commit();

        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.homeFrameContainer, fragment).commit();
        }
        currentFragment = fragment;
    }

}





