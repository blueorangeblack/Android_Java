package com.example.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class KeyboardManageActivity extends AppCompatActivity {
    //인스턴스변수=멤버변수=프로퍼티
    //뷰들의 참조를 저장할 프로퍼티를 선언
    EditText tfName;
    Button btnShowKeyboard, btnHideKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_manage);

        //뷰를 찾아오기
        tfName = (EditText)findViewById(R.id.tfName);
        btnShowKeyboard = (Button)findViewById(R.id.btnShowKeyboard);
        btnHideKeyboard = (Button)findViewById(R.id.btnHideKeyboard);

        //버튼 클릭 이벤트 처리
        btnShowKeyboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                //tfName에 입력하는 키보드를 화면에 출력합니다
                imm.showSoftInput(tfName, 0);
            }
        });

        btnHideKeyboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                //키보드 사라지게 함
                imm.hideSoftInputFromWindow(tfName.getWindowToken(), 0);
            }
        });
    }
}