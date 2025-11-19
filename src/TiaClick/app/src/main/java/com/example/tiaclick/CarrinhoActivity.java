package com.example.tiaclick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CarrinhoActivity extends AppCompatActivity {

    RecyclerView recycler;
    TextView totalTexto;
    double totalCarrinho;
    CarrinhoAdapter adapter;

    Button botaoFinalizarCompra;
    ImageView botaoRetornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        recycler = findViewById(R.id.recyclerCarrinho);
        totalTexto = findViewById(R.id.totalTexto);
        botaoRetornar = findViewById(R.id.botaoRetornar);
        botaoFinalizarCompra = findViewById(R.id.botaoFinalizarCompra);

        botaoFinalizarCompra.setOnClickListener(v -> FinalizarCompra());
        botaoRetornar.setOnClickListener(v -> retornar());

        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CarrinhoAdapter(Carrinho.getItens(), this);
        recycler.setAdapter(adapter);

        atualizarTotal();
    }

    private void atualizarTotal() {
        totalCarrinho = Carrinho.getTotal(); // <- usando o método da classe Carrinho
        totalTexto.setText("Total: R$ " + String.format("%.2f", totalCarrinho));
    }

    private void FinalizarCompra() {
        Intent intent = new Intent(CarrinhoActivity.this, PagamentoActivity.class);
        intent.putExtra("total", totalCarrinho);
        startActivity(intent);
        finish();
    }

    public void retornar(){
        Intent intent = new Intent(CarrinhoActivity.this, ActivityMenu.class);
        startActivity(intent);
        finish();
    }
}

