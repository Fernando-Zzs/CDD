package com.saveandstudio.mario.cdd;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.saveandstudio.mario.cdd.GameBasic.Global;

public class CSActivity extends AppCompatActivity {
    private Button mBtn_server;
    private Button mBtn_client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs);

        mBtn_server = findViewById(R.id.btn_server);
        mBtn_client = findViewById(R.id.btn_client);

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
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
    }
}
