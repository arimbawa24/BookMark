package com.example.bookmarkinaja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.UUID;


public class HalamanUtama extends AppCompatActivity {

    EditText txtJudul;
    EditText txtLink;
    Button btnSimpan, btnKembali;
    Spinner spinnerku;
    EditText txtdata ;


    ImageView imgview;
    Uri FilePathUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    private final int PICK_IMAGE_REQUEST = 71;
    ProgressDialog progressDialog ;
    Button btnbrowse;

    DatabaseReference databasebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        btnbrowse = (Button)findViewById(R.id.btnbrowse);
       // btnupload= (Button)findViewById(R.id.btnupload);

        imgview = (ImageView)findViewById(R.id.image_view);
        progressDialog = new ProgressDialog(HalamanUtama.this);

        databasebook = FirebaseDatabase.getInstance().getReference("Book");
        txtJudul = (EditText) findViewById(R.id.txtJudul);
        txtLink = ( EditText) findViewById(R.id.txtLink);
        spinnerku = (Spinner) findViewById(R.id.spinnerku);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnKembali = (Button) findViewById(R.id.btnKembali);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HalamanUtama.this, Fragment.class);
                startActivity(intent);
            }
        });

          btnbrowse.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  pilihGambar();
    }
});



        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
                addLink();

            }
        });
    }

    private void pilihGambar(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null){
            FilePathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e ){
                e.printStackTrace();
            }
        }

    }
    private void addLink(){
        String judul = txtJudul.getText().toString().trim();
        String link = txtLink.getText().toString().trim();
        String spinner = spinnerku.getSelectedItem().toString();

        if(!TextUtils.isEmpty(judul)){
            String id =  databasebook.push().getKey();

            Book Book = new Book(id, judul, link,spinner);

            databasebook.child(id).setValue(Book);

            Toast.makeText(this,"Bookmark telah ditambah", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, " Masukan Judul terlebih dahulu ", Toast.LENGTH_LONG).show();
        }

    }


    public String GetFileExtension(Uri uri) {


        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));


    }

    public void UploadImage() {

       if(FilePathUri !=null){
           final ProgressDialog progressDialog = new ProgressDialog(this);
           progressDialog.setTitle("Uploading....");
           progressDialog.show();   

           StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
           ref.putFile(FilePathUri)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           progressDialog.dismiss();
                           Toast.makeText(HalamanUtama.this, "Uploaded", Toast.LENGTH_SHORT).show();
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           progressDialog.dismiss();
                           Toast.makeText(HalamanUtama.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   })
                   .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                           double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                           progressDialog.setMessage("uploaded " + (int)progress+"%");
                       }
                   });

       }
    }

}
