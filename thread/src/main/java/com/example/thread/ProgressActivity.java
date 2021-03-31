package com.example.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity {
    //진행율 표시를 위한 대화상자
    ProgressDialog progressDialog;
    //진행율 표시를 위한 뷰
    ProgressBar progressBar;

    private TextView tfResult;

    //UI를 주기적으로 갱신할 핸들러 객체

    //이렇게만 만드는 경우는 handler의 post메소드를 호출해서 Runnable 객체를 대입해서 UI를 갱신
    //- 다른 작업이 없을 때 수행
    //Handler handler = new Handler(Looper.getMainLooper());

    //메시지를 전송해서 바로 작업을 수행시키는 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        //핸들러에게 메시지가 전송되면 호출되는 메소드
        @Override
        public void handleMessage(Message msg){
            //전송받은 코드를 저장
            int what = msg.what;
            //데이터 가져오기
            int i = (Integer)msg.obj;
            //내용을 출력
            tfResult.setText("i=" +i);
            if(i < 100){
                progressDialog.setProgress(i);
                progressBar.setProgress(i);
                //필요할 때만 보이도록 하려면
                progressBar.setVisibility(View.VISIBLE);
            }else{
                progressDialog.dismiss();
                //필요할 때만 보이도록 하려면
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        //ProgressBar찾아오기
        progressBar =(ProgressBar)findViewById(R.id.progressbar);

        tfResult = (TextView)findViewById(R.id.tfResult);

        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Thread를 이용해서 Handler를 호출
                new Thread(){
                    @Override
                    public void run(){
                        try{
                            for(int i=1; i<=100; i++){
                                Message msg = new Message();
                                msg.obj = i;
                                msg.what = 1;
                                handler.sendMessage(msg);
                                Thread.sleep(50);
                            }
                        }catch (Exception e){
                            Log.e("예외 발생", e.getMessage());
                        }
                    }
                }.start();

                progressDialog = new ProgressDialog(ProgressActivity.this);
                progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("다운로드 중");
                progressDialog.setMessage("잠시 대기...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        });
    }
}