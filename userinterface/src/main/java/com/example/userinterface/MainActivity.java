package com.example.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //인스턴스 변수를 선언 - 버튼 2개와 텍스트뷰 1개 : 화면에 디자인한 것
    //여러 메소드에서 사용하기 위해서 인스턴스 변수를 선언
    Button trueBtn, falseBtn;
    TextView targetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xml 파일에 디자인한 뷰 찾아오기 - onCreate 메소드의 setContentView 다음에 해야 함
        //먼저해도 에러는 아니지만 null이 대입됨
        trueBtn = (Button)findViewById(R.id.btn_visible_true);
        falseBtn = (Button)findViewById(R.id.btn_visible_false);
        targetView = (TextView)findViewById(R.id.text_visible_target);

        //버튼을 눌렀을 때 수행할 내용
        trueBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                targetView.setVisibility(View.VISIBLE);
                targetView.setText("setText!!");
                targetView.setTextColor(Color.DKGRAY);
                //targetView.setTextColor(new Color(rgb값 직접지정));
                //targetView.setTextColor(Color.rgb(100,200,100));
                targetView.setBackgroundColor(Color.rgb(100,200,200));
            }
        });

        falseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                targetView.setVisibility(View.INVISIBLE);
            }
        });
    }
}