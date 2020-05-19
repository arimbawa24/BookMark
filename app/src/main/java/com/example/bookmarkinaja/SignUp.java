package com.example.bookmarkinaja;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {
    FirebaseAuth mAuth;
    private static final String TAG = Login.class.getSimpleName();

    EditText email;
    EditText pass;
    Button singUp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth  = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password1);
        singUp = findViewById(R.id.btn_Enter);

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString();
                String password1 = pass.getText().toString();

                if(TextUtils.isEmpty(email1)){
                    email.setError("Email belum di masukan");
                    return;
                }
                if(TextUtils.isEmpty(password1)){
                    pass.setError("Email belum di masukan");
                    return;
                }
                final User user = new User(email1,password1);
                mAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Log.d(TAG, "Registrasai berhasil");
                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.d(TAG, "Data berhasil dimasukan");
                                        Toast.makeText(SignUp.this, "User berhasil di daftarkan", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });
                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);
                        }
                        else {
                            Log.w(TAG, "Registrasai gagal", task.getException());
                            Toast.makeText(SignUp.this, "gagal", Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
