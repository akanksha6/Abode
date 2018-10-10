package com.example.srushti.abode.AccountActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    private static SwipeMenuListView muserslistview;
    DatabaseReference mfire,mfire1,mfire2,mfire3;
    private MyAdapter myAdapter;
    private ArrayList<users> mulist;
    FirebaseUser cuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        muserslistview = (SwipeMenuListView) findViewById(R.id.lv);
        cuser= FirebaseAuth.getInstance().getCurrentUser();
        mfire= FirebaseDatabase.getInstance().getReference("Home").child("Favourites").child(cuser.getUid());
        mfire3= FirebaseDatabase.getInstance().getReference("Home").child("Favourites").child(cuser.getUid());


        mulist = new ArrayList<users>();
        myAdapter = new MyAdapter(this, mulist);
        mfire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot land:dataSnapshot.getChildren())
                {
                    final String id=land.getValue(String.class);
                    String[] tokens=id.split("/");
                    switch (tokens[0])
                    {
                        case "Landlords":
                        {
                                mfire1 = FirebaseDatabase.getInstance().getReference("Home").child("Landlords").child(land.getKey());
                                mfire1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String name=dataSnapshot.child("name").getValue(String.class);
                                        users abc = new users(name, "");
                                        mulist.add(abc);
                                        muserslistview.setAdapter(myAdapter);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            break;
                        }
                        case "Tenants":
                        {
                                mfire2 = FirebaseDatabase.getInstance().getReference("Home").child("Tenants").child(land.getKey());
                                {
                                    mfire2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String name = dataSnapshot.child("name").getValue(String.class);
                                            users abc = new users(name, "");
                                            mulist.add(abc);
                                            muserslistview.setAdapter(myAdapter);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            break;
                        }
                    }
                }
                muserslistview.setAdapter(myAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(180);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        muserslistview.setMenuCreator(creator);
        muserslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final users abc = (users) adapterView.getAdapter().getItem(i);
                final Intent intent = new Intent(FavouritesActivity.this, LandlordsProfile.class);
                mfire.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot land:dataSnapshot.getChildren())
                        {
                            final String id=land.getValue(String.class);
                            String[] tokens=id.split("/");
                            switch (tokens[0])
                            {
                                case "Landlords":
                                {
                                        mfire1 = FirebaseDatabase.getInstance().getReference("Home").child("Landlords").child(land.getKey());
                                        mfire1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshoto) {
                                                    String name = dataSnapshoto.child("name").getValue(String.class);
                                                    if (name == abc.getname()) {
                                                        String parent = dataSnapshoto.getKey();
                                                        intent.putExtra("UserInfo", parent);
                                                        intent.putExtra("Type","Landlords");
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    break;
                                }
                                case "Tenants":
                                {
                                    mfire2 = FirebaseDatabase.getInstance().getReference("Home").child("Tenants").child(land.getKey());
                                    mfire2.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshoto) {
                                                    String name = dataSnapshoto.child("name").getValue(String.class);
                                                    if (name == abc.getname()) {
                                                        String parent = dataSnapshoto.getKey();
                                                        intent.putExtra("UserInfo", parent);
                                                        intent.putExtra("Type","Tenants");
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    }
                                    });
                                    break;
                                }

                            }


                        }
                        muserslistview.setAdapter(myAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        muserslistview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        final users abc = (users) mulist.get(position);
                        mfire.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot land:dataSnapshot.getChildren())
                                {
                                    final String id=land.getValue(String.class);
                                    String[] tokens=id.split("/");
                                    if(abc.getname().equals(tokens[1]))
                                    {
                                       //
                                        mfire3.child(land.getKey()).removeValue();
                                        mulist.clear();
                                       // mulist.remove(position);
                                       // myAdapter.remove(abc);
                                        //muserslistview.invalidate();
                                       // mfire3.child(land.getKey()).removeValue();

                                        myAdapter.setNotifyOnChange(true);

                                        //   muserslistview.removeViewAt(position);
                                       // myAdapter.notifyDataSetChanged();
                                       // muserslistview.deferNotifyDataSetChanged();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
}
