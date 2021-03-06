package com.example.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {
    //xml 파일에 디자인한 뷰의 참조를 저장하기 위한 변수
    TextView txtView;
    EditText txtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        //TextView 가져오기
          //TextView txtView = (TextView)findViewById(R.id.txtView);
        //문자열 설정 (set은 변환하지 않고 할 수 있음)
          //txtView.setText("다른 문자열");
        //문자열 가져오기
        //String content = txtView.getText(); 그냥 이렇게 가져올 수 없음
        //toString()을 이용해서 문자열로 변환해서 String에 대입해야 함
        //EditText나 TextView의 입력된 문자열의 자료형은 Editable이나
        //CharSequence라서 String에 바로 대입이 안됨
        //반대로 설정할 때는 String이 Editable이나 CharSequence를
        //implements했기 때문에 대입이 가능
          //String content = txtView.getText().toString();
        
        //xml 파일에 디자인한 뷰 찾아오기
        txtView = (TextView)findViewById(R.id.txtView);
        txtId = (EditText)findViewById(R.id.txtId);
        
        //txtId의 내용이 변경되면 작업을 해주는 코드
        txtId.addTextChangedListener(new TextWatcher() {
            //EditText의 내용이 변경되기 직전에 호출되는 메소드
            //s가 입력된 문자열
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            //텍스트가 변경된 후에 호출되는 메소드
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //EditText의 내용을 TextView에 출력
                txtView.setText(txtId.getText());
            }

            //텍스트가 변경된 후에 호출되는 메소드
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}