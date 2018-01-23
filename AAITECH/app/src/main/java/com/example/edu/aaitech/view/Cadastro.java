package com.example.edu.aaitech.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edu.aaitech.R;
import com.example.edu.aaitech.configure.configureFirebase;
import com.example.edu.aaitech.model.Token;
import com.example.edu.aaitech.model.cliente;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Cadastro extends AppCompatActivity {

    private GoogleMap mMap;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    cliente c = new cliente();
    Token t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText nome = (EditText) findViewById(R.id.id_cadastro_nome);
        final EditText email = (EditText) findViewById(R.id.id_cadastro_email);
        final EditText senha = (EditText) findViewById(R.id.id_cadastro_senha);
        final EditText nomeEstabalecimento = (EditText) findViewById(R.id.id_cadastro_nomeEst);
        final EditText endereco = (EditText) findViewById(R.id.id_cadastro_endereco);
        final EditText telefone = (EditText) findViewById(R.id.id_cadastro_telefone);
        final EditText latitude = (EditText) findViewById(R.id.latitude);
        final EditText longitude = (EditText) findViewById(R.id.longitude);
        final EditText chave = (EditText) findViewById(R.id.id_chave);


        final DatabaseReference databaseReference = configureFirebase.getDatabaseReference();
        databaseReference.child("ACAI").child("Token").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                t = dataSnapshot.getValue(Token.class);


                final Button cadastar = (Button) findViewById(R.id.id_btn_cadastar);
                cadastar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        int sizeNome = nome.getText().length();
                        int sizeEmail = email.getText().length();
                        int sizeSenha = senha.getText().length();
                        int sizeNomeEsta = nomeEstabalecimento.getText().length();
                        int sizeEnd = endereco.getText().length();
                        int sizeTel = telefone.getText().length();
                        int sizeLat = latitude.getText().length();
                        int sizeLong = longitude.getText().length();
                        int sizeChav = chave.getText().length();



                        if (!(sizeNome == 0 || sizeEmail == 0 || sizeSenha == 0 || sizeNomeEsta ==0 || sizeEnd == 0 || sizeTel == 0 || sizeLat == 0 || sizeLong == 0 || sizeChav == 0)) {

                            c.setNome(nome.getText().toString());
                            c.setEmail(email.getText().toString());
                            c.setSenha(senha.getText().toString());
                            c.setNomeDoEstabelecimento(nomeEstabalecimento.getText().toString());
                            c.setEndereco(endereco.getText().toString());
                            c.setTelefone(Integer.valueOf(telefone.getText().toString()));
                            c.setLatitute(Double.valueOf(latitude.getText().toString()));
                            c.setLongitude(Double.valueOf(longitude.getText().toString()));
                            c.setChave(chave.getText().toString());


                            if (c.getChave().equals(t.getChave())) {
                                realizarCadastro();
                            } else {
                                Toast.makeText(Cadastro.this, "Chave invalida", Toast.LENGTH_LONG).show();
                            }

                        }else{
                            Toast.makeText(Cadastro.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                        }



                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void realizarCadastro() {

        firebaseAuth = configureFirebase.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(c.getEmail(), c.getSenha())
                .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Cadastro.this, "Sucesso ao cadastrar!!", Toast.LENGTH_LONG).show();

                            firebaseUser = task.getResult().getUser();
                            c.setId(firebaseUser.getUid());
                            c.salvarClienete();
                            c.salvarLocal();


                            AbrirTelaPrincipal();

                        } else {

                            String erro = "";

                            try {
                                throw task.getException();

                            } catch (FirebaseAuthWeakPasswordException e) {
                                erro = " A senha deve ter pelo menos seis caracteres: user123";

                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erro = " Digite um e-mail valido: 'exemple@email.com'";
                            } catch (FirebaseAuthUserCollisionException e) {

                                erro = " Esse e-mail já esta em uso no App!!!";
                            } catch (Exception e) {
                                e.printStackTrace();
                                erro = " Erro ao Cadastar";
                            }

                            Toast.makeText(Cadastro.this, "Opa, algo deu errado! " + erro, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void AbrirTelaPrincipal() {
        Intent intent = new Intent(Cadastro.this, Login.class);
        startActivity(intent);
        finish();
    }


}



