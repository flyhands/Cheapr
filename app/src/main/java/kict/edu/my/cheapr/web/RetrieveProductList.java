package kict.edu.my.cheapr.web;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by anas on 5/1/18.
 */

public class RetrieveProductList extends AsyncTask<String, Void, String> {
    private String jsonResult;
    private WebListener webListener;

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream response = connection.getInputStream();
            jsonResult = inputStreamToString(response).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((rLine = bufferedReader.readLine()) != null)
                stringBuilder.append(rLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (webListener != null) {
            if (jsonResult != null) {
                webListener.onWebSuccess(jsonResult);
            }
        }
    }

    public void setWebListener(WebListener webListener) {
        this.webListener = webListener;
    }
}
