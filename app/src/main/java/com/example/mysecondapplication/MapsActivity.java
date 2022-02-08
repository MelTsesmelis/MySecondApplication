package com.example.mysecondapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.filament.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mysecondapplication.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //arxikopoihseis
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static Context context;


    @Override// dhmioyrgia map mesw enous fragment
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



            }

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
    public void onMapReady(GoogleMap googleMap) { // otan tha nai etoimo to map
        mMap = googleMap;

        // Add a marker in Eleusis and move the camera
        LatLng eleusis = new LatLng(38.041275, 23.541812);
        mMap.addMarker(new MarkerOptions().position(eleusis).title("Marker in Eleusis"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eleusis));

                //gia thn epikoinwnia me thn allh efarmogh
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://com.example.geofencing/CORDINATES");

                // dhmiourgia cursora gia na parei tis plhrofories
                Cursor cursor = contentResolver.query(
                        uri,
                        null,
                        null,
                        null,
                        null);

                //an den kataferei na tis parei
                if (cursor == null) {
                    //nothing taken by database so do nothing
                    Log.d("Null Cursor","Nothing found in DB");
                    LatLng location1 = new LatLng(38.0412,23.5420);
                    mMap.addMarker(new MarkerOptions().position(location1));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location1));

                    LatLng location2= new LatLng(38.0413 ,23.5420);
                    mMap.addMarker(new MarkerOptions().position(location2));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location2));

                    LatLng location3 = new LatLng(38.0414, 23.5420);
                    mMap.addMarker(new MarkerOptions().position(location3));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location3));

                    LatLng location4 = new LatLng(38.0415, 23.5420);
                    mMap.addMarker(new MarkerOptions().position(location4));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location4));
                } else {//an tis pairnei
                    if (cursor.moveToFirst()) { //kai exei mesa data
                        while (cursor.moveToNext()) {
                           // Log.d("Cursor success", cursor.getString(0));
                            LatLng locations = new LatLng(cursor.getDouble(1),cursor.getDouble(2));
                            mMap.addMarker(new MarkerOptions().position(locations));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(locations)); //metakinei thn kamera sto shmeio auto

                        }
                    } else { //kai den exei mesa da
                        Log.d("ERROR", "nothing found in this cursor");
                    }
                }
            }
}