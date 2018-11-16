package co.uglytruth.snazzswag;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import co.uglytruth.snazzswag.walmart.Walmart;
import co.uglytruth.snazzswag.walmart.async_task.OkHttpAsyncTaskResponse;
import co.uglytruth.snazzswag.walmart.rest_api.WalmartRestAPI;

public class MainActivity extends AppCompatActivity implements OkHttpAsyncTaskResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void walmart_search(String q){

        try {

            Walmart.RequestBuilder requestBuilder = new Walmart.RequestBuilder();
            requestBuilder.response(this);

            Walmart.EndpointBuilder endpointBuilder = new Walmart.EndpointBuilder();

            Walmart.ArgumentsBuilder argumentsBuilder = new Walmart.ArgumentsBuilder();

            argumentsBuilder.query(q);

            WalmartRestAPI.Search search = new WalmartRestAPI.Search(endpointBuilder, argumentsBuilder, requestBuilder);

            String result = (String)search.getResults();

        }catch (Exception e){

            e.printStackTrace();
        }

//        String result = (String)search.getResults();



//        Log.d("WalmartSearch 1 ", result);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.walmart_search("women");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Object results) {

        Log.d("WalmartResponse", " " + String.valueOf(results));
    }
}
