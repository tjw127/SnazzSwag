package co.uglytruth.snazzswag.connectivity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import co.uglytruth.snazzswag.R;

public class Connectivity extends ConstraintLayout {
    public Connectivity(Context context) {
        super(context);
    }

    private TextView connectivity = null;

    public Connectivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try {
            layoutInflater.inflate(R.layout.connectivity, this, true);
            setConnectivity((TextView)findViewById(R.id.connectivity));

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void setConnectivity(TextView connectivity) {
        this.connectivity = connectivity;
    }

    public void setText(String text){
        this.connectivity.setText(text);
    }

    public TextView getConnectivity() {
        return this.connectivity;
    }
}
