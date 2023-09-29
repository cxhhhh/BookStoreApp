package com.example.bookstoreapp_32695918.provider;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "book")
    private int myBook;

    @ColumnInfo(name = "bookID")
    private String myBookID;
    @ColumnInfo(name = "bookTitle")
    private String myTitle;
    @ColumnInfo(name = "bookISBN")
    private String myISBN;
    @ColumnInfo(name = "bookAuthor")
    private String myAuthor;
    @ColumnInfo(name = "bookDescription")
    private String myDescription;
    @ColumnInfo(name = "bookPrice")
    private double myPrice;


    // Constructor
    public Book(String myBookID, String myTitle, String myISBN, String myAuthor, String myDescription, double myPrice) {
        this.myBookID = myBookID;
        this.myTitle = myTitle;
        this.myISBN = myISBN;
        this.myAuthor = myAuthor;
        this.myDescription = myDescription;
        this.myPrice = myPrice;
    }

    // Getter Method
    public int getMyBook() {
        return myBook;
    }

    public String getMyBookID() {
        return myBookID;
    }

    public String getMyTitle() {
        return myTitle;
    }

    public String getMyISBN() {
        return myISBN;
    }

    public String getMyAuthor() {
        return myAuthor;
    }

    public String getMyDescription() {
        return myDescription;
    }

    public double getMyPrice() {
        return myPrice;
    }

    // Setter Method
    public void setMyBook(int myBook) {
        this.myBook = myBook;
    }

    public void setMyBookID(String myBookID) {
        this.myBookID = myBookID;
    }

    public void setMyTitle(String myTitle) {
        this.myTitle = myTitle;
    }

    public void setMyISBN(String myISBN) {
        this.myISBN = myISBN;
    }

    public void setMyAuthor(String myAuthor) {
        this.myAuthor = myAuthor;
    }

    public void setMyDescription(String myDescription) {
        this.myDescription = myDescription;
    }

    public void setMyPrice(double myPrice) {
        this.myPrice = myPrice;
    }
}
