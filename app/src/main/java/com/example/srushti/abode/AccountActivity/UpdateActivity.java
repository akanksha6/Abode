package com.example.srushti.abode.AccountActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    private TextInputLayout mName,mAddress,mPhone,mRent,mType;
    private Button Change;
    private DatabaseReference musers,mdusers1;
    private ImageView proImg;
    private FirebaseUser  muser;
    private StorageReference mStorageRef;

    private static final int Gallary_pick=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Bundle bundle=getIntent().getExtras();
        final String abc= bundle.getString("UserInfo");
        proImg=findViewById(R.id.setting_image);
        mName=findViewById(R.id.uName);
        mAddress=findViewById(R.id.uAddress);
        mPhone=findViewById(R.id.uMobile_no);
        mRent=findViewById(R.id.uRent);
        mType=findViewById(R.id.uType);
        Change=findViewById(R.id.uUpdate);
        mRent.setVisibility(View.GONE);
        mAddress.setVisibility(View.GONE);
        mType.setVisibility(View.GONE);

        musers=FirebaseDatabase.getInstance().getReference("Home").child("Landlords").child(abc);
        mdusers1=FirebaseDatabase.getInstance().getReference("Home").child("Tenants").child(abc);
        muser= FirebaseAuth.getInstance().getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        musers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot land)
            {
                if(land.exists()) {
                    mRent.setVisibility(View.VISIBLE);
                    mAddress.setVisibility(View.VISIBLE);
                    mType.setVisibility(View.VISIBLE);

                }
                else
                {
                    mdusers1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot land) {

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



        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot land)
                    {
                        if(land.exists()) {
                            mRent.setVisibility(View.VISIBLE);
                            mAddress.setVisibility(View.VISIBLE);
                            mType.setVisibility(View.VISIBLE);
                            String name=mName.getEditText().getText().toString();
                            String address=mAddress.getEditText().getText().toString();
                            String rent=mRent.getEditText().getText().toString();
                            String phone=mPhone.getEditText().getText().toString();
                            String type=mType.getEditText().getText().toString();
                            Map<String, String> LandLord = new HashMap<>();
                            LandLord.put("name", name);
                            LandLord.put("address", address);
                            LandLord.put("phone", phone);
                            LandLord.put("rent", rent);
                            LandLord.put("type",type);
                            musers.setValue(LandLord).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(UpdateActivity.this,"Changed successfully",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(UpdateActivity.this,"Something went wrong", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            mdusers1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot land) {

                                    String name=mName.getEditText().getText().toString();
                                    String phone=mPhone.getEditText().getText().toString();
                                    Map<String, String> LandLord = new HashMap<>();
                                    LandLord.put("name", name);
                                    LandLord.put("phone", phone);
                                    mdusers1.setValue(LandLord).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(UpdateActivity.this,"Changed successfully",Toast.LENGTH_LONG).show();
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(UpdateActivity.this,"Something went wrong", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });


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


            }
        });
        proImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryI=new Intent();
                galleryI.setType("image/*");
                galleryI.setAction(Intent.ACTION_GET_CONTENT);
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(UpdateActivity.this);

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Gallary_pick && resultCode==RESULT_OK)
        {

            Uri imageUri=data.getData();
            CropImage.activity(imageUri).setAspectRatio(1,1)
                    .start(UpdateActivity.this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String userid=muser.getUid();
                StorageReference filepath=mStorageRef.child("profile_images").child(userid + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            final String download_url = task.getResult().getDownloadUrl().toString();
                            musers.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists())
                                    {
                                        musers.child("Image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(UpdateActivity.this,"Successful",Toast.LENGTH_LONG).show();}
                                            }
                                        });
                                    }
                                    else
                                    {
                                        mdusers1.child("Image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(UpdateActivity.this,"Successful",Toast.LENGTH_LONG).show();}
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(UpdateActivity.this,"Try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

}
