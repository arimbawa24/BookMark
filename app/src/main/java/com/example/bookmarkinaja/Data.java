package com.example.bookmarkinaja;

import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Data extends AppCompatActivity {

    DatabaseReference databasebook;
    ListView listViewBook;
    List<Book>bookList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        databasebook = FirebaseDatabase.getInstance().getReference("book");
        listViewBook = findViewById(R.id.ListBook);
        searchView = findViewById(R.id.searchView);
        bookList = new ArrayList<>();

        listViewBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Book book = bookList.get(position);

                showUpdateDelet(book.getBookId(),book.getBookJenis(),book.getBookLink());

                return false;
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (databasebook != null) {
            databasebook.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        bookList = new ArrayList<>();
                        for (DataSnapshot booksnapshoot : dataSnapshot.getChildren()) {
                            bookList.add(booksnapshoot.getValue(Book.class));
                        }
                        DataList adpter = new DataList(Data.this, bookList);
                        listViewBook.setAdapter(adpter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
            if (searchView != null){
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        search(s);
                        return true;
                    }
                });
            }

    }


    private void search(String str){

        ArrayList<Book> myList =  new ArrayList<>();
        for(Book object : bookList){
            if (object.getBookJenis().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        DataList adpter = new DataList(Data.this, myList);
        listViewBook.setAdapter(adpter);

    }


    private void showUpdateDelet(final String bookId, final String bookJenis, final String bookLink){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_delete, null);
        builder.setView(dialogView);

        final TextView jenis = dialogView.findViewById(R.id.textJenisaja);
        final EditText jenisbaru = dialogView.findViewById(R.id.editJenis);
        final EditText judulbaru = dialogView.findViewById(R.id.editJudul);
        final Spinner spinnerbaru = dialogView.findViewById(R.id.spinnernew);
        final Button update = dialogView.findViewById(R.id.btnUpdate);
        final Button delete = dialogView.findViewById(R.id.btnDelete);
        final Button link = dialogView.findViewById(R.id.btnLink);
        final Button share = dialogView.findViewById(R.id.btnShare);


        builder.setTitle("Update data" + bookJenis);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jenis = jenisbaru.getText().toString().trim();
                String judul = judulbaru.getText().toString().trim();
                String spinner = spinnerbaru.getSelectedItem().toString();

                updateData(bookId, jenis,judul,spinner);
                alertDialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(bookId);
                finish();
            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody1 = bookLink;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + shareBody1));
                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = bookLink;
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent,"ShareVia"));
            }
        });

    }
    private boolean updateData(String id, String jenis, String judul, String spinner){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("book").child(id);

        Book book = new Book(id,jenis,judul,spinner);

        databaseReference.setValue(book);

        Toast.makeText(this, "Data Berhasil di ganti", Toast.LENGTH_LONG).show();


        return true;

    }

    private void deleteData(String bookId){
        DatabaseReference drbook = FirebaseDatabase.getInstance().getReference("book").child(bookId);

        drbook.removeValue();

        Toast.makeText(this, "Data Berhasil di hapus", Toast.LENGTH_LONG).show();
    }

}
