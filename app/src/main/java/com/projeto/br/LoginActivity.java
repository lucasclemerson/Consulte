package com.projeto.br;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.projeto.br.bd.BancoDados;
import com.projeto.br.controller.Usuario;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private ImageButton btn_voltar;
    private Button btn_entrar;

    private EditText campo_nickname;
    private EditText campo_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campo_senha = (EditText) findViewById(R.id.campo_senha);
        campo_nickname = (EditText) findViewById(R.id.campo_nickname);


        btn_voltar = (ImageButton) findViewById(R.id.btn_voltar);
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar_welcome();
            }
        });

        btn_entrar = (Button) findViewById(R.id.btn_entrar);
        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senha = (campo_senha.getText()).toString();
                String nickname = (campo_nickname.getText()).toString();
                verificar_login(nickname, senha);
            }
        });
    }


    public void verificar_login (String nickname, String senha){
        BancoDados dao = new BancoDados(getApplicationContext());
        ArrayList<Usuario> list = dao.listar_usuarios();

        boolean autenticado = false;
        Usuario usuario = new Usuario();

        for (Usuario a : list) {
            //// comparar os usuários
            if (nickname.equals(a.getNickname())){
                if (senha.equals(a.getSenha())){
                    usuario.setNickname(nickname);
                    usuario.setNome(a.getNome());
                    usuario.setSenha(senha);
                    usuario.setAvatar(a.getAvatar());
                    autenticado = true;
                    break;
                }
            }
        }

        if (autenticado){
            Toast.makeText(getApplicationContext(), "Usuário logado!", Toast.LENGTH_LONG)
                    .show();
            ir_inicial(usuario);
        }else {
            Toast.makeText(getApplicationContext(), "Usuário inexistente!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void voltar_welcome (){
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void ir_inicial (Usuario usuario){
        Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
        intent.putExtra("nickname", usuario.getNickname());
        intent.putExtra("nome", usuario.getNome());
        intent.putExtra("senha", usuario.getSenha());
        intent.putExtra("avatar", usuario.getAvatar());
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        voltar_welcome();
    }
}