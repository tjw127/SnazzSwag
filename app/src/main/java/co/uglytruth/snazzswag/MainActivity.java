package co.uglytruth.snazzswag;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.mopub.common.MoPub;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

import co.uglytruth.snazzswag.connectivity.Connectivity;
import co.uglytruth.snazzswag.walmart.Walmart;
import co.uglytruth.snazzswag.walmart.adapter.WalmartAdapter;
import co.uglytruth.snazzswag.walmart.async_task.OkHttpAsyncTaskResponse;
import co.uglytruth.snazzswag.walmart.products.WalmartProducts;
import co.uglytruth.snazzswag.walmart.response.WalmartProductsResponse;
import co.uglytruth.snazzswag.walmart.rest_api.WalmartRestAPI;

public class MainActivity extends AppCompatActivity implements OkHttpAsyncTaskResponse, MoPubInterstitial.InterstitialAdListener {

    private RecyclerView walmartRecyclerView;
    private Connectivity connectivity;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private MoPubInterstitial mInterstitial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {

            this.setAds();

        }catch (Exception e){

            e.printStackTrace();
        }

        this.create_views();

    }

    private void create_views(){

        this.connectivity = (Connectivity)findViewById(R.id.internet_connectivity);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.walmartRecyclerView = (RecyclerView)findViewById(R.id.walmart_list);
        this.walmartRecyclerView.setHasFixedSize(false);
        this.walmartRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        this.progressBar = (ProgressBar)findViewById(R.id.main_progress_bar);

    }

    private void setAds(){
        this.mInterstitial = new MoPubInterstitial(this, Credentials.mopub_fullscreen_ad);
        this.mInterstitial.setInterstitialAdListener(this);
    }

    private void loadAds(){
        if (this.mInterstitial.isReady()){
            this.mInterstitial.load();
        }
    }

    private void walmart_search(String q){

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

    private boolean getConnectivityStatus() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;

        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null){

            return false;

        }else {
            return networkInfo.isConnected();
        }

    }

    private void setFabOnClickMethod(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshing the products.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                try {

                    if (getConnectivityStatus()) {
                        progressBar.setVisibility(View.VISIBLE);
                        walmart_search("women");
                        connectivity.setVisibility(View.INVISIBLE);


                    } else {
                        connectivity.setText("No Internet Connection");
                        connectivity.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                }catch (Exception e){
                    Log.d("Walmart_Main_Fab", " " + e.getLocalizedMessage());
                }

            }
        });
    }

    private void check_internet_status(){

        if (this.getConnectivityStatus()){

            this.connectivity.setVisibility(View.INVISIBLE);

        }else {

            this.connectivity.setText("No Internet Connection");
            this.connectivity.setVisibility(View.VISIBLE);
        }

    }

    private void setWalmartRecyclerView(){

        if (this.walmartRecyclerView.getAdapter() == null) {

            this.progressBar.setVisibility(View.VISIBLE);
            if (this.getConnectivityStatus()){

                this.walmart_search("women");
                this.connectivity.setVisibility(View.INVISIBLE);


            }else {

                this.connectivity.setText("No Internet Connection");
                this.connectivity.setVisibility(View.VISIBLE);
            }

        }else {

            this.check_internet_status();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.setFabOnClickMethod();
        try {

            this.loadAds();

        }catch (Exception e){

            Log.v("MoPubMainActivity", " On Start " + e.getLocalizedMessage());
        }

        this.setWalmartRecyclerView();


    }

    @Override
    protected void onResume() {
        super.onResume();

        this.check_internet_status();

        try {

            this.loadAds();

        }catch (Exception e){

            Log.v("MoPubMainActivity", " OnResume " + e.getLocalizedMessage());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        MoPub.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MoPub.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MoPub.onDestroy(this);

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

    private void onResponseWalmartRecyclerView(Object results){

        if (this.walmartRecyclerView.getAdapter() == null){

            WalmartProducts products = WalmartProductsResponse.getResults(String.valueOf(results));

            WalmartAdapter walmartAdapter = new WalmartAdapter(products.items, this.getApplicationContext());

            this.walmartRecyclerView.setAdapter(walmartAdapter);

            this.progressBar.setVisibility(View.INVISIBLE);

            Log.d("WalmartResponse", " " + walmartAdapter);
        }else {

            this.progressBar.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onResponse(Object results) {

        this.onResponseWalmartRecyclerView(results);
    }

    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {

    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {

    }
}
