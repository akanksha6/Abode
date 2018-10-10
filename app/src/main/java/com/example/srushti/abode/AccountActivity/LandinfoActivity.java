package com.example.srushti.abode.AccountActivity;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.provider.MediaStore;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
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
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;

        import java.util.*;

public class LandinfoActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE=1;
    public static final int CAM_REQUEST=1313;
   // Button insertg,insertc;
   // ImageView iw;
    Spinner spinner;
    Button submitb;
    EditText Maintext,Address,type,phone,rent,area,city;
    Uri filepath;
    FirebaseUser mauth=FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mfire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landinfo);

        spinner=(Spinner)findViewById(R.id.spinner);
       /* insertg=(Button)findViewById(R.id.button1);
        iw=(ImageView)findViewById(R.id.imageView);
        insertc=(Button)findViewById(R.id.button2);
        insertg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent gallary=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallary,RESULT_LOAD_IMAGE);

            }
        } );
        insertc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cam=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cam,CAM_REQUEST);
            }
        });*/



        ArrayList<String> categories = new ArrayList<>();
        categories.add("Hostel");
        categories.add("Flat");
        ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        spinner.setAdapter(arrayadapter);
        Maintext=findViewById(R.id.editText);
        Address=findViewById(R.id.editText2);
        phone=findViewById(R.id.editText5);
        rent=findViewById(R.id.editText9);
        city=findViewById(R.id.EditCity);
        area=findViewById(R.id.EditArea);
        submitb=findViewById(R.id.button);
        submitb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String name = Maintext.getText().toString();
                String addr = Address.getText().toString();
                String cell = phone.getText().toString();
                String money = rent.getText().toString();
                String type =spinner.getSelectedItem().toString();
                String earea=area.getText().toString();
                String ecity=city.getText().toString();
                Map<String, String> LandLord = new HashMap<>();
                LandLord.put("name", name);
                LandLord.put("address", addr);
                LandLord.put("phone", cell);
                LandLord.put("rent", money);
                LandLord.put("type",type);
                LandLord.put("city",ecity);
                LandLord.put("area",earea);
                String current=mauth.getUid();
                mfire= FirebaseDatabase.getInstance().getReference("Home").child("Landlords").child(current);


                mfire.setValue(LandLord).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(LandinfoActivity.this,"Inserted successfullly",Toast.LENGTH_LONG).show();
                    }
                });

                Intent intent=new Intent(LandinfoActivity.this,NavActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }




   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null)
        {
                filepath=data.getData();
                Uri images=data.getData();
                iw.setImageURI(images);

        }
        if(requestCode==CAM_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            Bitmap bt=(Bitmap)data.getExtras().get("data");
            iw.setImageBitmap(bt);
        }



    }*/


}
