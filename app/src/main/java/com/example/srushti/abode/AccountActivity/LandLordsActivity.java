package com.example.srushti.abode.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.srushti.abode.AccountActivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LandLordsActivity extends AppCompatActivity {
    private ListView muserslistview;
    DatabaseReference mfire;
    private MyAdapter myAdapter;
    private ArrayList<users> mulist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_lords);
        Bundle bundle=getIntent().getExtras();
        final String city=bundle.getString("city");
        final String area=bundle.getString("area");
        muserslistview = (ListView) findViewById(R.id.lv);
        mfire= FirebaseDatabase.getInstance().getReference("Home").child("Landlords");
        mulist = new ArrayList<users>();
        myAdapter = new MyAdapter(this, mulist);

       mfire.addValueEventListener(new ValueEventListener() {
               @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot land:dataSnapshot.getChildren())
               {
                   @NonNull String getcity=land.child("city").getValue(String.class);
                   @NonNull String putcity=city;
                   @NonNull String getarea=land.child("area").getValue(String.class);
                   @NonNull String putarea=area;
                   try {
                       if(getcity.equals(putcity))
                       {
                           if(getarea.equals(putarea)) {
                               Toast.makeText(LandLordsActivity.this, "Found", Toast.LENGTH_SHORT).show();
                               String name, address, phone, rent;
                               name = land.child("name").getValue(String.class);
                               address = land.child("address").getValue(String.class);
                               phone = land.child("phone").getValue(String.class);
                               rent = land.child("rent").getValue(String.class);
                               users abc = new users(name, address);
                               mulist.add(abc);
                           }
                           else {
                              // Toast.makeText(LandLordsActivity.this,"Not for this place",Toast.LENGTH_LONG).show();
                           }
                       }
                       else {
                          // Toast.makeText(LandLordsActivity.this,"Not for this place",Toast.LENGTH_LONG).show();
                       }
                   }
                   catch (Exception e)
                   {
                       //Toast.makeText(LandLordsActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                   }

               }
               muserslistview.setAdapter(myAdapter);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
        muserslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final users abc = (users) adapterView.getAdapter().getItem(i);
                mfire.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshoto) {
                        for (DataSnapshot onclick : dataSnapshoto.getChildren()) {

                            String name = onclick.child("name").getValue(String.class);
                            if (name == abc.getname()) {
                                String parent = onclick.getKey();
                                //Toast.makeText(LandLordsActivity.this,parent,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LandLordsActivity.this, LandlordsProfile.class);
                                intent.putExtra("UserInfo", parent);
                                intent.putExtra("Type","Landlords");
                                startActivity(intent);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
