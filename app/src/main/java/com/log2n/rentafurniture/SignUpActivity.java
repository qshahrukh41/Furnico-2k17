package com.log2n.rentafurniture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.view.Window;

public class SignUpActivity extends Activity implements View.OnClickListener{

    //define all the view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private TextView textViewSignin;
    private EditText editConfirmPassword;
    private EditText editTextAddress;
    private EditText editTextFullname;
    private EditText editTextContact;
    private DatabaseReference mDatabaseRef;

    private ProgressDialog progressDialog;

    // defining FirebaseAuth
    private FirebaseAuth firebaseAuth;

    public static final String Data="user_profile";
    public static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        //getting the firebae object
        firebaseAuth = FirebaseAuth.getInstance();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Data);

        //initializing all the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editConfirmPassword =(EditText) findViewById(R.id.ConfirmPassword);
        editTextContact=(EditText) findViewById(R.id.editTextContact);
        editTextFullname=(EditText) findViewById(R.id.editTextFullname);
        editTextAddress=(EditText) findViewById(R.id.editTextAddress);
        buttonSignup = (Button) findViewById(R.id.angry_btn);
        textViewSignin = (TextView) findViewById(R.id.linkSignIn);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button sign up
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignup){
            registerUser();
        }
        if (view == textViewSignin){
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        }
    }
    private void registerUser() {

        //getting all the inputs from the user
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirm_password = editConfirmPassword.getText().toString().trim();

        //validation
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.equals(password,confirm_password))
        {
            //Toast.makeText(this, "Okay",Toast.LENGTH_SHORT).show();
            //return;
        }
        else
        {
            Toast.makeText(this, "Passwords Don't Match",Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() <= 5){
            Toast.makeText(this, "Password must be more than 5 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering, Please Wait...");
        progressDialog.show();

        //now we can create the user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            String uploadId=mDatabaseRef.push().getKey();
                            ProfileInfo profileInfo= new ProfileInfo(editTextFullname.getText().toString(),editTextAddress.getText().toString(),editTextContact.getText().toString(),editTextEmail.getText().toString(),editTextPassword.getText().toString(),uploadId);
                            mDatabaseRef.child(uploadId).setValue(profileInfo);
                            progressDialog.hide();
                            return;

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignUpActivity.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Something went terrible wrong!", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                                return;
                            }
                        }
                    }
                });
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
    }
}
