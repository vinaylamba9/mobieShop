package com.example.cr7.mobieshop;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.logging.Handler;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<upload_data> upload_dataList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener = listener;
    }

    public ProductAdapter(Context context,List<upload_data> upload_dataList)
    {
        this.context = context;
        this.upload_dataList = upload_dataList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.customcard,viewGroup,false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        upload_data upload_current = upload_dataList.get(i);
        productViewHolder.textViewName.setText(upload_current.getmName());
        productViewHolder.textViewPrice.setText("Rs. " + upload_current.getmPrice());
        GlideApp.with(context)
                .load(upload_current.getmImageUrl())
                .into(productViewHolder.imageViewThumbnail);

        //Picasso.get().load(upload_current.getmImageUrl()).into(productViewHolder.imageViewThumbnail);

    }

    @Override
    public int getItemCount() {
        return upload_dataList.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public ImageView imageViewThumbnail;
        public TextView textViewPrice;
        public ImageView imageViewoverflow;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.titlee);
            imageViewThumbnail = itemView.findViewById(R.id.thumbnail);
            textViewPrice = itemView.findViewById(R.id.price);
            imageViewoverflow = itemView.findViewById(R.id.overflow);

            itemView.setOnClickListener(new View.OnClickListener() {
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
