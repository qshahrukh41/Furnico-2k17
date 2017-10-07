package com.log2n.rentafurniture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



public class MainActivity extends Activity {

    private TextView name, title, rent, contact, address;
    private ImageView image;
    private DatabaseReference mDatabaseRef;
    private SensorManager sm;
    private static String mobile;

    private float accelVal;
    private float accelLast;
    private float shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        accelVal = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        name = (TextView) findViewById(R.id.textView46);
        title = (TextView) findViewById(R.id.textView59);
        rent = (TextView) findViewById(R.id.textView60);
        address = (TextView) findViewById(R.id.textViewAddress);
        contact = (TextView) findViewById(R.id.textView47);
        image = (ImageView) findViewById(R.id.imageView11);


        final ImageUpload model = (ImageUpload) getIntent().getSerializableExtra("Editing");

        title.setText(model.getName());
        rent.setText(model.getRent());
        address.setText(model.getLocation());

        Picasso.with(getApplicationContext()).load(model.getUrl()).fit().into(image);

        //Toast.makeText(MainActivity.this,model.getEmail(), Toast.LENGTH_SHORT).show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(SignUpActivity.Data);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    ProfileInfo profileInfo = snapshot.getValue(ProfileInfo.class);
                    if (profileInfo.getEmail().equals(model.getEmail())) {
                        name.setText(profileInfo.getName());
                        contact.setText(profileInfo.getContact_no());
                        mobile=profileInfo.getContact_no();
                        //Toast.makeText(MainActivity.this,mobile, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            accelLast = accelVal;
            accelVal = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = accelVal - accelLast;
            shake = shake * 0.9f + delta;


            if (shake > 12) {
                //Toast.makeText(MainActivity.this, mobile, Toast.LENGTH_SHORT).show();
                String uri = "tel:" + mobile ;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);


            }






        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
