package com.example.bookmarkinaja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class profil extends AppCompatActivity {
    private TextView textPassword1,textEmail1;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    Button btnBack;
    ImageView imgView;
    private String email;
    private String userid;
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private Object Uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());

        textPassword1 = findViewById(R.id.textPassword);
        textEmail1 = findViewById(R.id.textEmail);
        btnBack = findViewById(R.id.btnBack);
        imgView = findViewById(R.id.imageView1);



        Glide
                .with(getApplicationContext()) // get context of Fragment
                .load("https://firebasestorage.googleapis.com/v0/b/bookmarkinaja-55d85.appspot.com/o/images%2F3b61dba8-d166-46d5-8e9c-dc3e7ffbe4be?alt=media&token=047822b7-5abc-4b29-8549-472c73c62057")
                .centerCrop()

                .into(imgView);

        rootRef.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Map<String, String> dataAwal = (Map<String, String>) dataSnapshot.getValue();

                        textEmail1.setText(dataAwal.get("email"));
                        textPassword1.setText(dataAwal.get("password"));
                        //user.email now has your email value
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





//        userRef.addValueEventListener(new ValueEventListener() {
//            String vemail, vpassword;
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//                    if(ds.child("email").getValue().equals(email)){
//                        vemail = ds.child("email").getValue(String.class);
//                        vpassword = ds.child("password").getValue(String.class);
//                        break;
//                    }
//                }
//                textEmail1.setText(vemail);
//                textPassword1.setText(vpassword);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w("Failed to read value.", databaseError.toException());
//            }
//        });

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
