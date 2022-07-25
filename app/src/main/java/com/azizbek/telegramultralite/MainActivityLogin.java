package com.azizbek.telegramultralite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.azizbek.telegramultralite.databinding.ActivityMainLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivityLogin extends AppCompatActivity {

    ActivityMainLoginBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<User> userArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Userlarim");
        firebaseAuth = FirebaseAuth.getInstance();

        userArrayList = new ArrayList<>();

        binding.buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressbarlogin.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(binding.edittextgmail.getText().toString(), binding.edittextpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            binding.progressbarlogin.setVisibility(View.GONE);
                            Intent intent=new Intent(MainActivityLogin.this,MainActivity.class);
                            intent.putExtra("userid",firebaseAuth.getCurrentUser().getUid());
                            startActivity(intent);
                        }

                    }
                });
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    User user = dataSnapshot.getValue(User.class);
                    userArrayList.add(user);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityLogin.this, MainActivityRegister.class));
            }
        });

    }
}