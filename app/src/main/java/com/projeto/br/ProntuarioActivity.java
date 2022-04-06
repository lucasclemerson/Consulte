package com.projeto.br;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.projeto.br.bd.BancoDados;
import com.projeto.br.controller.Prontuario;
import com.projeto.br.controller.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ProntuarioActivity extends AppCompatActivity implements ProntuarioItemSelecterListener{

    private ChipGroup chipGrou;
    private EditText buscar;
    private List<Prontuario> lista = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private Button limpar_pesquisa;
    private ImageButton btn_voltar;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prontuario);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String nickname = bundle.getString("nickname");
            String avatar = bundle.getString("avatar");
            String nome = bundle.getString("nome");
            String senha = bundle.getString("senha");
            usuario = new Usuario(nome, nickname, senha, avatar);
        }

        //// declarar componentes
        chipGrou = (ChipGroup) findViewById(R.id.chip_group);
        buscar = (EditText) findViewById(R.id.campo_pesquisa);
        recyclerView = (RecyclerView) findViewById(R.id.listViewProntuarios);
        limpar_pesquisa = (Button) findViewById(R.id.limpar_pesquisa);

        //// setar dados no adapterview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        getProntuarios();
        recyclerAdapter = new RecyclerAdapter(this, lista);
        recyclerView.setAdapter(recyclerAdapter);

        btn_voltar = (ImageButton) findViewById(R.id.btn_voltar);
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar_inicio();
            }
        });

        limpar_pesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar.setText("");
            }
        });

        //// buscar dados
        buscar.addTextChangedListener(new TextWatcher() {
            /// principal
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String texto = charSequence.toString();
                List<Prontuario> nova_busca = new ArrayList<>();
                BancoDados bd = new BancoDados(getApplicationContext());
                for (Prontuario p : bd.listar_prontuarios()) {
                    if(p.getNumero().contains(texto))
                        nova_busca.add(p);
                }
                recyclerAdapter = new RecyclerAdapter(ProntuarioActivity.this, nova_busca);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        //// botão flutuante
        ((FloatingActionButton) findViewById(R.id.adiciona_prontuario)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onItemSelected(Prontuario p) {
        Log.e("NUMERO PRONTUARIA", p.getNumero());
    }


    /// preencher prontuarios
    private void getProntuarios (){
        BancoDados bd = new BancoDados(getApplicationContext());
        for (Prontuario p : bd.listar_prontuarios()) {
            lista.add(p);
        }
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