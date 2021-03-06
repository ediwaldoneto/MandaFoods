package com.br.nt.mandafoods.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;


import com.br.nt.mandafoods.R;
import com.br.nt.mandafoods.helper.ConfiguracaoFirebase;
import com.br.nt.mandafoods.helper.UsuarioFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class AutenticacaoActivity extends AppCompatActivity {

    //ATRIBUTOS DA MINHA TELA DE LOGIN

    private Button botaoAcessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso, tipoUsuario;
    private FirebaseAuth autenticacao;
    private LinearLayout linearLayoutTipoUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);


        inicializaComponentes();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        verificaLogado();

        //Deslogar Usuario autenticado
        //autenticacao.signOut();

        tipoAcesso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){//Empresa
                    linearLayoutTipoUsuario.setVisibility(View.VISIBLE);
                }else {//Usuario
                    linearLayoutTipoUsuario.setVisibility(View.GONE);
                }
            }
        });

        //EVENTO DO MEU BOTAO
        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //RECUPERANDO O QUE FOI DIGITADO

                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                //Verificando os campos login e senha

                if (!email.isEmpty()){
                    if (!senha.isEmpty()) {

                        //Verificando o estado do switch

                        if(tipoAcesso.isChecked()){//Cadastro
                            autenticacao.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                                        String tipoUsuario = getTipoUsuario();
                                        UsuarioFirebase.atualizarTipoUsuario(tipoUsuario);

                                        //Direcionando para minha home
                                        abrirTelaPrincipal(tipoUsuario);


                                    }else{

                                        String erroExcecao = "";

                                        try {
                                            throw task.getException();
                                        }catch (FirebaseAuthWeakPasswordException e){
                                            erroExcecao = "Digite uma senha mais forte !";
                                        }catch (FirebaseAuthInvalidCredentialsException e) {
                                            erroExcecao = "Por favor Digite um E-mail válido !";
                                        }catch (FirebaseAuthUserCollisionException e){
                                            erroExcecao = "Conta de E-mail já cadastrada !";
                                        }catch (Exception e){
                                            erroExcecao = "Erro ao cadastrar usuário:  " + e.getMessage();
                                            e.printStackTrace();
                                        }

                                        Toast.makeText(getApplicationContext(), "ERRO " + erroExcecao , Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        }else {

                            autenticacao.signInWithEmailAndPassword(
                                    email,senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(getApplicationContext(), "Logado com sucesso !", Toast.LENGTH_SHORT).show();
                                       //
                                       @NonNull
                                       String tipoUsuario;
                                       tipoUsuario = task.getResult().getUser().getDisplayName();
                                       abrirTelaPrincipal(tipoUsuario);

                                   }else {
                                       Toast.makeText(getApplicationContext(), "Erro ao fazer login " + task.getException() , Toast.LENGTH_SHORT).show();
                                   }

                                }
                            });

                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "Preencha a Senha !", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha o E-mail !", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    // METEDO PARA DIRECIONAMENTO DE TELA
    private void abrirTelaPrincipal(String tipoUsuario){
        if (tipoUsuario.equals("E")) {//EMPRESA
            startActivity(new Intent(getApplicationContext(), EmpresaActivity.class));
        }else {//USUARIO
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

    }




    //Retornando meu tipo usuario

    private String getTipoUsuario(){
        return tipoUsuario.isChecked() ? "E" : "U";
    }


    //INICIALIZACAO DOS MEUS COMPONENTES

    private void inicializaComponentes(){

        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        botaoAcessar = findViewById(R.id.buttonAcesso);
        tipoAcesso = findViewById(R.id.switchAcesso);
        tipoUsuario = findViewById(R.id.switchTipoAutentica);
        linearLayoutTipoUsuario = findViewById(R.id.linearTipoUsuario);

    }

    //VERIFICANDO USUARIO LOGADO

    private void verificaLogado(){
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if (usuarioAtual != null){
            String tipoUsuario = usuarioAtual.getDisplayName();
            abrirTelaPrincipal(tipoUsuario);
        }
    }
}
