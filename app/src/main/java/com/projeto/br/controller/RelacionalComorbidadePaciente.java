package com.projeto.br.controller;

public class RelacionalComorbidadePaciente {

    private String cartao_paciente;
    private String id_comorbidade;
    private int id;

    public RelacionalComorbidadePaciente(String cartao_usuario, String id_comorbidade, int id) {
        this.cartao_paciente = cartao_usuario;
        this.id_comorbidade = id_comorbidade;
        this.id = id;
    }

    public RelacionalComorbidadePaciente() {
    }

    public String getCartao_usuario() {
        return cartao_paciente;
    }

    public void setCartao_usuario(String cartao_usuario) {
        this.cartao_paciente = cartao_usuario;
    }

    public String getId_comorbidade() {
        return id_comorbidade;
    }

    public void setId_comorbidade(String id_comorbidade) {
        this.id_comorbidade = id_comorbidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RelacionalComorbidadePaciente{" +
                "cartao_usuario='" + cartao_paciente + '\'' +
                ", id_comorbidade='" + id_comorbidade + '\'' +
                ", id=" + id +
                '}';
    }
}
