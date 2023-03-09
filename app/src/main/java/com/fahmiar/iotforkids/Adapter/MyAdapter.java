package com.fahmiar.iotforkids.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fahmiar.iotforkids.R;
import com.fahmiar.iotforkids.model.Item;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<Item> itemList;
    OnItemListener mOnItemListener;
    OffItemListener mOffItemListener;


    public MyAdapter(Context context, List<Item> itemList, OnItemListener onItemListener, OffItemListener offItemListener) {
        this.context = context;
        this.itemList = itemList;
        this.mOnItemListener = onItemListener;
        this.mOffItemListener = offItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_layout_ch, parent, false);
        return new MyViewHolder(itemView, mOnItemListener, mOffItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Glide.with(context).load(itemList.get(position).getImage()).into(holder.display1);
            holder.item_name1.setText(itemList.get(position).getName());
            holder.item_data1.setText(itemList.get(position).getData());
            holder.item_id1.setText(itemList.get(position).getId_item());
            holder.onSw.setText(itemList.get(position).getOnSw());
            holder.offSw.setText(itemList.get(position).getOffSw());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemListener {
        void onItemClick(int position);

    }

    public interface OffItemListener {
        void offItemClick(int position);
    }
}
