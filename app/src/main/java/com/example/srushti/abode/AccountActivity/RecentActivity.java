package com.example.srushti.abode.AccountActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
public class RecentActivity extends AppCompatActivity {
        DatabaseReference ref,mref,mfire;
        LinearLayout layout;
        ScrollView scrollView;
        ListView lv;
        ArrayAdapter<String> arrayAdapter;
        ArrayList<String> arraylist,arraylist1;
        TextView tv;
        FirebaseUser muser;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recent);
            layout = (LinearLayout)findViewById(R.id.layout1);
            lv=(ListView)findViewById(R.id.lv);
            arraylist=new ArrayList<>();
            arraylist1=new ArrayList<>();
            scrollView = (ScrollView)findViewById(R.id.scrollView);
            tv=(TextView)findViewById(R.id.tv1);
            muser= FirebaseAuth.getInstance().getCurrentUser();
            mfire= FirebaseDatabase.getInstance().getReference("Home");
            ref= FirebaseDatabase.getInstance().getReference("Home").child("Chat").child(muser.getUid());
            mref= FirebaseDatabase.getInstance().getReference("Home");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(final DataSnapshot s:dataSnapshot.getChildren())
                    {
                        mref= FirebaseDatabase.getInstance().getReference("Home");
                        mref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child("Landlords").child(s.getKey()).exists()) {
                                    String username=dataSnapshot.child("Landlords").child(s.getKey()).child("name").getValue(String.class);
                                    arraylist.add(username);
                                    Collections.reverse(arraylist);
                                    arrayAdapter=new ArrayAdapter<String>(RecentActivity.this,android.R.layout.simple_list_item_1,arraylist);
                                    lv.setAdapter(arrayAdapter);
                                }
                                else
                                {
                                    String username=dataSnapshot.child("Tenants").child(s.getKey()).child("name").getValue(String.class);
                                    arraylist.add(username);
                                    Collections.reverse(arraylist);
                                    arrayAdapter=new ArrayAdapter<String>(RecentActivity.this,android.R.layout.simple_list_item_1,arraylist);
                                    lv.setAdapter(arrayAdapter);
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
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final String name1=lv.getItemAtPosition(i).toString();
                    mfire.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshoto) {

                            for (DataSnapshot onclick : dataSnapshoto.getChildren()) {
                                for(DataSnapshot click:onclick.getChildren()) {
                                    String name = click.child("name").getValue(String.class);
                                    if (name == name1) {
                                        String parent = click.getKey();
                                        //Toast.makeText(RecentActivity.this,parent,Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RecentActivity.this, ChatActivity.class);
                                        intent.putExtra("name",name1);
                                        intent.putExtra("uid",parent);
                                        intent.putExtra("cuid",muser.getUid());
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //Intent intent=new Intent(recentchat.this,Chat1.class);
                    //intent.putExtra("Chatwith",lv.getItemAtPosition(i).toString());
                    //startActivity(intent);
                }
            });
        }
    }