package co.uglytruth.snazzswag.walmart.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import co.uglytruth.snazzswag.R;


public class ShoppingBuyButton extends LinearLayout {

    Button shoppingBuyButton;

    public ShoppingBuyButton(Context context) {
        super(context);
    }

    public ShoppingBuyButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try {
            layoutInflater.inflate(R.layout.shopping_button, this, true);
            setShoppingBuyButton((Button) findViewById(R.id.shopping_button));

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void setShoppingBuyButton(Button shoppingBuyButton) {
        this.shoppingBuyButton = shoppingBuyButton;
    }

    public Button getShoppingBuyButton() {
        return shoppingBuyButton;
    }
}
