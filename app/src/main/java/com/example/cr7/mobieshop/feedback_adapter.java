package com.example.cr7.mobieshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class feedback_adapter extends RecyclerView.Adapter<feedback_adapter.feedbackViewHolder> {

    private Context context;
    private List<feedback_data> feedback_dataList;

    public feedback_adapter(Context context,List<feedback_data> feedback_dataList)
    {
        this.context = context;
        this.feedback_dataList = feedback_dataList;
    }


    @NonNull
    @Override
    public feedbackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_feedback_card,viewGroup,false);
        return new feedback_adapter.feedbackViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull feedbackViewHolder feedbackViewHolder, int i) {
        feedback_data list = feedback_dataList.get(i);
        feedbackViewHolder.byuser_text.setText(list.getUser_name() + ", " + list.getRegno());
        feedbackViewHolder.feedback_text.setText(list.getFeed_back());
        feedbackViewHolder.time_posted_text.setText("Posted on : " + list.getTime_post());
    }

    @Override
    public int getItemCount() {
        return feedback_dataList.size();
    }

    public class feedbackViewHolder extends RecyclerView.ViewHolder{

        public TextView byuser_text;
        public TextView feedback_text;
        public TextView time_posted_text;

        public feedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            byuser_text = itemView.findViewById(R.id.username_and_uid);
            feedback_text = itemView.findViewById(R.id.feedback_textview);
            time_posted_text = itemView.findViewById(R.id.time_feedback);
        }
    }

}
