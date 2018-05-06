package kict.edu.my.cheapr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {
    DatabaseHelper myDB;
//    Button addToCart;
//    Button locate;
    String name;
    ListView lvMarket;
    ListView lvPrice;
    ListView lvCart;
    ListView lvLocate;
    ArrayList<String> market;
    ArrayList<String> price;
    ArrayList<String> locate;
    ArrayList<String> cart;
    ArrayAdapter<String> adapter;
//    CustomAdapter ca;
    CustomAdapter aa;

    private static final String TAG = "ItemActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        myDB = new DatabaseHelper(this);

//        addToCart = (Button) findViewById(R.id.addToCart);
//        locate = (Button) findViewById(R.id.locate);
        lvMarket = (ListView)findViewById(R.id.marketList);
        lvPrice = (ListView)findViewById(R.id.priceList);
        lvCart = (ListView)findViewById(R.id.cartList);
        lvLocate = (ListView)findViewById(R.id.locateList);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        locate.add("0");
         for(int a =0;a<2;a++){//nak edit sini wehh esok
            aa = new ArrayAdapter<String>(this,R.id.location_image,t
 /            aa = new CustomAdapter(this,locate );
            lvLocate.setAdapter(aa);
        }

//        for(int b=0;b<price.size();b++){
//
//            ca = new CustomAdapter(this,android.R.layout.simple_list_item_1,cart);
//            lvCart.setAdapter(ca);
//        }



        Intent intent = getIntent();
        name = intent.getStringExtra("text");

        TextView tv = (TextView) findViewById(R.id.itemName);
        tv.setText(name);

//        locate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("google.navigation:q=an+Giant"));
//                startActivity(intent);
//            }
//        });

//        addToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addItem(name);
//                Toast.makeText(ItemActivity.this, name + " added to cart", Toast.LENGTH_LONG).show(); // Toast for displaying
//
//
//            }
//        });


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

//    public boolean isServicesOK(){
//        Log.e(TAG,"isServicesOK: checking google services version");
//
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ItemActivity.this);
//
//        if(available == ConnectionResult.SUCCESS){
//            //everything is fine and the user can make map requests
//            Log.e(TAG, "isServicesOK:  Google play services is working");
//            return true;
//        }
//        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            //an error occurred but we can fit ix
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ItemActivity.this, available, ERROR_DIALOG_REQUEST);
//            dialog.show();
//        }
//        else{
//            Toast.makeText(this,"You can't make app requests", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
}
