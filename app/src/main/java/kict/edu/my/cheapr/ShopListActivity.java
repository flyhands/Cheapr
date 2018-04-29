package kict.edu.my.cheapr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopListActivity extends AppCompatActivity {
    ListView lv;
    ItemAdapter ia;
    ArrayList<String> values;
    TextView textv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        lv = (ListView)findViewById(R.id.listViewCart);
        values = new ArrayList<>();
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        textv = (TextView)findViewById(R.id.msg_cart);

        textv.setText(name);

//        Log.e("msg",name);

        ia = null;
        values.add(name);
        ia = new ItemAdapter(this, R.layout.listview_item, values.toArray(new String[0]),"");
        lv.setAdapter(ia);
//        ia.notifyDataSetChanged();

    }


}
