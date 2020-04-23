package com.example.companyk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectViewer extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private ArrayList<Project> projects;
    private ProjectAdapter projectAdapter;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_viewer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        imageView = (ImageView) findViewById(R.id.menu);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(ProjectViewer.this, view);
                popup.setOnMenuItemClickListener(ProjectViewer.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });
        projects = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Projects");

         projectAdapter = new ProjectAdapter(this, projects);
        recyclerView.setAdapter(projectAdapter);

        getProjects();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.ask:
                startActivity(new Intent(ProjectViewer.this, comp.class));
                return true;
            case R.id.about:
                return  true;
            default:
                return false;
        }
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
