package com.example.anthony_pc.prueba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    DatabaseReference mRootChild = mDatabaseReference.child("nombre");

    private TextView txtView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView1 = (TextView) findViewById(R.id.txtView1);

    }

    @Override
    protected void onStart() {
        super.onStart();


        mRootChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String texto = dataSnapshot.getValue().toString();
                txtView1.setText(texto);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
