package co.uglytruth.snazzswag;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import co.uglytruth.snazzswag.walmart.Walmart;
import co.uglytruth.snazzswag.walmart.adapter.WalmartAdapter;
import co.uglytruth.snazzswag.walmart.async_task.OkHttpAsyncTaskResponse;
import co.uglytruth.snazzswag.walmart.products.WalmartProducts;
import co.uglytruth.snazzswag.walmart.response.WalmartProductsResponse;
import co.uglytruth.snazzswag.walmart.rest_api.WalmartRestAPI;

public class MainActivity extends AppCompatActivity implements OkHttpAsyncTaskResponse {

    private RecyclerView walmartRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        create_views();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void create_views(){

        this.walmartRecyclerView = (RecyclerView)findViewById(R.id.walmart_list);
        this.walmartRecyclerView.setHasFixedSize(false);
        this.walmartRecyclerView.setLayoutManager(new GridLayoutManager(this,2));



    }

    public void walmart_search(String q){

        try {

            Walmart.RequestBuilder requestBuilder = new Walmart.RequestBuilder();

            requestBuilder.response(this);

            Walmart.EndpointBuilder endpointBuilder = new Walmart.EndpointBuilder();

            Walmart.ArgumentsBuilder argumentsBuilder = new Walmart.ArgumentsBuilder();

            argumentsBuilder.query(q);

            WalmartRestAPI.Search search = new WalmartRestAPI.Search(endpointBuilder, argumentsBuilder, requestBuilder);

            search.getResults();

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.walmartRecyclerView.getAdapter() == null) {
            
            this.walmart_search("women");

        }
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

       if (this.walmartRecyclerView.getAdapter() == null){

           WalmartProducts products = WalmartProductsResponse.getResults(String.valueOf(results));

           WalmartAdapter walmartAdapter = new WalmartAdapter(products.items, this.getApplicationContext());

           this.walmartRecyclerView.setAdapter(walmartAdapter);

           Log.d("WalmartResponse", " " + walmartAdapter);
       }




    }
}
