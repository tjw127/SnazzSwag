package co.uglytruth.snazzswag.walmart.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.walmartlabs.tofa.WalmartBuyNowButton;
import com.walmartlabs.tofa.WalmartItem;
import com.walmartlabs.tofa.WalmartItemIdType;
import com.walmartlabs.tofa.WalmartLineItem;

import java.text.DecimalFormat;
import java.util.ArrayList;


import co.uglytruth.snazzswag.R;
import co.uglytruth.snazzswag.walmart.products.WalmartProducts;
import co.uglytruth.snazzswag.walmart.views.WalmartViewHolder;

/**
 * Created by tjw127 on 6/24/17.
 */

public class WalmartAdapter extends RecyclerView.Adapter<WalmartViewHolder>{

    private WalmartProducts products;

    private Context context;

    private int id;

    private String url;

    WalmartProducts.Items[] items;

    public WalmartAdapter(WalmartProducts.Items[] aItems, Context aContext){

        items = aItems;

        context = aContext;

        Log.d("WalmartAdapter", " " + items.length);
    }



        @Override
        public WalmartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View walmart_view_holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.walmart_view, parent, false);

            walmart_view_holder = this.setWalmartViewHolderMeasurements(parent, walmart_view_holder);


            return new WalmartViewHolder(walmart_view_holder);
        }

        private View setWalmartViewHolderMeasurements(ViewGroup parent, View walmart_holder_view){

            int height = parent.getMeasuredHeight() / 4;

            int width = parent.getMeasuredWidth() / 4;

            walmart_holder_view.setMinimumHeight(height);

            walmart_holder_view.setMinimumWidth(width);

            return walmart_holder_view;
        }

        @Override
        public void onBindViewHolder(@NonNull WalmartViewHolder holder, int position) {

            id = position;

            holder.setIsRecyclable(false);

            WalmartProducts.Items item = items[position];


//            WalmartItem walmartItem = new WalmartItem(item.itemId, WalmartItemIdType.WALMART_ID);


            Integer quantity = null;

            //items.stock.equals("Available")

            quantity = this.isProductAvailable(quantity, item.availableOnline);

            //holder.walmartBuyNowButton.se
//            holder.walmartBuyNowButton.addItem(walmartItem, qualityInteger.intValue());

            try{
                this.buy_button_method(holder, position);

            }catch (Exception e){

                Log.d("WalmartBuyBMethod", " " + e.getLocalizedMessage());
            }

            try {

                this.setPriceInTextView(holder, item.salePrice, quantity);

            }catch (Exception e){

                Log.d("Walmart_Adapter_Price", " " + e.getLocalizedMessage());
            }

            try {

                Picasso.get().load(item.largeImage).into(holder.walmartImageView);

            }catch (NullPointerException e){

                Log.d("Walmart_Adapter_Image", " " + e.getLocalizedMessage());
            }


        }

        private void setPriceInTextView(WalmartViewHolder holder, String price_string, Integer quantity){

            if (quantity == 0) {

                String zero_price = "$" + "0.00";

                holder.walmartPriceTextView.setText(zero_price);

                holder.walmartPriceTextView.setTextColor(Color.RED);

            }else {


                Float itemFloat = Float.valueOf(price_string);

                String price = "$" + String.format("%.2f", itemFloat.floatValue());

                holder.walmartPriceTextView.setText(price);
            }

        }

        private Integer isProductAvailable(Integer quantity, boolean available){


            if (available)
            {
                quantity = 1;
            }else {

                quantity = 0;
            }

            return quantity;
        }

        private void buy_button_method(WalmartViewHolder holder, int position){


            final int product_position = position;

            holder.shoppingBuyButton.getShoppingBuyButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(items[product_position].productTrackingUrl));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setPackage("com.android.chrome");
                    try {

                        context.startActivity(i);

                    } catch (ActivityNotFoundException e) {
                        // Chrome is probably not installed
                        // Try with the default browser
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (items != null)
            {
                return items.length;
            }else {

                return 0;
            }

        }

}
