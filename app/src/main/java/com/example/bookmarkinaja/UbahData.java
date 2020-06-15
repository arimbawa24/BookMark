package com.example.bookmarkinaja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class UbahData extends AppCompatActivity {

    private DatabaseReference database;
    EditText emailbru, passbru;
    Button simpan, kembali;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data);
        emailbru = findViewById(R.id.textEmail);
        passbru = findViewById(R.id.textPassword);
        simpan = findViewById(R.id.btnSimpan);
        kembali = findViewById(R.id.btnKembali);

        database = FirebaseDatabase.getInstance().getReference();

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = emailbru.getText().toString().trim();
                String Password = passbru.getText().toString().trim();
                updateData(Email, Password);
                Intent intent = new Intent(UbahData.this,profil.class);
                startActivity(intent);
                finish();
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UbahData.this,profil.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateData(final String email, final String password ){



                        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid());

                        User user = new User(email,password);

                        databaseReference2.setValue(user);



    }


}
