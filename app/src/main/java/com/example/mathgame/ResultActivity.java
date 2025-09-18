package com.example.mathgame;
/*
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
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
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    private TextView scoreText, timeText;
    private Button playAgainButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initViews();
        displayResults();
    }

    private void initViews() {
        scoreText = findViewById(R.id.finalScore);
        timeText = findViewById(R.id.gameTime);
        playAgainButton = findViewById(R.id.playAgain);
        exitButton = findViewById(R.id.exit);

        playAgainButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        exitButton.setOnClickListener(v -> finish());
    }

    private void displayResults() {
        int score = getIntent().getIntExtra("score", 0);
        long elapsedTime = getIntent().getLongExtra("time", 0);

        scoreText.setText("Ваши очки: " + score);

        // Convert milliseconds to minutes and seconds
        int minutes = (int) (elapsedTime / 60000);
        int seconds = (int) ((elapsedTime % 60000) / 1000);

        timeText.setText(String.format("Время игры: %d:%02d", minutes, seconds));
    }
}