package com.projeto.br;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.projeto.br.bd.BancoDados;
import com.projeto.br.controller.Usuario;

public class CadastrarActivity extends AppCompatActivity {

    private EditText nome_completo;
    private EditText nickname;
    private EditText senha;

    private ImageButton btn_voltar;
    private Button btn_cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        nome_completo = (EditText) findViewById(R.id.nome_completo);
        nickname = (EditText) findViewById(R.id.nickname);
        senha = (EditText) findViewById(R.id.senha);

        btn_voltar = (ImageButton) findViewById(R.id.btn_voltar);
        btn_cadastrar = (Button) findViewById(R.id.btn_cadastrar);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar_welcome();
            }
        });

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = (nome_completo.getText()).toString();
                String nick = (nickname.getText()).toString();
                String passe = (senha.getText()).toString();
                String avatar = "padrao";

                Usuario usuario = new Usuario(nome, nick, passe, avatar);
                cadastrar_usuario(usuario);
            }
        });
    }


    public void cadastrar_usuario (Usuario usuario){
        /// verificar nickname
        if (usuario.getNickname().length() >= 10){

            /// verificar o nome completo do usuário
            if (usuario.getNome().length() >= 20){

                /// verificar senha do usuário
                if (usuario.getSenha().length() >=8){
                    BancoDados dao = new BancoDados(getApplicationContext());
                    boolean result = dao.inserir_usuario(usuario);
                    if (result){
                        Toast.makeText(getApplicationContext(), "Usuário cadastrado!", Toast.LENGTH_LONG)
                                .show();
                        ir_login();
                    }else {
                        Toast.makeText(getApplicationContext(), "Usuário não cadastrado!", Toast.LENGTH_LONG)
                                .show();
                    }
                }
                else {
                   Toast.makeText(getApplicationContext(), "Senha muito fraca!", Toast.LENGTH_LONG)
                            .show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Nome muito pequeno!", Toast.LENGTH_LONG)
                        .show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Nickname muito pequeno!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void voltar_welcome (){
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void ir_login (){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        voltar_welcome();
    }
}