package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.geofire.GeoFire;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private ImageButton map,publicMsg,privateMsg,add,me;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("path/to/geofire");
    GeoFire geoFire = new GeoFire(ref);

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /***************
         * change of current activity
         ***************/
//        map = (ImageButton) findViewById(R.id.mapBtn);
//        publicMsg = (ImageButton) findViewById(R.id.groupChatBtn);
//        privateMsg = (ImageButton) findViewById(R.id.privateBtn);
//        add = (ImageButton) findViewById(R.id.addBtn);
//        me = (ImageButton) findViewById(R.id.meBtn);
//
//        map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MapActivity.this, MapActivity.class));
//                finish();
//            }
//        });
//
//        publicMsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MapActivity.this, PublicMsgActivity.class));
//                finish();
//            }
//        });
//
//        privateMsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MapActivity.this, PrivateMsgActivity.class));
//                finish();
//            }
//        });
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MapActivity.this, NewActivity.class));
//                finish();
//            }
//        });
//
//        me.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MapActivity.this, AccountActivity.class));
//                finish();
//            }
//        });

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
