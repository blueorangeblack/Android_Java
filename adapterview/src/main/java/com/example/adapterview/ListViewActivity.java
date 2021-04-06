package com.example.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    //ListView 출력 관련 프로퍼티
    ListView listview;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    //ListAdapter<String> adapter;로 해도 됨

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //ListView 출력 관련 초기화 작업
        listview = (ListView)findViewById(R.id.listview);

        list = new ArrayList<>();
        list.add("Google");
        list.add("Apple");
        list.add("삼성");
        list.add("LG");

        //매개변수 (액티비티, 모양, 데이터)
        //android.R 로 시작하면 안드로이드에서 제공하는 리소스
        //R 로 시작하면 사용자가 삽입한 리소스
        adapter = new ArrayAdapter<>(
                ListViewActivity.this,
                //android.R.layout.simple_list_item_1,
                //android.R.layout.simple_list_item_single_choice, //라디오버튼을 옆에 배치하는 모양
                android.R.layout.simple_list_item_multiple_choice, //체크박스를 옆에 배치하는 모양
                list);

        //ListView에 Adapter 설정
        listview.setAdapter(adapter);

        //구분선 설정
        listview.setDivider(new ColorDrawable(Color.MAGENTA));
        listview.setDividerHeight(3);

        //listview의 선택모드 설정
        //listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // 1개 선택시 (라디오버튼)
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // 여러 개 선택시 (체크박스)

        //ListView에서 항목을 클릭했을 때 처리
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //매개변수 (이벤트가 발생한 AdapterView listview, 클릭한 항목 뷰, 누른 항목의 인덱스, 항목 뷰의 아이디)
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item= list.get(position);
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        item, Snackbar.LENGTH_SHORT).show();
                //Log.e("클릭한 항목",position + ""); //인덱스
                //Log.e("클릭한 항목",list.get(position)); //해당 인덱스의 이름
            }
        });


        //데이터 삽입 버튼
        Button btninsert = (Button)findViewById(R.id.btninsert);
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력한 내용 가져오기
                EditText newitem = (EditText)findViewById(R.id.newitem);
                String item = newitem.getText().toString().trim();

                //list 삽입 전에 유효성 검사 꼭! - null 체크, 중복 검사 등등
                //null체크
                if(item.length() <=0){
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                            "아이템은 필수 입력입니다.", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                //중복검사
                //toUpperCase - list의 데이터를 순회하면서 모두 대문자로 변경해서 비교해서 중복 검사
                for(String spring:list){
                    if(spring.toUpperCase().equals(item.toUpperCase())){
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                "중복된 아이템이 이미 있습니다.", Snackbar.LENGTH_SHORT)
                                .show();
                        return;
                    }
                }

                //list에 삽입
                list.add(item);
                //listview 업데이트
                adapter.notifyDataSetChanged();
                newitem.setText("");
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "아이템이 정상적으로 추가되었습니다.", Snackbar.LENGTH_SHORT)
                        .show();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(newitem.getWindowToken(), 0);
            }
        });


        //데이터 삭제 버튼
        Button btndelete = (Button)findViewById(R.id.btndelete);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1) 1개를 선택할 수 있을 때 삭제처리
/*
                //작업을 수행할 데이터 유효성 검사
                //선택된 항목의 인덱스 찾아오기
                int pos = listview.getCheckedItemPosition();
                //인덱스는 데이터 범위 내의 인덱스이어야 함
                if(pos < 0 || pos >= list.size()){ //보통 선택되지 않은 경우
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                            "아이템이 선택되지 않았습니다.", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                //작업 수행
                //데이터 삭제하고 다시 출력
                list.remove(pos);
                adapter.notifyDataSetChanged();
                //선택된 항목 해제
                listview.clearChoices();
*/

                // 2) 여러 개를 선택할 수 있을 때 삭제처리
                //선택 항목 관련 정보를 가져옴
                SparseBooleanArray sb = listview.getCheckedItemPositions();
                //유효성검사
                //선택된 것이 없는 게 아니라 데이터 자체가 없는 것
                if(sb.size() <= 0){
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                            "리스트 뷰에 데이터가 없습니다.", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                //배열 순회
                //배열을 순회하면서 true이면 삭제 (체크돼있으면 true)
                //for(int i=0; i<listview.getCount(); i++) 이렇게 하면 안돼!
                //뒤에서부터 삭제
                for(int i=listview.getCount()-1; i>=0; i--){
                    if(sb.get(i) == true){
                        list.remove(i);
                    }
                }
                listview.clearChoices();
                adapter.notifyDataSetChanged();

                //수행 결과 출력
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "아이템이 정상적으로 삭제되었습니다.", Snackbar.LENGTH_SHORT)
                        .show();

            }
        });
    }
}