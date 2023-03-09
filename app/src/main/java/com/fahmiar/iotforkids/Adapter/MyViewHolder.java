package com.fahmiar.iotforkids.Adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fahmiar.iotforkids.R;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView item_name, item_data, item_id,item_name1, item_data1, item_id1;
    ImageView display, display1;
    Button onSw, offSw;
    MyAdapter.OnItemListener onItemListener;
    MyAdapter.OffItemListener offItemListener;

    public MyViewHolder(@NonNull View itemView, MyAdapter.OnItemListener onItemListener, MyAdapter.OffItemListener offItemListener) {
        super(itemView);
        onSw = (Button) itemView.findViewById(R.id.onsw);
        offSw = (Button) itemView.findViewById(R.id.offsw);
        display = (ImageView) itemView.findViewById(R.id.display_nav);
        item_name = (TextView) itemView.findViewById(R.id.item_name);
        item_data = (TextView) itemView.findViewById(R.id.item_data);
        item_id = (TextView) itemView.findViewById(R.id.item_id);
        display1 = (ImageView) itemView.findViewById(R.id.display_nav1);
        item_name1 = (TextView) itemView.findViewById(R.id.item_name1);
        item_data1 = (TextView) itemView.findViewById(R.id.item_data1);
        item_id1 = (TextView) itemView.findViewById(R.id.item_id1);
        this.onItemListener = onItemListener;
        this.offItemListener = offItemListener;

        onSw.setOnClickListener(this);
        offSw.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.onsw:
                onItemListener.onItemClick(getAdapterPosition());
            break;
            case R.id.offsw:
                offItemListener.offItemClick(getAdapterPosition());
            break;
        }
    }
}
