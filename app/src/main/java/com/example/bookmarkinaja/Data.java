package com.example.bookmarkinaja;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;

public class Data extends AppCompatActivity {


    DatabaseReference databasebook;
    ListView listViewBook;
    List<Book>bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        databasebook = FirebaseDatabase.getInstance().getReference("book");
        listViewBook = findViewById(R.id.ListBook);
        bookList = new ArrayList<>();


    }
    @Override
    protected void onStart() {
    super.onStart();

    databasebook.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bookList.clear();
            for (DataSnapshot booksnapshoot : dataSnapshot.getChildren()){
                Book book = booksnapshoot.getValue(Book.class);
                bookList.add(book);
            }
            DataList adpter = new DataList(Data.this, bookList);
            listViewBook.setAdapter(adpter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }
}
