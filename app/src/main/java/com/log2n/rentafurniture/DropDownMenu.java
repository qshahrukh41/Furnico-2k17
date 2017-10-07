package com.log2n.rentafurniture;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class DropDownMenu extends Activity implements View.OnClickListener{

    private Button logout,backhome,manageProfile,uploadItem,myItems;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_drop_down_menu);

        logout = (Button) findViewById(R.id.button8);
        logout.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        manageProfile = (Button) findViewById(R.id.button12);
        manageProfile.setOnClickListener(this);

        backhome = (Button) findViewById(R.id.button13);
        backhome.setOnClickListener(this);



        uploadItem = (Button) findViewById(R.id.button11);
        uploadItem.setOnClickListener(this);

        myItems = (Button) findViewById(R.id.button17);
        myItems.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == logout){
            mAuth.getInstance().signOut();

            Intent intent = (new Intent(DropDownMenu.this, HomeActivity.class));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);

            startActivity(intent);

        }
        if (view == manageProfile){
            startActivity(new Intent(DropDownMenu.this,AccountDetail.class));
        }


        if (view == myItems){
            startActivity(new Intent(DropDownMenu.this,UploadedItems.class));
        }


        if (view == uploadItem){
            startActivity(new Intent(DropDownMenu.this,ItemDetail.class));
        }

        if(view == backhome)
        {
            startActivity(new Intent(DropDownMenu.this,HomeLoggedIn.class));
        }
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(DropDownMenu.this,HomeLoggedIn.class));
    }
}
