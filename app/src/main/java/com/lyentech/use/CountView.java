package com.lyentech.use;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CountView extends RelativeLayout {
    private View view;
    private int count = 0;

    public CountView(Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.count_view, this, true);
        init();
    }

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.count_view, this, true);
        init();
    }

    public CountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.count_view, this, true);
        init();
    }

    public CountView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        view = LayoutInflater.from(context).inflate(R.layout.count_view, this, true);
        init();
    }

    private void init(){
        TextView tvSub = (TextView) view.findViewById(R.id.tv_sub);
        TextView tvAdd = (TextView) view.findViewById(R.id.tv_add);
        TextView tvCount = (TextView) view.findViewById(R.id.tv_count);
        tvSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0){
                    count -= 1;
                    tvCount.setText(String.valueOf(count));
                }
            }
        });
        tvAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                count += 1;
                tvCount.setText(String.valueOf(count));
            }
        });
    }

    public int getCount(){
        return count;
    }
}
