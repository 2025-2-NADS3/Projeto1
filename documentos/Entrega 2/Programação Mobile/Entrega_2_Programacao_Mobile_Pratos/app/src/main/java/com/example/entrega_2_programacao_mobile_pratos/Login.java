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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    // campos de texto e botões da tela
    private EditText emailInput, senhaInput;
    private Button entrarBtn, irCadastroBtn;

    // link e chave do banco (supabase)
    private static final String SUPABASE_URL = "https://iocqueffbzebmfvmgiuj.supabase.co/rest/v1/consumidor";
    private static final String SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlvY3F1ZWZmYnplYm1mdm1naXVqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjI1NTE5NTIsImV4cCI6MjA3ODEyNzk1Mn0.oNNoJFaHW3tDHl86Yb5Or-1QeNgmzUPRsi7EUkAXMFg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // conecta os elementos do xml com o código
        emailInput = findViewById(R.id.editEmailLogin);
        senhaInput = findViewById(R.id.editSenhaLogin);
        entrarBtn = findViewById(R.id.buttonEntrar);
        irCadastroBtn = findViewById(R.id.buttonIrCadastro);

        // botão pra ir pra tela de cadastro
        irCadastroBtn.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Cadastro.class));
        });

        // botão pra tentar fazer login
        entrarBtn.setOnClickListener(v -> loginUser());
    }

    // método que faz o login
    private void loginUser() {
        // pega o texto digitado nos campos
        String email = emailInput.getText().toString().trim();
        String senha = senhaInput.getText().toString().trim();

        // se algum campo estiver vazio, mostra aviso e para
        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // cria a url pra buscar o usuário com o email digitado
        String url = SUPABASE_URL + "?email=eq." + email + "&select=*";

        // cria a fila de requisições http
        RequestQueue queue = Volley.newRequestQueue(this);

        // faz a requisição pra buscar o usuário
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    // se não encontrar nada, mostra aviso
                    if (response.length() == 0) {
                        Toast.makeText(Login.this, "Email não encontrado!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        // pega o primeiro usuário encontrado
                        JSONObject user = response.getJSONObject(0);
                        String senhaBanco = user.getString("senha");

                        // compara a senha digitada com a do banco
                        if (senha.equals(senhaBanco)) {
                            Toast.makeText(Login.this, "Login realizado!", Toast.LENGTH_SHORT).show();

                            // se estiver certa, vai pra tela principal
                            Intent i = new Intent(Login.this, TelaSelecao.class);
                            startActivity(i);
                            finish();
                        } else {
                            // se a senha estiver errada
                            Toast.makeText(Login.this, "Senha incorreta!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // se der erro de conexão
                    Log.e("LOGIN_ERROR", "Erro: " + error.toString());
                    Toast.makeText(Login.this, "Falha ao conectar ao servidor.", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                // adiciona a chave de acesso no cabeçalho da requisição
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", SUPABASE_ANON_KEY);
                return headers;
            }
        };

        // adiciona a requisição na fila pra ser executada
        queue.add(request);
    }
}
