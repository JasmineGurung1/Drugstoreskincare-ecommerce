package com.example.drugstoreskincare.Home.fragment.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    List<Product> productDataList;
    LayoutInflater inflater;
    Context context;
  //  WishLisItemClick wishlistItemClick;
    WishlistCartItemClick  wishlistCartItemClick ;
    Boolean removeEnabled = true;



    public WishlistAdapter(List<Product> productDataList, Context context){
        this.productDataList = productDataList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }


    public void setWishCartItemClick(WishlistCartItemClick wishlistCartItemClick) {
        this.wishlistCartItemClick = wishlistCartItemClick;
    }

    public void setRemoveEnabled(Boolean removeEnabled) {

        this.removeEnabled = removeEnabled;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WishlistViewHolder(inflater.inflate(R.layout.item_wishlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        holder.WishProductNameTV.setText(productDataList.get(position).getName());
        if (productDataList.get(position).getDiscountPrice() == null || productDataList.get(position).getDiscountPrice() == 0){
            holder.WishOldPriceTV.setVisibility(View.GONE);
            holder.WishDiscountPriceTV.setText("Rs." + productDataList.get(position).getPrice() + "");
        } else
            holder.WishDiscountPriceTV.setText("Rs." + productDataList.get(position).getDiscountPrice());
            holder.WishOldPriceTV.setText(productDataList.get(position).getPrice() + "");



        Picasso.get().load(productDataList.get(position).getImages().get(0)).into(holder.wishProductIV);
        holder.WishListMainLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productPage = new Intent(context, SingleProductActivity.class);
                System.out.println(productDataList.get(holder.getAdapterPosition()).getImages());
                productPage.putExtra(SingleProductActivity.SINGLE_DATA_KEY, productDataList.get(holder.getAdapterPosition()));
                context.startActivity(productPage);

            }
        });

    }

    public static void setMargins(View v, int l, int t, int r, int b) {
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

    public class WishlistViewHolder extends RecyclerView.ViewHolder {
        ImageView wishProductIV, WishRemoveIV;
        TextView WishProductNameTV, WishOldPriceTV, WishDiscountPriceTV;
        LinearLayout WishlistCartLL,WishListMainLL;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            wishProductIV = itemView.findViewById(R.id.WishProductIV);
            WishRemoveIV = itemView.findViewById(R.id.WishListRemoveIV);
            WishProductNameTV = itemView.findViewById(R.id.WishProductNameTV);
            WishOldPriceTV = itemView.findViewById(R.id.WishOldPriceTV);
            WishDiscountPriceTV = itemView.findViewById(R.id.WishDiscountPriceTV);
            WishlistCartLL = itemView.findViewById(R.id.WishlistCartLL);
            WishListMainLL = itemView.findViewById(R.id.WishListMainLL);
        }
    }


    public interface WishlistCartItemClick {
        public void onRemoveCart(int position);
    }
}
