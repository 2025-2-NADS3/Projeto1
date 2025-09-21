package com.example.cidades;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.os.Looper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Layout qual layout usa nessa Activity
        setContentView(R.layout.activity_main);

        // Cria um atraso de 3 segundos
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // cria um Intent para abrir a próxima tela
            Intent intent = new Intent(MainActivity.this, TelaSelecaoCidade.class);

            // Inicia a nova Activity
            startActivity(intent);

            // Finaliza a tela atual para o usuário não volte para ela ao pressionar "Voltar"
            finish();
        }, 3000); // Tempo de espera: 3 segundos
    }
}
