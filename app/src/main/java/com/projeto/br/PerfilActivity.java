package com.projeto.br;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto.br.bd.BancoDados;
import com.projeto.br.controller.Usuario;

public class PerfilActivity extends AppCompatActivity {

    private ImageButton btn_voltar;
    private Usuario usuario;

    private ImageView imagem_principal;
    private ImageButton img1,img2,img3,img4;

    private EditText nome_completo;
    private TextView nickname;
    private EditText senha;

    private Button btn_atualizar;
    private String imagem_original;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String nickname = bundle.getString("nickname");
            String avatar = bundle.getString("avatar");
            String nome = bundle.getString("nome");
            String senha = bundle.getString("senha");
            imagem_original = avatar;
            usuario = new Usuario(nome, nickname, senha, avatar);
        }

        //// setar dados
        nome_completo = (EditText) findViewById(R.id.nome_completo);
        nickname = (TextView) findViewById(R.id.nickname);
        senha = (EditText) findViewById(R.id.senha);

        senha.setText(usuario.getSenha());
        nickname.setText(usuario.getNickname());
        nome_completo.setText(usuario.getNome());

        //// setamos a imagem
        imagem_principal = (ImageView) findViewById(R.id.imagem_principal);
        imagem_principal.setImageResource(getFoto(usuario.getAvatar()));

        img1 = (ImageButton) findViewById(R.id.image1);
        img2 = (ImageButton) findViewById(R.id.image2);
        img3 = (ImageButton) findViewById(R.id.image3);
        img4 = (ImageButton) findViewById(R.id.image4);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagem_principal.setImageResource(getFoto("homem_moreno"));
                usuario.setAvatar("homem_moreno");
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagem_principal.setImageResource(getFoto("mulher_morena"));
                usuario.setAvatar("mulher_morena");
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagem_principal.setImageResource(getFoto("mulher_galega"));
                usuario.setAvatar("mulher_galega");
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagem_principal.setImageResource(getFoto("homem_galego"));
                usuario.setAvatar("homem_galego");
            }
        });

        btn_atualizar = (Button) findViewById(R.id.btn_atualizar);
        btn_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setNome(nome_completo.getText().toString());
                usuario.setSenha(senha.getText().toString());
                BancoDados bd = new BancoDados(getApplicationContext());
                boolean resultado = bd.update_usuario(usuario);

                if (resultado){
                    Toast.makeText(getApplicationContext(), "Usuário atualizado!", Toast.LENGTH_LONG)
                            .show();
                }else{
                    Toast.makeText(getApplicationContext(), "Usuário não atualizado!", Toast.LENGTH_LONG)
                            .show();
                }
                voltar_inicio();
            }
        });


        btn_voltar = (ImageButton) findViewById(R.id.btn_voltar);
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setAvatar(imagem_original);
                voltar_inicio();
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


    public void voltar_inicio (){
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
        voltar_inicio();
    }
}