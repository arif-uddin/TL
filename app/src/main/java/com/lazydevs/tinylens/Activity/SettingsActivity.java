package com.lazydevs.tinylens.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.lazydevs.tinylens.Model.ModelUser;
import com.lazydevs.tinylens.R;

public class SettingsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 2;

    TextView logOut,tvChangePhoto,textViewChoosePhoto;
    LinearLayout llChangePhoto,linearLayoutChoosePhoto;
    ImageView imageViewProfilePhoto;
    ImageButton imageButtonChoosePhoto;
    Button btnSaveProfilePhoto;
    ProgressBar pbUploadPhoto;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;

    private StorageTask photoUploadTask;
    private Uri profilePhotoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mDatabaseRef=FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        logOut=(TextView)findViewById(R.id.tv_log_out);
        tvChangePhoto=(TextView)findViewById(R.id.tv_profile_photo_change);
        llChangePhoto=(LinearLayout)findViewById(R.id.ll_profile_photo_change);
        linearLayoutChoosePhoto=(LinearLayout) findViewById(R.id.ll_upload_btn_layout);
        imageViewProfilePhoto=(ImageView)findViewById(R.id.iv_profile_photo_view);
        imageButtonChoosePhoto=(ImageButton)findViewById(R.id.ibtn_photo_chooser);
        textViewChoosePhoto=(TextView)findViewById(R.id.tv_choose_photo);
        btnSaveProfilePhoto=(Button)findViewById(R.id.btn_save_profile_photo);
        pbUploadPhoto=(ProgressBar)findViewById(R.id.pb_upload_photo);

        firebaseAuth = FirebaseAuth.getInstance();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        tvChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llChangePhoto.getVisibility()==View.VISIBLE)
                {
                    llChangePhoto.setVisibility(LinearLayout.GONE);
                }
                else {
                    llChangePhoto.setVisibility(LinearLayout.VISIBLE);
                }
            }
        });

        imageButtonChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        imageViewProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

        btnSaveProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoUploadTask != null && photoUploadTask.isInProgress()) {
                    Toast.makeText(SettingsActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadPhoto();
                }
            }
        });
    }

    public void btn_back_settings(View view) {
        Intent intent=new Intent(SettingsActivity.this,ProfileActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void btn_cancel_photo_change(View view) {
        llChangePhoto.setVisibility(LinearLayout.GONE);
    }

    private void openFileChooser() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, RC_PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RC_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            profilePhotoUri = data.getData();
            if (data.getDataString() != null)
            {
                imageButtonChoosePhoto.setVisibility(ImageButton.GONE);
                textViewChoosePhoto.setVisibility(TextView.GONE);
                imageViewProfilePhoto.setVisibility(ImageView.VISIBLE);
            }

        }

        Glide.
                with(getApplicationContext())
                .load(profilePhotoUri)
                .into(imageViewProfilePhoto);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadPhoto(){
        if (profilePhotoUri != null){

            final StorageReference fileReference = mStorageRef.child("user_photos").child(System.currentTimeMillis()+ "_" + FirebaseAuth.getInstance().getUid()
                    + "." + getFileExtension(profilePhotoUri));

            photoUploadTask = fileReference.putFile(profilePhotoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Toast.makeText(SettingsActivity.this, "Profile photo has changed!", Toast.LENGTH_SHORT).show();
                            final ModelUser modelUser=new ModelUser();
                            modelUser.setUserPhotoUrl(uri.toString());
                            mDatabaseRef.child("users").orderByKey().equalTo(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
                                            String key=datas.getKey();
                                            String userPhotoUrl=datas.child("userPhotoUrl").getValue().toString();
                                            mDatabaseRef.child("users").child(key).setValue(modelUser);

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SettingsActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    pbUploadPhoto.setVisibility(ProgressBar.VISIBLE);
                    pbUploadPhoto.setProgress((int) progress);
                    if (progress == 100) {
                        Toast.makeText(SettingsActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    Log.e("Prr", "" + progress);
                }
            });

        }

        else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}
