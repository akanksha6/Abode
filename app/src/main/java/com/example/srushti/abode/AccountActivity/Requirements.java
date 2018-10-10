package com.example.srushti.abode.AccountActivity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srushti.abode.AccountActivity.NavActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spark.submitbutton.SubmitButton;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Requirements extends AppCompatActivity {

    EditText message;
    ImageButton button,imageButton,imageButton2,imageButton3,imageButton4,imageButton5;
    ImageView imageView;
    SubmitButton submitButton;
    TextView textView,textView2,textView3,textView4,textView5,textView6;
    private FirebaseDatabase mDataBase;
    private FirebaseAuth mAuth;
    HashMap<String,String> userMap=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId=mAuth.getUid();
        mDataBase = FirebaseDatabase.getInstance();
        final DatabaseReference myref =mDataBase.getReference("Home").child("Requirements").child(userId);

        submitButton=(SubmitButton)findViewById(R.id.submitButton);
        message=(EditText)findViewById(R.id.editText);
        button=(ImageButton)findViewById(R.id.button3);
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        imageButton2=(ImageButton)findViewById(R.id.imageButton2);
        imageButton3=(ImageButton)findViewById(R.id.imageButton3);
        imageButton4=(ImageButton)findViewById(R.id.imageButton4);
        imageButton5=(ImageButton)findViewById(R.id.imageButton5);

        textView=(TextView)findViewById(R.id.textView);
        textView2=(TextView)findViewById(R.id.textView2);
        textView3=(TextView)findViewById(R.id.textView3);
        textView4=(TextView)findViewById(R.id.textView4);
        textView5=(TextView)findViewById(R.id.textView5);
        textView6=(TextView)findViewById(R.id.textView6);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.GONE);
                String name="Timely";
                userMap.put("Timely",name);
                button.setImageResource(R.drawable.tickmark);
            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView2.setVisibility(View.GONE);
                String name="No Noise";
                userMap.put("No_Noise",name);
                imageButton.setImageResource(R.drawable.tickmark);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView3.setVisibility(View.GONE);
                String name="Clean";
                userMap.put("Clean",name);
                imageButton2.setImageResource(R.drawable.tickmark);
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView4.setVisibility(View.GONE);
                String name="Only for Ladies";
                userMap.put("ladies",name);
                imageButton3.setImageResource(R.drawable.tickmark);
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView5.setVisibility(View.GONE);
                String name="Not for Bachlors";
                userMap.put("Bachlors",name);
                imageButton4.setImageResource(R.drawable.tickmark);
            }
        });

        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView6.setVisibility(View.GONE);
                String name="Not for Bachlors";
                userMap.put("Bachlors",name);
                imageButton5.setImageResource(R.drawable.tickmark);
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myref.setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Requirements.this,"Added Successfully",Toast.LENGTH_LONG).show();
                    }
                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Requirements.this,NavActivity.class));
                    }
                }, 3000);

            }
        });/*


    Button Time,Noise,smoke,clean,bachlores,ladies,continu;
    private FirebaseDatabase mDataBase;
    private FirebaseAuth mAuth;
    HashMap<String,String> userMap=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);
        Time=findViewById(R.id.Timely);
        Noise=findViewById(R.id.No_noise);
        smoke=findViewById(R.id.Non_Smoker_Drinker);
        clean=findViewById(R.id.Clean);
        bachlores=findViewById(R.id.No_Bachlors);
        ladies=findViewById(R.id.Only_for_Ladies);
        continu=findViewById(R.id.Continue);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId=mAuth.getUid();
        mDataBase = FirebaseDatabase.getInstance();
        final DatabaseReference myref =mDataBase.getReference("Home").child("Requirements").child(userId);


        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name="Timely";
                userMap.put("Timely",name);
                Time.setVisibility(View.GONE);
            }
        });
        Noise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name="No Noise";
                userMap.put("No_Noise",name);
                Noise.setVisibility(View.GONE);
            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name="Clean";
                userMap.put("Clean",name);
                clean.setVisibility(View.GONE);

            }
        });
        ladies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name="Only for Ladies";
                userMap.put("ladies",name);
                ladies.setVisibility(View.GONE);

            }
        });
        smoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name="Non Smoker and Drinker";
                userMap.put("smoke",name);
                smoke.setVisibility(View.GONE);
            }
        });
        bachlores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bachlores.setVisibility(View.GONE);
            }
        });
        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myref.setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Requirements.this,"Added Successfully",Toast.LENGTH_LONG).show();
                    }
                });
                startActivity(new Intent(Requirements.this,NavActivity.class));
            }
        });
 */   }
}
