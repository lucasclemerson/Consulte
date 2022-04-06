package com.projeto.br;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projeto.br.controller.Usuario;

public class InicioActivity extends AppCompatActivity {

    private TextView lbl_nome_completo;
    private Button btn_finalizar_sessao;
    private ImageButton imagem_editar_perfil;
    private ImageButton imagem_listar_prontuarios;
    private ImageButton imagem_listar_pessoas;
    private ImageButton imagem_faleconosco;
    private Usuario usuario;
    private ImageView imagem_principal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String nickname = bundle.getString("nickname");
            String avatar = bundle.getString("avatar");
            String nome = bundle.getString("nome");
            String senha = bundle.getString("senha");
            usuario = new Usuario(nome, nickname, senha, avatar);
        }

        lbl_nome_completo = (TextView) findViewById(R.id.lbl_nome_completo);
        lbl_nome_completo.setText(usuario.getNickname());
        imagem_principal = (ImageView) findViewById(R.id.imagem_principal);
        imagem_principal.setImageResource(getFoto(usuario.getAvatar()));

        imagem_editar_perfil = (ImageButton) findViewById(R.id.imagem_editar_perfil);
        imagem_editar_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);

                intent.putExtra("nickname", usuario.getNickname());
                intent.putExtra("nome", usuario.getNome());
                intent.putExtra("senha", usuario.getSenha());
                intent.putExtra("avatar", usuario.getAvatar());

                startActivity(intent);
                finish();
            }
        });

        imagem_listar_prontuarios = (ImageButton) findViewById(R.id.imagem_listar_prontuarios);
        imagem_listar_prontuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProntuarioActivity.class);

                intent.putExtra("nickname", usuario.getNickname());
                intent.putExtra("nome", usuario.getNome());
                intent.putExtra("senha", usuario.getSenha());
                intent.putExtra("avatar", usuario.getAvatar());

                startActivity(intent);
                finish();
            }

        });

        imagem_listar_pessoas = (ImageButton) findViewById(R.id.imagem_listar_pessoas);
        imagem_listar_pessoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btn_finalizar_sessao = (Button) findViewById(R.id.btn_finalizar_sessao);
        btn_finalizar_sessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ir_welcome();
            }
        });

        imagem_faleconosco = (ImageButton) findViewById(R.id.imagem_faleconosco);
        imagem_faleconosco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    public int getFoto (String avatar){
        int id=0;
        switch (avatar){
            case "homem_moreno":
                id = R.drawable.homem_moreno;
                break;
            case "homem_galego":
                id = R.drawable.homem_galego;
                break;
            case "mulher_morena":
                id = R.drawable.mulher_morena;
                break;
            case "mulher_galega":
                id = R.drawable.mulher_galega;
                break;
            default:
                id = R.drawable.padrao;
                break;
        }
        return id;
    }


    public void ir_welcome (){
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        ir_welcome();
    }
}