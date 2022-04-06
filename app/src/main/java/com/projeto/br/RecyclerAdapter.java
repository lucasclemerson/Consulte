package com.projeto.br;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto.br.bd.BancoDados;
import com.projeto.br.controller.Paciente;
import com.projeto.br.controller.Prontuario;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MinhaAdapterProntuario> {

    public Context context;
    private List<Prontuario> prontuarios;
    private ProntuarioItemSelecterListener interfaceProntuario;


    public RecyclerAdapter(Context context, List<Prontuario> p) {
        this.context = context;
        this.prontuarios = p;
        this.interfaceProntuario = (ProntuarioActivity) this.context;

    }

    @NonNull
    @Override
    public MinhaAdapterProntuario onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_prontuarios, parent, false);
        return new MinhaAdapterProntuario(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MinhaAdapterProntuario holder, int position) {
        String numero_prontuario = prontuarios.get(position).getNumero();
        holder.numero.setText(numero_prontuario);

        /// vamos contar quantos pacientes possuem nesse prontuário
        BancoDados bd = new BancoDados(context);
        ArrayList<Paciente> lista = bd.listar_pacientes();
        int contador = 0;

        for (Paciente p: lista) {
            if (p.getN_prontuario().equals(numero_prontuario)){
                contador++;
            }
        }
        holder.pessoas.setText(Integer.toString(contador));
    }

    @Override
    public int getItemCount() {
        return this.prontuarios.size();
    }

    public MinhaAdapterProntuario onCreateMinhaAdapterProntuario(ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_prontuarios, viewGroup);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero = ((TextView) view.findViewById(R.id.numero_prontuario)).getText().toString();
                Toast.makeText(context, numero, Toast.LENGTH_LONG).show();
            }
        });
        return new MinhaAdapterProntuario(view);
    }


    class MinhaAdapterProntuario extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RecyclerView lista;
        public TextView numero;
        public TextView pessoas;

        public MinhaAdapterProntuario(@NonNull View view) {
            super(view);
            numero = (TextView) view.findViewById(R.id.numero_prontuario);
            lista = (RecyclerView) view.findViewById(R.id.listViewProntuarios);
            pessoas = (TextView) view.findViewById(R.id.numero_de_pessoas);

            //lista.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //interfaceProntuario.onItemSelected(prontuarios.get(getAdapterPosition()));
        }
    }
}