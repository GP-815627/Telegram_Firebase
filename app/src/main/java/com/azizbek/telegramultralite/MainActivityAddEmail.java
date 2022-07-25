package com.azizbek.telegramultralite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivityAddEmail extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_email);
        lottieAnimationView=findViewById(R.id.lottieanimationviewaddgmail);
        imageView=findViewById(R.id.imageback2);

        lottieAnimationView.setAnimation(R.raw.cat);
        lottieAnimationView.playAnimation();

imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        finish();

    }
});

    }
}