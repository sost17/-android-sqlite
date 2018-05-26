package com.example.persistence.test_sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by commo on 2017/4/28.
 */

public class Login extends Activity{

    private Button insert_word;
    private Button list_word;
    private TextView word;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        word= (TextView) findViewById(R.id.textView5);
        insert_word= (Button) findViewById(R.id.button4);
        list_word = (Button) findViewById(R.id.button3);

        insert_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Login.this,Main.class);
                startActivity(intent);
                finish();
            }
        });

        list_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Login.this,List_word.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
