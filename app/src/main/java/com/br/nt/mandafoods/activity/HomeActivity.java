package com.br.nt.mandafoods.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.br.nt.mandafoods.R;
import com.br.nt.mandafoods.helper.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        iniciarlizaComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Manda Foods");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_usuario, menu);

        //Meu botaoa de pesquisa
        MenuItem item = menu.findItem(R.id.menuPesquisa);
        searchView.setMenuItem(item);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menuSair :
                deslogarUser();
                break;
            case R.id.menuConfiguracoes :
                abrirConfigs();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarUser(){

        try {
            autenticacao.signOut();
            finish();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void abrirConfigs(){
        startActivity(new Intent(HomeActivity.this, ConfiguracoesUsuarioActivity.class));
    }

    private void iniciarlizaComponentes(){
        searchView = findViewById(R.id.materialSearchView);
    }


}



