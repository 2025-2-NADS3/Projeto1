package com.example.tiaclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityConfirmado extends AppCompatActivity {

    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmado);

        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(v -> {
            moverActivity();
        });
    }

    private void moverActivity() {
        Intent intent = new Intent(ActivityConfirmado.this, ActivityInicio.class);
        startActivity(intent);
        finish();
    }
}
