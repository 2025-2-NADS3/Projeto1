package com.example.cidades;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class TelaSelecaoCidade extends AppCompatActivity {

    // Declaração de objetos da array
    CidadeArray cidade1, cidade2, cidade3, cidade4, cidade5, cidade6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selecionar);

        // criação das cidades seguindo a array
        cidade1 = new CidadeArray(
                "Guarulhos",
                "Segunda maior cidade do estado de São Paulo. É um dos maiores polos industriais do Brasil e abriga o Aeroporto Internacional, o mais movimentado do país. "
                        + "Sua economia é baseada em transporte aéreo, logística e indústria. ",
                "1.35 Milhões de Habitantes",
                "464 Anos",
                "PIB aproximado: R$ 62 bilhões.",
                "Área: 318 km²"
        );

        cidade2 = new CidadeArray(
                "Guararema",
                "Cidade turística do interior paulista, conhecida por suas belezas naturais e clima acolhedor. "
                        + "A economia gira em torno do turismo, artesanato e pequenas indústrias. ",
                "30.135 Habitantes",
                "125 Anos",
                "PIB aproximado: R$ 2 bilhões.",
                "Área: 270 km²"
        );

        cidade3 = new CidadeArray(
                "Guarujá",
                "Cidade litorânea conhecida como a Pérola do Atlântico, famosa por suas praias. "
                        + "Sua economia é fortemente ligada ao turismo e ao porto de Santos. ",
                "287.634 Habitantes",
                "494 Anos",
                "PIB aproximado: R$ 15 bilhões.",
                "Área: 143 km²"
        );

        cidade4 = new CidadeArray(
                "São Paulo",
                "Maior cidade do Brasil e da América do Sul. É o principal centro financeiro e cultural do país, com economia diversificada baseada em comércio, serviços, indústria e tecnologia. "
                        + "Recebe eventos internacionais e concentra sedes de grandes empresas. ",
                "11.9 Milhões de Habitantes",
                "471 Anos",
                "PIB aproximado: R$ 850 bilhões.",
                "Área: 1.521 km²"
        );

        cidade5 = new CidadeArray(
                "Santos",
                "Cidade portuária fundamental para a economia brasileira, com o maior porto da América Latina. "
                        + "Sua economia se baseia em logística, comércio, turismo e serviços. ",
                "433.966 Habitantes",
                "477 Anos",
                "PIB aproximado: R$ 25 bilhões.",
                "Área: 280 km²"
        );

        cidade6 = new CidadeArray(
                "Campinas",
                "Importante polo tecnológico e universitário do interior paulista. "
                        + "Sua economia é baseada em indústria, tecnologia, agronegócio e educação. ",
                "1.2 Milhões de Habitantes",
                "250 Anos",
                "PIB aproximado: R$ 65 bilhões.",
                "Área: 795 km²"
        );


        // referências aos botões
        Button botao1 = findViewById(R.id.botaoCidade);
        Button botao2 = findViewById(R.id.botaoCidade2);
        Button botao3 = findViewById(R.id.botaoCidade3);
        Button botao4 = findViewById(R.id.botaoCidade4);
        Button botao5 = findViewById(R.id.botaoCidade5);
        Button botao6 = findViewById(R.id.botaoCidade6);

        // define a ação de clique para cada botão
        botao1.setOnClickListener(view -> AbrirDetalhesCidade(cidade1, R.drawable.guarulhos));
        botao2.setOnClickListener(view -> AbrirDetalhesCidade(cidade2, R.drawable.guararema));
        botao3.setOnClickListener(view -> AbrirDetalhesCidade(cidade3, R.drawable.guaruja));
        botao4.setOnClickListener(view -> AbrirDetalhesCidade(cidade4, R.drawable.sp));
        botao5.setOnClickListener(view -> AbrirDetalhesCidade(cidade5, R.drawable.santos));
        botao6.setOnClickListener(view -> AbrirDetalhesCidade(cidade6, R.drawable.campinas));
    }

    // metodo para abrir a tela de detalhes da cidade
    private void AbrirDetalhesCidade(CidadeArray cidade, int IdImagem) {

        // cria um Intent para mudar a tela
        Intent IrTelaInfo = new Intent(TelaSelecaoCidade.this, TelaInfoCidade.class);

        //passa as informações da cidade para a próxima tela
        IrTelaInfo.putExtra("nome", cidade.NomeCidade);
        IrTelaInfo.putExtra("DescricaoCidade", cidade.DescricaoCidade);
        IrTelaInfo.putExtra("QuantidadeHabitantesCidade", cidade.QuantidadeHabitantesCidade);
        IrTelaInfo.putExtra("IdadeCidade", cidade.IdadeCidade);
        IrTelaInfo.putExtra("IdImagem", IdImagem);
        IrTelaInfo.putExtra("Pib", cidade.Pib);
        IrTelaInfo.putExtra("Tamanho", cidade.Tamanho);


        //inicia a nova tela
        startActivity(IrTelaInfo);
    }
}
