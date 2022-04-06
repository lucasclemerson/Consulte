package com.projeto.br.controller;

public class Comorbidade {
    private String nome;
    private int id;

    public Comorbidade(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public Comorbidade() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Comorbidade{" +
                "nome='" + nome + '\'' +
                ", id=" + id +
                '}';
    }
}
