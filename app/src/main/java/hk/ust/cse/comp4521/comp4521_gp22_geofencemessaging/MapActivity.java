package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//TO-DO here just read topic from firebase and display it on the map
//If have time can implement map clusterManager too

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageButton map,publicMsg,privateMsg,add,me;
    private static final String TAG = MapActivity.class.getSimpleName();

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("");


    private GoogleMap mMap;

    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //      .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);

        // We need to ensure that we get access to the map object. We need to wait
        // for the map object to be setup. This requires the implementation of an
        // async callback method onMapReady() where we get the reference to the map.
        setupMap();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //get the location from start intent
        longitude = getIntent().getExtras().getDouble("lng");
        latitude = getIntent().getExtras().getDouble("lat");

        /***************
         * change of current activity
         ***************/
        map = (ImageButton) findViewById(R.id.mapBtn);
        publicMsg = (ImageButton) findViewById(R.id.groupChatBtn);
        privateMsg = (ImageButton) findViewById(R.id.privateBtn);
        add = (ImageButton) findViewById(R.id.addBtn);
        me = (ImageButton) findViewById(R.id.meBtn);

//        map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MapActivity.this, MapActivity.class);
//                intent.putExtra("lat",latitude);
//                intent.putExtra("lng",longitude);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//                finish();
//            }
//        });

        publicMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, PublicMsgActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("lng",longitude);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        privateMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, PrivateMsgActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("lng",longitude);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, NewActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("lng",longitude);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, NewAccountActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("lng",longitude);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

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

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);


        }

        //TO-DO, Get data from fire base and add marker here
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng hk = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(hk).title("I am here!!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hk));

        mDatabase.child("public").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    //String name = postSnapShot.getKey();

                    String Topic = postSnapShot.child("topic").getValue(String.class);
                    double lat = postSnapShot.child("longitude").getValue(double.class);
                    double lng = postSnapShot.child("latitude").getValue(double.class);

                    mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(Topic));

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();

            }
        });





    }


    @Override
    public void onStop() {

        Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.mapview);
        if (f != null) {
            getFragmentManager().beginTransaction().remove(f).commit();
        }

        super.onStop();
    }
    @Override
    public void onDestroy(){
        Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.mapview);
        if (f != null) {
            getFragmentManager().beginTransaction().remove(f).commit();
        }

        super.onDestroy();

    }

    @Override
    public void onPause(){
        Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.mapview);
        if (f != null) {
            getFragmentManager().beginTransaction().remove(f).commit();
        }
        super.onPause();
    }



    @Override
    protected void onResume() {
        super.onResume();
        setupMap();
    }


    private void setupMap(){
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);
    }
}