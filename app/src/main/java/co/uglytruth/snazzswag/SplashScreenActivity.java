package co.uglytruth.snazzswag;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView title;
    private ScheduledExecutorService scheduledExecutorService = null;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.create_view();

    }

    private void sendToMainActivity(){
        intent = new Intent(this, MainActivity.class);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                scheduledExecutorService.shutdown();

            }
        }, 3, 30, TimeUnit.SECONDS);
    }

    private void create_view(){


        this.title = (TextView)findViewById(R.id.title_splash_screen);
    }

    private void setTitleAttributes(){
        this.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 45);
        this.title.setText(getResources().getString(R.string.app_name));
        this.title.setTextColor(Color.parseColor("#009688"));
        this.title.setTypeface(Typeface.DEFAULT_BOLD);
        this.title.setShadowLayer(5, 3, 2, Color.DKGRAY);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setTitleAttributes();
        this.sendToMainActivity();
    }
}
