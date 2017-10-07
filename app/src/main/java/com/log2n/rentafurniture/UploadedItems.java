package com.log2n.rentafurniture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UploadedItems extends Activity {

    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imglist;
    private ListView lv;
    private ItemListAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_uploaded_items);

        imglist = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImageUploads);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading Your all Uploaded Items...");
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
                        if (img.getEmail().equals(MyApplication.email)) {
                            imglist.add(img);
                    }
                }

                adapter = new ItemListAdapter(UploadedItems.this,R.layout.item_furniture_list,imglist);

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
                Intent i = new Intent(UploadedItems.this, RemoveEditUploadedItems.class);
                i.putExtra("Editing2", imageUpload);
                startActivity(i);
            }
        });


    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(UploadedItems.this,DropDownMenu.class));
    }
}
