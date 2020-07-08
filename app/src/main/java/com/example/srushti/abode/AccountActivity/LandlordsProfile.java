package com.example.srushti.abode.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LandlordsProfile extends AppCompatActivity {
    DatabaseReference mfire,fav;
    Button but_images,but_location,chat;
    ArrayList<Req> arrayList2=new ArrayList<>();
    Adapter2 adapter2;
    ListView lv2;
    private DatabaseReference mdusers;
    FirebaseUser cuser;
    ImageView image1,imageView,imageView3;
    Button submit;
    DatabaseReference mDatabase,cDatabase;
    int i=0;
    users ll;
   String stuff,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlords_profile);
        but_images=findViewById(R.id.View_Images);
        but_location=findViewById(R.id.Location);
        image1=findViewById(R.id.setting_image);
        //imageView=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.img3);
        chat=findViewById(R.id.chat);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        stuff= bundle.getString("UserInfo");
        type = bundle.getString("Type");
        fav=FirebaseDatabase.getInstance().getReference().child("Home").child("Favourites");
        cuser= FirebaseAuth.getInstance().getCurrentUser();

        but_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandlordsProfile.this, ImagesTenants.class);
                intent.putExtra("UserInfo", stuff);
                startActivity(intent);
            }
        });

        assert type != null;
        assert stuff != null;
        mfire= FirebaseDatabase.getInstance().getReference("Home").child(type).child(stuff);
        mfire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot land) {
                   String name,address,phone,rent,type;
                    name=land.child("name").getValue(String.class);
                    address=land.child("address").getValue(String.class);
                    if(!land.child("address").exists())
                    {
                        but_images.setVisibility(View.GONE);
                        but_location.setVisibility(View.GONE);
                    }
                    phone=land.child("phone").getValue(String.class);
                    rent=land.child("rent").getValue(String.class);
                    type=land.child("type").getValue(String.class);
                    TextView t1=findViewById(R.id.user_single_name);
                    TextView t5=findViewById(R.id.Type);
                    TextView t2=findViewById(R.id.Address);
                    TextView t3=findViewById(R.id.Phone);
                    TextView t4=findViewById(R.id.Rent);
                    t1.setText(name);
                    t2.setText("Address : "+address);
                    t3.setText("Contact No. : "+phone);
                    t4.setText("Rent : "+rent);
                    t5.setText("Type : "+type);
                    ll=new users(name,address);
                    if(land.child("Image").exists())
                    {
                        Picasso.get().load(land.child("Image").getValue().toString()).into(image1);
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        but_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        String text = ll.getaddress();
                        Bundle bundle = new Bundle();
                        bundle.putString("mytext",text);
                        Intent intent = new Intent(LandlordsProfile.this,MapsActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                                    }
        });
        lv2=findViewById(R.id.lv1);
        adapter2=new Adapter2(this,arrayList2);
        mdusers = FirebaseDatabase.getInstance().getReference().child("Home").child("Requirements").child(stuff);
        // Toast.makeText(ViewReq.this,stuff,Toast.LENGTH_LONG).show();
        mdusers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot contact:dataSnapshot.getChildren())
                {
                    String reqments1=contact.getValue(String.class);
                    Req poi=new Req(reqments1);
                    arrayList2.add(poi);
                }
                lv2.setAdapter(adapter2);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mfire.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name=dataSnapshot.child("name").getValue(String.class);
                        Intent intent=new Intent(LandlordsProfile.this,ChatActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("uid",stuff);
                        intent.putExtra("cuid",cuser.getUid());
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView3.setVisibility(View.GONE);
                mfire.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name1="/"+dataSnapshot.child("name").getValue(String.class);
                        fav.child(cuser.getUid()).child(stuff).setValue(type+name1);
                        Toast.makeText(LandlordsProfile.this,"Added to Favourites Successfully",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}