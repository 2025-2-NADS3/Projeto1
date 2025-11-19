package com.example.tiaclick;

import java.io.Serializable;

public class Produto implements Serializable {

    private String uid; // UUID do produto no DB (pode ser null se não existir)
    private String nome;
    private String descricao;
    private double preco;
    private String imagemUrl;
    private float nota;
    private int quantidade = 1;

    public Produto(String uid, String nome, String descricao, double preco, String imagemUrl, float nota) {
        this.uid = uid;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
        this.nota = nota;
    }

    public Produto(String uid, String nome, double preco, String imagemUrl, float nota) {
        this.uid = uid;
        this.nome = nome;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
        this.nota = nota;
    }

    public Produto(String uid, String nome, double preco, String imagemUrl) {
        this.uid = uid;
        this.nome = nome;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
    }

    public Produto (String nome, double preco, String imagemUrl, float nota){
        this.nota = nota;
        this.nome = nome;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
    }

    public Produto (String nome, double preco, String imagemUrl){
        this.nome = nome;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
    }



    // getters e setters
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public float getNota() {
        return nota;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int qnt) {
        this.quantidade = qnt;
    }
}
