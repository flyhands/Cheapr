package kict.edu.my.cheapr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import kict.edu.my.cheapr.web.RetrieveProductList;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

//    String dummy = "[" +
//            "{'id':3,'name':'443452','category':'house','category_name':'Household','supermarket':'Tesco','date_created':'2018-04-14T13:53:35.009779Z','date_updated':'2018-04-14T14:24:45.228848Z'}," +
//            "{'id':5,'name':'123','category':'snack','category_name':'Snack','supermarket':'Tesco','date_created':'2018-04-15T03:38:45.364446Z','date_updated':'2018-04-15T03:38:45.364490Z'},{'id':6,'name':'667123','category':'snack','category_name':'Snack','supermarket':'hello world','date_created':'2018-04-15T03:39:05.213126Z','date_updated':'2018-04-15T03:39:05.213154Z'},{'id':7,'name':'561','category':'snack','category_name':'Snack','supermarket':'Tesco','date_created':'2018-04-15T03:43:23.942425Z','date_updated':'2018-04-15T03:43:39.643425Z'},{'id':8,'name':'1','category':'fresh','category_name':'Fresh Food','supermarket':'hello world','date_created':'2018-04-15T04:02:34.589793Z','date_updated':'2018-04-15T04:02:34.589836Z'},{'id':9,'name':'12','category':'house','category_name':'Household','supermarket':'hello world','date_created':'2018-04-15T04:18:11.685498Z','date_updated':'2018-04-15T04:26:58.533399Z'}]";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Log.e("msg", "committed layoutdsdadsa");
        setContentView(R.layout.activity_home);
        Log.e("msg", "committed layout");

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, new HomeFragment());
        fragmentTransaction.commit();
        new RetrieveProductList().execute("http://35.189.162.214:8001/supermarket");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.e("msg", "committed fragmentmanager");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        int id = item.getItemId();

        if(id == R.id.action_cart) {
////            Toast.makeText(getApplicationContext(),((TextView)view).getText(),Toast.LENGTH_SHORT).show();//to display message like alert
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Add Item");
//            builder.show();
//            return true;
            Log.e("msg", "commited nav cart");
            Intent
                    x;
            x = new Intent(HomeActivity.this, ShopListActivity.class);
            startActivity(x);
//            CartFragment frag_cart = new CartFragment();
//            fragmentTransaction.replace(R.id.fragment, frag_cart);
//            fragmentTransaction.commit();
        }

        if(id == R.id.title_home){
            Log.e("msg", "commited nav home");
            HomeFragment frag_home = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putCharSequence("message", "Cheapr");
            frag_home.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment, frag_home);
            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
