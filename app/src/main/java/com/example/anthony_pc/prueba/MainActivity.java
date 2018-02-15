package com.example.anthony_pc.prueba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    DatabaseReference mRootChild = mDatabaseReference.child("nombre");

    
    private StorageReference mStorage;
    private ImageView mImgView;
    private ProgressDialog mProgressDialog;
    private TextView txtView1;
    private Button btnSubir;
    private static final int GALLERY_INTENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStorage = FirebaseStorage.getInstance().getReference();
        txtView1 = (TextView) findViewById(R.id.txtView1);
        mImgView = (ImageView) findViewById(R.id.imgView);
        btnSubir = (Button) findViewById(R.id.btnSubir);
        mProgressDialog = new ProgressDialog(this);

        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setTitle("Subiendo...");
            mProgressDialog.setMessage("Subiendo foto a firebase..");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            Uri uri = data.getData();

            StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.dismiss();
                    Uri descargarFoto = taskSnapshot.getDownloadUrl();
                    Glide.with(MainActivity.this)
                            .load(descargarFoto)
                            .fitCenter()
                    .centerCrop().into(mImgView);

                    Toast.makeText(MainActivity.this, "Imagen cargada tuanis", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
