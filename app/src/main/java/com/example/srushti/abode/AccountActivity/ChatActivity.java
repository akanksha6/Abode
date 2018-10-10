package com.example.srushti.abode.AccountActivity;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {


    String email;
    DatabaseReference ref1,ref2,ref;
    ImageButton btnsend;
    LinearLayout layout;
    EditText messageArea;
    ScrollView scrollView;
    TextView tv;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);






        auth= FirebaseAuth.getInstance();
        final String selfemail=auth.getCurrentUser().getEmail().toString();
        final String chatwith=getIntent().getExtras().getString("name");
        final String Land=getIntent().getExtras().getString("uid");
        final String cuser=getIntent().getExtras().getString("cuid");


        /*mref= FirebaseDatabase.getInstance().getReference("Home");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.child("Landlords").child(cuser).exists()) {
                   anc.setname(dataSnapshot.child("Landlords").child(cuser).child("name").getValue(String.class));
                   Toast.makeText(ChatActivity.this,dataSnapshot.child("Landlords").child(cuser).child("name").getValue(String.class),Toast.LENGTH_LONG).show();
               }
               else
               {
                   anc.setname(dataSnapshot.child("Tenants").child(cuser).child("name").getValue(String.class));
                   Toast.makeText(ChatActivity.this,dataSnapshot.child("Tenants").child(cuser).child("name").getValue(String.class),Toast.LENGTH_LONG).show();
               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        ref=FirebaseDatabase.getInstance().getReference("Home").child("Chat");
        ref1=FirebaseDatabase.getInstance().getReference("Home").child("Chat");
        ref2=FirebaseDatabase.getInstance().getReference("Home").child("Chat");
        btnsend=(ImageButton)findViewById(R.id.sendButton);
        tv=(TextView)findViewById(R.id.tvname);
        layout = (LinearLayout)findViewById(R.id.layout1);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        tv.setText("    "+chatwith);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageArea.getText().toString();
                if (!message.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("Message", message);
                    map.put("User", selfemail);
                    ref1.child(cuser ).child(Land).push().setValue(map);
                    ref2.child(Land).child(cuser ).push().setValue(map);
                }

            }

        });
        ref1.child(cuser).child(Land).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


               // Toast.makeText(ChatActivity.this,"ref",Toast.LENGTH_SHORT).show();
                String message1="";
                String userName="";
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    // Map<String,String> map =(Map<String, String>) snapshot.getValue();
                    GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                    Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );

                    message1 = map.get("Message");
                    userName = map.get("User");


                }
                if(selfemail.equals(userName))
                {

                    addMessageBox( message1, 1);

                }
                else
                {
                    addMessageBox( message1, 2);

                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference ref4=FirebaseDatabase.getInstance().getReference("Recent_chat");
        ref4.child(Land).setValue(chatwith);

    }


    public void addMessageBox(String message, int type){
        TextView textView=new TextView(ChatActivity.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        if(type == 1) {
            lp.setMargins(100, 0, 0, 10);
            textView.setLayoutParams(lp);
            textView.setBackgroundResource(R.drawable.rounded_corner1);
            layout.addView(textView);

        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
            lp.setMargins(0, 0,100, 10);
            textView.setLayoutParams(lp);
            layout.addView(textView);

        }

        scrollView.fullScroll(View.FOCUS_DOWN);
    }


}
