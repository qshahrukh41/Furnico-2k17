package com.log2n.rentafurniture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class AdvancedSearch extends Activity implements View.OnClickListener{

    Spinner item,rent,location;
    Button Dropdown;
    Button Backtohome,Search,Map;
    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imglist;
    private ListView lv;
    private ItemListAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_advanced_search);

        Map = (Button) findViewById(R.id.buttonMap) ;
        Map.setOnClickListener(this);

        Dropdown = (Button) findViewById(R.id.button10);

        Dropdown.setOnClickListener(this);

        Backtohome = (Button) findViewById(R.id.button4);

        Backtohome.setOnClickListener(this);

        Search = (Button) findViewById(R.id.button3);

        Search.setOnClickListener(this);



        item = (Spinner) findViewById(R.id.spinner);
        rent = (Spinner) findViewById(R.id.spinner3);
        location = (Spinner) findViewById(R.id.spinner2);

        //item.getSelectedItem().toString();
    }

    @Override
    public void onClick(View view) {
        if (view == Dropdown){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            startActivity(new Intent(AdvancedSearch.this, DropDownMenu.class));

        }
        if (view == Backtohome){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            startActivity(new Intent(AdvancedSearch.this, HomeLoggedIn.class)); // DOESN'T WORK
        }
        if (view == Search)
        {
            if(item.getSelectedItem().equals("Select an Item ---"))
            {
                Toast.makeText(AdvancedSearch.this,"Please Select an Item", Toast.LENGTH_LONG).show();
            }
            else if(location.getSelectedItem().equals("Select an Area ---"))
            {
                Toast.makeText(AdvancedSearch.this,"Please Select an Area",Toast.LENGTH_LONG).show();
            }
            else if(rent.getSelectedItem().equals("Select rent range ---"))
            {
                Toast.makeText(AdvancedSearch.this,"Please Select Rent Range",Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(AdvancedSearch.this, Logged_In.class);
                intent.putExtra("message2", item.getSelectedItem().toString());
                intent.putExtra("message3", location.getSelectedItem().toString());
                intent.putExtra("message4", rent.getSelectedItem().toString());
                startActivity(intent);
            }
        }
        if(view == Map)
        {
            startActivity(new Intent(AdvancedSearch.this,MapsActivity.class));
        }

    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        // optional depending on your needs
        startActivity(new Intent(AdvancedSearch.this, HomeLoggedIn.class));

    }
}
