package kict.edu.my.cheapr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Space;
import android.widget.TextView;

/**
 * Created by User on 17/4/2018.
 */

public class ItemAdapter extends ArrayAdapter {
    LayoutInflater layoutInflater;
    String[] values;
    String key;
    Context context;
    public ItemAdapter(@NonNull Context context, int resource, String[] objects, String key) {
        super(context, resource, objects);
        Log.e("msg1", "itemadapter");
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        values = objects;
        this.context = context;
        this.key = key;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.listview_item, null);
        TextView tv = (TextView)view.findViewById(R.id.tv);
        Log.e("msg1", key.isEmpty()?"empty":"not empty");
        Log.e("msg1",values[position]);
        if (!key.isEmpty()) {
            if(values[position].toLowerCase().contains(key)) {
                tv.setText(values[position]);
            }
            else {
                view = new Space(context);
            }
        }
        else {
            tv.setText(values[position]);
        }

        return view;
    }

    public void setKey(String key){
        this.key = key;
    }
}
