package com.example.resourceuse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);

        //이 코드를 만들 때
        //처음 배포돼서 실행이 되는 경우에는 savedInstance가 null이어서
        //NullPointException예외가 발생하므로 null이 아닐 때 읽을 수 있도록 해야 함
        if (savedInstanceState != null) {
            //key라는 이름으로 저장된 데이터를 출력
            String key = savedInstanceState.getString("key");
            //처음 실행할 때는 안나오고 회전이 발생하거나
            //다른 앱에 의해서 앱이 종료되었다가 다시 실행되는 경우 출력
            if (key != null) {
                Log.e("key", key);
            }
        }
    }
    //기기의 환경이 변경될 때 호출되는 메소드
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        //오버라이딩할 때 super(상위메소드) 꼭 호출하기!
        //안드로이드가 제공해주는 기능을 수행하고 내가 원하는 기능을 추가
        super.onConfigurationChanged(newConfig);

        TextView lblRotation = (TextView)findViewById(R.id.lblRotation);
        //기기 방향을 확인해서 텍스트 뷰와 로그에 방향을 출력
        //LANDSCAPE 가로
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            lblRotation.setText("기기 방향은 가로");
            Log.e("방향", "가로");
        } else {
            lblRotation.setText("기기 방향은 세로");
            Log.e("방향","세로");
        }
    }

    //Activity가 종료되기 전에 호출돼서 앱의 상태를 저장할 수 있는 메소드를 재정의
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        //상위 클래스의 메소드 호출출
       super.onSaveInstanceState(bundle);

       //데이터 저장
        bundle.putString("key","현재 상태 저장");
    }
}