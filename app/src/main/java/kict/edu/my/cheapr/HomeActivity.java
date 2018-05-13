package kict.edu.my.cheapr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;



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
            Log.e("msg", "commited nav cart");
            Intent x;
            x = new Intent(HomeActivity.this, ShopListActivity.class);
            startActivity(x);

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
