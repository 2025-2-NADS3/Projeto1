package com.example.tiaclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PedidoConfirmado extends AppCompatActivity {

    Button botaovoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmado);

        botaovoltar = findViewById(R.id.btnConfirmar); // O ID precisa EXISTIR

        botaovoltar.setOnClickListener(v -> voltarIntent());
    }


    public void voltarIntent() {
        Intent intent = new Intent(PedidoConfirmado.this, ActivityMenu.class);
        startActivity(intent);
        finish();
    }

}
