package com.example.cidades;

// Classe usada como molde para representar uma cidade
public class CidadeArray {
    // Atributos que cada cidade vai ter
    String NomeCidade;
    String DescricaoCidade;
    String QuantidadeHabitantesCidade;
    String IdadeCidade;
    String Pib;
    String Tamanho;



    // Construtor
    public CidadeArray(String Nome, String Descricao, String QuantidadeHabitantes, String Idade, String pib, String Tamanho) {
        this.NomeCidade = Nome;                                    // Define cada coisa
        this.DescricaoCidade = Descricao;
        this.QuantidadeHabitantesCidade = QuantidadeHabitantes;
        this.IdadeCidade = Idade;
        this.Pib = pib;
        this.Tamanho = Tamanho;
    }
}
