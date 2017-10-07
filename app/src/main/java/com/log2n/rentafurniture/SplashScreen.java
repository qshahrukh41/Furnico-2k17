package com.log2n.rentafurniture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Window;
import android.widget.TextView;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT=2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        String tempString="Rent A Furniture";
        TextView text=(TextView)findViewById(R.id.textView10);
        SpannableString spanString = new SpannableString(tempString);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, spanString.length(), 0);
        //spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
        text.setText(spanString);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent = new Intent(SplashScreen.this,HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);


    }
}
