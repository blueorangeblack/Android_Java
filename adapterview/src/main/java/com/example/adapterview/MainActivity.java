package com.example.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //ListView 출력을 위한 프로퍼티
    ListView listview;

/*
    // 1)
    //데이터모임과 Adapter의 제너릭은 항상 일치해야 함
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
*/

    // 2)
    ArrayAdapter<CharSequence> adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView 찾아오기
        listview = (ListView)findViewById(R.id.listview);

/*
        // 1) 데이터 생성

        list = new ArrayList<>();
        list.add("Finn");
        list.add("Jake");
        list.add("BMO");
        list.add("Bubblegum");
        list.add("Marceline");
        list.add("LadyRainicorn");
        list.add("IceKing");
        list.add("LSP");
        list.add("Susan");
        list.add("Gunter");
        list.add("Lemongrab");
        list.add("Peppermint");
        list.add("FlamePrincess");
        list.add("Billy");

        // 어댑터 생성 - ArrayList를 배열로 만들어도 됨
        adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, list);
*/


        // 2) 리소스를 이용해서 Adapter를 생성
        adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.oop, android.R.layout.simple_list_item_1);

        //ListView와 ArrayAdapter 연결
        listview.setAdapter(adapter);
    }
}