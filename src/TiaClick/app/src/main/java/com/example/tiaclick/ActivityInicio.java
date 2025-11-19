package com.example.tiaclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityInicio extends AppCompatActivity {

    //Apenas declarar
    Button btnLogin;
    Button btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);

        //Inicializar APÓS setContentView
        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCadastro);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityInicio.this, ActivityLogin.class);
            startActivity(intent);
        });

        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityInicio.this, ActivityCadastro.class);
            startActivity(intent);
        });
    }
}
