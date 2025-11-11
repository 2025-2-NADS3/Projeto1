package com.example.entrega_2_programacao_mobile_pratos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class TelaInfoPrato extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_infoprato);

        // liga os elementos do xml com o código
        TextView nomePrato = findViewById(R.id.nomeProduto);
        TextView descPrato = findViewById(R.id.descProduto);
        TextView valProduto = findViewById(R.id.valProduto);
        TextView dispProduto = findViewById(R.id.dispProduto);
        ImageView imagemPrato = findViewById(R.id.imagemProduto);
        TextView mediaNota = findViewById(R.id.mediaNota);
        RatingBar estrelas = findViewById(R.id.estrelas);
        Button Retornar = findViewById(R.id.btnVoltar);

        // recebe os dados enviados pela tela anterior (adapter)
        String nome = getIntent().getStringExtra("nome");
        String descricao = getIntent().getStringExtra("descricao");
        double preco = getIntent().getDoubleExtra("preco", 0.0);
        boolean disponivel = getIntent().getBooleanExtra("disponivel", true);
        String imagemUrl = getIntent().getStringExtra("imagemUrl");
        double rating = getIntent().getDoubleExtra("rating", 0.0);

        // mostra os dados recebidos na tela
        nomePrato.setText(nome);
        descPrato.setText(descricao);
        valProduto.setText("R$ " + String.format("%.2f", preco)); // formata o preço com 2 casas decimais
        mediaNota.setText(String.format("%.1f", rating)); // mostra a nota média
        estrelas.setRating((float) rating); // mostra estrelas de acordo com a nota

        // muda o texto e a cor conforme a disponibilidade
        dispProduto.setText(disponivel ? "Disponível" : "Indisponível");
        dispProduto.setTextColor(disponivel ? getColor(R.color.green) : getColor(android.R.color.holo_red_dark));

        // carrega a imagem do prato com o glide
        Glide.with(this).load(imagemUrl).into(imagemPrato);

        // botão pra voltar pra tela anterior
        Retornar.setOnClickListener(v -> Retorno());
    }

    // método pra voltar pra tela de seleção
    private void Retorno() {
        Intent intent = new Intent(TelaInfoPrato.this, TelaSelecao.class);
        startActivity(intent);
        finish();
    }
}
