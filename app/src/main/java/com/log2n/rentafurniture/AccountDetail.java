package com.log2n.rentafurniture;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountDetail extends Activity implements View.OnClickListener{

    private EditText Name,Password,Mobile,Address,Email;
    private DatabaseReference mDatabaseRef;
    private String id;
    private Button buttonEdit,buttonSave;
    private String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_account_detail);

        Email = (EditText) findViewById(R.id.textView8);
        Name = (EditText) findViewById(R.id.textView2);
        Password = (EditText) findViewById(R.id.textView9);
        Mobile = (EditText) findViewById(R.id.textView10);
        Address = (EditText) findViewById(R.id.editText10);
        buttonEdit = (Button) findViewById(R.id.button15);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        buttonEdit.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        Email.setEnabled(false);
        Name.setEnabled(false);
        Password.setEnabled(false);
        Mobile.setEnabled(false);
        Address.setEnabled(false);
        buttonSave.setVisibility(View.INVISIBLE);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(SignUpActivity.Data);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //ImageUpload class require default constructor
                    ProfileInfo profileInfo = snapshot.getValue(ProfileInfo.class);
                    if(profileInfo.getEmail().equals(MyApplication.email)) {

                        Email.setText(profileInfo.getEmail());
                        Name.setText(profileInfo.getName());
                        Password.setText(profileInfo.getPassword());
                        Mobile.setText(profileInfo.getContact_no());
                        pass=profileInfo.getPassword();
                        Address.setText(profileInfo.getAddress());
                        id=profileInfo.getUpload_id();
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
        if(view == buttonEdit){
            Name.setEnabled(true);
            Password.setEnabled(true);
            Mobile.setEnabled(true);
            Address.setEnabled(true);
            buttonEdit.setVisibility(view.INVISIBLE);
            buttonSave.setVisibility(view.VISIBLE);



        }
        if(view == buttonSave){
            Email.setEnabled(false);
            Name.setEnabled(false);
            Password.setEnabled(false);
            Mobile.setEnabled(false);
            Address.setEnabled(false);
            buttonEdit.setVisibility(view.VISIBLE);
            buttonSave.setVisibility(view.INVISIBLE);


    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(SignUpActivity.Data).child(id);
            String name = Name.getText().toString().trim();
            String email = Email.getText().toString().trim();
            final String password = Password.getText().toString().trim();
            String mobile = Mobile.getText().toString().trim();
            String address = Address.getText().toString().trim();

            if(!password.equals(pass)) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email, pass);

// Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(AccountDetail.this, "Password Updated!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(AccountDetail.this, "Couldn't Update Password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(AccountDetail.this, "Authenication Failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }


            ProfileInfo profileInfo = new ProfileInfo(name,address,mobile,email,password,id);

            databaseRef.setValue(profileInfo);

            Toast.makeText(this, "Profile Info Updated!", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(AccountDetail.this,DropDownMenu.class));
    }
}
