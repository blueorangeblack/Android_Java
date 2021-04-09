package com.example.supportlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

class Person{
    private String name;
    private String phone;

    //Generate - Constructor
    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    //Generate - Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

public class RecyclerBasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_basic);

        //RecyclerView찾아오기
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        //LayoutManager 설정 - 세로 방향
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(RecyclerBasicActivity.this,
                        LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //Adapter 클래스 객체 생성
        PersonAdapter adapter = new PersonAdapter();

        //데이터 추가1)
        //매개변수가 있는 생성자를 만들어놔서 이렇게 바로바로 데이터 넣을 수 있음
        adapter.addItem(new Person("유관순", "010-0000-0000"));
        adapter.addItem(new Person("이순신", "010-1111-1111"));

/*
        //데이터 추가 방법2)
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person("유관순", "010-0000-0000"));
        adapter.setItems(list);
*/

        //연결
        recyclerView.setAdapter(adapter);
    }
}