package com.example.bookmarkinaja;

public class Book {

    private String bookId;
    private String bookJudul;
    private String bookLink;
    private String bookJenis;

    public Book(){

    }

    public Book(String bookId, String bookJudul, String bookLink, String bookJenis) {
        this.bookId = bookId;
        this.bookJudul = bookJudul;
        this.bookLink = bookLink;
        this.bookJenis = bookJenis;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookJudul() {
        return bookJudul;
    }

    public void setBookJudul(String bookJudul) {
        this.bookJudul = bookJudul;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public String getBookJenis() {
        return bookJenis;
    }

    public void setBookJenis(String bookJenis) {
        this.bookJenis = bookJenis;
    }
}
