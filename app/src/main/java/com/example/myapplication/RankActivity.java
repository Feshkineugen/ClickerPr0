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
 // Объявление констант PREF_NAME, BEST_CLICK_COUNT_KEY, BEST_CLICK_NAME_KEY для использования в SharedPreferences.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//вызов метода onCreate родительского класса
        setContentView(R.layout.activity_rank);

        int clickCount = getIntent().getIntExtra("clickCount", 0);
        TextView rankView = findViewById(R.id.rankView);
        TextView bestResultView = findViewById(R.id.bestResultView);
        EditText playerNameEditText = findViewById(R.id.playerNameEditText);
   // Инициализация переменных и извлечение значений из Intent, а также поиска и привязки виджетов.
    
        int bestResult = loadBestResult();
        String bestPlayerName = loadBestPlayerName();
        String bestRank = loadBestRank();
// Загрузка лучшего результата, имени лучшего игрока и соответствующего звания из SharedPreferences.
        
        if (clickCount > bestResult) {
    // Если текущий результат превышает лучший результат, сохраняем новый лучший результат и его ранг
    saveBestResult(clickCount, calculateRank(clickCount));
    // Получаем имя игрока из текстового поля
    String playerName = playerNameEditText.getText().toString();
    // Если имя игрока не пустое, сохраняем его как лучшее имя игрока
    if (!TextUtils.isEmpty(playerName)) {
        saveBestPlayerName(playerName);
    }
    // Устанавливаем текст в представлении лучшего результата
    bestResultView.setText("Лучший результат: " + clickCount + " (Игрок: " + playerName + ", Звание: " + calculateRank(clickCount) + ")");
} else {
    // Если текущий результат не превышает лучший результат, отображаем лучший результат и имя игрока, а также скрываем поле ввода имени игрока
    bestResultView.setText("Лучший результат: " + bestResult + " (Игрок: " + bestPlayerName + ", Звание: " + bestRank + ")");
    playerNameEditText.setVisibility(View.GONE); 
}

// Вычисляем звание для текущего результата и устанавливаем текст в соответствующем представлении
String rank = calculateRank(clickCount);
rankView.setText("Твоё звание: " + rank);
    private SharedPreferences loadBestResultPreferences() {
        return getSharedPreferences(PREF_NAME, MODE_PRIVATE);    // Возвращаем экземпляр хранилища предпочтений с именем PREF_NAME и режимом MODE_PRIVATE
    }
    private int loadBestResult() {
        return loadBestResultPreferences().getInt(BEST_CLICK_COUNT_KEY, 0); // Загружаем лучший результат из хранилища предпочтений по ключу BEST_CLICK_COUNT_KEY,
    // если значение не найдено, возвращаем 0
    }

    private String loadBestRank() {
        return loadBestResultPreferences().getString("BestRank", "");// Загружаем лучший ранг из хранилища предпочтений по ключу "BestRank",
    // если значение не найдено, возвращаем пустую строку ("")
    }

    private void saveBestResult(int bestResult, String rank) {
        SharedPreferences preferences = loadBestResultPreferences(); // Загружаем экземпляр хранилища 
        SharedPreferences.Editor editor = preferences.edit();// Получаем доступ к редактору хранилища для внесения изменений
        editor.putInt(BEST_CLICK_COUNT_KEY, bestResult);/ Сохраняем лучший результат по ключу BEST_CLICK_COUNT_KEY
        editor.putString("BestRank", rank);// Сохраняем ранг по ключу "BestRank"
        editor.apply();   // Применяем все внесенные изменения
    }

    private String loadBestPlayerName() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);//В этой строке мы получаем объект `SharedPreferences`, связанный с именем PREF_NAME. `MODE_PRIVATE` указывает, что созданный файл будет доступен только для данного приложения
        return preferences.getString(BEST_CLICK_NAME_KEY, "");//Если значение с таким ключом не найдено, то будет возвращена пустая строка 
    }

    private void saveBestPlayerName(String playerName) { 
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);//В этой строке мы получаем объект `SharedPreferences`, связанный с именем PREF_NAME. `MODE_PRIVATE` указывает, что созданный файл будет доступен только для данного приложения
        SharedPreferences.Editor editor = preferences.edit();///Здесь мы получаем объект `Editor` из `SharedPreferences`. Редактор используется для внесения изменений в данные `SharedPreferences`.
        editor.putString(BEST_CLICK_NAME_KEY, playerName);//Здесь мы помещаем значение `playerName` в `SharedPreferences` с ключом `BEST_CLICK_NAME_KEY`. Это позволит нам сохранить имя лучшего игрока для последующего использования.
        editor.apply();//Этот вызов применяет все изменения, внесенные в редактор, к объекту `SharedPreferences`. Важно использовать `apply()`, чтобы гарантировать, что изменения будут сохранены асинхронно.
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

        super.onDestroy();//вызов метода onDestroy родительского класса, что позволяет реализовать дополнительную логику перед уничтожением активности.
        
        int clickCount = getIntent().getIntExtra("clickCount", 0);// извлечение значения clickCount из данных Intent, которые были отправлены в эту активность.
        saveBestResult(clickCount, calculateRank(clickCount));//вызов метода saveBestResult, который сохраняет лучший результат и соответствующее звание в зависимости от значения clickCount. Результаты хранятся в SharedPreferences.
        String playerName = ((EditText) findViewById(R.id.playerNameEditText)).getText().toString();//получение текста из поля ввода, где игрок может ввести свое имя.
        if (!TextUtils.isEmpty(playerName)) {
            saveBestPlayerName(playerName);//проверка, является ли введенное имя пустым. Если оно не пустое, то имя игрока сохраняется с помощью метода 
        }
    }
}
