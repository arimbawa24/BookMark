package com.example.bookmarkinaja;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class profil extends AppCompatActivity {
    private TextView textPassword1,textEmail1;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private static final String USERS = "users";
    Button btnBack, btnUpdate ;
    private String userid;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        textPassword1 = findViewById(R.id.textPassword);
        textEmail1 = findViewById(R.id.textEmail);
        btnBack = findViewById(R.id.btnBack);
        btnUpdate =findViewById(R.id.Updatebtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid());

        databaseReference1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Map<String, String> dataAwal = (Map<String, String>) dataSnapshot.getValue();
                        textEmail1.setText(dataAwal.get("email"));
                        textPassword1.setText(dataAwal.get("password"));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profil.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profil.this,UbahData.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
