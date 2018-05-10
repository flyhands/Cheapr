package kict.edu.my.cheapr;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {
    DatabaseHelper myDB;
//    Button addToCart;
//    Button locate;
    int a,b;
    String name;
    Button btnPredict;
    ListView lvMarket;
    ListView lvPrice;
    ListView lvCart;
    ListView lvLocate;
    ArrayList<String> market;
    ArrayList<String> price;
    ArrayList<String> locate;
    ArrayList<String> cart;
    ArrayAdapter<String> adapter;
    TextView tvMidRange;
    TextView tvMinRange;
    TextView tvMaxRange;
    ProgressBar pb;
//    CustomAdapter ca;
    CustomAdapter aa;
//    FragmentManager fm;
//    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);


        myDB = new DatabaseHelper(this);


        btnPredict = (Button)findViewById(R.id.btnPredict);
        lvMarket = (ListView)findViewById(R.id.marketList);
        lvPrice = (ListView)findViewById(R.id.priceList);
        lvCart = (ListView)findViewById(R.id.cartList);
        lvLocate = (ListView)findViewById(R.id.locateList);
        tvMidRange = (TextView)findViewById(R.id.tvMidRange);
        tvMinRange = (TextView)findViewById(R.id.tvMinRange);
        tvMaxRange = (TextView)findViewById(R.id.tvMaxRange);
        pb = (ProgressBar)findViewById(R.id.progressBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvMidRange.setText("RM12.50");
        tvMinRange.setText("RM13.50");
        tvMaxRange.setText("RM14.50");

        pb.setProgress(50);

        market = new ArrayList<>();
        price = new ArrayList<>();
        locate = new ArrayList<>();
        cart = new ArrayList<>();

        market.add("Giant");
        market.add("Econsave");
        Log.e("msg","Supermarket successfully added");

        for(int x =0;x<market.size();x++){
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,market);
            Log.e("msg","adapter works");
            lvMarket.setAdapter(adapter);
        }

        price.add("RM22.50");
        price.add("RM22.00");

        for(int y =0;y<price.size();y++){
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,price);
            Log.e("msg","adapter works");
            lvPrice.setAdapter(adapter);
        }
//        getSupportActionBar().setTitle(Html.fromHtml("<font color='#301631'>ActionBartitle</font>"));


         for(a=0;a<price.size();a++){
            locate.add("Locate");
             adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,locate);
             Log.e("msg","adapter works");
             lvLocate.setAdapter(adapter);

             lvLocate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     final String sMarket = market.get(position).toString();
                     Log.e("msg",sMarket);
                     AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
                     builder.setTitle("Google Maps");
                     builder.setMessage("The map will direct you to the nearest branch.");
                     builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             //Open google map URL
                             Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=an+"+ sMarket +""));
                             startActivity(intent);
                         }
                     });
                     builder.setNegativeButton("Cancel",null);
                     builder.show();
                 }
             });
        }



        //Adding item into arraylist and sqlite
        for(int b=0;b<price.size();b++){
            cart.add("Add");
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cart);
            Log.e("msg","adapter works");
            lvCart.setAdapter(adapter);

            lvCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    addItem(name);
                }
            });
        }

        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ItemActivity.this,PredictActivity.class);
                startActivity(i);
            }
        });





        Intent intent = getIntent();
        name = intent.getStringExtra("text");

        TextView tv = (TextView) findViewById(R.id.itemName);
        tv.setText(name);



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
