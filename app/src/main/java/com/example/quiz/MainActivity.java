package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameEditText = findViewById(R.id.nameEditText);
        Button startQuizButton = findViewById(R.id.startQuizButton);

        startQuizButton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString();
            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra("USER_NAME", name);
            startActivity(intent);
        });
    }
}