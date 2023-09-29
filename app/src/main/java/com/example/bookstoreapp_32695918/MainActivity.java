//FIT2081 S1 2023 - Chen Xinhui 32695918
package com.example.bookstoreapp_32695918;

import static com.example.bookstoreapp_32695918.RandomString.generateNewRandomString;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookstoreapp_32695918.provider.Book;
import com.example.bookstoreapp_32695918.provider.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private EditText myBookID;
    private EditText myTitle;
    private EditText myISBN;
    private EditText myAuthor;
    private EditText myDescription;
    private EditText myPrice;

    // Week5 listView
//    ArrayList<String> myList = new ArrayList<>();
//    ArrayAdapter myAdapter;
    DrawerLayout drawerLayout;

    // Week6 RecycleView
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyRecyclerViewAdapter myAdapter;
//    ArrayList<Book> myList = new ArrayList<>();

    // Week7 Database
    private int count;
    private BookViewModel mBookViewModel;
    DatabaseReference myRef;

    // Week10
    View myFrame;
    View myConstraint;
    int initial_x;
    int initial_y;

    // Week 11
    GestureDetector gestureDetector;
    ScaleGestureDetector scaleGestureDetector;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        myBookID = findViewById(R.id.BookID);
        myTitle = findViewById(R.id.Title);
        myISBN = findViewById(R.id.ISBN);
        myAuthor = findViewById(R.id.Author);
        myDescription = findViewById(R.id.Description);
        myPrice = findViewById(R.id.Price);
        myFrame = findViewById(R.id.frame_id);
        myConstraint = findViewById(R.id.constraint_id);


        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        /* Create and instantiate the local broadcast receiver
           This class listens to messages come from class SMSReceiver
         */
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();

        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        // Week6 RecycleView
        /*
//        recyclerView = findViewById(R.id.rv);
        //The Recycler View needs a layout manager
        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
        //Create a new adaptor and pass your data source to it
        myAdapter=new MyRecyclerViewAdapter();
        //Link the adaptor to the recycler View
        recyclerView.setAdapter(myAdapter);
        */


        // Week5 toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Week5 listView
//        ListView listView = findViewById(R.id.listView);
//        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
//        listView.setAdapter(myAdapter);

        // Week5 add book to the listView by FAB
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        // Week5 DrawerLayout-menu-toggle
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new MyDrawerListener());

        // Week7 Database
        // Declare in onCreate
        mBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        mBookViewModel.getAllBooks().observe(this, newData -> {
            count = newData.size();
//            adapter.setCustomers(newData);
//            adapter.notifyDataSetChanged();
//            myText.setText(newData.size() + "");
        });
        // Show the fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new RecyclerViewFragment()).commit();

        // Week8: Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Book/car");

        // Week 11
        gestureDetector = new GestureDetector(this, new MyGestureDetector());

        // Week10: Gesture
        myFrame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int motion = motionEvent.getActionMasked();

                // Week 11
                gestureDetector.onTouchEvent(motionEvent);

                return true;

//                // Week10-Extra Task: Capitalise the author if click on the upper left corner
//                if((int)motionEvent.getX() < 200 && (int)motionEvent.getY() < 200) {
//                    String author = myAuthor.getText().toString();
//                    myAuthor.setText(author.toUpperCase());
//                    return true;
//                }
//
//                switch(motion) {
//                    case (MotionEvent.ACTION_DOWN) :
//                        initial_x=(int)motionEvent.getX();
//                        initial_y=(int)motionEvent.getY();
//                        return true;
//                    case (MotionEvent.ACTION_MOVE) :
//                        // Week 10-Task 2: Add one dollar to bookPrice if swiping from left to right
//                        if(Math.abs(initial_y-motionEvent.getY()) < 40) {
//                            if(initial_x < motionEvent.getX()) {
//                                double oriPrice =  Double.parseDouble(myPrice.getText().toString());
//                                myPrice.setText(Double.toString(oriPrice + 1));
//                            }
//                        }
//                        return true;
//                    case (MotionEvent.ACTION_UP) :
//                        // Week10-Task 1: Add book if swiping from right to the left
//                        if(Math.abs(initial_y-motionEvent.getY()) < 40) {
//                            if(initial_x > motionEvent.getX()) {
//                                add();
//                            }
//                        }
//                        // Week 10-Task 3: Clear all the fields if swiping from bottom to top
//                        else if(Math.abs(initial_x-motionEvent.getX()) < 40) {
//                            if(initial_y > motionEvent.getY()) {
//                                clearInputField();
//                            }
//                            // Week 10-Extra task:
//                            else if(initial_y < motionEvent.getY()) {
//                                finish();
//                            }
//                        }
//                        return true;
//                    default :
//                        return false;
//                }

            }
        });

    }

    // Week 11: new class
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {
            Log.i("week11", "onSingleTapUp");
            // Week 11-Task 1: Generate a new random ISBN
            myISBN.setText(generateNewRandomString(5));
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            Log.i("week11", "onLongPress");
            // Week 11-task 3: Load default/saved values
            loading();
            super.onLongPress(e);
        }

        //e1 is on touch which is consistent, e2 is touch up, distanceX is the distance along the X axis that has been scrolled, distanceY is for Y axis
        @Override
        public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            Log.i("week11", "onScroll");
            double oriPrice =  Double.parseDouble(myPrice.getText().toString());

            // Week 11-Task 2: Scroll from left to right, increase the price
            if (e2.getX() > e1.getX()) {
                oriPrice += 1;
                myPrice.setText(Double.toString(oriPrice));
            }
            // Week 11-Task 2: Scroll from right to left, decrease the price
            else if (e2.getX() < e1.getX()) {
                oriPrice -= 1;
                myPrice.setText(Double.toString(oriPrice));
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            // Week 11-task 3: Move the app (activity) to the background by calling "moveTaskToBack(true);"
            if(velocityX > 1000 || velocityX < -1000) {
                moveTaskToBack(true);
                Log.i("week11", "onFling");
            }
            else if(velocityY > 1000 || velocityY < -1000) {
                myTitle.setText("untitled");
                Log.i("week11", "onFling");
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            Log.i("week11", "onDoubleTap");
            // Week 11-Task 2: Clear all fields
            clearInputField();
            return super.onDoubleTap(e);
        }
    }


    class MyBroadCastReceiver extends BroadcastReceiver {

        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */

            StringTokenizer sT = new StringTokenizer(msg, "|");
            String book_BookID = sT.nextToken();
            String book_Title = sT.nextToken();
            String book_ISBN = sT.nextToken();
            String book_Author = sT.nextToken();
            String book_Description = sT.nextToken();
            String book_Price = sT.nextToken();
            boolean book_boolean = Boolean.parseBoolean(sT.nextToken());
            /*
             * Now, its time to update the UI
             * */
            myBookID.setText(book_BookID);
            myTitle.setText(book_Title);
            myISBN.setText(book_ISBN);
            myAuthor.setText(book_Author);
            myDescription.setText(book_Description);
            myPrice.setText(book_Price);
            if (book_boolean == true){
                myPrice.setText(String.valueOf(Double.parseDouble(book_Price)+100));
            }
            else {
                myPrice.setText(String.valueOf(Double.parseDouble(book_Price)+5));
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Refer back to the same SP file
        SharedPreferences myData = getSharedPreferences("F1",0);
        myBookID.setText(myData.getString("Book_ID", ""));
        myTitle.setText(myData.getString("Book_title", ""));
        myISBN.setText(myData.getString("Book_ISBN", ""));
        myAuthor.setText(myData.getString("Book_author", ""));
        myDescription.setText(myData.getString("Book_description", ""));
        myPrice.setText(String.valueOf(myData.getFloat("Book_price", 0)));
    }

    public void add() {
        count++;

        // original is showToast
        String bookID = myBookID.getText().toString();
        String title = myTitle.getText().toString();
        String bookISBN = myISBN.getText().toString();
        String author = myAuthor.getText().toString();
        String description = myDescription.getText().toString();
        double price = Double.parseDouble(myPrice.getText().toString());

        Toast myMessage = Toast.makeText(this,"Book(" + title + ") and the price(" + price + ")", Toast.LENGTH_SHORT);
        myMessage.show();

        // W3-Task2: save the current book data in SP to use in load
        SharedPreferences myData = getSharedPreferences("F1",0);
        SharedPreferences.Editor myEditor = myData.edit();

        myEditor.putString("Book_ID", myBookID.getText().toString());
        myEditor.putString("Book_title", myTitle.getText().toString());
        myEditor.putString("Book_ISBN", myISBN.getText().toString());
        myEditor.putString("Book_author", myAuthor.getText().toString());
        myEditor.putString("Book_description", myDescription.getText().toString());
        myEditor.putFloat("Book_price", Float.parseFloat(myPrice.getText().toString()));
        myEditor.commit();


//        myList.add(title + " | " + price);
        Book myBook = new Book(bookID, title, bookISBN, author, description, price);
//        myList.add(myBook);

        // Week7 Add data to database instead of list array
        mBookViewModel.insert(myBook);
        // Week8: Add data to Firebase
        myRef.push().setValue(myBook);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Toast.makeText(getApplicationContext(),"Book added",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext(),"Book removed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        myAdapter.notifyDataSetChanged();
    }

    public void clearInputField() {
        myBookID.setText("");
        myTitle.setText("");
        myISBN.setText("");
        myAuthor.setText("");
        myDescription.setText("");
        myPrice.setText("");
    }

    public void loading() {
        // W3-Task3
        SharedPreferences myData = getSharedPreferences("F1",0);
        myBookID.setText(myData.getString("Book_ID",""));
        myTitle.setText(myData.getString("Book_title",""));
        myISBN.setText(myData.getString("Book_ISBN",""));
        myAuthor.setText(myData.getString("Book_author",""));
        myDescription.setText(myData.getString("Book_description",""));
        myPrice.setText(String.valueOf(myData.getFloat("Book_price",0)));
    }

    public void doublePrice(View v) {
        EditText myPrice = findViewById(R.id.Price);
        double oriPrice =  Double.parseDouble(myPrice.getText().toString());
        myPrice.setText(Double.toString(oriPrice*2));
    }

    public void setISBN(View v) {
        // W3-extra task
        // Declare an instance of SP.
        SharedPreferences myData = getSharedPreferences("F1",0);
        // Declare an instance of the Editor and link to the SP.
        SharedPreferences.Editor myEditor = myData.edit();
        myEditor.putString("Book_ISBN", "00112233");
        myEditor.commit();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
        // W3-Task1: Only remain title and ISBN
        outState.putString("Title", myTitle.getText().toString());
        outState.putString("ISBN", myISBN.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
        myTitle.setText(savedInstanceState.getString("Title"));
        myISBN.setText(savedInstanceState.getString("ISBN"));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    // Week5 DrawerClass
    class MyDrawerListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.drawer_addBook) {
                // Replace the Add book button with a FAB button
                add();
            }
            else if (id == R.id.drawer_removeLast) {
                deleteLastItem();
//                myAdapter.notifyDataSetChanged();
            }
            else if (id == R.id.drawer_removeAll) {
                deleteAll();
//                myAdapter.notifyDataSetChanged();
            }
            else if (id == R.id.drawer_close) {
                finish();
            }
            else if (id == R.id.drawer_listAll) {
                showList_usingDB();
            }
            else if (id == R.id.drawer_removePrice) {
                deletePrice_greater50();
            }
            // close the drawer
            drawerLayout.closeDrawers();
            // tell the OS
            return true;
        }
    }

    // Week5 OptionMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.option_clearFields) {
            // Clear Fields
            clearInputField();
            Toast.makeText(getApplicationContext(),"Clear Fields",Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.option_loadData) {
            // Load Data
            loading();
            Toast.makeText(getApplicationContext(),"Load Data",Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.option_totalBooks) {
            // Total Books
            Toast.makeText(getApplicationContext(),"Total number of books: " + count,Toast.LENGTH_SHORT).show();
        }
        // close the drawer
        // tell the OS
        return true;
//        return super.onOptionsItemSelected(item);
    }

    public void deleteAll()
    {
        mBookViewModel.deleteAll();
        // Week8: Remove all the data from Firebase
        myRef.removeValue();
    }

    public void deleteLastItem()
    {
        mBookViewModel.deleteLastItem();
    }

    public void deletePrice_greater50() {
        mBookViewModel.deletePrice_greater50();
    }
    // Week7 Database
    public void showList_usingDB()
    {
//        trigger = "Gson";
//
//        String dbStr = gson.toJson(data);
//
//        SharedPreferences sP = getSharedPreferences("db1",0);
//        SharedPreferences.Editor edit = sP.edit();
//        edit.putString("KEY_LIST", dbStr);
//        edit.apply();

        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }
}