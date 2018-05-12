package kict.edu.my.cheapr.web;

/**
 * Created by anas on 5/1/18.
 */

public interface WebListener {

    public static final String API = "http://35.189.162.214:8001";

    public String onWebSuccess(String response);

    public String onWebFailure(String response);

    public static final String CAT_SNACK = "snack";
    public static final String CAT_DRINK = "drink";
    public static final String CAT_FRESHFOOD = "fresh";
    public static final String CAT_TOILETRIES = "toilet";
    public static final String CAT_HOUSEHOLD = "house";
    public static final String CAT_INGREDIENT = "ingredient";
}
