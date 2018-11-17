package co.uglytruth.snazzswag.walmart.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.uglytruth.snazzswag.walmart.products.WalmartProducts;

/**
 * Created by tjw127 on 6/22/17.
 */

public class WalmartProductsResponse {

    public static WalmartProducts getResults(String result)
    {
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(result, WalmartProducts.class);
    }
}
