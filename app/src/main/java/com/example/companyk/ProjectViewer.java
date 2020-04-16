package com.example.companyk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectViewer extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private ArrayList<Project> projects;
    private ProjectAdapter projectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_viewer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Our Projects");
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        projects = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Projects");

         projectAdapter = new ProjectAdapter(this, projects);
        recyclerView.setAdapter(projectAdapter);

        getProjects();

    }


    private void getProjects() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    projects.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ArrayList<String> uri = new ArrayList<>();
                        Project project = new Project();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            if (!snapshot1.getKey().toString().equals("Pname") && !snapshot1.getKey().toString().equals("Pdesc")){
                               for (DataSnapshot snapshot2 : snapshot1.getChildren()){
                                   uri.add(snapshot2.getValue().toString());
                               }
                            } else if (snapshot1.getKey().equals("Pname")){
                                project.setPname(snapshot1.getValue().toString());
                            } else if (snapshot1.getKey().equals("Pdesc")){
                                project.setPdesc(snapshot1.getValue().toString());
                            }
                        }
                        project.setPhotos(uri);
                        projects.add(project);


                    }
                    projectAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
