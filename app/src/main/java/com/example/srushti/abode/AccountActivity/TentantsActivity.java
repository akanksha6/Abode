package com.example.srushti.abode.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class TentantsActivity extends AppCompatActivity {
    private final String Collection_key = "users";
    private static final String TAG = "TentantsActivity";
    private ListView muserslistview;
    DatabaseReference mfire;
    private MyAdapter myAdapter;
    private ArrayList<users> mulist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentants);
        muserslistview = (ListView) findViewById(R.id.lv);
        mfire = FirebaseDatabase.getInstance().getReference("Home").child("Tenants");
        mulist = new ArrayList<users>();
        myAdapter = new MyAdapter(this, mulist);


        mfire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot document : dataSnapshot.getChildren()) {
                    String name, address, phone, rent;
                    name = document.child("name").getValue(String.class);
                    phone = document.child("area").getValue(String.class)+"," +document.child("city").getValue(String.class);
                    rent = document.child("rent").getValue(String.class);
                    users abc = new users(name, phone, rent);
                    mulist.add(abc);
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
                                //Toast.makeText(TentantsActivity.this,parent,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(TentantsActivity.this, LandlordsProfile.class);
                                intent.putExtra("UserInfo", parent);
                                intent.putExtra("Type","Tenants");
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

