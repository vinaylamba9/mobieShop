package com.example.cr7.mobieshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class my_adds_adapter extends RecyclerView.Adapter<my_adds_adapter.addsViewHolder> {

    private Context context;
    private List<my_add_data> dataList;

    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener = listener;
    }

    public my_adds_adapter(Context context,List<my_add_data> dataList)
    {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public addsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_card_myadds,viewGroup,false);
        return new my_adds_adapter.addsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull addsViewHolder addsViewHolder, int i) {
        my_add_data list = dataList.get(i);
        addsViewHolder.product_name.setText(list.getProduct_name());
        addsViewHolder.product_price.setText("Rs." + list.getmPrice());
        addsViewHolder.product_time.setText("Date: " + list.getPost_time());
        GlideApp.with(context).load(list.getImage_url()).into(addsViewHolder.product_image);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class addsViewHolder extends RecyclerView.ViewHolder{

        ImageView product_image;
        TextView product_name,product_price,product_time;
        ImageButton delete_item;

        public addsViewHolder(@NonNull View itemView) {
            super(itemView);

            product_image = itemView.findViewById(R.id.product_imageview);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_time = itemView.findViewById(R.id.product_date);
            delete_item = itemView.findViewById(R.id.delete_button);

            delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistener != null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            mlistener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
