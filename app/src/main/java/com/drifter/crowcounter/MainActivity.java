package com.drifter.crowcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private long crowNum;
    TextView crowText;
    private static final String KEY_COUNT = "crowNum";
    private SharedPreferences settingsFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crowText = findViewById(R.id.crowsText);

        settingsFile = getSharedPreferences(KEY_COUNT, Context.MODE_PRIVATE);

        if (savedInstanceState != null) {
           crowNum = savedInstanceState.getLong(KEY_COUNT, 0);
           if(crowNum != 0){
           crowText.setText("Насчитано " + crowNum + " ворон");
           }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.about: {
                Intent intent = new Intent(MainActivity.this,
                        AboutActivity.class);
                intent.putExtra(KEY_COUNT, crowNum);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = settingsFile.edit();
        editor.putLong(KEY_COUNT, crowNum);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(settingsFile.contains(KEY_COUNT)) {
            crowNum = settingsFile.getLong(KEY_COUNT, 0);
            crowText.setText("Насчитано " + crowNum + " ворон");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_COUNT, crowNum);
    }

    public void countCrows(View view) {
        crowNum = crowNum+1;
        crowText.setText("Насчитано " + crowNum + " ворон");
    }
}