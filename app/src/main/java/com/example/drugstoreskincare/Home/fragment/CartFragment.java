package com.example.drugstoreskincare.Home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drugstoreskincare.Home.fragment.home.adapters.ShopAdapter;
import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.ApiClient;
import com.example.drugstoreskincare.api.response.AllProductResponse;
import com.example.drugstoreskincare.api.response.Product;
import com.example.drugstoreskincare.api.response.RegisterResponse;
import com.example.drugstoreskincare.checkOut.CheckOutActivity;
import com.example.drugstoreskincare.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    RecyclerView allProductRV;
    List<Product> products;
    TextView totalPriceTv;
    SwipeRefreshLayout swipeRefresh;
    LinearLayout orderLL;
    AllProductResponse allProductResponse;
    ImageView emptyCartIV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allProductRV = view.findViewById(R.id.allProductRV);
        totalPriceTv = view.findViewById(R.id.totalPriceTv);
        orderLL = view.findViewById(R.id.orderLL);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        emptyCartIV = view.findViewById(R.id.emptyCartIV);
        orderLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allProductResponse != null && allProductResponse.getProducts().size() > 0) {
                    Intent intent = new Intent(getContext(), CheckOutActivity.class);
                    intent.putExtra(CheckOutActivity.CHECK_OUT_PRODUCTS, allProductResponse);
                    getContext().startActivity(intent);
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                getCartItems();
            }
        });
        getCartItems();
    }

    private void getCartItems() {
        // load 
        String key = SharedPrefUtils.getSting(getActivity(), "apk");
        Call<AllProductResponse> cartItemsCall = ApiClient.getClient().getMyCart(key);
        cartItemsCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        if (response.body().getProducts().size() == 0) {
                            showEmptyLayout();
                        } else {
                            showCartLayout();
                            allProductResponse = response.body();
                            products = response.body().getProducts();
                            loadCartList();
                            setPrice();
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);

            }
        });
    }

    private void showCartLayout() {
        emptyCartIV.setVisibility(View.GONE);
        orderLL.setVisibility(View.VISIBLE);
    }

    private void showEmptyLayout() {
        emptyCartIV.setVisibility(View.VISIBLE);
        orderLL.setVisibility(View.GONE);
    }

    //  delete cart item
    private void loadCartList() {
        allProductRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        allProductRV.setLayoutManager(layoutManager);
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext(), true);
        shopAdapter.setCartItemClick(new ShopAdapter.CartItemClick() {
            @Override
            public void onRemoveCart(int position) {
                String key = SharedPrefUtils.getSting(getActivity(), "apk");
                Call<RegisterResponse> removeCartCall = ApiClient.getClient().deleteFromCart(key, products.get(position).getCartID());
                removeCartCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            if (!response.body().getError()) {
                                products.remove(products.get(position));
                                shopAdapter.notifyItemChanged(position);
                                setPrice();
                                Toast.makeText(getContext(), "Cart Item successfully deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });
            }
        });
        allProductRV.setAdapter(shopAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        getCartItems();
    }

    private void setPrice() {
        double totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getDiscountPrice() != 0 || products.get(i).getDiscountPrice() != null)
                totalPrice = totalPrice + products.get(i).getDiscountPrice();
            else
                totalPrice = totalPrice + products.get(i).getPrice();
        }
        totalPriceTv.setText("( Rs. " + totalPrice + " )");

    }
}


