package com.example.andriginting.pisang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andriginting.pisang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private static final String TAG = "emailPasswordLogin";
    private EditText email,password;
    private Button login;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        email =(EditText)findViewById(R.id.input_email);
        password=(EditText)findViewById(R.id.input_password);
        login = (Button)findViewById(R.id.btn_login_inUser);

        Button btn = (Button) findViewById(R.id.btn_login_inUser);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,MainMenu.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth =FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //signIn(email.getText().toString(),password.getText().toString());
                Intent login = new Intent(Login.this,MainMenu.class);
                startActivity(login);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    Log.d(TAG,"onAuthStateChanged:Signed_in: "+user.getUid());
                }else{
                    Log.d(TAG,"onAuthStateChanged:signed_out");
                }
            }
        };


    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT);
                        }else{
                            Intent intent = new Intent(getApplicationContext(),MainMenu.class);
                            startActivity(intent);
                        }
                    }
                });
    }

}
