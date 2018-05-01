package kict.edu.my.cheapr;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShopListActivity extends AppCompatActivity {
    ListView lv;
    ArrayAdapter adapter;
    ArrayList<String> arrayList;
    String key = null;
    String name;
    DatabaseHelper myDB;
    ArrayList<String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);


        lv = (ListView)findViewById(R.id.listCart);
//        arrayList = new ArrayList<String>();
//        adapter = new ArrayAdapter<String>(ShopListActivity.this, android.R.layout.simple_list_item_1, arrayList);
//        lv.setAdapter(adapter);
//
//        Intent intent = getIntent();
//        name = intent.getStringExtra("itemName");


//        adapter.add(name);
//        lv.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

        myDB = new DatabaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        itemList = new ArrayList<>();
        Cursor data = myDB.getAllData();
        if(data.getCount()==0){
            //show message
            showMessage("error","Nothing found");
            return;
        }
        else{
            while(data.moveToNext()){
                itemList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemList);
                lv.setAdapter(listAdapter);
            }
        }
    }


    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
