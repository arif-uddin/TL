package com.lazydevs.tinylens.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.lazydevs.tinylens.Model.ModelEarn;
import com.lazydevs.tinylens.R;

import java.util.ArrayList;

public class ProfitBalanceAdapter extends RecyclerView.Adapter<ProfitBalanceAdapter.ViewHolder> {

    private final Context context;
    ArrayList<ModelEarn> profitList;


    public ProfitBalanceAdapter(Context context, ArrayList<ModelEarn> profitList) {
        this.context = context;
        this.profitList = profitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.profit_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.tvProfit.setText(profitList.get(i).getProfitBalance());
        viewHolder.tvDeliveryDate.setText(profitList.get(i).getDeliveryDate());
    }

    @Override
    public int getItemCount() {
        return profitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvProfit, tvDeliveryDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProfit=(TextView)itemView.findViewById(R.id.tv_profit_list);
            tvDeliveryDate=(TextView)itemView.findViewById(R.id.tv_received_date_list);
        }
    }

    public void setValues(ModelEarn modelEarn){
        profitList.add(0,modelEarn);
        notifyDataSetChanged();
    }
}
