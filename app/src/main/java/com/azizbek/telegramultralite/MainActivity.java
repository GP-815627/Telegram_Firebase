package com.azizbek.telegramultralite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azizbek.telegramultralite.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String useruid = "";
    View viewheader;
    TextView textViewprofilename, textViewemail;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2222;
    ImageView imageView;
    ArrayList<User> alluserarraylist;
    UserAdapter userAdapter;
    public String USERNAME="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference2222 = FirebaseDatabase.getInstance().getReference().child("Userlarim");

        alluserarraylist = new ArrayList<>();


        binding.imagedrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.drawerlay.openDrawer(Gravity.LEFT);

            }
        });

        databaseReference2222.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alluserarraylist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    alluserarraylist.add(user);

                }

                for (int i = 0; i < alluserarraylist.size(); i++) {

                    if (useruid.equals(alluserarraylist.get(i).getUploadkey())){
                        alluserarraylist.remove(i);
                    }
                    
                }

                userAdapter=new UserAdapter(MainActivity.this,alluserarraylist);
                binding.recyclerview1.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                binding.recyclerview1.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewheader = binding.navigationview.getHeaderView(0);

        textViewprofilename = viewheader.findViewById(R.id.textviewprofilename);
        textViewemail = viewheader.findViewById(R.id.textviewemail);
        imageView = viewheader.findViewById(R.id.circleimageviewprofile);

        Intent intent = getIntent();
        useruid = intent.getStringExtra("userid");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Userlarim").child(useruid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String profilename = snapshot.child("username").getValue().toString();
                String surname = snapshot.child("surname").getValue().toString();
                String useremail = snapshot.child("useremail").getValue().toString();
                String userimageurl = snapshot.child("profilimage").getValue().toString();
                Glide.with(MainActivity.this).load(userimageurl).placeholder(R.drawable.telegram).into(imageView);
                textViewprofilename.setText(profilename + " " + surname);
                textViewemail.setText(useremail);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, MainActivityProfile.class);
                        intent.putExtra("userid", useruid);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}