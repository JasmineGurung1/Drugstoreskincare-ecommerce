package com.example.drugstoreskincare.api;

import com.example.drugstoreskincare.api.response.AddressResponse;
import com.example.drugstoreskincare.api.response.AllProductResponse;
import com.example.drugstoreskincare.api.response.CategoryResponse;
import com.example.drugstoreskincare.api.response.DashResponse;
import com.example.drugstoreskincare.api.response.LoginResponse;
import com.example.drugstoreskincare.api.response.OrderHistoryResponse;
import com.example.drugstoreskincare.api.response.RegisterResponse;
import com.example.drugstoreskincare.api.response.SliderResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("/ecommerce/api/v1/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/ecommerce/api/v1/register")
    Call<RegisterResponse> register(@Field("name") String names, @Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("ecommerce/api/v1/cart")
    Call<AllProductResponse> addToCart(@Header("Apikey") String apikey, @Field("product_id") int p, @Field("quantity") int q);

    @GET("/ecommerce/api/v1/get-all-products")
    Call<AllProductResponse> getAllProducts();

    @GET("/ecommerce/api/v1/get-categories")
    Call<CategoryResponse> getCategories();

    @GET("/ecommerce/api/v1/slider")
    Call<SliderResponse> getSliders();

    @GET("/ecommerce/api/v1/get-products-by-category")
    Call<AllProductResponse> getProductsByCategory(@Query("c_id") int catid);

    @DELETE("ecommerce/api/v1/cart")
    Call<RegisterResponse> deleteFromCart(@Header("Apikey") String apikey, @Query("c_id") int cartID);


    @GET("ecommerce/api/v1/cart")
    Call<AllProductResponse> getMyCart(@Header("Apikey") String apikey);


    @GET("ecommerce/api/v1/address")
    Call<AddressResponse> getMyAddresses(@Header("Apikey") String apikey);


    @FormUrlEncoded
    @POST("ecommerce/api/v1/order")
    Call<RegisterResponse> order(@Header("Apikey") String apikey,
                                 @Field("p_type") int p_type,
                                 @Field("address_id") int address_id,
                                 @Field("payment_refrence") String paymentRefrence);

    @GET("/api/v1/order")
    Call<OrderHistoryResponse> orderHistory(@Header("Apikey") String apikey);


    @FormUrlEncoded
    @POST("/api/v1/address")
    Call<RegisterResponse> addAddress(
            @Header("Apikey") String apikey,
            @Field("city") String city,
            @Field("street") String street,
            @Field("province") String province,
            @Field("description") String description);

    @Multipart
    @POST("/api/v1/upload-category")
    Call<RegisterResponse> uploadCategory(
            @Header("Apikey") String apikey,
            @Part MultipartBody.Part file,
            @Part("name") RequestBody name

    );

    @GET("/api/v1/dash")
    Call<DashResponse> getDash(@Header("Apikey") String apikey);

    @DELETE("/api/v1/category")
    Call<RegisterResponse> deleteCategory(@Header("Apikey") String apikey, @Query("c_id") int id);


    @Multipart
    @POST("/api/v1/upload-product")
    Call<RegisterResponse> uploadProduct(
            @Header("Apikey") String apikey,
            @Part MultipartBody.Part[] files,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("quantity") RequestBody quantity,
            @Part("discount_price") RequestBody discount_price,
            @Part("categories") RequestBody categories,
            @Part("production_date") RequestBody production_date,
            @Part("expire_date") RequestBody expire_date
    );


    @FormUrlEncoded
    @POST("ecommerce/api/v1/whishlist")
    Call<AllProductResponse> addtowishlist(@Header("Apikey") String apikey, @Field("product_id") int p);

    @GET("ecommerce/api/v1/cart")
    Call<AllProductResponse> getMyWishlist(@Header("Apikey") String apikey);


}
