package com.example.tiaclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ActivitySemPedido extends AppCompatActivity {

    Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sempedido);

        btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> {
            moverActivity();
        });
    }

    private void moverActivity() {
        Intent intent = new Intent(ActivitySemPedido.this, ActivityInicio.class);
        startActivity(intent);
        finish();
    }

}
