package com.example.companyk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminQuestions extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionAdapter questionAdapter;
    private ArrayList<Question> questions;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_questions);

        recyclerView = (RecyclerView) findViewById(R.id.recQ);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        questions = new ArrayList<>();
        questionAdapter = new QuestionAdapter(this,questions);
        recyclerView.setAdapter(questionAdapter);

        reference = FirebaseDatabase.getInstance().getReference().child("Questions");

        getQuestions();

    }


    private void getQuestions() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){
                        questions.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Question question = snapshot.getValue(Question.class);
                            questions.add(question);
                        }
                    }
                    questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
