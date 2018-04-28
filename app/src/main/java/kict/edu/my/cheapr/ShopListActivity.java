package kict.edu.my.cheapr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopListActivity extends AppCompatActivity {
    ListView lv;
    ArrayAdapter ia;
    ArrayList<String> values;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        lv = (ListView)findViewById(R.id.listViewCart);
        values = new ArrayList<>();
        tv = (TextView)findViewById(R.id.msg_cart);

        tv.setText(R.string.title_cart);
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        Log.e("msg","Name received");

        ia = null;
        values.add(name);
        ia = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_item,values);
        lv.setAdapter(ia);
        ia.notifyDataSetChanged();

    }


}
