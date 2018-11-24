package com.lazydevs.tinylens.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lazydevs.tinylens.Model.ModelOrder;
import com.lazydevs.tinylens.Model.ModelUser;
import com.lazydevs.tinylens.R;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private final Context context;
    ArrayList<ModelOrder> orders;

    public OrderListAdapter(Context context, ArrayList<ModelOrder> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_view,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.productType.setText(orders.get(i).getOrderProductType());
        viewHolder.productQuantity.setText(orders.get(i).getQuantity());
        viewHolder.orderDate.setText(orders.get(i).getOrderDate());
        viewHolder.orderStatus.setText(orders.get(i).getOrderStatus());
    }


    @Override
    public int getItemCount() { return orders.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productType,productQuantity,orderDate,orderStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productType=(TextView)itemView.findViewById(R.id.tv_product_type_order_list);
            productQuantity=(TextView)itemView.findViewById(R.id.tv_product_quantity_order_list);
            orderDate=(TextView)itemView.findViewById(R.id.tv_order_date_order_list);
            orderStatus=(TextView)itemView.findViewById(R.id.tv_order_status_order_list);

        }
    }

    public void setValues(ModelOrder order){
        orders.add(0,order);
        notifyDataSetChanged();
    }
}
