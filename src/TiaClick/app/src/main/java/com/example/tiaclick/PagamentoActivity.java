package com.example.tiaclick;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PagamentoActivity extends AppCompatActivity {

    TextView valorTotalPagamento;
    RadioGroup grupoPagamentos;
    Button btnRealizarPagamento;
    double totalRecebido;

    String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlvY3F1ZWZmYnplYm1mdm1naXVqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjI1NTE5NTIsImV4cCI6MjA3ODEyNzk1Mn0.oNNoJFaHW3tDHl86Yb5Or-1QeNgmzUPRsi7EUkAXMFg";
    String BASE_URL = "https://iocqueffbzebmfvmgiuj.supabase.co/rest/v1/pedido";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);

        valorTotalPagamento = findViewById(R.id.valorTotalPagamento);
        grupoPagamentos = findViewById(R.id.grupoPagamentos);
        btnRealizarPagamento = findViewById(R.id.btnRealizarPagamento);

        // Pegando o total recebido
        totalRecebido = getIntent().getDoubleExtra("total", 0.0);
        valorTotalPagamento.setText(String.format("R$ %.2f", totalRecebido));

        btnRealizarPagamento.setOnClickListener(v -> {

            if (grupoPagamentos.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Selecione um método de pagamento", Toast.LENGTH_SHORT).show();
                return;
            }

            registrarPedido();
        });
    }

    private void registrarPedido() {

        // Recupera o ID do usuário salvo no login
        String userId = getSharedPreferences("APP_PREF", MODE_PRIVATE)
                .getString("USER_ID", null);

        if (userId == null) {
            Toast.makeText(this, "Erro: usuário não encontrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        // JSON que será enviado para Supabase
        String jsonBody = "{\n" +
                "\"uid_consumidor\": \"" + userId + "\",\n" +
                "\"valor_total\": " + totalRecebido + ",\n" +
                "\"status_pedido\": \"aberto\"\n" +
                "}";

        Log.d("JSON_ENVIADO", jsonBody);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(
                Request.Method.POST,
                BASE_URL,
                response -> {
                    Log.d("SUPABASE", "Resposta: " + response);

                    Toast.makeText(this, "Pedido registrado!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(PagamentoActivity.this, PedidoConfirmado.class);
                    startActivity(intent);
                    finish();
                },
                error -> {
                    if (error.networkResponse != null) {
                        String retornoErro = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.e("SUPABASE", "Erro (" + error.networkResponse.statusCode + "): " + retornoErro);
                    } else {
                        Log.e("SUPABASE", "Erro desconhecido: " + error.toString());
                    }

                    Toast.makeText(this, "Erro ao registrar pedido", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            public byte[] getBody() {
                return jsonBody.getBytes(StandardCharsets.UTF_8);
            }

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

        queue.add(request);
    }
}
