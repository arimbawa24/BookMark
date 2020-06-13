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


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.UUID;


public class HalamanUtama extends AppCompatActivity {

    EditText txtJudul;
    EditText txtLink;
    Button btnSimpan, btnKembali;
    Spinner spinnerku;
    EditText txtdata ;

    private StorageTask taskChecker;


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
        storageReference = storage.getReference("images/" + UUID.randomUUID().toString());
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        btnbrowse = (Button)findViewById(R.id.btnbrowse);
        // btnupload= (Button)findViewById(R.id.btnupload);

        imgview = (ImageView)findViewById(R.id.image_view);
        progressDialog = new ProgressDialog(HalamanUtama.this);

        databasebook = FirebaseDatabase.getInstance().getReference("book");
        txtJudul = (EditText) findViewById(R.id.txtJudul);
        txtLink = ( EditText) findViewById(R.id.txtLink);
        spinnerku = (Spinner) findViewById(R.id.spinnerku);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnKembali = (Button) findViewById(R.id.btnKembali);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HalamanUtama.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });



        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();

            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

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

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //upload file
    private void uploadFile() {
        if (FilePathUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() //bisa juga mStorageRef.child("uploads/" + System.currentTimeMillis()
                    + "." + getFileExtension(FilePathUri));

            final UploadTask uploadTask = fileReference.putFile(FilePathUri);
            taskChecker = uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();

                            }
                            // Continue with the task to get the download URL

                            //Toast.makeText(ProfileTestActivity.this, "Upload Berhasil", Toast.LENGTH_LONG).show();
                            return fileReference.getDownloadUrl();

                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                //lokasi file disimpan di firebase
                                String namaFileMeta = task.getResult().toString();

                                String judul = txtJudul.getText().toString().trim();
                                String link = txtLink.getText().toString().trim();
                                String spinner = spinnerku.getSelectedItem().toString();
                                String gambar = namaFileMeta;

                                if(!TextUtils.isEmpty(judul)){
                                    String id =  databasebook.push().getKey();

                                    Book Book = new Book(id, judul, link,spinner,gambar);

                                    databasebook.child(id).setValue(Book);

                                    Toast.makeText(HalamanUtama.this, "Bookmark telah ditambah", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(HalamanUtama.this, "Masukan Judul terlebih dahulu", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });

                }
            });
            //
        } else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }

}
