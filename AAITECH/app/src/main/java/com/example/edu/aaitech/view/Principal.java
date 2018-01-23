package com.example.edu.aaitech.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.edu.aaitech.R;
import com.example.edu.aaitech.configure.configureFirebase;
import com.example.edu.aaitech.model.cliente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class Principal extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference = configureFirebase.getDatabaseReference();
    String referencia = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();

        String id = uid.getUid();

        databaseReference.child("ACAI").child("clientes").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                cliente c = dataSnapshot.getValue(cliente.class);

                referencia = c.getNomeDoEstabelecimento();


                Button definifStatus = (Button) findViewById(R.id.btnStatus);
                definifStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText status = (EditText) findViewById(R.id.status);
                        databaseReference.child("ACAI").child("locais").child(referencia).child("status").setValue(status.getText().toString());
                    }
                });


                Button definifPreco = (Button) findViewById(R.id.btnPreco);
                definifPreco.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText preco = (EditText) findViewById(R.id.preco);
                        databaseReference.child("ACAI").child("locais").child(referencia).child("preco").setValue(preco.getText().toString());
                    }
                });


                Button irMapa = (Button) findViewById(R.id.btnMapa);
                irMapa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Principal.this, MapsActivity.class);
                        startActivity(intent);

                    }
                });


                TextView sair = (TextView) findViewById(R.id.sair);
                sair.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        firebaseAuth = configureFirebase.getFirebaseAuth();
                        firebaseAuth.signOut();
                        finish();
                        Intent intent = new Intent(Principal.this, Login.class);
                        startActivity(intent);

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
