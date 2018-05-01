package kict.edu.my.cheapr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    Button addToCart;
    Button locate;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        myDB = new DatabaseHelper(this);

        addToCart = (Button) findViewById(R.id.addToCart);
        locate = (Button) findViewById(R.id.locate);

//        getSupportActionBar().setTitle(Html.fromHtml("<font color='#301631'>ActionBartitle</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent intent = getIntent();
        name = intent.getStringExtra("text");

        TextView tv = (TextView) findViewById(R.id.itemName);
        tv.setText(name);

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=an+Giant+Batu Caves"));
                startActivity(intent);
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(name);
                Toast.makeText(ItemActivity.this, name + " added to cart", Toast.LENGTH_LONG).show(); // Toast for displaying
//                Intent i = new Intent(getApplicationContext(), ShopListActivity.class);
//                i.putExtra("itemName", name);
//                startActivity(i);
//
//                Log.e("msg", "dapat send intent");
//
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item1 = menu.findItem(R.id.title_home);
//        MenuItem item2 = menu.findItem(R.id.)
        item1.setVisible(false);//set home to invisible
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_cart) {
            Log.e("msg", "commited nav cart");
            Intent x = new Intent(getApplicationContext(), ShopListActivity.class);
            startActivity(x);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    //Method for adding item into database
    public void addItem(String name){
        boolean isInserted = myDB.insertData(name);
        if(isInserted == true)
            Toast.makeText(ItemActivity.this,"Data inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ItemActivity.this,"Data not inserted", Toast.LENGTH_LONG).show();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
