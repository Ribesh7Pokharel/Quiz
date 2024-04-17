package com.example.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.example.quiz.Question;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.quiz.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {

    private Question[] questions = new Question[]{
            new Question("What is the capital of France?", new String[]{"Paris", "London", "Rome", "Berlin"}, 0),
            new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 1),
            new Question("Who painted the Mona Lisa?", new String[]{"Vincent Van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"}, 2),
            new Question("What is the chemical symbol for water?", new String[]{"H2O", "CO2", "NaCl", "O2"}, 0),
            new Question("What element is a diamond made entirely of?", new String[]{"Gold", "Carbon", "Silicon", "Oxygen"}, 1)
    };
    private int currentQuestionIndex = 0;
    private int score = 0;

    private TextView questionTextView;
    private RadioGroup answersGroup;
    private ProgressBar quizProgressBar;

    private RadioGroup.OnCheckedChangeListener answerListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton selectedAnswer = findViewById(checkedId);
            int answerIndex = answersGroup.indexOfChild(selectedAnswer);
            if (answerIndex == questions[currentQuestionIndex].getAnswerIndex()) {
                selectedAnswer.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                score++;
            } else {
                selectedAnswer.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }

            new Handler().postDelayed(() -> {
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    displayQuestion();
                    answersGroup.setOnCheckedChangeListener(answerListener); // Use the listener reference
                } else {
                    showResults();
                }
            }, 2000); // Delay
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        answersGroup = findViewById(R.id.answersGroup);
        quizProgressBar = findViewById(R.id.quizProgressBar);

        quizProgressBar.setMax(questions.length);
        displayQuestion();

        answersGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Temporarily remove the listener to avoid firing while updating radio buttons
            group.setOnCheckedChangeListener(null);

            RadioButton selectedAnswer = findViewById(checkedId);
            answersGroup.setOnCheckedChangeListener(answerListener);
            int answerIndex = answersGroup.indexOfChild(selectedAnswer);
            if (answerIndex == questions[currentQuestionIndex].getAnswerIndex()) {
                selectedAnswer.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                score++;
            } else {
                selectedAnswer.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }

            new Handler().postDelayed(() -> {
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    displayQuestion();
                    answersGroup.setOnCheckedChangeListener(answerListener);

                } else {
                    showResults();
                }
            }, 2000); // 2 seconds delay to show the color before moving to next question
        });
    }

    private void displayQuestion() {
        Question currentQuestion = questions[currentQuestionIndex];
        questionTextView.setText(currentQuestion.getQuestionText());
        answersGroup.removeAllViews();

        for (int i = 0; i < currentQuestion.getOptions().length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(currentQuestion.getOptions()[i]);
            radioButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            answersGroup.addView(radioButton);
        }
        quizProgressBar.setProgress(currentQuestionIndex + 1);
    }

    private void showResults() {
        Intent intent = new Intent(this, ResultActivity.class);
        String userName = getIntent().getStringExtra("USER_NAME"); // Retrieve the user name passed from WelcomeActivity
        intent.putExtra("USER_NAME", userName); // Pass the name to ResultActivity
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", questions.length);
        startActivity(intent);
        finish(); // This will close the current activity
    }
}