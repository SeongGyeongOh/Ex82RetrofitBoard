package com.osg.ex82retrofitboard;

import android.widget.ImageView;

//서버에서 읽어온 MarketTable의 한 record(row)의 데이터를 저장하는 VO클래스
public class BoardItem {

    //테이블에 넣는 모든 데이터...
    int no;         //저장 아이템 번호
    String name;
    String title;   //제목
    String msg; //내용
    String price;   //가격
    String file;    //업로드 이미지 파일 경로
    int favor;      //boolean favor;->MySql에 true - false 저장 불가 -> 0과 1로 대체
    String date;    //작성일자

    public BoardItem() {
    }


    public BoardItem(int no, String name, String title, String msg, String price, String file, int favor, String date) {
        this.no = no;
        this.name = name;
        this.title = title;
        this.msg = msg;
        this.price = price;
        this.file = file;
        this.favor = favor;
        this.date = date;
    }
}
