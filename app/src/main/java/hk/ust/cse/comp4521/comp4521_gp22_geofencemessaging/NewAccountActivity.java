package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DialogFragment;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class NewAccountActivity extends AppCompatActivity
        implements ChangePasswordDialog.changePasswordDialogListener,
                    ChangeEmailDialog.ChangeEmailDialogListener,
                    SendEmailDialog.SendEmailDialogListener,
                    ChangeUserNameDialog.ChangeUserNameDialogListener{

    private Button changeEmail, changePassword, sendEmail, remove, signOut;
    private ImageButton map,publicMsg,privateMsg,add,me;
    private ImageButton changeUserName;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser user;

    private TextView userName;

    private double longitude;
    private double latitude;


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Display user name
        userName = (TextView) findViewById(R.id.DisplayName);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(NewAccountActivity.this, LoginActivity.class));
                    finish();
                }else{

                }
            }
        };

        userName.setText(user.getDisplayName());

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

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewAccountActivity.this, MapActivity.class);
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
                Intent intent = new Intent(NewAccountActivity.this, PublicMsgActivity.class);
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
                Intent intent = new Intent(NewAccountActivity.this, PrivateMsgActivity.class);
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
                Intent intent = new Intent(NewAccountActivity.this, NewActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("lng",longitude);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

//        me.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NewAccountActivity.this, AccountActivity.class);
//                intent.putExtra("lat",latitude);
//                intent.putExtra("lng",longitude);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//                finish();
//            }
//        });



        /*************
         * content part
         **************/
        changeUserName =(ImageButton) findViewById(R.id.EditName);
        changePassword =(Button) findViewById(R.id.changePW);
        changeEmail= (Button) findViewById(R.id.changeMail);
        sendEmail = (Button) findViewById(R.id.resetemail);
        remove = (Button) findViewById(R.id.deleteAcc);
        signOut = (Button) findViewById(R.id.signout);


        changeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeUsernameDialog();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog();
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeEmailDialog();
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSendEmailDialog();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(NewAccountActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(NewAccountActivity.this, signupActivity.class));
                                        finish();

                                    } else {
                                        Toast.makeText(NewAccountActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }


    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }



    public void showChangePasswordDialog(){
        DialogFragment dialog = new ChangePasswordDialog();
        dialog.show(getFragmentManager(),"ChangePasswordDialog");
    }

    @Override
    public void onReturnValue(String oldPassword, String newPassword) {


        if (user != null && !newPassword.equals("")) {
            if (newPassword.length() < 6) {
                Toast.makeText(NewAccountActivity.this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();

            } else {
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(NewAccountActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                    signOut();

                                } else {
                                    Toast.makeText(NewAccountActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        } else if (newPassword.equals("")) {
            Toast.makeText(NewAccountActivity.this, "Please enter a minimum 6 characters password!", Toast.LENGTH_SHORT).show();
        }
    }

    public void showChangeEmailDialog(){
        DialogFragment dialog = new ChangeEmailDialog();
        dialog.show(getFragmentManager(),"ChangeEmailDialog");
    }

    @Override
    public void onReturnEmail(String email) {
        if (user != null && !email.equals("")) {
            user.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(NewAccountActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                signOut();

                            } else {
                                Toast.makeText(NewAccountActivity.this, "Failed to update email! Please enter a valid email", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        } else if (email.equals("")) {
            Toast.makeText(NewAccountActivity.this, "Please enter Email!", Toast.LENGTH_SHORT).show();

        }

    }
    public void showSendEmailDialog(){
        DialogFragment dialog = new SendEmailDialog();
        dialog.show(getFragmentManager(),"SendEmailDialog");
    }

    @Override
    public void onReturnSendEmail(String email) {
        if (!email.equals("")) {
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(NewAccountActivity.this, "Reset password email is sent!", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(NewAccountActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        } else {
            Toast.makeText(NewAccountActivity.this, "Please enter Email!", Toast.LENGTH_SHORT).show();

        }
    }

    public void showChangeUsernameDialog(){
        DialogFragment dialog = new ChangeUserNameDialog();
        dialog.show(getFragmentManager(),"ChangeUserNameDialog");
    }

    @Override
    public void onReturnUsername(final String username) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //if successfully registered
                            Toast.makeText(NewAccountActivity.this, "Your name" + username+" is updated", Toast.LENGTH_SHORT).show();
                        }                    }
                });


    }
}
