package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewActivity extends AppCompatActivity {

    private ImageButton map,publicMsg,privateMsg,add,me;

    private EditText inputSubject,inputMsg,inputToUser;
    private RadioButton anonymous,isPrivate;
    private Button share;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    private double longitude;
    private double latitude;


    String uid;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        uid =user.getUid();
        username = user.getDisplayName();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(NewActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

       setupFirebase();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //get the location from start intent
        longitude = getIntent().getExtras().getDouble("lng");
        latitude = getIntent().getExtras().getDouble("lat");

        Toast.makeText(this, String.valueOf(latitude )+" "+ String.valueOf(longitude), Toast.LENGTH_LONG).show();

        /***************
         * change of current activity
         ***************/
        map = (ImageButton) findViewById(R.id.mapBtn);
        publicMsg = (ImageButton) findViewById(R.id.groupChatBtn);
        privateMsg = (ImageButton) findViewById(R.id.privateBtn);
        add = (ImageButton) findViewById(R.id.addBtn);
        me = (ImageButton) findViewById(R.id.meBtn);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewActivity.this, MapActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("lng",longitude);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        publicMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewActivity.this, PublicMsgActivity.class);
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
                Intent intent = new Intent(NewActivity.this, PrivateMsgActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("lng",longitude);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NewActivity.this, NewActivity.class);
//                intent.putExtra("lat",latitude);
//                intent.putExtra("lng",longitude);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//                finish();
//            }
//        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewActivity.this, NewAccountActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("lng",longitude);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });



        //Process data
        inputSubject = (EditText) findViewById(R.id.subject);
        inputMsg = (EditText) findViewById(R.id.msg);
        share = (Button) findViewById(R.id.send);
        anonymous = (RadioButton) findViewById(R.id.anonymous);
        isPrivate = (RadioButton) findViewById(R.id.button_private);
        inputToUser = (EditText) findViewById(R.id.send_to);

        //TO-DO: add to firebase
        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String subject = inputSubject.getText().toString();
                String message = inputMsg.getText().toString();
                String getName = inputToUser.getText().toString();

                //generate timestamp
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                if (TextUtils.isEmpty(subject)) {
                    inputSubject.setError("Required");
                    //Toast.makeText(getApplicationContext(), "Please enter subject!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(message)) {
                    inputMsg.setError("Required");
                    Toast.makeText(getApplicationContext(), "Please enter content!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(isPrivate.isChecked()){
                    String key = mDatabase.child("private").push().getKey();
                    mDatabase.child("private").child(key).child("name").setValue(username);
                    mDatabase.child("private").child(key).child("topic").setValue(subject);
                    mDatabase.child("private").child(key).child("content").setValue(message);
                   // mDatabase.child("private").child(key).child("longitude").setValue(latitude);
                   // mDatabase.child("private").child(key).child("latitude").setValue(longitude);
                    mDatabase.child("private").child(key).child("uid").setValue(uid);
                    mDatabase.child("private").child(key).child("_tags").setValue(getName);
                    mDatabase.child("private").child(key).child("_geoloc").child("lat").setValue(latitude);
                    mDatabase.child("private").child(key).child("_geoloc").child("lng").setValue(longitude);

                    Toast.makeText(getApplicationContext(), "Private Posted!", Toast.LENGTH_SHORT).show();


                }else {


                    //if set the msg as anonymous
                    if (anonymous.isChecked()) {
                        username = "Anonymous";
                    }
                    String key = mDatabase.child("public").push().getKey();
                    mDatabase.child("public").child(key).child("name").setValue(username);
                    mDatabase.child("public").child(key).child("topic").setValue(subject);
                    mDatabase.child("public").child(key).child("content").setValue(message);
                    mDatabase.child("public").child(key).child("longitude").setValue(latitude);
                    mDatabase.child("public").child(key).child("latitude").setValue(longitude);
                    mDatabase.child("public").child(key).child("uid").setValue(uid);
                    mDatabase.child("public").child(key).child("_geoloc").child("lat").setValue(latitude);
                    mDatabase.child("public").child(key).child("_geoloc").child("lng").setValue(longitude);

                    Toast.makeText(getApplicationContext(), "Public Posted!", Toast.LENGTH_SHORT).show();

                }



                Intent intent = new Intent(NewActivity.this, PublicMsgActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();


            }
        });


    }


    private void setupFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }



}
