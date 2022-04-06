package com.projeto.br.controller;

public class Usuario {
    private String nome;
    private String nickname;
    private String senha;
    private String avatar;

    public Usuario(String nome, String nickname, String senha, String avatar) {
        this.nome = nome;
        this.nickname = nickname;
        this.senha = senha;
        this.avatar = avatar;
    }

    public Usuario() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", nickname='" + nickname + '\'' +
                ", senha='" + senha + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
