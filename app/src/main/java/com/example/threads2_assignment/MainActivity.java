package com.example.threads2_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MainActivity extends AppCompatActivity {

    private EditText mEtText;
    private TextView mTvText;
    private Button mBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = mEtText.getText().toString();
                WorkerThread workerThread = new WorkerThread(MainActivity.this,"async", mainThreadHandler, data);
                workerThread.start();
            }
        });

    }

    private void initViews() {
        mEtText = findViewById(R.id.etText);
        mTvText = findViewById(R.id.tvText);
        mBtnSave = findViewById(R.id.btnWrite);
    }

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String data = (String) msg.obj;
            mTvText.setText(data);
        }
    };
}