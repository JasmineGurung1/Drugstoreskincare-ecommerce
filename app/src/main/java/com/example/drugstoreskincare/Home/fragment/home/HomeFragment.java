package com.example.drugstoreskincare.Home.fragment.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drugstoreskincare.Home.fragment.home.adapters.CategoryAdapter;
import com.example.drugstoreskincare.Home.fragment.home.adapters.ShopAdapter;
import com.example.drugstoreskincare.Home.fragment.home.adapters.SliderAdapter;
import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.Search.SearchActivity;
import com.example.drugstoreskincare.api.ApiClient;
import com.example.drugstoreskincare.api.response.AllProductResponse;
import com.example.drugstoreskincare.api.response.Category;
import com.example.drugstoreskincare.api.response.CategoryResponse;
import com.example.drugstoreskincare.api.response.Product;
import com.example.drugstoreskincare.api.response.Slider;
import com.example.drugstoreskincare.api.response.SliderResponse;
import com.example.drugstoreskincare.categoryPage.CategoryActivity;
import com.example.drugstoreskincare.singleProductPage.SingleProductActivity;
import com.example.drugstoreskincare.utils.DataHolder;
import com.example.drugstoreskincare.utils.SharedPrefUtils;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView allProductsRV, categoryRV;
    ProgressBar loadingProgress;
    SliderView imageSlider;
    TextView userNameTV, SearchTV;
    CircleImageView profileImage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allProductsRV = view.findViewById(R.id.allProductRV);
        categoryRV = view.findViewById(R.id.categoryRV);
        loadingProgress = view.findViewById(R.id.loadingProgress);
        imageSlider = view.findViewById(R.id.imageSlider);
        userNameTV = view.findViewById(R.id.userNameTV);
        profileImage = view.findViewById(R.id.profileImage);
        SearchTV = view.findViewById(R.id.SearchTV);
        serverCall();
        getCategoriesOnline();
        getSliders();
        //setClickListeners();
        getusername();
        goSearch();

    }

    private void goSearch() {
        SearchTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);

            }
        });

    }

    private void getusername() {

        userNameTV.setText(SharedPrefUtils.getSting(getActivity(),getString(R.string.name_key)));
    }


    private void getSliders() {
        Call<SliderResponse> sliderResponseCall = ApiClient.getClient().getSliders();
        sliderResponseCall.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {
                if (response.isSuccessful()){
                    if (!response.body().getError()){
                        setSliders(response.body().getSliders());

                    }
                }
            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {

            }
        });


    }

    private void setSliders(List<Slider> sliders) {
        SliderAdapter sliderAdapter = new SliderAdapter(sliders, getContext());
        sliderAdapter.setClickLister(new SliderAdapter.OnSliderClickLister() {
            @Override
            public void onSliderClick(int position, Slider slider) {
                if( slider.getType() == 1){
                    Intent intent = new Intent(getContext(), SingleProductActivity.class);
                    intent.putExtra(SingleProductActivity.SINGLE_DATA_KEY,slider.getRelatedId());
                    getContext().startActivity(intent);

                }else if (slider.getType() == 2 ){
                    Intent cat = new Intent(getContext(), CategoryActivity.class);
                    Category category = new Category();
                    category.setCategoryId(slider.getRelatedId());
                    category.setName(slider.getDesc());
                    cat.putExtra(CategoryActivity.CATEGORY_DATA_KEY,category);
                    getContext().startActivity(cat);

                }
            }
        });
        imageSlider.setSliderAdapter(sliderAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        imageSlider.startAutoCycle();

    }



    private void getCategoriesOnline() {
        Call<CategoryResponse> getCategories = ApiClient.getClient().getCategories();
        getCategories.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    if (!response.body().getError()){
                        DataHolder.categories = response.body().getCategories();
                        showCategories(response.body().getCategories());

                    }
                }
            }



            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });



    }

    private void showCategories(List<Category> categories) {

        categoryRV.setHasFixedSize(true);
        categoryRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories,getContext(), true, false, null);
        categoryRV.setAdapter(categoryAdapter);
    }



    private void serverCall() {
        toggleProgress(true);
        Call<AllProductResponse> allProductResponseCall = ApiClient.getClient().getAllProducts();
        allProductResponseCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                toggleProgress(false);
                setProductRecyclerView(response.body().getProducts());
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                toggleProgress(false);
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void toggleProgress(Boolean toggle){
        if (toggle)
            loadingProgress.setVisibility(View.VISIBLE);
        else
            loadingProgress.setVisibility(View.GONE);
    }

    private void setProductRecyclerView (List<Product> products) {
        allProductsRV.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        allProductsRV.setLayoutManager(layoutManager);
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext(), false);
        allProductsRV.setAdapter(shopAdapter);
    }
}

