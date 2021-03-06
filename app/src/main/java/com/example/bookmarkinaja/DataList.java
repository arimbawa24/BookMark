package com.example.bookmarkinaja;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class DataList extends ArrayAdapter<Book> {

    private Activity contex;
    private List<Book>bookList;

    public DataList(@NonNull Activity context, List<Book>bookList) {
        super(context, R.layout.book_list_item, bookList);
        this.contex= context;
        this.bookList =bookList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = contex.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.book_list_item, null, true);

        TextView jenisBuku = listViewItem.findViewById(R.id.jenis_txtView);
        TextView judulBuku = listViewItem.findViewById(R.id.judul_txtView);
        TextView link = listViewItem.findViewById(R.id.link_txtView);
        ImageView imageView = listViewItem.findViewById(R.id.gambarData);

        Book book = bookList.get(position);

        jenisBuku.setText(book.getBookJenis());
        judulBuku.setText(book.getBookJudul());
        link.setText(book.getBookLink());
        Glide
                .with(contex) // get context of Fragment
                .load(book.bookGambar)
                .centerCrop()
                .into(imageView);

        return listViewItem;

    }
}
