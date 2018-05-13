package kict.edu.my.cheapr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kict.edu.my.cheapr.models.Product;
import kict.edu.my.cheapr.web.RetrieveProductData;
import kict.edu.my.cheapr.web.WebListener;

public class SearchActivity extends AppCompatActivity implements WebListener {

    private final String TAG = getClass().getName();

    SearchView sv;
    ListView lv;
    ArrayAdapter<Product> ia;
//    ArrayAdapter<String> ia;
    JSONArray json;
//    ArrayList<String> values;
    ArrayList<Product> values;

    private boolean loading;
    private String url;
    private boolean isSearch;

//    String dummy = "[" +
//            "{'id':3,'name':'Daia Toilet Cleaner','category':'household','category_name':'Household','supermarket':'Tesco','date_created':'2018-04-14T13:53:35.009779Z','date_updated':'2018-04-14T14:24:45.228848Z'}," +
//            "{'id':5,'name':'Mamee Monster','category':'snack','category_name':'Snack','supermarket':'Tesco','date_created':'2018-04-15T03:38:45.364446Z','date_updated':'2018-04-15T03:38:45.364490Z'},{'id':6,'name':'Jalin Soy Sauce','category':'ingredient','category_name':'Ingredient','supermarket':'hello world','date_created':'2018-04-15T03:39:05.213126Z','date_updated':'2018-04-15T03:39:05.213154Z'},{'id':7,'name':'Oldtown White Coffee 3 in 1','category':'drink','category_name':'Drink','supermarket':'Tesco','date_created':'2018-04-15T03:43:23.942425Z','date_updated':'2018-04-15T03:43:39.643425Z'},{'id':8,'name':'King Dorry Fish','category':'freshfood','category_name':'Fresh Food','supermarket':'hello world','date_created':'2018-04-15T04:02:34.589793Z','date_updated':'2018-04-15T04:02:34.589836Z'},{'id':9,'name':'Rejoice Shampoo ','category':'toiletries','category_name':'Toiletries','supermarket':'hello world','date_created':'2018-04-15T04:18:11.685498Z','date_updated':'2018-04-15T04:26:58.533399Z'}]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("SEARCH", "enter search activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lv = (ListView)findViewById(R.id.itemSearch);
        sv = (SearchView)findViewById(R.id.searchField);

        values = new ArrayList<>();
        loading = false;
        isSearch = false;
        url = String.format("%s/product/search", WebListener.API);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrieveProductListFromWeb();

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
                Log.e(TAG,"dapat text");
                Log.d(TAG, text);
                int size = values.size();
                Log.d(TAG, "size "+size);
                String idp = "-1";
                for (int idx = 0; idx < size; idx++) {
                    Log.d(TAG, String.format("%s %s", text, values.get(idx).toString()));
                    if (text.equals(values.get(idx).toString())) {
                        idp = values.get(idx).getId();
                        Log.d(TAG, String.format("%s %s", text, idx));
                        x.putExtra("id", values.get(idx).getId());
                        break;
                    }
                }
                Log.d(TAG, idp);
                x.putExtra("text",text);
                startActivity(x);

            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (!absListView.canScrollList(View.SCROLL_AXIS_VERTICAL)
                        && scrollState == SCROLL_STATE_IDLE) {
                    Parcelable parcelable = lv.onSaveInstanceState();
                    retrieveProductListFromWeb();
                    if (!loading) lv.onRestoreInstanceState(parcelable);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {}
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "text submit "+s);
                isSearch = true;
                url = String.format("%s/product/search?name=%s", WebListener.API, s);
                retrieveProductListFromWeb();
//                ia.setKey(s);
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

    @Override
    public String onWebSuccess(String response) {
        Log.d(TAG, "onwebsuccess");
        Log.d(TAG, response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            url = jsonObject.getString("next");
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            Log.d(TAG, jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                if (i < 10) Log.d(TAG, object.toString());
//                values.add(object.getString("name"));
                values.add(new Product(
                        object.getString("id"), object.getString("name")
                ));
            }
            if (lv.getAdapter() == null) {
                ia = new ArrayAdapter<>(this, R.layout.listview_item, R.id.tv, values);
                lv.setAdapter(ia);
            }
            else {
                ia.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String onWebFailure(String response) {
        return null;
    }

    private void retrieveProductListFromWeb() {
        if (loading) return;
        loading = true;
        RetrieveProductData retrieveProductList = new RetrieveProductData();
        retrieveProductList.setWebListener(this);
        if (isSearch) {
            values.clear();
            retrieveProductList.execute(String.format("%s", url));
            isSearch = false;
        }
        else if (!values.isEmpty())
            retrieveProductList.execute(String.format("%s", url));
        else if (getIntent().hasExtra("category")) {
           sv.setVisibility(View.GONE);
           String cat = getIntent().getExtras().getString("category");
           retrieveProductList.execute(String.format("%s?category=%s", url, cat));
        }
        else {
            String key = getIntent().getExtras().getString("searchkeyword");
            sv.setQuery(key, true);
            retrieveProductList.execute(String.format("%s?name=%s", url, key));
        }
        loading = false;
    }
}
