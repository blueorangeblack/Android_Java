package com.example.network;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloadActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){

            //이미지 뷰 찾아오기
            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            //출력할 이미지를 저장하기 위한 변수
            Bitmap bitmap = null;
            //여러 스레드에서 사용하도록 하고자 할 때는 msg의 what을 이용해서 분기
            switch (msg.what){
                case 1:
                    //데이터 가져오기
                    bitmap = (Bitmap)msg.obj;
                    //출력
                    imageView.setImageBitmap(bitmap);
                    break;
                case 2:
                    //파일이 존재하는 경우
                    Snackbar.make(imageView, "파일이 이미 존재합니다.", Snackbar.LENGTH_LONG).show();
                    //기존 파일 출력
                    //파일의 경로 가져오기
                    String dirPath = Environment.getDataDirectory().getAbsolutePath();
                    String filePath = dirPath + "/data/com.example.network/files/aaa.jpg";
                    imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    break;
                case 3:
                    //파일이 존재하지 않는 경우
                    Snackbar.make(imageView, "파일이 없어서 다운로드 받아서 출력합니다.", Snackbar.LENGTH_LONG).show();
                    //다운로드받은 파일 출력
                    //파일의 경로 가져오기
                    String dirPath1 = Environment.getDataDirectory().getAbsolutePath();
                    String filePath1 = dirPath1 + "/data/com.example.network/files/aaa.jpg";
                    imageView.setImageBitmap(BitmapFactory.decodeFile(filePath1));
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download);

        //버튼1)
        //이미지 다운로드 받아서 바로 출력하는 버튼의 클릭 이벤트 핸들러 작성
        Button btnDisplay = (Button)findViewById(R.id.btnDisplay);
        //Anonymous Class 이용해서 이벤트 처리
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run(){
                        //이미지 바로 가져오기
                        try{
                            //이미지를 다운로드 받을 스트림 생성
                            InputStream is = new URL(
                                    "https://image.jtbcplus.kr/data/contents/jam_photo/202103/24/a82fa157-70e8-4604-9f96-827739ae284b.jpg")
                                    .openStream();
                            //이미지가져와서 bitmap에 저장
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            //핸들러에게 전송할 메시지를 생성
                            Message message = new Message();
                            message.what=1;
                            message.obj=bitmap;
                            //핸들러에게 메시지 전송
                            handler.sendMessage(message);

                        }catch (Exception e){
                            Log.e("예외 발생 1", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });


        //버튼2)
        //이미지를 다운로드 받아서 파일로 저장하고 출력
        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // <<현재 앱 내에 파일이 존재하는지 확인 먼저 해야함!>> - 827739ae284b.jpg
                
                //Data가 저장되는 Directory 경로를 생성
                String dirPath =
                        Environment.getDataDirectory().getAbsolutePath();
                
                //파일 경로 생성 "/data/패키지이름/files/파일이름"
                String path = dirPath + "/data/com.example.network/files/aaa.jpg";
                
                //파일이 있는지 확인
                //File - 존재여부, 마지막 수정 날짜, 생성 날짜, 크기 가져오는 것이 중요
                if(new File(path).exists()){
                    //핸들러에게 전송할 메시지를 생성해서 출력을 요청
                    //이미 파일이 있는 경우이므로 데이터를 전달할 필요가 없음
                    Message message = new Message();
                    message.what=2;
                    handler.sendMessage(message);
                }else{
                    //이미지 파일이 없는 경우 이미지 파일을 다운로드받아서 저장하고
                    //핸들러에게 메시지를 전송
                    new Thread(){
                        @Override
                        public void run(){
                            try {
                                //이미지 데이터 다운로드 받기
                                //다운로드받을 URL 생성
                                URL url = new URL(
                                        "https://image.jtbcplus.kr/data/contents/jam_photo/202103/24/8eecb235-fcb6-436f-9119-768571381401.jpg");
                                //연결
                                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                                //옵션 설정
                                con.setConnectTimeout(30000);
                                con.setUseCaches(false);

                                //텍스트가 아닌 데이터를 가져오기 위한 스트림을 생성
                                InputStream is = con.getInputStream();
                                //기록할 파일 스트림을 생성
                                FileOutputStream fos = openFileOutput("aaa.jpg",0);

                                //데이터를 임시로 저장할 바이트 배열을 생성
                                byte[] raster = new byte[con.getContentLength()];

                                //다운로드받아서 저장하기
                                while(true){
                                    int read = is.read(raster);
                                    if(read <= 0){
                                        break;
                                    }
                                    fos.write(raster, 0, read);
                                }
                                is.close();
                                fos.close();
                                con.disconnect();

                                //핸들러에게 메시지 전송
                                Message msg = new Message();
                                msg.what=3;
                                handler.sendMessage(msg);

                            }catch(Exception e){
                                Log.e("예외 발생 2",e.getLocalizedMessage());
                            }
                        }
                    }.start();
                }
            }
        });
    }
}