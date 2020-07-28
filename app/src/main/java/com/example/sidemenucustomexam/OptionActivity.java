package com.example.sidemenucustomexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class OptionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OptionActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}