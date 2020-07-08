package com.example.srushti.abode.AccountActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.srushti.abode.AccountActivity.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TenantinfoActivity extends AppCompatActivity {

    Spinner spinner;
    Button submitb;
    EditText Maintext,Phone,Area,City;
    FirebaseUser mauth= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mfire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenaninfo);
        Maintext=findViewById(R.id.editText);
        Phone=findViewById(R.id.editText5);
        Area=findViewById(R.id.earea);
        City=findViewById(R.id.ecity);
        submitb=(Button)findViewById(R.id.buttonsubmit);

        submitb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String name=Maintext.getText().toString();
                String cell = Phone.getText().toString();
                String area=Area.getText().toString();
                String city=City.getText().toString();
                Map<String,String> Tenant=new HashMap<>();
                Tenant.put("name",name);
                Tenant.put("phone", cell);
                Tenant.put("area",area);
                Tenant.put("city",city);
                String current=mauth.getUid();
                mfire= FirebaseDatabase.getInstance().getReference("Home").child("Tenants").child(current);
                mfire.setValue(Tenant).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TenantinfoActivity.this,"Inserted successfullly",Toast.LENGTH_LONG).show();
                    }
                });

                Intent intent=new Intent(TenantinfoActivity.this,NavActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}