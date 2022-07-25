package com.azizbek.telegramultralite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.azizbek.telegramultralite.databinding.ActivityMainChatBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivityChat extends AppCompatActivity {

    ActivityMainChatBinding binding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String myusername = intent.getStringExtra("myusername");
        String username = intent.getStringExtra("username");
        String surname = intent.getStringExtra("surname");
        String imageurl = intent.getStringExtra("userimage");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Mychat").child(myusername).child(username);


        binding.textviewusername3.setText(username + " " + surname);
        Glide.with(MainActivityChat.this).load(imageurl).placeholder(R.drawable.telegram).into(binding.circleimageviewprofile2);

        binding.imagesendchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar=Calendar.getInstance();
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);


                Chat chat=new Chat();
                chat.setMessage(binding.edittextchat.getText().toString());
                chat.setDatatime(hour+":"+minute);
                databaseReference.push().setValue(chat);
            }
        });

    }
}