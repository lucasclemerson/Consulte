package com.projeto.br;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.projeto.br.R;
import com.projeto.br.controller.Prontuario;
import com.projeto.br.controller.Usuario;

public class DetalharProntuarioActivity extends AppCompatActivity {


    private ImageButton btn_voltar;
    private Usuario usuario;
    private Prontuario prontuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_prontuario);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String nickname = bundle.getString("nickname");
            String avatar = bundle.getString("avatar");
            String nome = bundle.getString("nome");
            String senha = bundle.getString("senha");

            String numero = bundle.getString("prontuario_numero");
            String endereco = bundle.getString("prontuario_endereco");
            prontuario.setNumero(numero);
            prontuario.setEndereco(endereco);
            usuario = new Usuario(nome, nickname, senha, avatar);
        }

        btn_voltar = (ImageButton) findViewById(R.id.btn_voltar);
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar_lista_prontuarios();
            }
        });
    }

    public void voltar_lista_prontuarios (){
        Intent intent = new Intent(getApplicationContext(), Prontuario.class);

        intent.putExtra("nickname", usuario.getNickname());
        intent.putExtra("nome", usuario.getNome());
        intent.putExtra("senha", usuario.getSenha());
        intent.putExtra("avatar", usuario.getAvatar());

        startActivity(intent);
        finish();
    }


}