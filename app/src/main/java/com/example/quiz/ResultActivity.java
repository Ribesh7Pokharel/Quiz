package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.quiz.databinding.ActivityResultBinding;

    public class ResultActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_result);

            String userName = getIntent().getStringExtra("USER_NAME");
            int score = getIntent().getIntExtra("SCORE", 0);
            int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);

            TextView userNameTextView = findViewById(R.id.userNameTextView);
            TextView scoreTextView = findViewById(R.id.scoreTextView);
            userNameTextView.setText("Hello, " + userName + "!");
            scoreTextView.setText("Your score: " + score + "/" + totalQuestions);

            Button restartButton = findViewById(R.id.restartButton);
            restartButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra("USER_NAME", userName); // Pass the name back if restarting the quiz
                startActivity(intent);
                finish();
            });

            Button exitButton = findViewById(R.id.exitButton);
            exitButton.setOnClickListener(v -> {
                finishAffinity(); // Closes all activities in the task and the process itself.
            });
        }
    }
