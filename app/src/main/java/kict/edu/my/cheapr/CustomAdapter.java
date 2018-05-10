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
    private final ArrayList<Integer> imgId;


    public CustomAdapter(Context context, int resource, ArrayList<Integer> imgId) {
        super(context, resource, imgId);

        this.context = context;
        this.resourceID = resource;
        this.imgId = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.location_image, null, true);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.locate);


        imageView.setImageResource(imgId.get(position));

        return rowView;
    }

}
