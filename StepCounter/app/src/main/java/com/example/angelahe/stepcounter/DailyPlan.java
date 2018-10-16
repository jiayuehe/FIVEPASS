package com.example.angelahe.stepcounter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DailyPlan extends AppCompatActivity {

    private FloatingActionButton addExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);

        addExerciseButton = findViewById(R.id.addExerciseButton);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExerciseOptions();
            }
        });
    }

    public void openExerciseOptions() {
        Intent intent = new Intent(this, AddExerciseActivity.class);
        startActivity(intent);
    }
}
