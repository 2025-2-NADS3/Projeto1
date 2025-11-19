package com.example.tiaclick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class ListaProdutosActivity extends AppCompatActivity {

String API = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlvY3F1ZWZmYnplYm1mdm1naXVqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjI1NTE5NTIsImV4cCI6MjA3ODEyNzk1Mn0.oNNoJFaHW3tDHl86Yb5Or-1QeNgmzUPRsi7EUkAXMFg";

    RecyclerView recyclerView;
    ProdutosAdapter adapter;
    ArrayList<Produto> lista = new ArrayList<>();

    ImageView retorno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        String categoria = getIntent().getStringExtra("categoria");

        retorno = findViewById(R.id.botaoRetornar);
        recyclerView = findViewById(R.id.recyclerProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProdutosAdapter(this, lista);

        retorno.setOnClickListener(v -> retornar());
        recyclerView.setAdapter(adapter);

        carregarProdutos(categoria);
    }

    private void carregarProdutos(String categoria) {

        String url = "https://iocqueffbzebmfvmgiuj.supabase.co/rest/v1/cardapio"
                + "?select=*"
                + "&tipo_produto=eq." + categoria;

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    lista.clear();
                    try {
                        for (int i = 0; i < response.length(); i++) {

                            var obj = response.getJSONObject(i);

                            Produto p = new Produto(
                                    obj.getString("nome_produto"),
                                    obj.getDouble("preco"),
                                    obj.getString("imagemurl")
                            );


                            lista.add(p);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Log.e("ERRO_JSON", e.toString());
                        Toast.makeText(this, "Erro ao processar dados!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("ERRO_SUPABASE", error.toString());
                    Toast.makeText(this, "Erro ao carregar produtos!", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("apikey", API);
                headers.put("Authorization", API);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    public void retornar(){
        Intent intent = new Intent(ListaProdutosActivity.this, ActivityMenu.class);
        startActivity(intent);
        finish();
    }
}
