package com.example.persistence.test_sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by commo on 2017/4/28.
 */

public class List_word extends Activity {

    private ListView listView;
    private SimpleAdapter adapter;
    private List<Map<String,Object>> data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word);
        listView= (ListView) findViewById(R.id.word_list);

        data=new ArrayList<Map<String,Object>>();
        Map<String,Object> map;
        SQLiteDatabase db=openOrCreateDatabase("test.db", Context.MODE_APPEND,null);
        String sql="select * from word";
        Cursor cursor=db.rawQuery(sql,null);
        String word2 = null;
        String explain2=null;
        while (cursor.moveToNext()){
            word2=cursor.getString(cursor.getColumnIndex("word"));
            explain2=cursor.getString(cursor.getColumnIndex("explain"));
            map=new HashMap<String ,Object>();
            map.put("word",word2);
            map.put("explain",explain2);
            data.add(map);
        }
        cursor.close();
        db.close();
        adapter=new SimpleAdapter(List_word.this,data,R.layout.wordlist,
                new String[]{"word","explain"},new int[]{R.id.textView4,R.id.textView3});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final int positions=position;
                new AlertDialog.Builder(List_word.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("提示")
                        .setMessage("确定要删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                data.remove(positions);
                                String d_word=null;
                                d_word= (String) data.get(positions).get("word");
                                adapter.notifyDataSetChanged();
                                SQLiteDatabase db=openOrCreateDatabase("test.db", Context.MODE_APPEND,null);
                                String sql="delete from word where word=?";
                                db.execSQL(sql,new String[]{d_word});
                                db.close();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .create()
                        .show();
            }
        });

    }
}
