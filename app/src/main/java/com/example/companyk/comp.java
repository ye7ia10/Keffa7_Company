package com.example.companyk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class comp extends AppCompatActivity {
    private EditText name, phone , ask;
    private Button submit;
    private ProgressDialog progressDialog;
    private DatabaseReference reference;
    private HashMap<String, String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp);

        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        ask = (EditText) findViewById(R.id.asks);
        submit = (Button) findViewById(R.id.submit);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Question");
        progressDialog.setMessage("Please wait sending you question");
        progressDialog.setCanceledOnTouchOutside(false);

        reference = FirebaseDatabase.getInstance().getReference().child("Questions");
        map = new HashMap<>();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitQuestion();
            }
        });
    }

    private void SubmitQuestion() {
        if (TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(this, "Please Enter Your name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone.getText().toString())){
            Toast.makeText(this, "Please Enter Your phone", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(ask.getText().toString())){
            Toast.makeText(this, "Please Enter Your Question", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            map.put("name", name.getText().toString());
            map.put("phone", phone.getText().toString());
            map.put("phone", ask.getText().toString());
            reference.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(comp.this, "Your Question Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
            });
            progressDialog.dismiss();
        }

    }
}
