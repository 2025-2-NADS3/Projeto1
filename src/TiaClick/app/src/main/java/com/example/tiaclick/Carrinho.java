package com.example.tiaclick;

import java.util.ArrayList;

public class Carrinho {

    private static ArrayList<Produto> itens = new ArrayList<>();

    public static void adicionar(Produto novo) {

        for (Produto p : itens) {
            if (p.getNome().equals(novo.getNome())) {
                // já existe → soma quantidade
                p.setQuantidade(p.getQuantidade() + novo.getQuantidade());
                return;
            }
        }

        // se não achou, adiciona novo
        itens.add(novo);
    }

    public static ArrayList<Produto> getItens() {
        return itens;
    }

    public static double getTotal() {
        double total = 0;
        for (Produto p : itens) {
            total += p.getPreco() * p.getQuantidade(); // Multiplica pela quantidade
        }
        return total;
    }

    public static void limpar() {
        itens.clear();
    }
}
