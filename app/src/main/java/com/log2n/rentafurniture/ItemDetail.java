package com.log2n.rentafurniture;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.log2n.rentafurniture.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemDetail extends Activity{

    private EditText txtItemName;
    private Spinner spinnerItemLocation;
    private EditText txtItemRent;
    private ImageView imageView;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef,nDatabaseRef;
    private Uri imgUri;



    public static final String FB_STORAGE_PATH="image/";
    public static final String FB_DATABASE_PATH="image";
    public static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_item_detail);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        imageView= (ImageView) findViewById(R.id.imageView8);


        txtItemName = (EditText) findViewById(R.id.editTextName);
        spinnerItemLocation =(Spinner) findViewById(R.id.spinnerGenres);
        txtItemRent = (EditText) findViewById(R.id.editTextRent);


    }

    public void btnBrowse_Click(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"),REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!=null && data.getData() != null){
            imgUri=data.getData();

            try{
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imageView.setImageBitmap(bm);
            }catch(FileNotFoundException e){
                e.printStackTrace();;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    @SuppressWarnings("VisibleForTests")
    public void btnUpload_Click(View v)
    {
        if(imgUri!=null){
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading Image");
            dialog.show();


            //Get the storage ref
            StorageReference ref= mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() +"." + getImageExt(imgUri));

            //Add file to ref

            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    //ADD CONTACT_NO,FULL_NAME,ADDRESS to Itemdetail database FROM Signup profile attribute database
                    //ImageUpload will have these 3 fields included
                    //email will be passed from log in page

                    /*
                    String contact_no,address,full_name;

                    nDatabaseRef = FirebaseDatabase.getInstance().getReference(SignUpActivity.Data);
                    nDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                //ImageUpload class require default constructor
                                ProfileInfo profileInfo = snapshot.getValue(ProfileInfo.class);
                                if(profileInfo.getEmail().equals(email)) {
                                      contact_no=
                                      address=
                                      full_name=
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    */

                    //Dismiss dialog when success
                    dialog.dismiss();
                    //Display success toast message
                    Toast.makeText(getApplicationContext(), "Image Uploaded",Toast.LENGTH_SHORT).show();

                    //Save image info in Firebase database
                    String uploadId=mDatabaseRef.push().getKey();
                    ImageUpload imageUpload = new ImageUpload(txtItemName.getText().toString(),taskSnapshot.getDownloadUrl().toString(),spinnerItemLocation.getSelectedItem().toString(),txtItemRent.getText().toString(),MyApplication.email,MyApplication.upload_id,uploadId);

                    mDatabaseRef.child(uploadId).setValue(imageUpload);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //Dismiss dialog when error
                            dialog.dismiss();
                            //Display error toast message
                            Toast.makeText(getApplicationContext(), e.getMessage() ,Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //show upload progress

                            double progress = (100 * taskSnapshot.getBytesTransferred() ) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int)progress+"%");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please Select Image" ,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(ItemDetail.this,DropDownMenu.class));
    }
}