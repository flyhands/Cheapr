package kict.edu.my.cheapr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    SearchView sv;
    ListView lv;
    ItemAdapter ia;
    JSONArray json;

    String dummy = "[" +
            "{'id':3,'name':'Daia Toilet Cleaner','category':'household','category_name':'Household','supermarket':'Tesco','date_created':'2018-04-14T13:53:35.009779Z','date_updated':'2018-04-14T14:24:45.228848Z'}," +
            "{'id':5,'name':'Mamee Monster','category':'snack','category_name':'Snack','supermarket':'Tesco','date_created':'2018-04-15T03:38:45.364446Z','date_updated':'2018-04-15T03:38:45.364490Z'},{'id':6,'name':'Jalin Soy Sauce','category':'ingredient','category_name':'Ingredient','supermarket':'hello world','date_created':'2018-04-15T03:39:05.213126Z','date_updated':'2018-04-15T03:39:05.213154Z'},{'id':7,'name':'Oldtown White Coffee 3 in 1','category':'drink','category_name':'Drink','supermarket':'Tesco','date_created':'2018-04-15T03:43:23.942425Z','date_updated':'2018-04-15T03:43:39.643425Z'},{'id':8,'name':'King Dorry Fish','category':'freshfood','category_name':'Fresh Food','supermarket':'hello world','date_created':'2018-04-15T04:02:34.589793Z','date_updated':'2018-04-15T04:02:34.589836Z'},{'id':9,'name':'Rejoice Shampoo ','category':'toiletries','category_name':'Toiletries','supermarket':'hello world','date_created':'2018-04-15T04:18:11.685498Z','date_updated':'2018-04-15T04:26:58.533399Z'}]";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("SEARCH", "enter search activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            Log.e("SEARCH", "asfa");
            json = new JSONArray(dummy);
            Log.e("SEARCH", json.toString());
        } catch (JSONException e) {
            Log.e("SEARCH", "Failed to parse");
        }

        final ArrayList<String> values = new ArrayList<>();
        String key = getIntent().getExtras().getString("searchkeyword");
        Log.e("msg", getIntent().hasExtra("category")?getIntent().getExtras().getString("category"):"NO");
        lv = (ListView)findViewById(R.id.itemSearch);


        sv = (SearchView)findViewById(R.id.searchField);
        if (getIntent().hasExtra("category")) {
            sv.setVisibility(View.GONE);//hide the searchview
            String cat = getIntent().getExtras().getString("category");
            try {
                for (int i = 0; i < json.length(); i++) {
                    JSONObject obj = json.getJSONObject(i);
                    if (obj.getString("category").equals(cat))
                        values.add(obj.get("name").toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ia = new ItemAdapter(this, R.layout.listview_item, values.toArray(new String[0]), "");
        }
        else {
            sv.setQuery(key, true);
            try {
                for (int i = 0; i < json.length(); i++) {
                    JSONObject obj = json.getJSONObject(i);
                    values.add(obj.get("name").toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ia = new ItemAdapter(this, R.layout.listview_item, values.toArray(new String[0]), key);
        }
        lv.setAdapter(ia);
//        lv.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return json.length();
//            }
//
//            @Override
//            public Object getItem(int i) {
//                return "item " + i;
//            }
//
//            @Override
//            public long getItemId(int i) {
//                return i;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                if(view == null){
//                    view = new TextView(SearchActivity.this);
//                    view.setPadding(10,10,10,10);
//                    ((TextView)view).setTextColor(Color.BLUE);
//                }
//
//                view.setBackgroundColor((i == 1) ?
//                        Color.argb(0x80, 0x20, 0xa0, 0x40) : Color.argb(0, 0, 0, 0));
//                ((TextView)view).setText((String)getItem(i));
//                return view;
//            }
//        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Toast.makeText(getApplicationContext(),((TextView)view).getText(),Toast.LENGTH_SHORT).show();//to display message like alert
                Intent x = new Intent(getBaseContext(),ItemActivity.class);
                //kat sini boleh tambah untuk send details ke cart

                Log.e("msg","dapat send intent");
                TextView txtview = (TextView)view.findViewById(R.id.tv);

                Log.e("msg","dapat access listview");
                String text = txtview.getText().toString();
                Log.e("msg","dapat text");
                x.putExtra("text",text);
                startActivity(x);

            }
        });




        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ia.setKey(s);
                lv.invalidateViews();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
