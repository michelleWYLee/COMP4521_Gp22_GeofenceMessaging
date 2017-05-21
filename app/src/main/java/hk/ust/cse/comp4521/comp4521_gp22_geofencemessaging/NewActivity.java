package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

    private EditText inputSubject,inputMsg;
    private RadioButton anonymous;
    private Button share;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    private double longitude;
    private double latitude;



    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
                else{
                    uid =user.getUid();
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference();

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

        //TO-DO: add to firebase
        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String subject = inputSubject.getText().toString();
                String message = inputMsg.getText().toString();

                //if set the msg as anonymous
                if(anonymous.isChecked()){

                }


            }
        });


    }



}
