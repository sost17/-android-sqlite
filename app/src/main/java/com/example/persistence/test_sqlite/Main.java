package com.example.persistence.test_sqlite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity implements View.OnClickListener {
    private EditText word,explain;
    private Button insertword,listall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        word= (EditText) findViewById(R.id.editText);
        explain= (EditText) findViewById(R.id.editText2);
        insertword= (Button) findViewById(R.id.button);
        listall= (Button) findViewById(R.id.button2);
        insertword.setOnClickListener(this);
        listall.setOnClickListener(this);

        SQLiteDatabase db=openOrCreateDatabase("test.db", Context.MODE_APPEND,null);
        Cursor cursor1=db.rawQuery("select count(*) from sqlite_master where type='table' and name='word'",null);
        int aa=0;
        while(cursor1.moveToNext()) {
            aa=cursor1.getInt(0);
        }
        if(aa==0){
            db.execSQL("create table word(word varchar(100),explain varchar(100))");
        }
        cursor1.close();
        db.close();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                String word1=word.getText().toString();
                String explain1=explain.getText().toString();
                if(word1.equals("")||explain1.equals("")){
                    Toast.makeText(Main.this,"单词或解释不能为空",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SQLiteDatabase db = openOrCreateDatabase("test.db", Context.MODE_APPEND, null);
                    Cursor cursor1=db.rawQuery("select count(*) from word where word = ?",new String[]{word1});
                    int aa=0;
                    while(cursor1.moveToNext()) {
                        aa=cursor1.getInt(0);
                    }
                    if(aa==0) {
                        db.execSQL("insert into word values(?,?)", new String[]{word1, explain1});
                        db.close();
                        Toast.makeText(Main.this, "插入成功", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Main.this, "单词已存在", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.button2:
                Intent intent = new Intent();
                intent.setClass(Main.this,List_word.class);
                startActivity(intent);

        }
    }
}
