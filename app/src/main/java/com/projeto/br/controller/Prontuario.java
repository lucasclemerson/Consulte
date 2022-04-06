package com.projeto.br.controller;

public class Prontuario {
    private String numero;
    private String endereco;

    public Prontuario(String numero, String endereco) {
        this.numero = numero;
        this.endereco = endereco;
    }

    public Prontuario() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Prontuario{" +
                "numero='" + numero + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
