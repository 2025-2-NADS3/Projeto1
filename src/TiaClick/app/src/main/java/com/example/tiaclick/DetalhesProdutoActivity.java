package com.example.tiaclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetalhesProdutoActivity extends AppCompatActivity {

    ImageView imgProduto;
    TextView nomeProduto, descProduto, valProduto, notaProduto, qntProduto;
    Button btnVoltar, addCarrinho, btnAdd, btnRemove;

    int quantidade = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);

        imgProduto = findViewById(R.id.imgProduto);
        nomeProduto = findViewById(R.id.nomeProduto);
        descProduto = findViewById(R.id.descProduto);
        valProduto = findViewById(R.id.valProduto);
        notaProduto = findViewById(R.id.notaProduto);
        btnVoltar = findViewById(R.id.btnMenu);
        addCarrinho = findViewById(R.id.addCarrinho);

        // novos
        qntProduto = findViewById(R.id.qntProduto);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);

        // Recebendo dados
        String nome = getIntent().getStringExtra("nome");
        String desc = getIntent().getStringExtra("descricao");
        double preco = getIntent().getDoubleExtra("preco", 0);
        String imagem = getIntent().getStringExtra("imagem");
        float nota = getIntent().getFloatExtra("nota", 0);
        String uid = getIntent().getStringExtra("uid");


        // Exibindo dados
        nomeProduto.setText(nome);
        descProduto.setText(desc);
        valProduto.setText("R$ " + String.format("%.2f", preco));
        notaProduto.setText(String.valueOf(nota));

        Glide.with(this)
                .load(imagem)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(imgProduto);

        // quantidade inicial
        qntProduto.setText(String.valueOf(quantidade));

        // aumentar
        btnAdd.setOnClickListener(v -> {
            quantidade++;
            qntProduto.setText(String.valueOf(quantidade));
        });

        // diminuir
        btnRemove.setOnClickListener(v -> {
            if (quantidade > 1) {
                quantidade--;
                qntProduto.setText(String.valueOf(quantidade));
            }
        });

        // voltar
        btnVoltar.setOnClickListener(v -> onBackPressed());

        // adicionar ao carrinho
        addCarrinho.setOnClickListener(v -> {

            Produto p = new Produto(uid, nome, preco, imagem, nota);
            p.setQuantidade(quantidade);

            Carrinho.adicionar(p);

            Intent i = new Intent(this, CarrinhoActivity.class);
            startActivity(i);
        });
    }
}
