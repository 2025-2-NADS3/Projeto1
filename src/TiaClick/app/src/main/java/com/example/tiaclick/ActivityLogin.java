package com.example.tiaclick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityLogin extends AppCompatActivity {

    Button btnVoltarInicio, btnLogar, btnIrCadastro;
    TextInputEditText campoEmail, campoSenha;

    // Supabase Key
    String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlvY3F1ZWZmYnplYm1mdm1naXVqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjI1NTE5NTIsImV4cCI6MjA3ODEyNzk1Mn0.oNNoJFaHW3tDHl86Yb5Or-1QeNgmzUPRsi7EUkAXMFg";

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btnVoltarInicio = findViewById(R.id.btnVoltarInicio);
        btnIrCadastro = findViewById(R.id.btnIrCadastro);
        btnLogar = findViewById(R.id.btnLogar);

        campoEmail = findViewById(R.id.campoEmail);
        campoSenha = findViewById(R.id.campoSenha);

        btnVoltarInicio.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityInicio.class));
        });

        btnIrCadastro.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityCadastro.class));
        });

        btnLogar.setOnClickListener(v -> loginUsuario());
    }

    private void loginUsuario() {

        String AUTH_URL = "https://iocqueffbzebmfvmgiuj.supabase.co/auth/v1/token?grant_type=password";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject body = new JSONObject();
        try {
            body.put("email", Objects.requireNonNull(campoEmail.getText()).toString().trim());
            body.put("password", Objects.requireNonNull(campoSenha.getText()).toString().trim());
        } catch (Exception e) {
            Log.e("JSON", "Erro ao montar JSON: " + e.getMessage());
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                AUTH_URL,
                body,
                response -> {
                    Log.d("LOGIN", "Resposta: " + response);

                    try {
                        // Pegando ID do usuário logado
                        String userId = response.getJSONObject("user").getString("id");

                        // Pegando token (opcional mas útil)
                        String token = response.getString("access_token");

                        // Salvando no SharedPreferences
                        getSharedPreferences("APP_PREF", MODE_PRIVATE)
                                .edit()
                                .putString("USER_ID", userId)
                                .putString("TOKEN", token)
                                .apply();

                        Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        moverActivity();

                    } catch (Exception e) {
                        Log.e("LOGIN", "Erro ao processar login: " + e.getMessage());
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        String msg = new String(error.networkResponse.data);
                        Log.e("LOGIN", "Erro (" + error.networkResponse.statusCode + "): " + msg);
                    } else {
                        Log.e("LOGIN", "Erro inesperado: " + error.toString());
                    }

                    Toast.makeText(this, "E-mail ou senha incorretos!", Toast.LENGTH_SHORT).show();
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

        queue.add(request);
    }

    private void moverActivity() {
        Intent intent = new Intent(ActivityLogin.this, ActivityMenu.class);
        startActivity(intent);
        finish();
    }
}
