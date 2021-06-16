package com.saveandstudio.mario.cdd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.sql.BatchUpdateException;

public class MainActivity extends AppCompatActivity {
    private Button mBtn_start_game;
    private Button mBtn_exit_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn_start_game = findViewById(R.id.btn_start_game);
        mBtn_exit_game = findViewById(R.id.btn_exit_game);

        mBtn_start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CSActivity.class);
                startActivity(intent);
            }
        });
        mBtn_exit_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}