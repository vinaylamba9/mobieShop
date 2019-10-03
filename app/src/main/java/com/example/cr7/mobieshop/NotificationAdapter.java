package com.example.cr7.mobieshop;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ItemViewHolder> {

    private ArrayList<NotificationModel> orderList;
    Context context;



    public NotificationAdapter(Context context, ArrayList<NotificationModel> orderList) {
        this.context = context;
        this.orderList=orderList;


    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_row, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        holder.tvTitle.setText(orderList.get(position).getTitle());
        holder.tvDescription.setText(orderList.get(position).getBody());
        holder.tvSender.setText(orderList.get(position).getSender());
        holder.tvReceiver.setText(orderList.get(position).getSend_to());
        //holder.tvPhoneNumber.setText(orderList.get(position).getPhone_num());
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RideStatusActivity.class);
                intent.putExtra("customer",orderList.get(position).getSender());
                intent.putExtra("vehicle_id",orderList.get(position).getVehicle_id());
                intent.putExtra("sit",orderList.get(position).getSit());
                intent.putExtra("type",orderList.get(position).getType());
                context.startActivity(intent);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle,tvDescription,tvSender,tvReceiver,tvPhoneNumber;
        private LinearLayout llStatus;
        public ItemViewHolder(View itemView) {
            super(itemView);

            tvTitle=(TextView)itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView)itemView.findViewById(R.id.tvDescription);
            tvSender = (TextView)itemView.findViewById(R.id.tvSender);
            tvReceiver = (TextView)itemView.findViewById(R.id.tvReceiver);
            //tvPhoneNumber = itemView.findViewById(R.id.tvphone_number);
        }
    }
}
