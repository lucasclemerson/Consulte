package com.projeto.br;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.projeto.br.bd.BancoDados;
import com.projeto.br.controller.Comorbidade;
import com.projeto.br.controller.Paciente;
import com.projeto.br.controller.Prontuario;
import com.projeto.br.controller.Usuario;

import java.util.Timer;
import java.util.TimerTask;

public class ComecoActivity extends AppCompatActivity {

    private ProgressBar pb;
    private TextView texto_progresso;

    private int progresso=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comeco);

        pb = (ProgressBar) findViewById(R.id.dots_pagina);
        texto_progresso = (TextView) findViewById(R.id.status_progresso);

        Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progresso <= 100){
                    handler.post(new Runnable() {
                        public void run() {
                            pb.setProgress(progresso);
                            texto_progresso.setText(progresso+"%");
                        }
                    });

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progresso++;
                }
                ir_welcome();
            }
        }).start();
    }


    public void preencher_branco (){
        ///// deletar todos os dados
        BancoDados bd =new BancoDados(getApplicationContext());

        for (Paciente p: bd.listar_pacientes()) {
            bd.delete_paciente(p.getCartao_sus());
        }

        for (Usuario u: bd.listar_usuarios()) {
            bd.delete_usuario(u.getNickname());
        }

        for (Prontuario p: bd.listar_prontuarios()) {
            bd.delete_prontuario(p.getNumero());
        }

        for (Comorbidade c: bd.listar_comorbidade()) {
            bd.delete_comorbidade(c.getId());
        }

        //// inserir dados
        bd.inserir_comorbidade(new Comorbidade("Hipertensão", 0));
        bd.inserir_comorbidade(new Comorbidade("Diabetes", 1));
        bd.inserir_comorbidade(new Comorbidade("Câncer", 2));
        bd.inserir_comorbidade(new Comorbidade("Tabagista", 3));
        bd.inserir_comorbidade(new Comorbidade("Sintômatico respiratório", 4));
        bd.inserir_comorbidade(new Comorbidade("Alcoólatra", 5));
        bd.inserir_comorbidade(new Comorbidade("Alzhaimer", 6));

        bd.inserir_prontuario(new Prontuario("1", "Rua 1"));
        bd.inserir_prontuario(new Prontuario("2", "Rua 2"));
        bd.inserir_prontuario(new Prontuario("3", "Rua 3"));

        bd.inserir_paciente(new Paciente("Chris jones", "58964", "1", "Masculino"));
        bd.inserir_paciente(new Paciente("Chris Hawkins", "59276", "2", "Masculino"));
        bd.inserir_paciente(new Paciente("Marta Counts", "21345", "3", "Feminino"));
        bd.inserir_paciente(new Paciente("Christina Hawkins", "41412", "2", "Feminino"));

        bd.inserir_usuario(new Usuario("Clemerson Lucas de Oliveira", "clemerson", "12345", "padrao"));
        bd.inserir_usuario(new Usuario("Caio César Penha Dantas", "caio", "12345", "padrao"));
    }



    public void ir_welcome (){
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}