package com.osg.ex82retrofitboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BoardItem> boardItems = new ArrayList<>();
    BoardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        adapter = new BoardAdapter(this, boardItems);
        recyclerView.setAdapter(adapter);
    }


    //액티비티가 화면에 보여질 때 자동 실행되는 라이프사이클 메소드 -에서 서버 데이터 읽어오기
    @Override
    protected void onResume() {
        super.onResume();

        //서버에서 데이터 불러오기
        loadData();
    }

    //서버에서 데이터를 불러들이는 작업 메소드
    void loadData(){
//        boardItems.add(new BoardItem(1, "aa", "title", "message","price",null,1, "data"));

        //레트로핏으로 데이터 불러오기 - GsonConverterFactory
        Retrofit retrofit = RetrofitHelper.getInstanceGson();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<ArrayList<BoardItem>> call = retrofitService.loadDataFromBoard();

        call.enqueue(new Callback<ArrayList<BoardItem>>() {
            @Override
            public void onResponse(Call<ArrayList<BoardItem>> call, Response<ArrayList<BoardItem>> response) {
                if(response.isSuccessful()){
                    //서버데이터를 읽어와 새로운 list 객체를 만드는 것...
                     ArrayList<BoardItem> items = response.body();
                     //recycler에서 보여주기 위한 첫번째 방법 - 새로 얻어온 list객체로 새롭게 아답터를 설정
//                    adapter = new BoardAdapter(MainActivity.this, items);
//                    recyclerView.setAdapter(adapter);

                    //새로운 아답터를 만들면 조금 느리니까...
                    //기존의 아답터가 보여주던 boardItems 값들을 갱신(변경)
                    //  일단, 기존 리스트를 모두 삭제, 아답터에 노티파이
                    boardItems.clear();
                    adapter.notifyDataSetChanged();

                    //서버에서 읽어온 items를 boardItems에 새로 추가시키기
                    for (BoardItem item : items){
                        boardItems.add(0, item);
                        adapter.notifyItemInserted(0); //하나하나 바뀔 때마다 아답터에 노티파이 하기
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BoardItem>> call, Throwable t) {

            }
        });

    }

    public void clickEdit(View view) {
        //글작성 페이지로 화면 전환!
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);

    }
}
