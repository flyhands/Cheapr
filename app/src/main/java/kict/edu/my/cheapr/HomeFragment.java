package kict.edu.my.cheapr;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import org.json.JSONArray;

import kict.edu.my.cheapr.web.WebListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Button btnSnack;
    Button btnDrink;
    Button btnFreshfood;
    Button btnIngredient;
    Button btnToiletries;
    Button btnHousehold;

    SearchView sv;
    JSONArray json;
    String dummy = "[" +
            "{'id':3,'name':'443452','category':'house','category_name':'Household','supermarket':'Tesco','date_created':'2018-04-14T13:53:35.009779Z','date_updated':'2018-04-14T14:24:45.228848Z'}," +
            "{'id':5,'name':'123','category':'snack','category_name':'Snack','supermarket':'Tesco','date_created':'2018-04-15T03:38:45.364446Z','date_updated':'2018-04-15T03:38:45.364490Z'},{'id':6,'name':'667123','category':'snack','category_name':'Snack','supermarket':'hello world','date_created':'2018-04-15T03:39:05.213126Z','date_updated':'2018-04-15T03:39:05.213154Z'},{'id':7,'name':'561','category':'snack','category_name':'Snack','supermarket':'Tesco','date_created':'2018-04-15T03:43:23.942425Z','date_updated':'2018-04-15T03:43:39.643425Z'},{'id':8,'name':'1','category':'fresh','category_name':'Fresh Food','supermarket':'hello world','date_created':'2018-04-15T04:02:34.589793Z','date_updated':'2018-04-15T04:02:34.589836Z'},{'id':9,'name':'12','category':'house','category_name':'Household','supermarket':'hello world','date_created':'2018-04-15T04:18:11.685498Z','date_updated':'2018-04-15T04:26:58.533399Z'}]";



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        /*Bundle b = getArguments();
        TextView tv = (TextView) view.findViewById(R.id.message_home);
        tv.setText((CharSequence) b.get("message"));
        return view;*/

//        try {
//            Log.e("SEARCH", "asfa");
//            json = new JSONArray(dummy);
//            Log.e("SEARCH", json.toString());
//            for (int i = 0; i < json.length(); i++) {
//                JSONObject obj = json.getJSONObject(i);
//                Log.e("SEARCH", obj.get("category").toString());
//            }
//        } catch (JSONException e) {
//            Log.e("SEARCH", "Failed to parse");
//        }

        btnSnack = (Button)view.findViewById(R.id.ButtonSnack);
        btnDrink = (Button)view.findViewById(R.id.ButtonDrink);
        btnFreshfood = (Button)view.findViewById(R.id.ButtonFreshFood);
        btnIngredient = (Button)view.findViewById(R.id.ButtonIngredient);
        btnToiletries = (Button)view.findViewById(R.id.ButtonToiletries);
        btnHousehold = (Button)view.findViewById(R.id.ButtonHousehold);

        sv = (SearchView)view.findViewById(R.id.searchField);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Intent i = new Intent(getContext(),SearchActivity.class);
                i.putExtra("searchkeyword", s);
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        btnSnack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getContext(),SearchActivity.class);
                i.putExtra("category", WebListener.CAT_SNACK);
                startActivity(i);
            }
        });

        btnDrink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getContext(),SearchActivity.class);
                i.putExtra("category", WebListener.CAT_DRINK);
                startActivity(i);
            }
        });

        btnFreshfood.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getContext(),SearchActivity.class);
                i.putExtra("category", WebListener.CAT_FRESHFOOD);
                startActivity(i);
            }
        });

        btnIngredient.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getContext(),SearchActivity.class);
                i.putExtra("category", WebListener.CAT_INGREDIENT);
                startActivity(i);
            }
        });

        btnToiletries.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getContext(),SearchActivity.class);
                i.putExtra("category", WebListener.CAT_TOILETRIES);
                startActivity(i);
            }
        });

        btnHousehold.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getContext(),SearchActivity.class);
                i.putExtra("category", WebListener.CAT_HOUSEHOLD);
                startActivity(i);
            }
        });


        return view;
    }

}
