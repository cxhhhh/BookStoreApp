package com.example.bookstoreapp_32695918.provider;

import androidx.room.Dao;
import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {
    // The Dao data access object try to do some Query, Query depends on the entity
    @Query("select * from books")
    LiveData<List<Book>> getAllBook();

    @Query("select * from books where bookID=:id")
    List<Book> getBook(String id);

    @Insert
    void addBook(Book book);

    @Query("delete from books where bookID= :id")
    void deleteBook(String id);

    @Query("delete FROM books")
    void deleteAllBooks();

    @Query("delete FROM books WHERE book=(SELECT MAX(book) FROM books)")
    void deleteLastItem();

    @Query("delete FROM books WHERE bookPrice > 50")
    void deletePrice_greater50();
}
