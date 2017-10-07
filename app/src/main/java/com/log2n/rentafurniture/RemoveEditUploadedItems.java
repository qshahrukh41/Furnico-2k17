package com.log2n.rentafurniture;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RemoveEditUploadedItems extends Activity implements View.OnClickListener{

    private EditText Name,Rent,Address;
    private Button Edit,Save,Delete;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    private String id;
    private String name,url,location,rent,email,upload_id,image_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remove_edit_uploaded_items);


        Rent = (EditText) findViewById(R.id.textView60);
        Name = (EditText) findViewById(R.id.textView59);
        Address = (EditText) findViewById(R.id.textViewAddress);

        Edit = (Button) findViewById(R.id.button14);
        Save = (Button) findViewById(R.id.button21);
        Delete = (Button) findViewById(R.id.button20);
        imageView = (ImageView) findViewById(R.id.imageView11);

        Edit.setOnClickListener(this);
        Save.setOnClickListener(this);
        Delete.setOnClickListener(this);

        final ImageUpload model = (ImageUpload) getIntent().getSerializableExtra("Editing2");

        Name.setText(model.getName());
        Rent.setText(model.getRent());
        Address.setText(model.getLocation());

        Picasso.with(getApplicationContext()).load(model.getUrl()).fit().into(imageView);

        Name.setEnabled(false);
        Address.setEnabled(false);
        Rent.setEnabled(false);

        //Edit.setVisibility(View.INVISIBLE);
        Save.setVisibility(View.INVISIBLE);
        //Delete.setVisibility(View.INVISIBLE);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(ItemDetail.FB_DATABASE_PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //ImageUpload class require default constructor
                    ImageUpload imageUpload= snapshot.getValue(ImageUpload.class);
                    if(imageUpload.getEmail().equals(MyApplication.email) && imageUpload.image_id.equals(model.getImage_id())) {
                        name=imageUpload.getName();
                        url=imageUpload.getUrl();
                        location=imageUpload.getLocation();
                        rent=imageUpload.getRent();
                        email=imageUpload.getEmail();
                        upload_id=imageUpload.getUpload_id();
                        image_id=imageUpload.getImage_id();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view==Edit)
        {
            Delete.setVisibility(View.INVISIBLE);
            Edit.setVisibility(View.INVISIBLE);
            Save.setVisibility(View.VISIBLE);

            Name.setEnabled(true);
            Address.setEnabled(true);
            Rent.setEnabled(true);
        }

        if(view==Save){
            Delete.setVisibility(View.VISIBLE);
            Edit.setVisibility(View.VISIBLE);
            Save.setVisibility(View.INVISIBLE);

            Name.setEnabled(false);
            Address.setEnabled(false);
            Rent.setEnabled(false);

            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(ItemDetail.FB_DATABASE_PATH).child(image_id);
            String name2 = Name.getText().toString().trim();
            String rent2 = Rent.getText().toString().trim();
            String address = Address.getText().toString().trim();

            ImageUpload imageUpload = new ImageUpload(name2,url,address,rent2,email,upload_id,image_id);

            databaseRef.setValue(imageUpload);

            Toast.makeText(this, "Item Info Updated!", Toast.LENGTH_SHORT).show();
        }
        if(view==Delete){

            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(ItemDetail.FB_DATABASE_PATH).child(image_id);

            databaseRef.removeValue();

            Toast.makeText(this, "Item Deleted!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(RemoveEditUploadedItems.this, UploadedItems.class));
        }

    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(RemoveEditUploadedItems.this, UploadedItems.class));
    }
}
