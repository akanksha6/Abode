package com.example.srushti.abode.AccountActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class DisplayActivity extends AppCompatActivity {
    private StorageReference mStorageRef;
    private static final int Gallary_pick=1;
    private DatabaseReference mUserDatabase;
    private FirebaseUser muser;
    ImageView mImageView;
    Button btcimg,Choose;
    EditText Title;
    Button View_img;
    ProgressBar mprogress;
    private StorageTask mtask;
    private static final int PICK_IMAGE_REQUEST=1;
    Uri mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        btcimg=findViewById(R.id.button_upload);
        Choose=findViewById(R.id.button_choose_image);
        View_img=findViewById(R.id.text_view_show_uploads);
        Title=findViewById(R.id.edit_text_file_name);
        mImageView=findViewById(R.id.image_view);
        mprogress=findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        muser= FirebaseAuth.getInstance().getCurrentUser();
        String uid=muser.getUid();
        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Images").child(uid);
        btcimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mtask !=null && mtask.isInProgress())
                {
                    Toast.makeText(DisplayActivity.this,"Upload in progress",Toast.LENGTH_LONG).show();
                }
                else {
                UploadFile();}
            }
        });
        View_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayActivity.this,ImagesActivity.class));
            }
        });
        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }
        private void openFileChooser()
        {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null)
        {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }
    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private  void UploadFile()
    {
        if(mImageUri != null)
        {
            StorageReference fireStg=mStorageRef.child(System.currentTimeMillis()
            +"."+getFileExtension(mImageUri));
            mtask=fireStg.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mprogress.setProgress(0);
                        }
                    },500);
                    //Toast.makeText(DisplayActivity.this,"Upload Successfull",Toast.LENGTH_LONG).show();
                    Upload upload= new Upload(Title.getText().toString().trim(),
                            taskSnapshot.getDownloadUrl().toString());
                    String upLoadId= mUserDatabase.push().getKey();
                    mUserDatabase.child(upLoadId).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(DisplayActivity.this,"Upload Successfull ",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DisplayActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                  double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mprogress.setProgress((int) progress);
                }
            });
        }
        else
        {
            Toast.makeText(this,"No Image Selected",Toast.LENGTH_LONG).show();
        }
    }

}

