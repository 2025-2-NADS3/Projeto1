package com.example.entrega_2_programacao_mobile_pratos;


//Construtoooor :D
public class Produto {
    private String id;
    private String tipo;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private boolean disponivel;
    private String imagemUrl;

    private double rating;

    public Produto(String id, String tipo, String nome, String descricao, double preco, int estoque, boolean disponivel, String imagemUrl, double rating) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.disponivel = disponivel;
        this.imagemUrl = imagemUrl;
        this.rating = rating;
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }
    public boolean isDisponivel() { return disponivel; }
    public String getImagemUrl() { return imagemUrl; }

    public double getRating() {return rating;}
}
