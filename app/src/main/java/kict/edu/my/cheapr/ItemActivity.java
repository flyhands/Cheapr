package kict.edu.my.cheapr;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import kict.edu.my.cheapr.models.Price;
import kict.edu.my.cheapr.web.RetrieveProductData;
import kict.edu.my.cheapr.web.WebListener;

public class ItemActivity extends AppCompatActivity implements WebListener {
    private final String TAG = getClass().getName();

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

    Bundle bundle;
    private boolean loading;
    private String url;
    private ArrayList<Price> prices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        bundle = getIntent().getExtras();
        Log.d(TAG, String.format("%s", getIntent().hasExtra("id") ? bundle.getString("id") : "no id"));

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

        loading = false;
        url = String.format("%s/product/%s", WebListener.API, bundle.getString("id"));
        Log.d(TAG, "url "+url);
        retrieveProductDetailsFromWeb();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public String onWebSuccess(String response) {
        Log.d(TAG, "onwebsuccess");
        Log.d(TAG, response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("prices");
            prices = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                prices.add(new Price(
                        object.getDouble("price_value"),
                        object.getString("supermarket")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(prices, new Comparator<Price>() {
            @Override
            public int compare(Price p1, Price p2) {
                return Double.compare(p1.getValue(), p2.getValue());
            }
        });
        Log.d(TAG, prices.toString());
        init();
        return null;
    }

    @Override
    public String onWebFailure(String response) {
        return null;
    }

    private void retrieveProductDetailsFromWeb() {
        if (loading) return;
        loading = true;
        RetrieveProductData retrieveProductData = new RetrieveProductData();
        retrieveProductData.setWebListener(this);
        retrieveProductData.execute(String.format("%s", url));
        loading = false;
    }

    private int getPercentage() {
        Log.d(TAG, "getpercentage");
        int size = prices.size();
        double min = prices.get(0).getValue();
        double max = prices.get(size-1).getValue();
        double mid = prices.get(size/2).getValue();
        Double ans = ((mid-min) / (max-min)) * 100;
        Log.d(TAG, "min "+min);
        Log.d(TAG, "max "+max);
        Log.d(TAG, "mid "+mid);
        Log.d(TAG, "ans "+ans);
        initTextViewRange(min, mid, max);
        return ans.intValue();
    }

    private void init() {
        pb.setProgress(getPercentage());
        initMarket();
        initPrice();
        initLocate();
        initCart();
    }

    private void initTextViewRange(double min, double mid, double max) {
        tvMinRange.setText(String.format("RM%.2f", min));
        tvMidRange.setText(String.format("RM%.2f", mid));
        tvMaxRange.setText(String.format("RM%.2f", max));
    }

    private void initMarket() {
        Log.d(TAG, "initmarket");
        market = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            market.add(prices.get(i).getSupermarket());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, market) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView item = (TextView)super.getView(position, convertView, parent);
                item.setTextColor(Color.parseColor("#25969a"));
                item.setTypeface(item.getTypeface(), Typeface.BOLD);
                return item;
            }
        };
        lvMarket.setAdapter(adapter);
    }

    private void initPrice() {
        Log.d(TAG, "initprice");
        price = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            price.add(String.format("RM%.2f", prices.get(i).getValue()));
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, price) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView item = (TextView)super.getView(position, convertView, parent);
                item.setTextColor(Color.parseColor("#ad0b47"));
                item.setTypeface(item.getTypeface(), Typeface.BOLD);
                return item;
            }
        };
        lvPrice.setAdapter(adapter);
    }

    private void initLocate() {
        Log.d(TAG, "initlocate");
        locate = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            locate.add("");
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locate) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView item = (TextView)super.getView(position, convertView, parent);
                item.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.location_place, null));
                return item;
            }
        };
        lvLocate.setAdapter(adapter);
        lvLocate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String sMarket = market.get(i).toString();
                Log.d(TAG, "lvlocate click "+sMarket);
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
                builder.setTitle("Google Maps");
                builder.setMessage("The map will direct you to the nearest branch.");
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=an+"+sMarket+""));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });
    }

    private void initCart() {
        Log.d(TAG, "initcart");
        cart = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            cart.add("");
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cart) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView item = (TextView)super.getView(position, convertView, parent);
                item.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.shopping_add, null));
                return item;
            }
        };
        lvCart.setAdapter(adapter);
        lvCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addItem(name);
            }
        });
    }
}
