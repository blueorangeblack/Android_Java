package com.example.adapterview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {
    //다운로드 진행 상황을 표시할 뷰
    private ProgressBar downloadview;

    //파싱한 결과 데이터를 저장할 프로퍼티를 생성
    private int count; //데이터 개수 저장
    private ArrayList<Movie> movieList; //데이터 목록 저장

    //ListView 관련 프로퍼티
    ListView listView;
    //ArrayAdapter<Movie> adapter;
    //사용자 항목 뷰를 사용하기 위한 Adapter 로 바꿔줌
    MovieAdapter adapter;

    //lListView의 데이터를 다시 출력할 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            //ListView 재출력
            adapter.notifyDataSetChanged();
            //진행 상황을 나타내는 뷰를 사라지게 함 (필요할 때만 보이도록 함)
            downloadview.setVisibility(View.INVISIBLE);
            //스레드 변수를 초기화
            th = null;
        }
    };


    //마지막 항목 뷰가 스크롤에 의해서 보여진 것인지를 판별할 프로퍼티
    boolean lastItemVisibleFlag = false;

    //페이지 번호를 저장할 프로퍼티
    int page = 1;

    //데이터를 다운로드 받는 스레드 클래스
    class ThreadEx extends Thread{
        public void run(){
            try{
                //URL을 생성 - 이 때 한글이 포함되었는지 확인하고 한글이 있으면 인코딩해서 작성
                URL url = new URL("http://cyberadam.cafe24.com/movie/list?page=" + page);
                //HttpURLConnection을 생성
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                //옵션 설정 - 전송방식 그리고 파라미터 확인!
                con.setRequestMethod("GET");
                con.setConnectTimeout(30000);
                con.setUseCaches(false);
                //POST방식이면 파라미터 파일이 포함되었는지 여부에 따라 코드를 추가해야 함

                //문자열을 다운로드받아서 저장할 객체와 스트림을 생성
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));

                //문자열을 줄 단위로 읽어서 sb에 추가
                while(true){
                    //한 줄 가져오기
                    String line = br.readLine();
                    //읽은 내용이 없으면 더이상 가져오지 않음
                    if(line == null){
                        break;
                    }
                    //읽은 내용이 있을 때는 line에 추가
                    sb.append(line + "\n");
                }

                //연결해제
                br.close();
                con.disconnect();

                //데이터 확인
                //Log.e("다운로드 받은 문자열", sb.toString());

                //전체 문자열을 JSONObject로 변환
                JSONObject root = new JSONObject(sb.toString());
                //데이터 개수 가져오기 (key name)
                count = root.getInt("count");
                //영화 목록 가져오기 (key name)
                JSONArray movies = root.getJSONArray("list");
                //(배열) 영화목록 순회
                for(int i=0; i<movies.length(); i++){
                    //하나의 데이터 가져오기 (배열에서 가져올 때는 번호로 i)
                    JSONObject item = movies.getJSONObject(i);
                    //하나의 행을 저장할 객체를 생성
                    Movie movie = new Movie();

                    //각 속성을 가져와서 movie에 대입
                    movie.setTitle(item.getString("title"));
                    movie.setSubtitle(item.getString("subtitle"));
                    movie.setRating(item.getDouble("rating"));
                    movie.setLink(item.getString("link"));
                    movie.setThumbnail(item.getString("thumbnail"));

                    //파싱한 데이터를 List에 대입
                    //마지막에 추가
                    //movieList.add(movie);
                    //첫번째에 추가
                    movieList.add(0, movie);

                    //핸들러에게 메시지 전송
                    Message msg = new Message();
                    handler.sendMessage(msg);
                    //보낼 데이터가 없을 때는 아래처럼 작성해도 됨
                    //(1)은 구분하기위한 매개변수라서 아무거나 써도 됨
                    //handler.sendEmptyMessage(1);

                }
                //Log.e("파싱한 데이터", movieList.toString());

            }catch (Exception e){
                Log.e("다운로드 또는 파싱 예외", e.getLocalizedMessage());
            }
        }
    }


    //스레드 클래스의 객체를 참조할 프로퍼티
    //여기에 만든 이유는 스레드가 동작중이면 다른 스레드를 생성하지 못하도록 하기 위해서
    ThreadEx th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //영화 목록을 저장할 List 생성
        movieList = new ArrayList<>();

        //ListView 출력을 위한 설정
        //movieList = new ArrayList<>(); 보다 아래 있어야 함!
        listView = (ListView)findViewById(R.id.listview);
        /*
        adapter = new ArrayAdapter<>(
                MovieListActivity.this,
                android.R.layout.simple_list_item_1,
                movieList);

         */
        //사용자 항목뷰를 출력하기 위한 Adapter생성
        adapter = new MovieAdapter(MovieListActivity.this, movieList);
        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(Color.MAGENTA));
        listView.setDividerHeight(3);

        //진행 상황을 표시할 뷰를 찾아오기기
        downloadview = (ProgressBar)findViewById(R.id.downloadview);
        //화면에 출력
        downloadview.setVisibility(View.VISIBLE);
        //다운로드받고 JSON 파싱을 수행한 후 재출력을 위해서 핸들러를 호출하는
        //스레드를 생성하고 실행 => 클래스로 다시 정의함
        //이전 스레드가 동작중이면 중지
        if(th != null){
            return;
        }
        //이전 스레드가 없다면 스레드를 생성해서 데이터 가져오기를 수행
        th = new ThreadEx();
        th.start();


        //ListView의 항목을 클릭했을 때 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Snackbar.make(view, "선택", Snackbar.LENGTH_SHORT).show();

                //하위 Activity에게 전달할 데이터 가져오기
                String link = movieList.get(position).getLink();
                //하위 Activity를 출력할 Intent 생성 (나, 출력할 곳)
                Intent intent = new Intent(MovieListActivity.this,
                        MovieLinkActiviy.class);
                //전달할 데이터를 저장
                intent.putExtra("link", link);
                //하위 Activity출력
                startActivity(intent);
            }
        });

        //Listview의 Scroll 이벤트 처리
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            //스크롤 상태가 변경될 때 호출되는 메소드 - 다음 페이지 데이터 가져오기를 수행
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && lastItemVisibleFlag == true){
                    //페이지 번호를 증가
                    page = page + 1;
                    //전체 데이터를 출력했는지 확인
                    //10은 페이지당 데이터 개수
                    if(page * 10 >= count){
                        Snackbar.make(view, "더 이상 데이터가 없습니다.",
                                Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    //스레드가 동작중면 중지
                    if(th != null){
                        return;
                    }
                    //스레드가 동작 중이 아닐 때만 작업을 수행
                    downloadview.setVisibility(View.VISIBLE);
                    th = new ThreadEx();
                    th.start();

                }
            }

            //firstVisibleItem은 현재 보여지고 있는 항목 중에서 첫번째 항목의 인덱스
            //visibleItemCount는 보여지고 있는 데이터의 개수
            //totalItemCount는 전체 데이터 개수
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                //마지막에서 스크롤한 것인지 판단 여부를 저장하는 프로퍼티의 값 설정

                //데이터가 있고 첫번째 인덱스와 보여진 데이터 개수를 더한 값이
                //전체 데이터 개수보다 크거나 같다면
                //lastItemVisibleFlag는 true
                lastItemVisibleFlag = totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount;
            }
        });


        //스와이프 레이아웃의 리프레시 이벤트 처리
        SwipeRefreshLayout swipe_layout =
                (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //업데이트 된 데이터가 있는지 확인
                //마지막 업데이트 된 시간을 비교

                page = page + 1;

                //더 이상 가져올 데이터가 없으면 데이터 업데이트 안함
                if(page * 10 >= count){
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                            "업데이트 할 데이터가 없습니다.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //현재 다운로드 중이면 다운로드 안함
                if(th != null){
                    return;
                }

                downloadview.setVisibility(View.VISIBLE);
                th = new ThreadEx();
                th.start();
                swipe_layout.setRefreshing(false);
            }
        });

    }
}