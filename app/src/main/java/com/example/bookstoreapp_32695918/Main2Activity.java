package com.example.bookstoreapp_32695918;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

//import com.example.myapp.provider.Item;
//import com.example.myapp.provider.ItemViewModel;

import com.example.bookstoreapp_32695918.provider.BookViewModel;

public class Main2Activity extends AppCompatActivity {
//    RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
//    MyRecyclerViewAdapter myAdapter;
//    //    ArrayList<Book> myList = new ArrayList<>();
    private BookViewModel mBookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        recyclerView =  findViewById(R.id.rv2);
//
//        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
//        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
//
//
//        adapter = new MyRecyclerViewAdapter();
//        recyclerView.setAdapter(adapter);
//
//        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
//        mItemViewModel.getAllItems().observe(this, newData -> {
//            adapter.setData(newData);
//            adapter.notifyDataSetChanged();
//        });

        // Show the fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame2,new RecyclerViewFragment()).commit();
    }
}
