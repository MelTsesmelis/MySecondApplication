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

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ContentResolver contentResolver = getContentResolver();
                        Uri uri = Uri.parse("content://com.example.geofencing/CORDINATES");
                        Cursor cursor = contentResolver.query(
                                uri,
                                null,
                                null,
                                null,
                                null);

                        if (cursor == null) {
                            //nothing taken by database so do nothing
                            Log.d("Null Cursor","Nothing found in DB");
                        } else {
                            if (cursor.moveToFirst()) {
                                while (cursor.moveToNext()) {
                                    Log.d("Cursor", cursor.getString(0));
                                }
                            } else {
                                Log.d("ERROR", "nothing found in this cursor");
                            }
                        }
                    }
                });
                thread.start();
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Eleusis and move the camera
        LatLng eleusis = new LatLng(38.041275, 23.541812);
        mMap.addMarker(new MarkerOptions().position(eleusis).title("Marker in Eleusis"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eleusis));
    }
}