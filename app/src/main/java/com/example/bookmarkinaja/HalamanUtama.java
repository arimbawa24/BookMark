package com.example.bookmarkinaja;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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



public class HalamanUtama extends AppCompatActivity {

    EditText txtJudul;
    EditText txtLink;
    Button btnSimpan;
    Spinner spinnerku;
    EditText txtdata ;


    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    Button btnbrowse, btnupload;

    DatabaseReference databasebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        btnbrowse = (Button)findViewById(R.id.btnbrowse);
        btnupload= (Button)findViewById(R.id.btnupload);

        imgview = (ImageView)findViewById(R.id.image_view);
        progressDialog = new ProgressDialog(HalamanUtama.this);

        databasebook = FirebaseDatabase.getInstance().getReference("book");
        txtJudul = (EditText) findViewById(R.id.txtJudul);
        txtLink = ( EditText) findViewById(R.id.txtLink);
        spinnerku = (Spinner) findViewById(R.id.spinnerku);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);


          btnbrowse.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent();
                  intent.setType("image/*");
                  intent.setAction(Intent.ACTION_GET_CONTENT);
                  startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
              }
          });

          btnupload.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  UploadImage();
              }
          });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLink();

            }
        });
    }


    private void addLink(){
        String judul = txtJudul.getText().toString().trim();
        String link = txtLink.getText().toString().trim();
        String spinner = spinnerku.getSelectedItem().toString();

        if(!TextUtils.isEmpty(judul)){
            String id =  databasebook.push().getKey();

            book Book = new book(id, judul, link,spinner);

            databasebook.child(id).setValue(Book);

            Toast.makeText(this,"Bookmark telah ditambah", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, " Masukan Judul terlebih dahulu ", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData()!=null){
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

    public String GetFileExtension(Uri uri) {


        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));


    }

    public void UploadImage() {

       if(FilePathUri !=null){
           progressDialog.setTitle("Gambar sedang di upload . . .");
           progressDialog.show();
           StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." +  GetFileExtension(FilePathUri));
           storageReference2.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                   progressDialog.dismiss();
                   Toast.makeText(getApplicationContext(),"Gambar Sukses Di Upload", Toast.LENGTH_LONG).show();

               }
           });
       }
    }
}
