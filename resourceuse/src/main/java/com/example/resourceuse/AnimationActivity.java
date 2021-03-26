package com.example.resourceuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        Button btnScale = (Button)findViewById(R.id.btnScale);
        btnScale.setOnClickListener(new View.OnClickListener() {
            @Override
            //Delegate 이벤트 처리 모델에서 이벤트 처리 메소드의 첫번째 매개변수는
            //이벤트가 발생한 객체 (여기서 v)
            public void onClick(View v) {
                //xml로 디자인한 Animation가져오기
                Animation anim = AnimationUtils.loadAnimation(
                        AnimationActivity.this, R.anim.scale);
                //Animation적용
                v.startAnimation(anim);
            }
        });
    }
}