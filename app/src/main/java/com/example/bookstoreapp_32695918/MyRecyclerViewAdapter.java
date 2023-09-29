package com.example.bookstoreapp_32695918;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstoreapp_32695918.provider.Book;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{
    List<Book> myList = new ArrayList<>();

    public MyRecyclerViewAdapter() {
//        myList = _myList;
    }

    public void setMyList(List<Book> _myList) {
        myList = _myList;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        Book myBook = myList.get(position);
        holder.setBook(myBook);
        holder.no.setText("No: " + position);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView myBookID;
        public TextView myTitle;
        public TextView myISBN;
        public TextView myAuthor;
        public TextView myDescription;
        public TextView myPrice;
        public TextView no;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            myBookID = itemView.findViewById(R.id.cardview_bookID);
            myTitle = itemView.findViewById(R.id.cardview_title);
            myISBN = itemView.findViewById(R.id.cardview_ISBN);
            myAuthor = itemView.findViewById(R.id.cardview_author);
            myDescription = itemView.findViewById(R.id.cardview_description);
            myPrice = itemView.findViewById(R.id.cardview_price);
            no = itemView.findViewById(R.id.cardview_position);
        }

        public void setBook(Book book) {
            myBookID.setText("ID: " + book.getMyBookID());
            myTitle.setText("Title: " + book.getMyTitle());
            myISBN.setText("ISBN: " + book.getMyISBN());
            myAuthor.setText("Author: " + book.getMyAuthor());
            myDescription.setText("Description: " + book.getMyDescription());
            myPrice.setText(String.format("Price: %f", book.getMyPrice()));
        }
    }
}
