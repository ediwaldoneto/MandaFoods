package com.br.nt.mandafoods.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.br.nt.mandafoods.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Ocultar action bar
        getSupportActionBar().hide();

        //Delay para minha tela de splash
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                abrirAutenticação();
            }
        },3000);
    }

        //Metodo para abrir a tela de autenticacao do usuairo
        private void abrirAutenticação(){
        Intent i = new Intent(SplashActivity.this, AutenticacaoActivity.class);
        startActivity(i);
        finish();
    }
}
