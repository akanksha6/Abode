package com.example.srushti.abode.AccountActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerifiersActivity extends AppCompatActivity {
    private ListView muserslistview;
    DatabaseReference mfire;
    private VerifiersAdapter myAdapter;
    private ArrayList<users> mulist;
    private ArrayList<Upload> listi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifiers);
        muserslistview = (ListView) findViewById(R.id.lv);
        mfire= FirebaseDatabase.getInstance().getReference("Home").child("Verifiers");
        mulist = new ArrayList<users>();
        listi = new ArrayList<Upload>();
        myAdapter = new VerifiersAdapter(VerifiersActivity.this, mulist);
        mfire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot land:dataSnapshot.getChildren())
                {
                    String name,phone,fee,ProImage,id;
                    id=land.getKey();
                    ProImage=land.child("ProImg").getValue(String.class);
                    fee=land.child("fee").getValue(String.class);
                    name=land.child("name").getValue(String.class);
                    phone=land.child("phone").getValue(String.class);
                    users abc=new users(name,phone,fee,ProImage,id,1);
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
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+abc.getphone()));
                    startActivity(callIntent);
                }catch (SecurityException e)
                {
                    Toast.makeText(VerifiersActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
