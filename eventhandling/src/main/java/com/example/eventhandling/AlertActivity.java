package com.example.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.LinkedList;

public class AlertActivity extends AppCompatActivity {
    private Button btnAlert;

    //선택한 데이터를 저장할 문자열 프로퍼티
    private String myColor;

    //대화상자에 출력할 데이터 배열
    private String [] languages;
    //선택할 데이터를 저장할 문자열 프로퍼티
    private String myLanguage;

    //여러개를 선택했을 때 저장할 문자열 List
    private LinkedList<String> myLanguageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        // 1)
        btnAlert = (Button)findViewById(R.id.btnAlert);
        //버튼을 클릭했을 때 처리
        btnAlert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //대화상자 객체 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(AlertActivity.this);
                dlg.setTitle("제목")
                        .setMessage("내용~~")
                        .setIcon(android.R.drawable.ic_lock_idle_alarm)//ic_ 로 시작하는 것중에 다른 것으로 선택할 수도 있음
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertActivity.this, "토스트가 대화상자 닫히고 난 후 출력됩니다.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .show();
/*                //토스트 출력 - 대화상자 출력 구문과 동시에 실행되는 것처럼 동작
                Toast.makeText(AlertActivity.this, "언제 출력되나요!!", Toast.LENGTH_LONG)
                        .show();*/
            }
        });

        // 2)
        //선택 대화상자 출력하기
        Button btnItemDialog = (Button)findViewById(R.id.btnItemDialog);
        //클릭했을 때 처리
        btnItemDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AlertActivity.this)
                        .setTitle("좋아하는 색깔")
                        .setIcon(android.R.drawable.ic_input_add)
                        .setItems(R.array.color, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String [] colors =
                                        getResources().getStringArray(R.array.color);
                                myColor = colors[which];
                                Toast.makeText(AlertActivity.this, myColor, Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        // 3)
        //배열 생성
        languages = new String[]{
          "C", "C++", "C#", "Java", "Assembly", "VisualBasic",
                "Python", "Swift", "Kotlin", "Scala", "Ruby",
                "Dart", "Objective-C", "Go", "Delphi"
        };
        //버튼 찾아오기
        Button btnOneLanguage = (Button)findViewById(R.id.btnOneLanguage);
        //클릭했을 때 처리
        btnOneLanguage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AlertActivity.this)
                        .setTitle("언어를 선택하세요")
                        .setIcon(android.R.drawable.ic_menu_compass)
                        .setSingleChoiceItems(languages, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myLanguage = languages[which];
                                Log.e("선택한 언어", myLanguage);
                                Toast.makeText(AlertActivity.this, myLanguage, Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        // 4)
        //여러개 선택한  항목들을 저장할 List 생성
        myLanguageList = new LinkedList<>();
        //버튼찾아오기
        Button btnMultiLanguage = (Button)findViewById(R.id.btnMultiLanguage);
        //버튼 클릭 이벤트 처리
        btnMultiLanguage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AlertActivity.this)
                        .setTitle("좋아하는 언어를 모두 선택하세요")
                        .setIcon(android.R.drawable.ic_menu_preferences)
                        //위에 배열의 갯수만큼 false 또는 true(처음부터 체크되어있음 -> 로그나 토스트에는 출력안됨..)
                        .setMultiChoiceItems(languages, new boolean[]{
                                false, false, false, true, false,
                                false, false, false, false, false,
                                false, false, false, false, false},
                                new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                //선택을 한 경우에는 선택한 항목을 List에 추가
                                //선택을 하지 않은 경우는 List에서 제거
                                if(isChecked == true){
                                    myLanguageList.add(languages[which]);
                                }else{
                                    myLanguageList.remove(languages[which]);
                                }
                                //출력
                                Log.e("내가 선택한 언어들", myLanguageList.toString());
                                Toast.makeText(AlertActivity.this, myLanguageList.toString(), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        // 5)
        //로그인 버튼을 찾고 클릭 이벤트 처리
        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //대화상자에 출력할 뷰를 생성 - login.xml 파일을 이용
                LinearLayout login = (LinearLayout)View.inflate(
                        AlertActivity.this,
                        R.layout.login,
                        null);
                //대화상자를 생성해서 출력
                new AlertDialog.Builder(AlertActivity.this)
                        .setTitle("로그인")
                        .setIcon(android.R.drawable.ic_menu_send)
                        .setView(login)
                        .setNegativeButton("취소", null)
                        .setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //아이디 입력란 찾아오기
                                EditText tfId = login.findViewById(R.id.tfId);
                                //비밀번호 입력란 찾아오기
                                EditText tfPassword = login.findViewById(R.id.tfPassword);

                                //입력한 아이디와 비밀번호를 로그에 출력하기
                                Log.e("아이디", tfId.getText().toString());
                                Log.e("비밀번호", tfPassword.getText().toString());
                                //토스트로 출력하기
                                Toast.makeText(AlertActivity.this, "아이디 :" + tfId.getText().toString() , Toast.LENGTH_SHORT).show();
                                Toast.makeText(AlertActivity.this, "비밀번호 :" + tfPassword.getText().toString() , Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }
}