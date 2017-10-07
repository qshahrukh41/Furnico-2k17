package com.log2n.rentafurniture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
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

public class search_results extends Activity implements View.OnClickListener{

    private Button Search;
    private TextView AdvancedSearch;
    private Button LogIn;
    private Button SignUp;
    private ImageView ItemD;
    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imglist;
    private ListView lv;
    private ItemListAdapter adapter;
    private ProgressDialog progressDialog;
    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_results);

        Search = (Button) findViewById(R.id.button1);
        LogIn = (Button) findViewById(R.id.angry_btn4);
        SignUp = (Button) findViewById(R.id.angry_btn3);
        AdvancedSearch = (TextView) findViewById(R.id.textView3);
        textView = (TextView) findViewById(R.id.textView2);
        editText = (EditText) findViewById(R.id.editText3);


        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("message");

        textView.setText("'"+message+"'");


        Search.setOnClickListener(this);
        AdvancedSearch.setOnClickListener(this);
        LogIn.setOnClickListener(this);
        SignUp.setOnClickListener(this);


        imglist = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading all Uploaded Items...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(ItemDetail.FB_DATABASE_PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                //Fetch image data from firebase database
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //ImageUpload class require default constructor
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    if(img.getName().toLowerCase().contains(message.toLowerCase())) {
                        imglist.add(img);
                    }
                }


                //Init adapter
                adapter = new ItemListAdapter(search_results.this,R.layout.item_furniture_list,imglist);

                //set adapter for listview

                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(search_results.this, "Please Login First to See Content", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(search_results.this, SignInActivity.class);
                startActivity(intent);
                */
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == Search){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            String message = editText.getText().toString();
            if (message.isEmpty()){
                Toast.makeText(this, "Please enter keywords to search for!", Toast.LENGTH_SHORT).show();
                //return;
            }
            else {
                //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(search_results.this, search_results.class);
                intent.putExtra("message", message);
                startActivity(intent);
            }// DOESN'T WORK
        }
        if (view == AdvancedSearch){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Login First!!", Toast.LENGTH_SHORT).show();; // DOESN'T WORK
        }
        if (view == LogIn){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            startActivity(new Intent(search_results.this, SignInActivity.class)); // DOESN'T WORK
        }
        if (view == SignUp){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            startActivity(new Intent(search_results.this, SignUpActivity.class)); // DOESN'T WORK

    }
}
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(search_results.this,HomeActivity.class));
    }

}
