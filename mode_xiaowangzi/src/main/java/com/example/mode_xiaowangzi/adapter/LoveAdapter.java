package com.example.mode_xiaowangzi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mode_xiaowangzi.R;
import com.example.mode_xiaowangzi.bean.GrilBean;
import com.example.mode_xiaowangzi.bean.LoveBean;

import java.util.ArrayList;

public class LoveAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<LoveBean> mList;
    private CallBack callBack;

    public LoveAdapter(Context context, ArrayList<LoveBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_item, null);
        return new MyViewHolder(inflate);
    }

    public void setList(ArrayList<LoveBean> list) {
        mList = list;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final LoveBean loveBean = mList.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Glide.with(mContext).load(loveBean.getUrl()).into(myViewHolder.image);
        myViewHolder.tv.setText(loveBean.getPublishedAt());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.OnClick(loveBean, position);
                }
            }
        });
        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callBack != null) {
                    callBack.OnLongClick(loveBean, position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    //接口回调
    public interface CallBack {
        void OnClick(LoveBean loveBean, int position);

        void OnLongClick(LoveBean loveBean, int position);
    }

    //方法
    public void setOnClickListener(CallBack callBack) {
        this.callBack = callBack;
    }
}
