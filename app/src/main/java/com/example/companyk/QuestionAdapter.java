package com.example.companyk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionAdapter extends  RecyclerView.Adapter<QuestionAdapter.ViewHolder> implements View.OnClickListener{
    private Context mContext;
    private List<Question> questions;

    public QuestionAdapter(Context mContext, List<Question> questions) {
        this.mContext = mContext;
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.question_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            final Question question = questions.get(position);
            holder.name.setText(question.getName());
            holder.phone.setText(question.getPhone());
            holder.date.setText(question.getDate());
            holder.ask.setText(question.getAsk());
            holder.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Delete Question");
                    builder.setMessage("Are you sure you want to delete this ? ");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Questions");
                            reference.child(question.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(mContext, "Question deleted Successfully" , Toast.LENGTH_LONG).show();
                                    questions.remove(position);
                                    notifyDataSetChanged();
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @Override
    public void onClick(View view) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name, phone, ask, date;
        public ImageView trash;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext=itemView.getContext();
            name = itemView.findViewById(R.id.textname);
            phone = itemView.findViewById(R.id.textphone);
            date = itemView.findViewById(R.id.textDate);
            ask = itemView.findViewById(R.id.ques);
            trash = itemView.findViewById(R.id.trash);
        }
    }
}
