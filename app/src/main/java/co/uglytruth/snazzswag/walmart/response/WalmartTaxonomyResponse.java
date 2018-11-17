package co.uglytruth.snazzswag.walmart.response;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.uglytruth.snazzswag.walmart.taxonomy.WalmartTaxonomy;


/**
 * Created by tjw127 on 6/22/17.
 */

public class WalmartTaxonomyResponse {

    public static WalmartTaxonomy.Categories[] getResult(String result) throws JSONException
    {
        Gson gson = new GsonBuilder().create();

        JSONArray jsonObject = new JSONObject(result).getJSONArray("categories");

        return (WalmartTaxonomy.Categories[])gson.fromJson(jsonObject.toString(), WalmartTaxonomy.Categories[].class);

        /*
         for (WalmartTaxonomy.Categories categories1 : categories)
        {
            Log.v("Categories ", " " + categories1.name + " \n" + " " + categories1.id);
        }

         */

    }
}
