package co.uglytruth.snazzswag.walmart.adapter;

import android.content.Context;
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

    WalmartProducts.Items[] items;

    public WalmartAdapter(WalmartProducts.Items[] aItems, Context aContext){

        items = aItems;

        context = aContext;

        Log.d("WalmartAdapter", " " + items.length);
    }



        @Override
        public WalmartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.walmart_view, parent, false);

            int height = parent.getMeasuredHeight() / 4;

            int width = parent.getMeasuredWidth() / 4;

            itemView.setMinimumHeight(height);

            itemView.setMinimumWidth(width);

            return new WalmartViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull WalmartViewHolder holder, int position) {

            id = position;

            holder.setIsRecyclable(false);

            WalmartProducts.Items item = items[position];


//            WalmartItem walmartItem = new WalmartItem(item.itemId, WalmartItemIdType.WALMART_ID);


            Integer qualityInteger = null;

            //items.stock.equals("Available")

            if (item.availableOnline)
            {
                qualityInteger = 1;
            }else {

                qualityInteger = 0;
            }

            //holder.walmartBuyNowButton.se
//            holder.walmartBuyNowButton.addItem(walmartItem, qualityInteger.intValue());

            if (qualityInteger == 0) {

                String zero_price = "$" + "0.00";

                holder.walmartPriceTextView.setText(zero_price);

                holder.walmartPriceTextView.setTextColor(Color.RED);

            }else {

                String s;
                Float itemFloat = Float.valueOf(item.salePrice);

                String price = "$" + String.format("%.2f", itemFloat.floatValue());

                holder.walmartPriceTextView.setText(price);
            }

            Log.d("WalmartImage", " " + item.largeImage);

            Picasso.get().load(item.largeImage).into(holder.walmartImageView);

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
