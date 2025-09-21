package com.example.cidades;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TelaInfoCidade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_infocidade);

        // Declaração dos componentes
        TextView TituloCidade, DescriCidade, HabitantesCidade, IdadeCidade, PibCidade, TamCidade;
        ImageView ImagemCidadela;

        // ligação entre os elementos
        TituloCidade = findViewById(R.id.tituloCidade6);
        DescriCidade = findViewById(R.id.Descricaocidade);
        PibCidade = findViewById(R.id.PibCidade);
        TamCidade = findViewById(R.id.TamanhoCidade);
        HabitantesCidade = findViewById(R.id.HabitantesCidades);
        IdadeCidade = findViewById(R.id.IdadeCidade);
        ImagemCidadela = findViewById(R.id.ImagemCidadeMenu);



        // recebe as informações enviadas pela Intent da tela anterior
        String nomeRecebido = getIntent().getStringExtra("nome");                                  // Nome da cidade
        String DescricaoRecebida = getIntent().getStringExtra("DescricaoCidade");                 // Descrição da cidade
        String ValQuantidadeHabitante = getIntent().getStringExtra("QuantidadeHabitantesCidade"); // População
        String ValIdadeCidade = getIntent().getStringExtra("IdadeCidade");
        String ValPib = getIntent().getStringExtra("Pib");
        String ValTamanho = getIntent().getStringExtra("Tamanho");
        int Imagem = getIntent().getIntExtra("IdImagem", 0);                          // Imagem da cidade

        // exibe os dados nos componentes
        TituloCidade.setText(nomeRecebido);
        DescriCidade.setText(DescricaoRecebida);
        ImagemCidadela.setImageResource(Imagem);
        HabitantesCidade.setText(ValQuantidadeHabitante);
        IdadeCidade.setText(ValIdadeCidade);
        PibCidade.setText(ValPib);
        TamCidade.setText(ValTamanho);
    }
}
