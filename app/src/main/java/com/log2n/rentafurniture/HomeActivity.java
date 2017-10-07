package com.log2n.rentafurniture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends Activity implements View.OnClickListener {

    private Button HomeLogInButton;
    private Button HomeSignUpButton;
    private Button Search;
    private EditText editText;
    ViewPager viewPager;
    customSwipe customSwip;
    private FirebaseAuth mAuth;
    private int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            String email = user.getEmail();
            MyApplication.email=email;
            startActivity(new Intent(HomeActivity.this,HomeLoggedIn.class));
        }


        editText = (EditText) findViewById(R.id.editText3);

        Search = (Button) findViewById(R.id.imageView);

        Search.setOnClickListener(this);

        HomeLogInButton = (Button) findViewById(R.id.angry_btn4);

        HomeLogInButton.setOnClickListener(this);

        HomeSignUpButton = (Button) findViewById(R.id.angry_btn3);

        HomeSignUpButton.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        customSwip = new customSwipe(this);
        viewPager.setAdapter(customSwip);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,3000);

    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run(){
            HomeActivity.this.runOnUiThread(new Runnable() {
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
        if(view == HomeLogInButton){
            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
        }
        if (view == HomeSignUpButton){
            startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
        }
        if (view == Search){
            String message = editText.getText().toString();
            if (message.isEmpty()){
                Toast.makeText(this, "Please enter keywords to search for!", Toast.LENGTH_SHORT).show();
                //return;
            }
            else {
                Intent intent = new Intent(HomeActivity.this, search_results.class);
                intent.putExtra("message", message);
                startActivity(intent);
            }
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
