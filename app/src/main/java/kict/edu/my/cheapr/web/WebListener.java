package kict.edu.my.cheapr.web;

/**
 * Created by anas on 5/1/18.
 */

public interface WebListener {

    public static final String API = "http://35.189.162.214:8001";

    public String onWebSuccess(String response);

    public String onWebFailure(String response);
}
