package com.example.tiaclick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityCadastro extends AppCompatActivity {

    // Botões
    Button btnVoltarInicio, btnCadastro, btnIrLogin;

    // Radio Group
    RadioGroup rbUsuario;

    // Campos
    TextInputEditText campoNome, campoEmail, campoSenha, campoConfirmarSenha, campoRA;
    TextInputLayout layoutEmail, layoutSenha, layoutRA;

    // Supabase Key
    String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlvY3F1ZWZmYnplYm1mdm1naXVqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjI1NTE5NTIsImV4cCI6MjA3ODEyNzk1Mn0.oNNoJFaHW3tDHl86Yb5Or-1QeNgmzUPRsi7EUkAXMFg";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();
        configurarBotoes();
        configurarExibicaoRA();
    }

    private void inicializarComponentes() {
        btnVoltarInicio = findViewById(R.id.btnVoltarInicio);
        btnCadastro = findViewById(R.id.btnCadastro);
        btnIrLogin = findViewById(R.id.btnIrLogin);

        rbUsuario = findViewById(R.id.rbUsuario);

        campoNome = findViewById(R.id.campoNome);
        campoEmail = findViewById(R.id.campoEmail);
        campoSenha = findViewById(R.id.campoSenha);
        campoConfirmarSenha = findViewById(R.id.campoConfirmarSenha);
        campoRA = findViewById(R.id.campoRA);

        layoutEmail = findViewById(R.id.layoutEmail);
        layoutSenha = findViewById(R.id.layoutSenha);
        layoutRA = findViewById(R.id.layoutRA);
    }

    private void configurarBotoes() {
        btnVoltarInicio.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityInicio.class));
        });

        btnIrLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityLogin.class));
        });

        btnCadastro.setOnClickListener(v -> cadastrarUsuario());
    }

    private void configurarExibicaoRA() {
        rbUsuario.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbAluno) {
                layoutRA.setVisibility(View.VISIBLE);
            } else {
                layoutRA.setVisibility(View.GONE);
                campoRA.setText("");
            }
        });
    }

    // Validação de email
    private boolean validarEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // Validação de senha
    private boolean validarSenha(String senha) {
        return senha.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");
    }

    // Interface do callback
    interface OnUidReceived {
        void onUid(String uid);
        void onErro(String mensagem);
    }

    // *** 1. Criar usuário no AUTH ***
    private void criarUsuarioAuth(String email, String senha, OnUidReceived callback) {

        String AUTH_URL = "https://iocqueffbzebmfvmgiuj.supabase.co/auth/v1/signup";

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject body = new JSONObject();

        try {
            body.put("email", email);
            body.put("password", senha);
        } catch (Exception e) {
            callback.onErro("Erro ao montar JSON.");
            return;
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, AUTH_URL, body,
                response -> {
                    try {
                        String uid = response.getJSONObject("user").getString("id");
                        callback.onUid(uid);
                    } catch (Exception e) {
                        callback.onErro("Erro ao pegar UID.");
                    }
                },
                error -> {
                    callback.onErro("Erro ao cadastrar usuário.");
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", API_KEY);
                headers.put("Authorization", "Bearer " + API_KEY);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(req);
    }

    // *** 2. Inserir usuário na tabela consumidor ***
    private void salvarDadosConsumidor(String uid, String nome, String tipo, String raAluno) {

        String URL = "https://iocqueffbzebmfvmgiuj.supabase.co/rest/v1/consumidor";

        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject body = new JSONObject();
        try {
            body.put("uid_consumidor", uid);
            body.put("nome_consumidor", nome);
            body.put("tipo_consumidor", tipo);

            if (tipo.equals("aluno") && !raAluno.isEmpty()) {
                body.put("ra", Integer.parseInt(raAluno));
            } else {
                body.put("ra", JSONObject.NULL);
            }

        } catch (Exception e) {
            Log.e("JSON", "Erro ao criar JSON: " + e.getMessage());
        }

        JSONArray arrayBody = new JSONArray();
        arrayBody.put(body);

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST, URL, arrayBody,
                response -> {
                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    moverActivity();
                },
                error -> {
                    String resposta = error.networkResponse != null ?
                            new String(error.networkResponse.data) : "Erro desconhecido";

                    Log.e("SUPABASE", resposta);
                    Toast.makeText(this, "Erro ao salvar dados.", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", API_KEY);
                headers.put("Authorization", "Bearer " + API_KEY);
                headers.put("Content-Type", "application/json");
                headers.put("Prefer", "return=representation");
                return headers;
            }
        };

        queue.add(req);
    }

    // *** 3. Função principal do cadastro ***
    private void cadastrarUsuario() {

        // Verificar se o usuário escolheu tipo
        int idSelect = rbUsuario.getCheckedRadioButtonId();
        if (idSelect == -1) {
            Toast.makeText(this, "Selecione um tipo de usuário!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rbSelect = findViewById(idSelect);

        String tipoUsuario = rbSelect.getText().toString().trim().toLowerCase();
        String nome = campoNome.getText().toString().trim();
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();
        String confirmar = campoConfirmarSenha.getText().toString().trim();
        String ra = campoRA.getText().toString().trim();

        if (!validarEmail(email)) {
            layoutEmail.setError("Email inválido!");
            return;
        }
        layoutEmail.setError(null);

        if (!validarSenha(senha)) {
            layoutSenha.setError("Senha fraca! Use letras, números e símbolo.");
            return;
        }
        layoutSenha.setError(null);

        if (!senha.equals(confirmar)) {
            campoConfirmarSenha.setError("As senhas não coincidem!");
            return;
        }

        criarUsuarioAuth(email, senha, new OnUidReceived() {
            @Override
            public void onUid(String uid) {
                salvarDadosConsumidor(uid, nome, tipoUsuario, ra);
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(ActivityCadastro.this, mensagem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moverActivity() {
        startActivity(new Intent(this, ActivityInicio.class));
        finish();
    }
}
