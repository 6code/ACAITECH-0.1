package com.example.edu.aaitech.view;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.edu.aaitech.R;
import com.example.edu.aaitech.model.loadMarkers;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(-3.776872, -49.675535);
        CameraPosition update = new CameraPosition(latLng, 13, 0, 0);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(update), 1000, null);

        markers();

    }


    public void markers() {


        final FirebaseDatabase database = null;

        final DatabaseReference markers = database.getInstance().getReference().child("ACAI").child("locais");

        markers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //datasnapshot captura qualquer  alteraçoes no BD


                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                mMap.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshots) {

                    loadMarkers mdlk = dataSnapshot1.getValue(loadMarkers.class);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(mdlk.getLatitude(), mdlk.getLongitude())).title(mdlk.getName()).snippet("Valor R$:"+mdlk.getPreco() + " Status:"+ mdlk.getStatus()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

