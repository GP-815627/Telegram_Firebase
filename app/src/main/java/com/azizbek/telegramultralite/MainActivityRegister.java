 package com.azizbek.telegramultralite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.azizbek.telegramultralite.databinding.ActivityMainRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

 public class MainActivityRegister extends AppCompatActivity {

     ActivityMainRegisterBinding binding;
     FirebaseAuth firebaseAuth;
     DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Userlarim");
        firebaseAuth = FirebaseAuth.getInstance();

        binding.buttonregister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.progresbarregister.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(binding.edittextgmail.getText().toString(),binding.edittextpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

if (task.isSuccessful()){

    binding.progresbarregister.setVisibility(View.GONE);

    String uploadkey=firebaseAuth.getCurrentUser().getUid();

    User user=new User();
    user.setUsername(binding.edittextname.getText().toString());
    user.setSurname(binding.edittextsurname.getText().toString());
    user.setUseremail(binding.edittextgmail.getText().toString());
    user.setUploadkey(uploadkey);
    user.setProfilimage("");
    databaseReference.child(uploadkey).setValue(user);
    Toast.makeText(MainActivityRegister.this, "Успешно!", Toast.LENGTH_SHORT).show();

}

                    }
                });

            }
        });

    }
}