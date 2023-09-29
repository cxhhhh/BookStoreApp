package com.example.bookstoreapp_32695918.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {
    private BookDao myBookDao;
    private LiveData<List<Book>> myAllBooks;

    BookRepository(Application application) {
        // Connect to the database (connect to the Dao data access object)
        BookDatabase db = BookDatabase.getDatabase(application);
        myBookDao = db.bookDao();
        myAllBooks = myBookDao.getAllBook();
    }

    LiveData<List<Book>> getAllBooks() {
        return myAllBooks;
    }

    void insert(Book book) {
        BookDatabase.databaseWriteExecutor.execute(() -> myBookDao.addBook(book));
    }

    void deleteAll(){
        BookDatabase.databaseWriteExecutor.execute(()->{
            myBookDao.deleteAllBooks();
        });
    }

    void deleteLastItem() {
        BookDatabase.databaseWriteExecutor.execute(()->{
            myBookDao.deleteLastItem();
        });
    }

    void deletePrice_greater50() {
        BookDatabase.databaseWriteExecutor.execute(()->{
            myBookDao.deletePrice_greater50();
        });
    }
}
