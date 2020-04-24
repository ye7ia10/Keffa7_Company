package com.example.companyk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class admin extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private Button showProjects;
    private Button addProject;
    private Button Ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Admin");


        showProjects = (Button) findViewById(R.id.showProjects);
        addProject = (Button) findViewById(R.id.AddProject);
        Ans = (Button) findViewById(R.id.ansQues);

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(admin.this, AddProject.class));
            }
        });

        Ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(admin.this, adminQuestions.class));
            }
        });

    }
}
