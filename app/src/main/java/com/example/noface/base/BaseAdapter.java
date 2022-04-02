package com.example.noface.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    protected List<T> mList = new ArrayList<>();
    private int layoutId;
    private Context mContext;

    public BaseAdapter(Context context, int layoutId, List<T> list) {
        this.layoutId = layoutId;
        this.mList = list;
        this.mContext = context;
    }

    public void setData(List<T> list) {
        mList = list;
    }

    public void addData(List<T> list) {
        for (T t : list) {
            mList.add(t);
        }
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public Context getmContext() {
        return mContext;
    }

    //onCreateViewHolder用来给rv创建缓存
    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        BaseHolder baseHolder = new BaseHolder(view);
        return baseHolder;
    }

    //onBindViewHolder给缓存控件设置数据
    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        T item = mList.get(position);
        setListener(holder, item, position);
        setContent(holder, item, position);

    }

    /**
     * 设置内容
     *
     * @param holder
     * @param item
     */
    public abstract void setContent(BaseHolder holder, T item, int position);

    /**
     * 设置点击事件
     *
     * @param holder
     * @param item
     */
    public abstract void setListener(BaseHolder holder, T item, int position);

    //获取记录数据
    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

}
