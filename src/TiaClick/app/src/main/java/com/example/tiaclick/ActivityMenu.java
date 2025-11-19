package com.example.tiaclick;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class ActivityMenu extends AppCompatActivity {

    LinearLayout catSalgados;
    LinearLayout catQueridinhos;
    LinearLayout catSobremesas;
    LinearLayout catBebidas;
    LinearLayout botaoPedido;
    LinearLayout botaoHome;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // AQUI SIM podemos fazer findViewById
        catSalgados = findViewById(R.id.categoriaSalgados);
        catQueridinhos = findViewById(R.id.categoriaQueridinhos);
        catSobremesas = findViewById(R.id.categoriaSobremesas);
        catBebidas = findViewById(R.id.categoriaBebidas);
        botaoHome = findViewById(R.id.btnHome);
        botaoPedido = findViewById(R.id.botaoPedidos);



        // SALGADOS
        catSalgados.setOnClickListener(v -> openCategoria("Salgado"));

        // QUERIDINHOS
        catQueridinhos.setOnClickListener(v -> openCategoria("Queridinhos"));

        // SOBREMESAS
        catSobremesas.setOnClickListener(v -> openCategoria("Sobremesa"));

        // BEBIDAS GELADAS
        catBebidas.setOnClickListener(v -> openCategoria("Bebida Gelada"));

        botaoPedido.setOnClickListener(v -> AbrirCarrinho());
    }

    private void openCategoria(String categoria) {
        Intent intent = new Intent(this, ListaProdutosActivity.class);
        intent.putExtra("categoria", categoria);
        startActivity(intent);
    }
    private void AbrirMenu(){
        Intent intent = new Intent(this, ActivityMenu.class);
        startActivity(intent);
    }
    private void AbrirCarrinho(){
        Intent intent = new Intent(this, CarrinhoActivity.class);
        startActivity(intent);
    }


}
