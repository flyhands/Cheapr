package kict.edu.my.cheapr;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopListActivity extends AppCompatActivity {
    ListView lv;
    String name;
    DatabaseHelper myDB;
    ArrayAdapter<String> aa;
    ArrayList<String> itemList;
    int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        //Create and arraylist objet to store selected items

        lv = (ListView)findViewById(R.id.listCart);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        myDB = new DatabaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        itemList = new ArrayList<String>();
        Cursor data = myDB.getAllData();
        if(data.getCount()==0){
            //show message
            showMessage("Hey","Your list is empty!");
            return;
        }
        else{
            while(data.moveToNext()){
                itemList.add(data.getString(1));

                aa = new ArrayAdapter<String>(this,R.layout.checkable_list_layout,R.id.txt_title,itemList);
                lv.setAdapter(aa);
            }
        }

        //This will change color for the checked position and the repeating ones with the same value of i
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                CheckedTextView ctv = (CheckedTextView)findViewById(R.id.txt_title);
//               if(ctv.isChecked() == true) {
//                   ctv.setBackgroundColor(Color.BLUE);
//               }
//            }
//        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                name = adapterView.getItemAtPosition(i).toString();
                Log.e("msg","You clicked on " + name);

                Cursor data = myDB.getItemID(name);

                itemID = -1;

                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }

        }
        });




    }



    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear_data,menu);
        MenuItem btnClear = menu.findItem(R.id.btnClear);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.btnClear) {
            Log.e("msg", "Database cleared");
            Log.e("msg","Item deleted from array");
            Toast.makeText(ShopListActivity.this,  "Shopping list cleared", Toast.LENGTH_LONG).show(); // Toast for displaying
            myDB.removeAll();
            aa.clear();//to clear the listview

        }
        return super.onOptionsItemSelected(item);
    }
}


