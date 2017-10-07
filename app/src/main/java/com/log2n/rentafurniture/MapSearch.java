package com.log2n.rentafurniture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapSearch extends Activity implements View.OnClickListener{

    private TextView Advanced_search;
    private Button Search;
    private Button Dropdown;
    private EditText editText;
    private TextView textView;
    private ImageView ItemD;
    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imglist;
    private ListView lv;
    private ItemListAdapter adapter;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map_search);

        editText = (EditText) findViewById(R.id.editText3);

        Advanced_search = (TextView) findViewById(R.id.textView17);

        Dropdown = (Button) findViewById(R.id.button9);

        Search = (Button) findViewById(R.id.imageView);

        //Checkout = (Button) findViewById(R.id.button2);

        textView = (TextView) findViewById(R.id.textView2);

        Advanced_search.setOnClickListener(this);

        Search.setOnClickListener(this);

        Dropdown.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        final String Location= bundle.getString("Location");

        textView.setText("'" + Location + "'");

        imglist = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage2);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading all Uploaded Items...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(ItemDetail.FB_DATABASE_PATH);


        final String finalMessage = Location;

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();


                    //Fetch image data from firebase database
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //ImageUpload class require default constructor
                        ImageUpload img = snapshot.getValue(ImageUpload.class);

                        if (img.getLocation().toLowerCase().equals(finalMessage.toLowerCase()) && img.getEmail()!=MyApplication.email) {

                            imglist.add(img);

                        }
                    }



                adapter = new ItemListAdapter(MapSearch.this,R.layout.item_furniture_list,imglist);

                //set adapter for listview

                lv.setAdapter(adapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageUpload imageUpload = (ImageUpload) adapter.getItem(position);
                Intent i = new Intent(MapSearch.this, MainActivity.class);
                i.putExtra("Editing", imageUpload);
                startActivity(i);
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view == Advanced_search){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            startActivity(new Intent(MapSearch.this, AdvancedSearch.class)); // DOESN'T WORK
        }
        if (view ==Search){

            String message2 = editText.getText().toString();
            if (message2.isEmpty()){
                Toast.makeText(this, "Please enter keywords to search for!", Toast.LENGTH_SHORT).show();
                //return;
            }
            else {
                //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MapSearch.this, Logged_In.class);
                intent.putExtra("message2", message2);
                startActivity(intent);
            }
            // DOESN'T WORK
        }
        if (view == Dropdown){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            startActivity(new Intent(MapSearch.this, DropDownMenu.class)); // DOESN'T WORK
        }
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(MapSearch.this,MapsActivity.class));
    }
}
