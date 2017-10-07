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

public class Logged_In extends Activity implements View.OnClickListener{

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
        setContentView(R.layout.activity_logged__in);

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
        final String message2 = bundle.getString("message2");
        final String message3 = bundle.getString("message3");
        final String message4 = bundle.getString("message4");

        if (message3==null) textView.setText("'" + message2 + "'");
        else textView.setText("'" + "Advanced Search" + "'");

        imglist = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage2);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading all Uploaded Items...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(ItemDetail.FB_DATABASE_PATH);


        final String finalMessage = message2;
        final String finalMessage2= message3;
        final String finalMessage3= message4;
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                if(finalMessage2==null) {
                    //Fetch image data from firebase database
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //ImageUpload class require default constructor
                        ImageUpload img = snapshot.getValue(ImageUpload.class);

                        if (img.getName().toLowerCase().contains(finalMessage.toLowerCase()) && !img.getEmail().equals(MyApplication.email)) {

                            imglist.add(img);

                        }
                    }
                }

                else{

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //ImageUpload class require default constructor
                        ImageUpload img = snapshot.getValue(ImageUpload.class);

                        String numberOnly= img.getRent().replaceAll("[^0-9]", "");
                        int foo = Integer.parseInt(numberOnly);

                        if (img.getName().toLowerCase().contains(finalMessage.toLowerCase()) && !img.getEmail().equals(MyApplication.email) && img.getLocation().toLowerCase().equals(finalMessage2.toLowerCase())) {
                            if(finalMessage.equals("Others")){
                                if(!img.getName().toLowerCase().contains("chair") && !img.getName().toLowerCase().contains("table") && !img.getName().toLowerCase().contains("dining table") && !img.getName().toLowerCase().contains("sofa set") && !img.getName().toLowerCase().contains("bed") )
                                {
                                    if (finalMessage3.equals("100 to 500")) {

                                        if(foo >= 100  && foo <=500 ) imglist.add(img);

                                    } else if (finalMessage3.equals("500 to 1000")) {

                                        if(foo >= 500  && foo <=1000 ) imglist.add(img);

                                    } else if (finalMessage3.equals("1000 and above")) {

                                        if(foo >= 1000) imglist.add(img);
                                    }
                                }
                            }
                            else {
                                if (finalMessage3.equals("100 to 500")) {

                                    if(foo >= 100  && foo <=500 ) imglist.add(img);

                                } else if (finalMessage3.equals("500 to 1000")) {

                                    if(foo >= 500  && foo <=1000 ) imglist.add(img);

                                } else if (finalMessage3.equals("1000 and above")) {

                                    if(foo >= 1000) imglist.add(img);
                                }
                            }

                        }
                    }

                }

                adapter = new ItemListAdapter(Logged_In.this,R.layout.item_furniture_list,imglist);

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
                Intent i = new Intent(Logged_In.this, MainActivity.class);
                i.putExtra("Editing", imageUpload);
                startActivity(i);
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view == Advanced_search){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Logged_In.this, AdvancedSearch.class)); // DOESN'T WORK
        }
        if (view ==Search){

            String message2 = editText.getText().toString();
            if (message2.isEmpty()){
                Toast.makeText(this, "Please enter keywords to search for!", Toast.LENGTH_SHORT).show();
                //return;
            }
            else {
                //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Logged_In.this, Logged_In.class);
                intent.putExtra("message2", message2);
                startActivity(intent);
            }
            // DOESN'T WORK
        }
        if (view == Dropdown){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Logged_In.this, DropDownMenu.class)); // DOESN'T WORK
        }
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(Logged_In.this,HomeLoggedIn.class));
    }
}
