package com.log2n.rentafurniture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends Activity implements View.OnClickListener{

    private EditText nEmailField;
    private EditText nPasswordField;
    private TextView textViewSignup;

    private Button mLogInButton;

    private DatabaseReference mDatabaseRef,nDatabaseRef;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();


        nEmailField = (EditText) findViewById(R.id.editTextEmailLogIn);
        nPasswordField = (EditText) findViewById(R.id.editTextPasswordLogIn);
        textViewSignup = (TextView) findViewById(R.id.linkSignUp);

        mLogInButton = (Button) findViewById(R.id.angry_btn2);

        mLogInButton.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }
    @Override
    public void onClick(View view) {
        if(view == mLogInButton){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            startLogIn();
        }
        if (view == textViewSignup){
            //Toast.makeText(SignInActivity.this,"thulla",Toast.LENGTH_LONG).show();
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class)); // DOESN'T WORK
        }
    }




    private void startLogIn() {

        final String email = nEmailField.getText().toString();
        String password = nPasswordField.getText().toString();


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter email!",Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Logging In, Please Wait...");
        progressDialog.show();


            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();

                    if(task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(SignInActivity.this,HomeLoggedIn.class);
                        i.putExtra("Email",email);
                        startActivity(i);
                        MyApplication.email=email;

                        nDatabaseRef = FirebaseDatabase.getInstance().getReference(SignUpActivity.Data);

                        nDatabaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    //ImageUpload class require default constructor
                                    ProfileInfo profileInfo = snapshot.getValue(ProfileInfo.class);
                                    if(profileInfo.getEmail().equals(MyApplication.email)) {

                                        id=profileInfo.getUpload_id();
                                        MyApplication.upload_id=id;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    else {
                        Toast.makeText(SignInActivity.this, "Something went terrible wrong!", Toast.LENGTH_SHORT).show();
                    }

                }
            });


    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(SignInActivity.this,HomeActivity.class));
    }

}
