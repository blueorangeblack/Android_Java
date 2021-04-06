package com.example.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CustomCellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_cell);

        //데이터 생성
        List<DataStructure> list = new ArrayList<>();
        DataStructure ds = new DataStructure();
        ds.setIcon(R.mipmap.ic_launcher);
        ds.setName("Finn");
        ds.setDescription("우 랜드에 존재하는 유일한 인간이다. 정의로운 성품을 지니고 있으며, 영웅이 되는 것이 꿈이다.");
        list.add(ds);

        ds = new DataStructure();
        ds.setIcon(R.mipmap.ic_launcher);
        ds.setName("Jake");
        ds.setDescription("종족은 마법 개라고 하며, 핀과는 형제와 같은 사이여서 우애가 매우 깊다.");
        list.add(ds);

        ds = new DataStructure();
        ds.setIcon(R.mipmap.ic_launcher);
        ds.setName("BMO");
        ds.setDescription("핀과 제이크와 함께 나무집에서 살고 있는 룸메이트이자 친구이며 게임기다.");
        list.add(ds);

        ds = new DataStructure();
        ds.setIcon(R.mipmap.ic_launcher);
        ds.setName("Bubblegum");
        ds.setDescription("캔디 왕국의 공주이며, 캔디 왕국의 통치자이기도 하다. 줄여서 PB라 불린다.");
        list.add(ds);

        //Adapter 만들기
        MyAdapter myAdapter = new MyAdapter(CustomCellActivity.this, list);

        //ListView에 출력
        ListView listView = (ListView)findViewById(R.id.customlistview);
        listView.setAdapter(myAdapter);


        //애니메이션 집합을 생성
        AnimationSet set = new AnimationSet(true);
        //위치 이동 애니메이션 생성
        Animation rtl = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f, //시작 x
                Animation.RELATIVE_TO_SELF, 0.0f, //시작 y
                Animation.RELATIVE_TO_SELF, 0.0f, //끝 x
                Animation.RELATIVE_TO_SELF, 0.0f //끝 y
        );
        //시간 설정
        rtl.setDuration(2000);
        //집합에 추가
        set.addAnimation(rtl);

        //불투명도 애니메이션을 생성하고 추가
        Animation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(2000);
        set.addAnimation(alpha);

        //애니메이션 적용
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        listView.setLayoutAnimation(controller);
    }
}