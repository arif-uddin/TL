package com.lazydevs.tinylens.adapter;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lazydevs.tinylens.Activity.OrderDetailActivity;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.productType.setText(orders.get(i).getOrderProductType());
        viewHolder.productQuantity.setText(orders.get(i).getQuantity());
        viewHolder.orderDate.setText(orders.get(i).getOrderDate());
        viewHolder.orderStatus.setText(orders.get(i).getOrderStatus());

        viewHolder.orderListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,OrderDetailActivity.class);
                intent.putExtra("contact_no",orders.get(i).getContactNo());
                intent.putExtra("ordered_image_url",orders.get(i).getOrderedImageUrl());
                intent.putExtra("order_product_type",orders.get(i).getOrderProductType());
                intent.putExtra("order_id",orders.get(i).getOrderId());
                intent.putExtra("order_description",orders.get(i).getOrderDescription());
                intent.putExtra("order_date",orders.get(i).getOrderDate());
                intent.putExtra("order_status",orders.get(i).getOrderStatus());
                intent.putExtra("order_product_quantity",orders.get(i).getQuantity());
                intent.putExtra("buyer_name",orders.get(i).getBuyerName());
                intent.putExtra("owner_name",orders.get(i).getOwnerName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                Log.d("OrderLog", "Buyer name: " + orders.get(i).getBuyerName());

            }
        });

    }


    @Override
    public int getItemCount() { return orders.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productType,productQuantity,orderDate,orderStatus;
        CardView orderListItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productType=(TextView)itemView.findViewById(R.id.tv_product_type_order_list);
            productQuantity=(TextView)itemView.findViewById(R.id.tv_product_quantity_order_list);
            orderDate=(TextView)itemView.findViewById(R.id.tv_order_date_order_list);
            orderStatus=(TextView)itemView.findViewById(R.id.tv_order_status_order_list);
            orderListItem=(CardView) itemView.findViewById(R.id.cv_order_item);

        }
    }

    public void setValues(ModelOrder order){
        orders.add(0,order);
        notifyDataSetChanged();
    }
}
