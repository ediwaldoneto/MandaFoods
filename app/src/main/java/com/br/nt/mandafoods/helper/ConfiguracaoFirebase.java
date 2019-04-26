package com.br.nt.mandafoods.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth referenciaAutenticacao;
    private static StorageReference referenciaStorage;

    public static String getUsuario(){

        FirebaseAuth autenticacao = getFirebaseAutenticacao();
        return autenticacao.getCurrentUser().getUid();
    }


    //Retornando a referencia do database

    public static DatabaseReference getFirebase(){
        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();

        }
        return referenciaFirebase;
    }

    //Retornando a referencia da autenticacao

    public static FirebaseAuth getFirebaseAutenticacao(){
        if (referenciaAutenticacao == null) {
            referenciaAutenticacao = FirebaseAuth.getInstance();

        }
        return referenciaAutenticacao;
    }


    public static StorageReference getFirebaseStorage(){
        if (referenciaStorage == null) {
            referenciaStorage = FirebaseStorage.getInstance().getReference();

        }
        return referenciaStorage;
    }


}
