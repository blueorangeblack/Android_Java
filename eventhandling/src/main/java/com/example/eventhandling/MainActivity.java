package com.example.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    //클릭 이벤트 처리를 위한 클래스
    class ClickClass implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Snackbar.make(v, "버튼의 클릭 이벤트 처리", Snackbar.LENGTH_SHORT).show();
        }
    }

    Button btnToast;
    Button btnSnackbar;
    Button btnDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToast = (Button)findViewById(R.id.btnToast);
        //버튼의 클릭 이벤트 처리
        btnToast.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "토스트 메시지", Toast.LENGTH_LONG).show();
            }
        });

        btnSnackbar = (Button)findViewById(R.id.btnSnackbar);
        btnSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "스낵바를 출력합니다.", Snackbar.LENGTH_LONG)
                        .setAction("확인", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.e("태그", "로그를 출력합니다");
                    }
                })
                        .show();
            }
        });

        btnDelegate = (Button)findViewById(R.id.btnDelegate);
        //버튼과 클릭 이벤트 처리를 위한 객체 지정
        //btnDelegate.setOnClickListener(new ClickClass());

        //익명 객체 이용
/*
        btnDelegate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "익명 객체를 이용한 이벤트 처리", Toast.LENGTH_LONG).show();
            }
        });
*/

        //람다 식 이용
        btnDelegate.setOnClickListener((View v)->{
            Toast.makeText(getApplicationContext(), "람다식을 이용한 이벤트 처리", Toast.LENGTH_LONG).show();
        });
    }


    //빈 화면 터치 이벤트가 발생했을 때 호출되는 메소드
    @Override
    public boolean onTouchEvent(MotionEvent event) {
       Toast.makeText(getApplicationContext(), "터치 이벤트 처리 - Hierarchy Model", Toast.LENGTH_LONG).show();
       return true;
    }

    //백 버튼을 누르면 현재 Activity 종료하도록 하기
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //누른 키 값을 확인 - 백 버튼을 눌렀으면
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //현재 Activity 종료
            finish();
        }
        return true;
    }
}