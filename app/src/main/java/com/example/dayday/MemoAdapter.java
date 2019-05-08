package com.example.dayday;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//https://black-jin0427.tistory.com/100
//https://recipes4dev.tistory.com/154
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {
    private ArrayList<MemoData> memos = new ArrayList<>();

    public MemoAdapter() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    MemoAdapter(ArrayList<String> list){
    }

    //아이템뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.memos, parent, false);
        MemoAdapter.ViewHolder vh = new MemoAdapter.ViewHolder(view);

        return vh;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemoData memoData = memos.get(position);
    }

    @Override
    public int getItemCount() {
        return memos.size();
    }

}
