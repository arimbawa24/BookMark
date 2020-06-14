package com.example.bookmarkinaja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profil extends AppCompatActivity {
    private TextView textPassword1,textEmail1;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    Button btnBack;
    private String email;
    private String userid;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        textPassword1 = findViewById(R.id.textPassword);
        textEmail1 = findViewById(R.id.textEmail);
        btnBack = findViewById(R.id.btnBack);

        textEmail1.setText(firebaseUser.getEmail());






        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profil.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
