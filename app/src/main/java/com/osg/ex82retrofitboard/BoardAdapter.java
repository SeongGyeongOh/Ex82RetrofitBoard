package com.osg.ex82retrofitboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<BoardItem> boardItems;

    public BoardAdapter() {
    }

    public BoardAdapter(Context context, ArrayList<BoardItem> boardItems) {
        this.context = context;
        this.boardItems = boardItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.board_item, parent, false);
        VH holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH)holder;

        BoardItem item = boardItems.get(position);

        //DB안에는 업로드된 파일의 서버 내부 경로만 저장되어 있음
        //우리는 서버 주소까지 포함한 풀 서버주소가 필요하다
        String imgUrl = "http://kamniang.dothome.co.kr/Retrofit/"+item.file;
        Glide.with(context).load(imgUrl).into(vh.iv);
        vh.tvTitle.setText(item.title);
        vh.tvMsg.setText(item.msg);
        vh.tvPrice.setText(item.price+"원");
        vh.tbFav.setChecked(false);


    }

    @Override
    public int getItemCount() {
        return boardItems.size();
    }

    //innerclass
    class VH extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView tvTitle, tvMsg, tvPrice;
        ToggleButton tbFav;

        public VH(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvMsg = itemView.findViewById(R.id.tv_msg);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tbFav = itemView.findViewById(R.id.tb_favor);

            //좋아요 토글버튼 선택 리스너
            tbFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //바꿔야 할 데이터는 favor뿐이지만, 나중에 확장성을 위해서...
                    BoardItem item = boardItems.get( getLayoutPosition() ); //현재 누른 아이템 얻어오기
                    item.favor = isChecked?1:0;

                    RetrofitService retrofitService = RetrofitHelper.getInstanceGson().create(RetrofitService.class);
                    Call<BoardItem> call = retrofitService.updateData("updateFavor.php", item);

                    call.enqueue(new Callback<BoardItem>() {
                        @Override
                        public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {

                        }

                        @Override
                        public void onFailure(Call<BoardItem> call, Throwable t) {

                        }
                    });
                }
            });

        }
    }

}
