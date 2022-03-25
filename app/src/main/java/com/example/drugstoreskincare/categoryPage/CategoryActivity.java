package com.example.drugstoreskincare.categoryPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.drugstoreskincare.Home.fragment.home.adapters.ShopAdapter;
import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.ApiClient;
import com.example.drugstoreskincare.api.response.AllProductResponse;
import com.example.drugstoreskincare.api.response.Category;
import com.example.drugstoreskincare.api.response.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    public static String CATEGORY_DATA_KEY = "cdk";
    Category category;
    RecyclerView allProductRV;
    ProgressBar loadingProgress;
    ImageView emptyIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        if (getIntent().getSerializableExtra(CATEGORY_DATA_KEY) == null)
            finish();
        allProductRV = findViewById(R.id.allProductsRV);
        loadingProgress = findViewById(R.id.loadingProgress);
        emptyIV = findViewById(R.id.emptyIV);


        category = (Category) getIntent().getSerializableExtra(CATEGORY_DATA_KEY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(category.getName());
        getCategoryOnline();

    }


    private void getCategoryOnline() {
        toggleLoading(true);
        Call<AllProductResponse> getProductsByCategory = ApiClient.getClient().getProductsByCategory(category.getCategoryId());
        getProductsByCategory.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                if (response.isSuccessful()) {
                    toggleLoading(false);
                    if (!response.body().getError()) {
                        if (response.body().getProducts().size() == 0)
                            showEmptyLayout();
                        else
                            showCategoriesProducts(response.body().getProducts());
                    }

                }
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                toggleLoading(false);
                Toast.makeText(CategoryActivity.this, "An Unknown Error Occured!", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void showEmptyLayout() {
        emptyIV.setVisibility(View.VISIBLE);
    }


    private void showCategoriesProducts(List<Product> products) {
        allProductRV.setHasFixedSize(true);
        allProductRV.setLayoutManager(new GridLayoutManager(this, 2));
        ShopAdapter shopAdapter = new ShopAdapter(products, this, false);
        allProductRV.setAdapter(shopAdapter);
    }


    private void toggleLoading(Boolean toggle) {
        if (toggle)
            loadingProgress.setVisibility(View.VISIBLE);
        else
            loadingProgress.setVisibility(View.GONE);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}