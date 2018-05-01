package kict.edu.my.cheapr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private String message;
    ArrayList<String> shoppingList = null;
    ItemAdapter adapter;
    ListView lv = null;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
//        TextView tv = (TextView) view.findViewById(R.id.message_cart);
//        tv.setText(R.string.title_cart);

        String item_name = getArguments().getString("name");
        Log.e("msg","name successfully sent");

        setHasOptionsMenu(true);

//        shoppingList = new ArrayList<>();
//        lv = (ListView)findViewById(R.id)
//        adapter = new ItemAdapter(this,R.layout.listview_item,shoppingList);


        return view;
    }


}
