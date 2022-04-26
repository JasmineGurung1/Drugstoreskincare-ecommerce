package com.example.drugstoreskincare.checkOut.orderHsitory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.response.OrderHistory;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    List<OrderHistory> data;
    Context context;
    OnOrderDetailsClick onOrderDetailsClick;

    public OrderAdapter(List<OrderHistory> data, Context context)  {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

        OrderHistory orderHistory = data.get(position);
        holder.Orderid.setText("#"+orderHistory.getId() + " " );
        holder.dateOrder.setText(orderHistory.getOrderDateTime() + " ");

        if (orderHistory.getStatus()==0){
            holder.ordrStats.setText("Pending");
        }else{
            holder.ordrStats.setText("Delivered");
        }
        if (orderHistory.getPaymentType()==1){
            holder.paymentMthd.setText("Unpaid");
        }else{
            holder.paymentMthd.setText("Paid");
        }

        holder.paymentStatus.setText(orderHistory.getPaymentRefrence());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Orderid,dateOrder,ordrStats,paymentMthd, paymentStatus;
        LinearLayout OrderDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Orderid = itemView.findViewById(R.id.OrderId);
            dateOrder = itemView.findViewById(R.id.dateOrder);
            ordrStats = itemView.findViewById(R.id.ordrStats);
            paymentMthd = itemView.findViewById(R.id.paymentMthd);
            paymentStatus = itemView.findViewById(R.id.paymentStatus);
            OrderDetails = itemView.findViewById(R.id.orderDetails);

            OrderDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOrderDetailsClick.onOrderClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnOrderDetailsClick {
        void onOrderClick (int position);

    }
    public void setEachHistory(OnOrderDetailsClick onOrderDetailsClick){this.onOrderDetailsClick=onOrderDetailsClick;}
}
