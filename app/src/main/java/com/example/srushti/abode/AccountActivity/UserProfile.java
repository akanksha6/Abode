package com.example.srushti.abode.AccountActivity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    private DatabaseReference mdusers;
    private DatabaseReference mdusers1;
    private CircleImageView mcircle;
    private TextView mname, mAddress,mPhone,mRent,mType;
    private Button Update;
    private DatabaseReference mautht;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Bundle bundle=getIntent().getExtras();
        final String abc= bundle.getString("UserInfo");
        mAddress=findViewById(R.id.Address);
        mname= findViewById(R.id.Name);
        mPhone=findViewById(R.id.Mobile_No);
        mRent=findViewById(R.id.Rent);
        mType=findViewById(R.id.Type);
        Update=findViewById(R.id.Update);
        mcircle=findViewById(R.id.setting_image);
        mdusers=FirebaseDatabase.getInstance().getReference("Home").child("Landlords").child(abc);
        mdusers1=FirebaseDatabase.getInstance().getReference("Home").child("Tenants").child(abc);
        mdusers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot land)
            {
                if(land.exists()) {
                    String name, address, phone, rent, type;
                    name = land.child("name").getValue(String.class);
                    address = land.child("address").getValue(String.class);
                    phone = land.child("phone").getValue(String.class);
                    rent = land.child("rent").getValue(String.class);
                    type = land.child("type").getValue(String.class);
                    mname.setText("Name : "+name);
                    mAddress.setText("Address : "+address);
                    mPhone.setText("Phone no. : "+phone);
                    mRent.setText("Rent : "+rent);
                    mType.setText("Type : "+type);
                    if(land.child("Image").exists())
                    {
                        Picasso.get().load(land.child("Image").getValue().toString()).into(mcircle);
                       // Toast.makeText(UserProfile.this,land.child("Image").getValue().toString(),Toast.LENGTH_LONG).show();


                    }
                }
                else
                {
                    mdusers1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot land) {

                            String name, address, phone, rent, type;
                            name = land.child("name").getValue(String.class);
                            //address = land.child("address").getValue(String.class);
                            phone = land.child("phone").getValue(String.class);
                            //rent = land.child("rent").getValue(String.class);
                            //type = land.child("type").getValue(String.class);
                            mname.setText(name);
                            // mAddress.setText(address);
                            mPhone.setText(phone);
                            //mRent.setText(rent);
                            //mType.setText(type);
                            if(land.child("Image").exists())
                            {
                                //Toast.makeText(UserProfile.this,land.child("Image").getValue().toString(),Toast.LENGTH_LONG).show();
                                Picasso.get().load(land.child("Image").getValue().toString()).into(mcircle);

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserProfile.this,UpdateActivity.class);
                intent.putExtra("UserInfo",abc);
                startActivity(intent);
            }
        });

    }
}