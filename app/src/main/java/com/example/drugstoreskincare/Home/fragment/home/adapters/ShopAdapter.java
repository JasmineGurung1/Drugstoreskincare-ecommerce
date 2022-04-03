package com.example.drugstoreskincare.Home.fragment.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.response.Product;
import com.example.drugstoreskincare.singleProductPage.SingleProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class ShopAdapter extends RecyclerView.Adapter <ShopAdapter.ShopViewHolder>{
    List<Product> productDataList;
    LayoutInflater layoutInflater;
    Context context;//layout tasney kaam garcha
    Boolean isCart = false;
    Boolean removeEnabled = true;
    CartItemClick cartItemClick;

    public ShopAdapter(List<Product> products, Context context, Boolean isCart) {
        this.productDataList = products;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.isCart = isCart;

    }


    public void setCartItemClick(CartItemClick cartItemClick) {
        this.cartItemClick = cartItemClick;
    }

    public void setRemoveEnabled(Boolean removeEnabled) {
        this.removeEnabled = removeEnabled;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isCart)
            return new ShopViewHolder(layoutInflater.inflate(R.layout.item_cart, parent, false));
        else
            return new ShopViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        holder.nameTV.setText(productDataList.get(position).getName());
        if (productDataList.get(position).getDiscountPrice() == null || productDataList.get(position).getDiscountPrice()==0){
            holder.PriceTV.setVisibility(View.GONE);
            holder.discountPrice.setText(productDataList.get(position).getPrice() + "");
        }
        else {
            holder.discountPrice.setText(productDataList.get(position).getDiscountPrice() + "");
            holder.PriceTV.setText(productDataList.get(position).getPrice() + "");

            Picasso.get().load(productDataList.get(position).getImages().get(0)).into(holder.productIV);
            holder.mainLL.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    Intent productPage = new Intent(context, SingleProductActivity.class);
                    productPage.putExtra(SingleProductActivity.key,  productDataList.get(holder.getAdapterPosition()));
                    context.startActivity(productPage);

                }
            });
            if (isCart) {
                if (removeEnabled)
                    holder.removeCartIV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cartItemClick.onRemoveCart(holder.getAdapterPosition());

                        }
                    });
                else {
                    holder.removeCartIV.setVisibility(View.GONE);
                    holder.mainLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    setMargins(holder.mainLL, 0, 0, 16, 0);
                }
                //we need textview to set its value not any other view
//                holder.quantityIV.setText(productDataList.get(position).getCartQuantity() + "");
            }

        }
    }


    private void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV, removeCartIV;
        TextView nameTV, PriceTV, discountPriceTV, discountPrice;
        LinearLayout mainLL, quantityIV;
        public ShopViewHolder( View itemView){
            super(itemView);
            productIV = itemView.findViewById(R.id.productIV);
            nameTV = itemView.findViewById(R.id.productNameTV);
            PriceTV = itemView.findViewById(R.id.oldPriceTV);
            discountPrice = itemView.findViewById(R.id.discountPriceTV);
            mainLL = itemView.findViewById(R.id.mainLL);
            if (isCart)
                removeCartIV = itemView.findViewById(R.id.removeCartIV);
            quantityIV = itemView.findViewById(R.id.quantityLL);

        }

    }

    public interface CartItemClick {

        public void onRemoveCart(int position);
    }
}