package com.example.edu.aaitech.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.aaitech.R;
import com.example.edu.aaitech.configure.configureFirebase;
import com.example.edu.aaitech.model.cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private cliente usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        VerificaUsuarioLogado();

        final EditText email = (EditText) findViewById(R.id.id_email);
        final EditText senha = (EditText) findViewById(R.id.id_senha);

        Button logar = (Button) findViewById(R.id.id_logar);
        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = new cliente();

                if (!(email.getText().length() == 0 || senha.getText().length() == 0)) {

                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    ValidarLogin();
                } else {

                    Toast.makeText(Login.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                }

            }
        });


        TextView cadastro = (TextView) findViewById(R.id.id_cadastro);
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, Cadastro.class);
                startActivity(intent);
            }
        });


        Button mapa = (Button) findViewById(R.id.id_mapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MapsActivity.class);
                startActivity(intent);
            }
        });


    }


    public void ValidarLogin() {

        firebaseAuth = configureFirebase.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login feito com sucesso!!", Toast.LENGTH_LONG).show();

                            AbrirTelaPrincipal();


                        } else {

                            String erro = "";

                            try {

                                throw task.getException();

                            } catch (FirebaseAuthInvalidUserException e) {
                                erro = "conta do usuário correspondente ao e-mail não existi ou está desativada";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erro = "talvez sua senha esteja errada";
                            } catch (Exception e) {
                                e.printStackTrace();
                                erro = "Erro ao fazer login!!";
                            }

                            Toast.makeText(Login.this, "Opa, algo deu errado, " + erro, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    private void VerificaUsuarioLogado() {

        firebaseAuth = configureFirebase.getFirebaseAuth();
        if (firebaseAuth.getCurrentUser() != null) {

            AbrirTelaPrincipal();

        }

    }

    private void AbrirTelaPrincipal() {

        Intent intent = new Intent(Login.this, Principal.class);
        startActivity(intent);
        finish();


    }


}
