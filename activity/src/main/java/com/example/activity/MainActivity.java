package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 클릭 이벤트 처리
        Button btnGoSub = (Button)findViewById(R.id.btnGoSub);
        btnGoSub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
/*
                // 1) 명시적 intent
                //하위 Activity출력을 위한 Intent생성
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                //하위 Activity출력
                startActivity(intent);
*/
/*

                // 2) 암시적 intent
                Intent intent = new Intent();
                intent.setAction("com.example.ACTION_VIEW");
                startActivity(intent);
*/
/*

                // 3) intent사이의 데이터 전달
                //상위Activity가 전달한 데이터를 하위Activity에서 출력하기
                //암시적 intent 호출
                Intent intent = new Intent();
                //입력한 내용 가져오기
                EditText mainEdit = (EditText)findViewById(R.id.mainEdit);
                String msg = mainEdit.getText().toString();
                //출력할 Activity에 데이터 전달
                intent.putExtra("data",msg);

                intent.setAction("com.example.ACTION_VIEW");
                startActivity(intent);
*/

                // 4) intent사이의 데이터 전달
                //하위Activity가 전달한 데이터를 상위Activity에서 출력하기
                //암시적 intent 호출
                Intent intent = new Intent();
                //입력한 내용 가져오기
                EditText mainEdit = (EditText)findViewById(R.id.mainEdit);
                String msg = mainEdit.getText().toString();
                //출력할 Activity에 데이터 전달
                intent.putExtra("data",msg);

                intent.setAction("com.example.ACTION_VIEW");

                //SubActivity가 소멸될 때 콜백 메소드를 호출하도록 작성
                //10은 구분하기 위한 코드
                startActivityForResult(intent, 10);
            }
        });
    }

    //하위 Activity가 사라질 때 호출되는 메소드
    //이름을 잘못입력했으면 @Override를 지우라고 에러
    //그런 경우가 아니면 return을 해야 하거나 상위 클래스의 메소드를 호출라하고 에러
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode){
            case 10:
                switch (resultCode){
                    case RESULT_OK:
                        //전달된 데이터를 찾아서 출력하기
                        String msg = intent.getStringExtra("subData");
                        EditText mainEdit = (EditText)findViewById(R.id.mainEdit);
                        mainEdit.setText(msg);

                }
        }
    }
}