package com.log2n.rentafurniture;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.LocationListener;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
       /* GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener*/ {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker MUttara,MBaridhara,MGulshan,MDhanmondi,MOldTown,MMirpur;
    public static final int REQUEST_LOCATION_CODE = 99 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

      /*  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }*/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission is granted
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                    else
                    {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
        }
    }*/


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng Dhaka = new LatLng(23.8103,90.4125);

        LatLng Mirpur = new LatLng(23.8223, 90.3654);

        MarkerOptions markerOptions1=new MarkerOptions();
        markerOptions1.position(Mirpur).title("Mirpur").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        MMirpur = mMap.addMarker(markerOptions1);

        LatLng Dhanmodi = new LatLng(23.7465, 90.3760);
        MarkerOptions markerOptions2=new MarkerOptions();
        markerOptions2.position(Dhanmodi).title("Dhanmondi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        MDhanmondi = mMap.addMarker(markerOptions2);

        LatLng Baridhara = new LatLng(23.7999, 90.4208);
        MarkerOptions markerOptions3=new MarkerOptions();
        markerOptions3.position(Baridhara).title("Baridhara").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        MBaridhara = mMap.addMarker(markerOptions3);

        LatLng Gulshan = new LatLng(23.7925, 90.4078);
        MarkerOptions markerOptions4=new MarkerOptions();
        markerOptions4.position(Gulshan).title("Gulshan").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        MGulshan = mMap.addMarker(markerOptions4);

        LatLng Uttara = new LatLng(23.8759, 90.3795);
        MarkerOptions markerOptions5=new MarkerOptions();
        markerOptions5.position(Uttara).title("Uttara").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        MUttara = mMap.addMarker(markerOptions5);


        LatLng Old_Dhaka = new LatLng(23.7099 , 90.4071);
        MarkerOptions markerOptions6=new MarkerOptions();
        markerOptions6.position(Old_Dhaka).title("Old Town").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        MOldTown = mMap.addMarker(markerOptions6);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((Dhaka),11.5f));

        Toast.makeText(this,"Click on the location markers to show furniture items for those locations",Toast.LENGTH_LONG).show();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {

                //Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MapsActivity.this,MapSearch.class);
                intent.putExtra("Location",arg0.getTitle().toString().trim());
                startActivity(intent);

                return true;
            }

        });



        //mMap.animateCamera(CameraUpdateFactory.zoomBy(10));


        // Add a marker in Sydney and move the camera

        //mMap.setMapType(GoogleMap.);
       /* if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {

            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        } */

    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(MapsActivity.this,AdvancedSearch.class));
    }



  /*  protected synchronized void buildGoogleApiClient()
    {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastlocation= location;

        if(currentLocationMarker != null)
        {
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);
        }
    }

    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/
}
