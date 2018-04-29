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

import java.util.List;

public class ItemActivity extends AppCompatActivity {
    private ItemDataSource datasource;
    Button addToCart;
    Button locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        datasource = new ItemDataSource(this);
        datasource.open();
        Log.e("msg", "Datasource open");

        List<StoreData> values = datasource.getAllItemNames();
        Log.e("msg", "Store data stored in the list");

        addToCart = (Button) findViewById(R.id.addToCart);
        locate = (Button) findViewById(R.id.locate);

//        getSupportActionBar().setTitle(Html.fromHtml("<font color='#301631'>ActionBartitle</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent intent = getIntent();
        final String name = intent.getStringExtra("text");

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
//                datasource.createItem_name(name);
                Log.e("msg", "item created");

//                Bundle bundle = new Bundle();
//                bundle.putString("name",name);
//                // set fragment_cart argument
//                CartFragment fragCart = new CartFragment();
//                fragCart.setArguments(bundle);
                Toast.makeText(ItemActivity.this, name + " added to cart", Toast.LENGTH_LONG).show(); // Toast for displaying
                Intent i = new Intent(getApplicationContext(), ShopListActivity.class);
                i.putExtra("name", name);
                startActivity(i);
                Log.e("msg", "dapat send intent");

//                TextView txtview = (TextView)view.findViewById(R.id.tv);
//
//                Log.e("msg","dapat access listview");
//                String text = txtview.getText().toString();
//                Log.e("msg","dapat text");
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
    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }
    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
