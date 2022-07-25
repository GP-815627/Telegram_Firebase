package com.azizbek.telegramultralite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivityAddUser extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_user);

        imageView=findViewById(R.id.imageback3);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

finish();

            }
        });

    }
}