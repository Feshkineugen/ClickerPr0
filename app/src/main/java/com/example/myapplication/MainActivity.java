package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView timerView;
    private TextView resultView;
    private TextView clickCountView;
    private Button clickButton;
    private int clickCount = 0;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerView = findViewById(R.id.timerView);
        resultView = findViewById(R.id.resultView);
        clickButton = findViewById(R.id.clickButton);
        clickCountView = findViewById(R.id.clickCountView);

        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                timerView.setText("Время: " + l / 1000 + " Секунд");
            }
            private void openRankActivity(int clickCount) {
                Intent intent = new Intent(MainActivity.this, RankActivity.class);
                intent.putExtra("clickCount", clickCount);
                startActivity(intent);
            }
            @Override
            public void onFinish() {
                clickButton.setEnabled(false);
                openRankActivity(clickCount);
            }
        };

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount++;
                clickCountView.setText("Клики: " + clickCount);
                resultView.setText("Твоё звание: " + calculateRank(clickCount));
            }
        });
    }

    private String calculateRank(int clickCount) {
        if (clickCount < 10) {
            return "Салага";
        } else if (clickCount < 30) {
            return "Новичок";
        } else if (clickCount < 60) {
            return "Бывалый";
        } else if (clickCount < 90) {
            return "Кликер-Мастер";
        } else if (clickCount < 120) {
            return "Король кликов";
        } else if (clickCount < 150) {
            return "Убийца мышки ";
        }
        return "Бог";
    }

    @Override
    protected void onStart() {
        super.onStart();
        countDownTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }
}