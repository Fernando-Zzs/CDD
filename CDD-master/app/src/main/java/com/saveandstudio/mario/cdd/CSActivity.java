package com.saveandstudio.mario.cdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.saveandstudio.mario.cdd.Components.GameSystem;
import com.saveandstudio.mario.cdd.GameBasic.Global;

import java.util.ArrayList;

public class CSActivity extends AppCompatActivity {
    private Button mBtn_server;
    private Button mBtn_client;
    private GameSystem gameSystem = new GameSystem();
    public static ArrayList<com.saveandstudio.mario.cdd.Prefabs.Card> cards = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs);

        mBtn_server = findViewById(R.id.btn_server);
        mBtn_client = findViewById(R.id.btn_client);

        Global.seed = (int) (1 + Math.random() * (1000000));
        setOnClickListener();
    }

    public void setOnClickListener() {
        OnClick onClick = new OnClick();

        mBtn_server.setOnClickListener(onClick);
        mBtn_client.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_server:
                    intent = new Intent(CSActivity.this, GameActivity.class);
                    Global.isServer = true;
                    Global.player_id = 0;
                    break;
                case R.id.btn_client:
                    intent = new Intent(CSActivity.this, GameActivity.class);
                    Global.isServer = false;
                    Global.player_id = 1;
                    Toast.makeText(CSActivity.this, "正在进入游戏……", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_bound_devices:
                default:
                    break;
            }
            startActivity(intent);
        }
    }
}
