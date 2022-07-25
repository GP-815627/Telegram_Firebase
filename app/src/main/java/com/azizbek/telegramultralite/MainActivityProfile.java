package com.azizbek.telegramultralite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.azizbek.telegramultralite.databinding.ActivityMainProfileBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class MainActivityProfile extends AppCompatActivity {

    ActivityMainProfileBinding binding;
    Uri mImageUri;
    DatabaseReference databaseReference;
    String useruid = "";
    String username = "";
    String surname = "";
    String useremail = "";
    StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        useruid = intent.getStringExtra("userid");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Userlarim").child(useruid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                username = snapshot.child("username").getValue().toString();
                surname = snapshot.child("surname").getValue().toString();
                useremail = snapshot.child("useremail").getValue().toString();
                String profilename = snapshot.child("username").getValue().toString();
                String surname = snapshot.child("surname").getValue().toString();
                String useremail = snapshot.child("useremail").getValue().toString();
                String userimageurl = snapshot.child("profilimage").getValue().toString();
                Glide.with(MainActivityProfile.this).load(userimageurl).placeholder(R.drawable.telegram).into(binding.imageviewadd);
                binding.textviewprofilename2.setText(profilename + " " + surname);
                binding.textviewemail2.setText(useremail);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.imageoptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(MainActivityProfile.this, binding.imageoptions);
                popupMenu.inflate(R.menu.optionsmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.itemphoto:
                                openFileChooser();
                                return true;

                            case R.id.itemdelete:
                                return true;
                            default:
                                return true;

                        }

                    }
                });
                popupMenu.show();
            }
        });

        binding.linearlayusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivityProfile.this,MainActivityAddUser.class));

            }
        });

binding.linearlayemail.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        startActivity(new Intent(MainActivityProfile.this,MainActivityAddEmail.class));

    }
});



        binding.floatingactionbuttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFileChooser();

            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Glide.with(MainActivityProfile.this).load(mImageUri).placeholder(R.drawable.telegram).into(binding.imageviewadd);
            uploadimage();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    public void uploadimage() {

        if (mImageUri != null) {
            StorageReference filereference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            filereference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            User user = new User();
                            user.setUsername(username);
                            user.setSurname(surname);
                            user.setUploadkey(useruid);
                            user.setUseremail(useremail);
                            user.setProfilimage(uri.toString());
                            databaseReference.setValue(user);

                        }
                    });
                }

            });
        }

    }

}