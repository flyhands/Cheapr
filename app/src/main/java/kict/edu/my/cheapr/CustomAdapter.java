package kict.edu.my.cheapr;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by User on 28/4/2018.
 */

public class CustomAdapter extends ArrayAdapter {

    private final Context context;
    private final int resourceID;


    public CustomAdapter(Context context, int resource, ArrayList<String> bah) {
        super(context, resource, bah);

        this.context = context;
        this.resourceID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.location_image, parent, false);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.locate);



        return rowView;
    }

}
