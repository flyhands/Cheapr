package kict.edu.my.cheapr.web;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import kict.edu.my.cheapr.models.Price;

/**
 * Created by anas on 5/14/18.
 */

public class PredictPrice extends AsyncTask<String, Void, String> {

    private final String TAG = "predict";

    TextView textView;
    Price price;
    String name;
    String category;

    public PredictPrice(TextView textView, Price price, String name, String category) {
        this.textView = textView;
        this.price = price;
        this.name = name;
        this.category = category;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(
                    "https://ussouthcentral.services.azureml.net/workspaces/5b63e1bd902e4065bd4463f51b5d9ce9/services/b9965bd689ab4e4e9743fc29fa35ec58/execute?api-version=2.0&details=true");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty(
                    "Authorization",
                    "Bearer LSjk1w2E4dSy1lDyUtPLgetUteT1fJ+bb23spXYarjh0bVoTftAEWOPTjB79ErjH52WdM+3KrsHsl3TmozWQhw=="
            );
            connection.setRequestProperty(
                    "Content-Type",
                    "application/json"
            );
            connection.setRequestProperty(
                    "Accept",
                    "application/json"
            );
            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(getPostData());
            writer.flush();
            writer.close();
            out.close();
            InputStream response = connection.getInputStream();
            return inputStreamToString(response).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onpostexecute");
        Log.d(TAG, s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            jsonObject = jsonObject.getJSONObject("Results");
            jsonObject = jsonObject.getJSONObject("output1");
            jsonObject = jsonObject.getJSONObject("value");
            JSONArray jsonArray = jsonObject.getJSONArray("Values");
            Log.d(TAG, jsonArray.toString());
            s = jsonArray.getString(0);
            jsonArray = new JSONArray(s);
            textView.setText(String.format("RM%.2f",
                    Double.parseDouble(jsonArray.getString(jsonArray.length()-1))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder inputStreamToString(InputStream inputStream) {
        String rLine = "";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((rLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    private String getPostData(String... strings) {
        String data = String.format("" +
                "{\n" +
                "  'Inputs': {\n" +
                "    'input1': {\n" +
                "      'ColumnNames': [\n" +
                "        'Product Name',\n" +
                "        'Description',\n" +
                "        'Categories',\n" +
                "        'ProductPrice',\n" +
                "        'CurrentCurrency',\n" +
                "        'DayStart',\n" +
                "        'DayEnd',\n" +
                "        'MonthStart',\n" +
                "        'MonthEnd',\n" +
                "        'YearStart',\n" +
                "        'YearEnd',\n" +
                "        'Supermarket'\n" +
                "      ],\n" +
                "      'Values': [\n" +
                "        [\n" +
                "          '%s',\n" +
                "          '',\n" +
                "          '%s',\n" +
                "          '%.2f',\n" +
                "          '%.2f',\n" +
                "          '%d',\n" +
                "          '%d',\n" +
                "          '%d',\n" +
                "          '%d',\n" +
                "          '%d',\n" +
                "          '%d',\n" +
                "          ''\n" +
                "        ]\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  'GlobalParameters': {}\n" +
                "}",
                name, category,
                price.getPrice_value(), price.getCurrency_value(),
                price.getDay_start(), price.getDay_end(),
                price.getMonth_start(), price.getMonth_end(),
                price.getYear_start(), price.getYear_end());
        Log.d(TAG, data);
        return data;
    }
}
