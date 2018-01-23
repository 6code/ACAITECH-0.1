package com.example.edu.aaitech.model;

import com.example.edu.aaitech.configure.configureFirebase;
import com.google.firebase.database.DatabaseReference;



/**
 * Created by edu on 16/01/18.
 */

public class cliente {

    String id;
    String nome;
    String email;
    String senha;
    String nomeDoEstabelecimento;
    String endereco;
    Integer telefone;
    Double Latitute;
    Double longitude;
    String chave;

    public void salvarClienete() {

        DatabaseReference databaseReference = configureFirebase.getDatabaseReference();
        databaseReference.child("ACAI").child("clientes").child(getId()).setValue(this);
    }

    public void salvarLocal() {

        DatabaseReference databaseReference = configureFirebase.getDatabaseReference();
        databaseReference.child("ACAI").child("locais").child(getNomeDoEstabelecimento()).child("latitude").setValue(getLatitute());
        databaseReference.child("ACAI").child("locais").child(getNomeDoEstabelecimento()).child("longitude").setValue(getLongitude());
        databaseReference.child("ACAI").child("locais").child(getNomeDoEstabelecimento()).child("name").setValue(getNomeDoEstabelecimento());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeDoEstabelecimento() {
        return nomeDoEstabelecimento;
    }

    public void setNomeDoEstabelecimento(String nomeDoEstabelecimento) {
        this.nomeDoEstabelecimento = nomeDoEstabelecimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public Double getLatitute() {
        return Latitute;
    }

    public void setLatitute(Double latitute) {
        Latitute = latitute;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getChave() {
        return chave;
    }

    public String setChave(String chave) {
        this.chave = chave;
        return chave;
    }
}
