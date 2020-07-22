package com.example.companyk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;


import java.util.ArrayList;
import java.util.HashMap;

public class AddProject extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText pname;
    private EditText pdesc;
    private CircleImageView addPhotos;
    private Button upload;
    private static final int PICK_IMAGE = 1;
    private ArrayList<Uri> photosuUri = new ArrayList<Uri>();
    private Uri currenImageUri;
    private ProgressDialog progressDialog;
    private DatabaseReference references;
    private HashMap<String, String> projectData = new HashMap<>();
    private StorageTask task;
    private int i = 0;
    private ArrayList<Uri> path = new ArrayList<>();
    private CircleImageView ddd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add project");

        pname = (EditText) findViewById(R.id.projectName);
        pdesc = (EditText) findViewById(R.id.projectDesc);
        addPhotos = (CircleImageView) findViewById(R.id.choosePhotos);
        ddd = (CircleImageView) findViewById(R.id.ddd);

        upload = (Button) findViewById(R.id.uploadProject);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while uploading the project .... ");
        progressDialog.setCanceledOnTouchOutside(false);
        references = FirebaseDatabase.getInstance().getReference().child("Projects");


        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotos();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadProject();
            }
        });

    }

    private String getFileExtension(Uri uri1){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri1));
    }

    private void UploadProject() {

        if (photosuUri.size() == 0){
            Toast.makeText(AddProject.this, "Please Select Project Photos", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pdesc.getText().toString())){
            Toast.makeText(AddProject.this, "Please add Project description", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pname.getText().toString())){
            Toast.makeText(AddProject.this, "Please Select Project name", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            final StorageReference  reference = FirebaseStorage.getInstance().getReference().child("Posts");
            for (i = 0 ; i < photosuUri.size() ; i++){
                Uri cur = photosuUri.get(i);
                final StorageReference singleRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(cur)) ;
                singleRef.putFile(cur).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        singleRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String urri = String.valueOf(uri);
                                projectData.put("key", urri);
                                references.child(pname.getText().toString()).push().setValue(projectData);
                            }
                        });

                    }
                });
            }

            if (photosuUri.size() > 0){
                references.child(pname.getText().toString()).child("Pname").setValue(pname.getText().toString());
                references.child(pname.getText().toString()).child("Pdesc").setValue(pdesc.getText().toString());
                progressDialog.dismiss();
                Toast.makeText(AddProject.this, "project uploaded successfully", Toast.LENGTH_SHORT).show();
                projectData.clear();
                photosuUri.clear();
            }
        }

    }

    private void choosePhotos() {
       /* Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE);*/
       // new GligarPicker().requestCode(PICK_IMAGE).withActivity(this).show();

        FishBun.with(this).setImageAdapter(new GlideAdapter()).startAlbum();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FishBun.FISHBUN_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    // path = imageData.getStringArrayListExtra(Define.INTENT_PATH);
                    // you can get an image path(ArrayList<String>) on <0.6.2

                    photosuUri = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);
                   // ddd.setImageURI(path.get(0));
                    // you can get an image path(ArrayList<Uri>) on 0.6.2 and later
                    break;
                }
        }
    }
}
