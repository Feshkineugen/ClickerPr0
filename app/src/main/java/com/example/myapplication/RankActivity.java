package com.example.myapplication;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RankActivity extends AppCompatActivity {

    private static final String PREF_NAME = "ClickerPrefs";
    private static final String BEST_CLICK_COUNT_KEY = "BestClickCount";
    private static final String BEST_CLICK_NAME_KEY = "BestClickName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        int clickCount = getIntent().getIntExtra("clickCount", 0);
        TextView rankView = findViewById(R.id.rankView);
        TextView bestResultView = findViewById(R.id.bestResultView);
        EditText playerNameEditText = findViewById(R.id.playerNameEditText);

        // Load the best result, player name, and rank
        int bestResult = loadBestResult();
        String bestPlayerName = loadBestPlayerName();
        String bestRank = loadBestRank();

        // Check if the current click count is better than the best result
        if (clickCount > bestResult) {
            // Update best result, player name, and rank if the current click count is better
            saveBestResult(clickCount, calculateRank(clickCount));
            String playerName = playerNameEditText.getText().toString();
            if (!TextUtils.isEmpty(playerName)) {
                saveBestPlayerName(playerName);
            }
            bestResultView.setText("Лучший результат: " + clickCount + " (Игрок: " + playerName + ", Звание: " + calculateRank(clickCount) + ")");
        } else {
            // Display the best result, player name, and rank without prompting for player name
            bestResultView.setText("Лучший результат: " + bestResult + " (Игрок: " + bestPlayerName + ", Звание: " + bestRank + ")");
            playerNameEditText.setVisibility(View.GONE);  // Hide the player name input field
        }

        String rank = calculateRank(clickCount);
        rankView.setText("Твоё звание: " + rank);
    }
    private SharedPreferences loadBestResultPreferences() {
        return getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }
    private int loadBestResult() {
        return loadBestResultPreferences().getInt(BEST_CLICK_COUNT_KEY, 0);
    }

    private String loadBestRank() {
        return loadBestResultPreferences().getString("BestRank", "");
    }

    private void saveBestResult(int bestResult, String rank) {
        SharedPreferences preferences = loadBestResultPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(BEST_CLICK_COUNT_KEY, bestResult);
        editor.putString("BestRank", rank);
        editor.apply();
    }

    private String loadBestPlayerName() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return preferences.getString(BEST_CLICK_NAME_KEY, "");
    }

    private void saveBestPlayerName(String playerName) {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(BEST_CLICK_NAME_KEY, playerName);
        editor.apply();
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
    protected void onDestroy() {
        super.onDestroy();
        // Save the best result, player name, and rank when the activity is destroyed
        int clickCount = getIntent().getIntExtra("clickCount", 0);
        saveBestResult(clickCount, calculateRank(clickCount));
        String playerName = ((EditText) findViewById(R.id.playerNameEditText)).getText().toString();
        if (!TextUtils.isEmpty(playerName)) {
            saveBestPlayerName(playerName);
        }
    }
}