package com.example.drugstoreskincare.checkOut.orderHsitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.response.Bag;

import java.io.Serializable;
import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements Serializable {


    LayoutInflater layoutInflater;
    List<Bag> data;
    Context context;


    public HistoryAdapter(List<Bag> data,Context context){
        this.data=data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.history_details,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        Bag bag=data.get(position);
        holder.unitPrice.setText(bag.getUnitPrice() + "");
        holder.orderQuantity.setText(bag.getQuantity()+"");
        holder.orderProduct.setText(bag.getProduct().getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView unitPrice, orderProduct, orderQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unitPrice=itemView.findViewById(R.id.unitPrice);
            orderProduct=itemView.findViewById(R.id.orderProduct);
            orderQuantity=itemView.findViewById(R.id.orderQuantity);
        }
    }


}
