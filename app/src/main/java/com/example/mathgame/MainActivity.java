package com.example.mathgame;
/*
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
package com.mathgame;*/

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView firstNumberText, operatorText, secondNumberText, livesText, scoreText;
    private Button answer1, answer2, answer3;
    private Chronometer chronometer;

    private int firstNumber, secondNumber, correctAnswer, lives, score;
    private String operator;
    private Random random;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initGame();
        generateQuestion();
    }

    private void initViews() {
        firstNumberText = findViewById(R.id.firstNumber);
        operatorText = findViewById(R.id.operator);
        secondNumberText = findViewById(R.id.secondNumber);
        livesText = findViewById(R.id.lives);
        scoreText = findViewById(R.id.score);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        chronometer = findViewById(R.id.chronometer);

        answer1.setOnClickListener(v -> checkAnswer(Integer.parseInt(answer1.getText().toString())));
        answer2.setOnClickListener(v -> checkAnswer(Integer.parseInt(answer2.getText().toString())));
        answer3.setOnClickListener(v -> checkAnswer(Integer.parseInt(answer3.getText().toString())));
    }

    private void initGame() {
        random = new Random();
        lives = 3;
        score = 0;
        startTime = SystemClock.elapsedRealtime();

        updateUI();
        chronometer.setBase(startTime);
        chronometer.start();
    }

    private void generateQuestion() {
        firstNumber = random.nextInt(9) + 1;
        secondNumber = random.nextInt(9) + 1;

        String[] operators = {"+", "-", "*", "/"};
        operator = operators[random.nextInt(4)];

        switch (operator) {
            case "+":
                correctAnswer = firstNumber + secondNumber;
                break;
            case "-":
                correctAnswer = firstNumber - secondNumber;
                break;
            case "*":
                correctAnswer = firstNumber * secondNumber;
                break;
            case "/":
                // Ensure division results in whole number
                firstNumber = secondNumber * (random.nextInt(9) + 1);
                correctAnswer = firstNumber / secondNumber;
                break;
        }

        firstNumberText.setText(String.valueOf(firstNumber));
        operatorText.setText(operator);
        secondNumberText.setText(String.valueOf(secondNumber));

        generateAnswerOptions();
    }

    private void generateAnswerOptions() {
        int[] answers = new int[3];
        int correctPosition = random.nextInt(3);

        answers[correctPosition] = correctAnswer;

        for (int i = 0; i < 3; i++) {
            if (i != correctPosition) {
                int wrongAnswer;
                do {
                    wrongAnswer = correctAnswer + (random.nextInt(20) - 10);
                } while (wrongAnswer == correctAnswer || wrongAnswer < 0);
                answers[i] = wrongAnswer;
            }
        }

        answer1.setText(String.valueOf(answers[0]));
        answer2.setText(String.valueOf(answers[1]));
        answer3.setText(String.valueOf(answers[2]));
    }

    private void checkAnswer(int selectedAnswer) {
        if (selectedAnswer == correctAnswer) {
            score++;
            Toast.makeText(this, "Правильно! +1 очко", Toast.LENGTH_SHORT).show();
            generateQuestion();
        } else {
            lives--;
            Toast.makeText(this, "Неправильно! -1 жизнь", Toast.LENGTH_SHORT).show();

            if (lives <= 0) {
                endGame();
                return;
            } else {
                generateQuestion();
            }
        }
        updateUI();
    }

    private void updateUI() {
        livesText.setText("Жизни: " + lives);
        scoreText.setText("Очки: " + score);
    }

    private void endGame() {
        chronometer.stop();
        long elapsedTime = SystemClock.elapsedRealtime() - startTime;

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("time", elapsedTime);
        startActivity(intent);
        finish();
    }
}