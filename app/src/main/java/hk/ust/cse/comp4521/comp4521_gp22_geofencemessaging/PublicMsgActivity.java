package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PublicMsgActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    private ImageButton map,publicMsg,privateMsg,add,me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_msg);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.v("FIREBASE","user status"+user);
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(PublicMsgActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

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
                startActivity(new Intent(PublicMsgActivity.this, MapActivity.class));
                finish();
            }
        });

        publicMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PublicMsgActivity.this, PublicMsgActivity.class));
                finish();
            }
        });

        privateMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PublicMsgActivity.this, PrivateMsgActivity.class));
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PublicMsgActivity.this, NewActivity.class));
                finish();
            }
        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PublicMsgActivity.this, AccountActivity.class));
                finish();
            }
        });
    }
}
