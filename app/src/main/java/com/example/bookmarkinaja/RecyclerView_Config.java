package com.example.bookmarkinaja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private BooksAdapter mBooksAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Book> book, List<String> keys){
        mContext = context;
        mBooksAdapter = new BooksAdapter(book, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mBooksAdapter);
    }

    class BookItemView extends RecyclerView.ViewHolder {
        private TextView mJudul;
        private TextView mJenis;
        private TextView mLink;
        private ImageView imageView;

        private String key;

        public BookItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.book_list_item, parent, false));

            mJudul = (TextView) itemView.findViewById(R.id.judul_txtView);
            mJenis = (TextView) itemView.findViewById(R.id.jenis_txtView);
            mLink = (TextView) itemView.findViewById(R.id.link_txtView);
            imageView = itemView.findViewById(R.id.gambarData);

        }
        public void bind(Book book, String key){
            mJudul.setText(book.getBookJudul());
            mJenis.setText(book.getBookJenis());
            mLink.setText(book.getBookLink());
            Glide
                    .with(mContext) // get context of Fragment
                    .load(book.bookGambar)
                    .centerCrop()
                    .into(imageView);
            Toast.makeText(mContext, book.bookGambar, Toast.LENGTH_SHORT).show();
            this.key = key;
        }
    }
    class BooksAdapter extends RecyclerView.Adapter<BookItemView>{
        private List<Book> mBookList;
        private List<String> mKeys;

        public BooksAdapter(List<Book> mBookList, List<String> mKeys) {
            this.mBookList = mBookList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public BookItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookItemView holder, int position) {
            holder.bind(mBookList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }
    }
}
