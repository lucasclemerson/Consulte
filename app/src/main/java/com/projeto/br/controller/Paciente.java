package com.projeto.br.controller;

public class Paciente {

    private String nome;
    private String cartao_sus;
    private String n_prontuario;
    private String sexo;

    public Paciente(String nome, String cartao_sus, String n_prontuario, String sexo) {
        this.nome = nome;
        this.cartao_sus = cartao_sus;
        this.n_prontuario = n_prontuario;
        this.sexo = sexo;
    }

    public Paciente (){

    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCartao_sus() {
        return cartao_sus;
    }

    public void setCartao_sus(String cartao_sus) {
        this.cartao_sus = cartao_sus;
    }

    public String getN_prontuario() {
        return n_prontuario;
    }

    public void setN_prontuario(String n_prontuario) {
        this.n_prontuario = n_prontuario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nome='" + nome + '\'' +
                ", cartao_sus='" + cartao_sus + '\'' +
                ", n_prontuario='" + n_prontuario + '\'' +
                ", sexo='" + sexo + '\'' +
                '}';
    }
}
