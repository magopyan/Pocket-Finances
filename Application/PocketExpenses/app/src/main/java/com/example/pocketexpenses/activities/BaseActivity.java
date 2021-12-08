package com.example.pocketexpenses.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pocketexpenses.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}