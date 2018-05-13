package kict.edu.my.cheapr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import kict.edu.my.cheapr.models.Price;
import kict.edu.my.cheapr.web.PredictPrice;

public class PredictActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    private TextView textViewPredictedPrice;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        textViewPredictedPrice = (TextView)findViewById(R.id.predictedPrice);

        bundle = getIntent().getBundleExtra("bundle");
        Price price = new Price(
                bundle.getDouble("price_value"),
                bundle.getDouble("currency_value"),
                bundle.getInt("day_start"),
                bundle.getInt("day_end"),
                bundle.getInt("month_start"),
                bundle.getInt("month_end"),
                bundle.getInt("year_start"),
                bundle.getInt("year_end"),
                bundle.getString("supermarket")
        );

        new PredictPrice(
                textViewPredictedPrice,
                price,
                bundle.getString("name"),
                bundle.getString("category")).execute();
    }
}
