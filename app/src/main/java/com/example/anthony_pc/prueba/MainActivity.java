package com.example.anthony_pc.prueba;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Prueba";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button btnIngresar;
    private EditText txtCorreo,txtPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtPassword = (EditText) findViewById(R.id.txtPassw);

        mAuth = FirebaseAuth.getInstance();

        btnIngresar = (Button) findViewById(R.id.btnIngresar);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!= null){
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));

                }else{
                    //Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

/*
    private void loginUserUnkn() {
        String email = txtCorreo.getText().toString();
        String password = txtPassword.getText().toString();
        Log.d("user",email);
        Log.d("pass",password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

*/
    private void loginUser() {
        String email = txtCorreo.getText().toString();
        String password = txtPassword.getText().toString();

        Log.d("user",email);
        Log.d("pass",password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            /*
                            Toast.makeText(MainActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();*/

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success",task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } /*else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }*/

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
    }

}
