package com.example.bookmarkinaja;

public class Book {

    public String bookId;
    public String bookJudul;
    public String bookLink;
    public String bookJenis;
    public String bookGambar;

    public Book(){

    }

    public Book(String bookId, String bookJudul, String bookLink, String bookJenis, String bookGambar) {
        this.bookId = bookId;
        this.bookJudul = bookJudul;
        this.bookLink = bookLink;
        this.bookJenis = bookJenis;
        this.bookGambar = bookGambar;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookJudul() {
        return bookJudul;
    }

    public String getBookLink() {
        return bookLink;
    }

    public String getBookJenis() {
        return bookJenis;
    }

    public String getBookGambar() {
        return bookGambar;
    }
}
