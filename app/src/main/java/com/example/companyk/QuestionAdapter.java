package com.example.companyk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionAdapter extends  RecyclerView.Adapter<QuestionAdapter.ViewHolder>{
    private Context mContext;
    private List<Question> questions;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.question_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Question question = questions.get(position);
            holder.name.setText(question.getName());
            holder.phone.setText(question.getPhone());
            holder.date.setText(question.getDate());
            holder.ask.setText(question.getAsk());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name, phone, ask, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext=itemView.getContext();
            name = itemView.findViewById(R.id.textname);
            phone = itemView.findViewById(R.id.textphone);
            date = itemView.findViewById(R.id.textDate);
            ask = itemView.findViewById(R.id.ques);
        }
    }
}
