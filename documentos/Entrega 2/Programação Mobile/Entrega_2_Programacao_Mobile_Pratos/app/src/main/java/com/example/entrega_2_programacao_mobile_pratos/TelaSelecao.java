package com.example.entrega_2_programacao_mobile_pratos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TelaSelecao extends AppCompatActivity {

    // url da tabela "cardapio" no banco supabase
    private static final String SUPABASE_URL = "https://iocqueffbzebmfvmgiuj.supabase.co/rest/v1/cardapio?select=*";

    // chave de api necessária para autenticação nas requisições do supabase
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlvY3F1ZWZmYnplYm1mdm1naXVqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjI1NTE5NTIsImV4cCI6MjA3ODEyNzk1Mn0.oNNoJFaHW3tDHl86Yb5Or-1QeNgmzUPRsi7EUkAXMFg";

    // elementos principais da tela
    RecyclerView recyclerView;               // mostra a lista dos produtos
    ArrayList<Produto> listaProdutos = new ArrayList<>();  // guarda os produtos carregados do banco
    ProdutoAdapter produtoAdapter;           // adapter que liga os dados à recyclerview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar); // define o layout da tela

        // configura a recyclerview
        recyclerView = findViewById(R.id.recyclerProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // define que os itens ficarão em lista vertical

        // cria o adapter passando o contexto e a lista de produtos
        produtoAdapter = new ProdutoAdapter(this, listaProdutos);
        recyclerView.setAdapter(produtoAdapter); // liga o adapter à recyclerview

        // chama o método que vai buscar os dados no banco
        carregarDados();
    }

    private void carregarDados() {
        // cria uma requisição get para pegar todos os registros da tabela "cardapio"
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                SUPABASE_URL,
                null,
                response -> {
                    // se a requisição for bem-sucedida:
                    listaProdutos.clear(); // limpa a lista antes de adicionar os novos itens
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            // lê os campos de cada produto do json retornado
                            String id = response.getJSONObject(i).getString("uid_produto");
                            String tipo = response.getJSONObject(i).getString("tipo_produto");
                            String nome = response.getJSONObject(i).getString("nome_produto");
                            String descricao = response.getJSONObject(i).getString("descricao");
                            double preco = response.getJSONObject(i).getDouble("preco");
                            int estoque = response.getJSONObject(i).optInt("estoque", 0); // usa 0 se não existir
                            boolean disponivel = response.getJSONObject(i).optBoolean("disponivel", true); // padrão: true
                            String imagemUrl = response.getJSONObject(i).optString("imagemurl", ""); // padrão: vazio
                            double rating = response.getJSONObject(i).optDouble("rating"); // média de avaliação

                            // cria o objeto produto e adiciona na lista
                            listaProdutos.add(new Produto(id, tipo, nome, descricao, preco, estoque, disponivel, imagemUrl, rating));
                        }

                        // atualiza a tela com os novos dados
                        produtoAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        // mostra erro de leitura de json no logcat
                        Log.e("supabase", "erro ao ler json: " + e.getMessage());
                    }
                },
                error -> Log.e("supabase", "erro na requisição: " + error.getMessage()) // caso a requisição falhe
        ) {
            // define os cabeçalhos da requisição (obrigatórios pro supabase)
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", API_KEY);
                headers.put("Authorization", "Bearer " + API_KEY);
                return headers;
            }
        };

        // adiciona a requisição à fila de execução do volley (executa a chamada http)
        Volley.newRequestQueue(this).add(request);
    }

}
