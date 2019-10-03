package com.example.cr7.mobieshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class category_card_adapter extends RecyclerView.Adapter<category_card_adapter.CategoryViewHolder> {

    private Context context;
    private List<category_data> category_list;

    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener = listener;
    }

    public category_card_adapter(Context context,List<category_data> category_list)
    {
        this.context = context;
        this.category_list = category_list;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_categorycard,viewGroup,false);
        return new category_card_adapter.CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        category_data list = category_list.get(i);
        categoryViewHolder.title_category.setText(list.getTitle());
        GlideApp.with(context)
                .load(list.getImage())
                .into(categoryViewHolder.imageViewThumbnail);
    }

    @Override
    public int getItemCount() {
        return category_list.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewThumbnail;
        public TextView title_category;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            title_category = itemView.findViewById(R.id.category_title);
            imageViewThumbnail = itemView.findViewById(R.id.category_thumbnail);

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
