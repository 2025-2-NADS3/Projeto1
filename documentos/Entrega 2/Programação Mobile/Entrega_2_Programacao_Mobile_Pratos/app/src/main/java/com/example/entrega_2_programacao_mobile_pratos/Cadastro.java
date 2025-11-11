package com.example.entrega_2_programacao_mobile_pratos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    // campos de texto e botões da tela
    private EditText nomeEditText, raEditText, emailEditText, senhaEditText;
    private Button butaoConfirmar, tenhoContaButton;

    // link e chave do banco (supabase)
    private static final String SUPABASE_URL = "https://iocqueffbzebmfvmgiuj.supabase.co/rest/v1/consumidor";
    private static final String SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlvY3F1ZWZmYnplYm1mdm1naXVqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjI1NTE5NTIsImV4cCI6MjA3ODEyNzk1Mn0.oNNoJFaHW3tDHl86Yb5Or-1QeNgmzUPRsi7EUkAXMFg";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // cria uma fila para as requisições http
        requestQueue = Volley.newRequestQueue(this);

        // conecta os elementos do xml com o código
        nomeEditText = findViewById(R.id.nome);
        raEditText = findViewById(R.id.editNome3);
        emailEditText = findViewById(R.id.editNome2);
        senhaEditText = findViewById(R.id.editTextNumberPassword);
        butaoConfirmar = findViewById(R.id.button);
        tenhoContaButton = findViewById(R.id.tenhoConta);

        // quando clicar em "tenho conta", vai pra tela de login
        tenhoContaButton.setOnClickListener(v -> {
            startActivity(new Intent(Cadastro.this, Login.class));
        });

        // quando clicar em "confirmar", chama o método pra cadastrar
        butaoConfirmar.setOnClickListener(v -> cadastrarUsuario());
    }

    // método que faz o cadastro no banco
    private void cadastrarUsuario() {
        // pega o texto digitado nos campos
        String nome = nomeEditText.getText().toString().trim();
        String ra = raEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String senha = senhaEditText.getText().toString().trim();

        // se algum campo estiver vazio, mostra aviso e para
        if (nome.isEmpty() || ra.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // cria um objeto json com os dados do usuário
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("nome_consumidor", nome);
            userJson.put("email", email);
            userJson.put("senha", senha);
            userJson.put("ra", Integer.parseInt(ra));
            userJson.put("tipo_consumidor", "aluno"); // cadastra sempre como aluno
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar JSON de cadastro!", Toast.LENGTH_SHORT).show();
            return;
        }

        // cria a requisição http do tipo POST (enviar dados)
        JsonObjectRequest insertRequest = new JsonObjectRequest(Request.Method.POST, SUPABASE_URL, userJson,
                response -> {
                    // se der certo, mostra mensagem e vai pra tela de login
                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                    Log.d("SUPABASE", "Usuário cadastrado: " + response.toString());

                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                    finish();
                },
                error -> {
                    // se der erro, mostra o tipo de erro
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        String mensagem = "Erro ao cadastrar! Código: " + statusCode;

                        // mensagens específicas pra alguns códigos
                        if (statusCode == 409)
                            mensagem = "E-mail ou RA já cadastrados!";
                        else if (statusCode == 400)
                            mensagem = "Verifique os dados informados.";

                        Log.e("SUPABASE_INSERT", "Erro HTTP " + statusCode);
                        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
                    } else {
                        // se não tiver resposta do servidor, trata como sucesso
                        Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                // adiciona cabeçalhos necessários pro supabase aceitar o pedido
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", SUPABASE_ANON_KEY);
                headers.put("Authorization", "Bearer " + SUPABASE_ANON_KEY);
                headers.put("Content-Type", "application/json");
                headers.put("Prefer", "return=representation");
                return headers;
            }
        };

        // adiciona a requisição na fila pra ser executada
        requestQueue.add(insertRequest);
    }
}
