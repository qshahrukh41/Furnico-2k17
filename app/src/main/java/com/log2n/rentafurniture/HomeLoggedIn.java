package com.log2n.rentafurniture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class HomeLoggedIn extends Activity implements View.OnClickListener {

    private Button Search;
    private TextView AdvancedSearch;
    private EditText editText;
    ViewPager viewPager;
    customSwipe customSwip;
    private Button Dropdown;
    private int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home_logged_in);

        Dropdown = (Button) findViewById(R.id.button22);


        editText = (EditText) findViewById(R.id.editTextHomeSearch);

        Search = (Button) findViewById(R.id.imageViewSearch);

        Search.setOnClickListener(this);

        AdvancedSearch = (TextView) findViewById(R.id.textView17);

        AdvancedSearch.setOnClickListener(this);
        Dropdown.setOnClickListener(this);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        customSwip = new customSwipe(this);
        viewPager.setAdapter(customSwip);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,3000);

    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run(){
            HomeLoggedIn.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    }
                    else if(viewPager.getCurrentItem()==1){
                        viewPager.setCurrentItem(2);
                    }
                    else if(viewPager.getCurrentItem()==2){
                        viewPager.setCurrentItem(3);
                    }
                    else if(viewPager.getCurrentItem()==3){
                        viewPager.setCurrentItem(4);
                    }
                    else if(viewPager.getCurrentItem()==4){
                        viewPager.setCurrentItem(0);
                    }

                }
            });
        }

    }

    @Override
    public void onClick(View view) {

        if (view == Search){
            String message = editText.getText().toString();
            if (message.isEmpty()){
                Toast.makeText(this, "Please enter keywords to search for!", Toast.LENGTH_SHORT).show();
                //return;
            }
            else {
                Intent intent = new Intent(HomeLoggedIn.this, Logged_In.class);
                intent.putExtra("message2", message);
                startActivity(intent);
            }
        }
        if(view == AdvancedSearch)
        {
            startActivity(new Intent(HomeLoggedIn.this, AdvancedSearch.class));
        }
        if(view==Dropdown)
        {
            startActivity(new Intent(HomeLoggedIn.this, DropDownMenu.class));
        }
    }

    @Override
    public void onBackPressed()
    {
        if(flag==0)
        {
            Toast.makeText(this, "Press back again to exit" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        else if(flag==1)
        {
            finishAffinity();
        }
    }
}
