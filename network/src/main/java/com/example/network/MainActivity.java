package com.example.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    //전송 받은 내용을 출력해주는 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            //데이터 가져오기
            String data = (String)msg.obj;
            TextView tfOutput = (TextView)findViewById(R.id.tfOutput);
            tfOutput.setText(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tfOutput = (TextView)findViewById(R.id.tfOutput);
        EditText tfInput = (EditText)findViewById(R.id.tfInput);
        Button btnSend = (Button)findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스레드를 만들어서 실행
                new Thread(){
                    public void run(){
                        try{
                            //서버와 통신할 소켓을 생성
                            //host는 IP주소이고, port는 서버의 포트 번호
                            Socket socket = new Socket("172.30.1.40", 11001);
                            //데이터 전송
                            ObjectOutputStream oos =
                                    new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(tfInput.getText().toString());
                            oos.flush();

                            //전송된 데이터 읽기
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            String msg = (String)ois.readObject();

                            //데이터 출력을 스레드가 하지 말고 핸들러에게 위임
                            //tfInput.setText(msg);

                            Message message = new Message();
                            message.obj = msg;
                            handler.sendMessage(message);


                            //정리
                            oos.close();
                            ois.close();
                            socket.close();
                        }catch (Exception e){
                            Log.e("예외 발생", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });
    }
}