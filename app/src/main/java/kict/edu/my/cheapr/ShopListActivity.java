package kict.edu.my.cheapr;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class ShopListActivity extends AppCompatActivity{
    String name;
    DatabaseHelper myDB;
    ArrayAdapter<String> aa;
    ArrayList<String> itemList;
    int itemID;
    SwipeMenuListView clv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        Log.e("msg", "Layout formed");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Create and arraylist objet to store selected items

        clv = (SwipeMenuListView)findViewById(R.id.swipeListCart);


        Log.e("msg", "Listview initialized");

        myDB = new DatabaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        itemList = new ArrayList<String>();
        Cursor data = myDB.getAllData();
        Log.e("msg", "Data received");

        try {
            if (data.getCount() == 0) {
                //show message
                showMessage("Hey", "Your list is empty!");
                Log.e("msg", "Message sent");


                return;
            } else {
                while (data.moveToNext()) {
                    itemList.add(data.getString(1));

                    aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
                    clv.setAdapter(aa);

//
                    Log.e("msg", "Adapter set");

                }
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            Log.e("msg", "NPE Occur");

        }

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(170);
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xff,
                        0xff, 0xff)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_checked_item);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        clv.setMenuCreator(creator);

        clv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ShopListActivity.this, "Swipe left, tick to delete", Toast.LENGTH_SHORT).show();

            }
        });
        clv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        Log.e("msg","Clicked item + index");
                        name = clv.getItemAtPosition(position).toString();
                        Cursor item = myDB.getItemID(name);
                        itemID = -1;
                        while(item.moveToNext()){
                            itemID = item.getInt(0);
                        }
                        if(itemID > -1){
                            myDB.deleteData(itemID,name);
                        }
                        itemList.remove(position);
                        aa.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });



        Log.e("msg", "After Item listener set");


    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton("Add item to the list", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(ShopListActivity.this, HomeActivity.class );
                startActivity(i);
            }
        });
        builder.show();
        Log.e("msg", "Builder created");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear_data, menu);
        MenuItem btnClear = menu.findItem(R.id.btnClear);
        Log.e("msg", "Menu inflated");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnClear) {
            Log.e("msg", "Database cleared");
            Log.e("msg", "Item deleted from array");
            Toast.makeText(ShopListActivity.this, "Shopping list cleared", Toast.LENGTH_LONG).show(); // Toast for displaying
            myDB.removeAll();
            aa.clear();//to clear the listview

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

