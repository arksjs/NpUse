package com.lyentech.use;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NpViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_np_view);
        TextView tvBase = (TextView) findViewById(R.id.tv_base);
        TextView tvEvent = (TextView) findViewById(R.id.tv_event);
        TextView tvRecord = (TextView) findViewById(R.id.tv_record);
        BaseFragment baseFragment = new BaseFragment();
        EventFragment eventFragment = new EventFragment();
        RecordFragment recordFragment = new RecordFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment,baseFragment, "baseFragment")
                .add(R.id.fragment,eventFragment,"eventFragment")
                .add(R.id.fragment,recordFragment,"recordFragment")
                .show(baseFragment)
                .hide(eventFragment)
                .hide(recordFragment)
                .commit();
        tvBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvBase.getCurrentTextColor() != Color.parseColor("#0581FE")) {
                    getSupportFragmentManager().beginTransaction()
                            .show(baseFragment)
                            .hide(eventFragment)
                            .hide(recordFragment)
                            .commit();
                    tvBase.setTextColor(Color.parseColor("#0581FE"));
                    tvEvent.setTextColor(Color.parseColor("#101010"));
                    tvRecord.setTextColor(Color.parseColor("#101010"));
                }
            }
        });
        tvEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvEvent.getCurrentTextColor() != Color.parseColor("#0581FE")) {
                    getSupportFragmentManager().beginTransaction()
                            .hide(baseFragment)
                            .show(eventFragment)
                            .hide(recordFragment)
                            .commit();
                    tvBase.setTextColor(Color.parseColor("#101010"));
                    tvEvent.setTextColor(Color.parseColor("#0581FE"));
                    tvRecord.setTextColor(Color.parseColor("#101010"));
                }
            }
        });
        tvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvRecord.getCurrentTextColor() != Color.parseColor("#0581FE")) {
                    getSupportFragmentManager().beginTransaction()
                            .hide(baseFragment)
                            .hide(eventFragment)
                            .show(recordFragment)
                            .commit();
                    tvBase.setTextColor(Color.parseColor("#101010"));
                    tvEvent.setTextColor(Color.parseColor("#101010"));
                    tvRecord.setTextColor(Color.parseColor("#0581FE"));
                }
            }
        });
    }
}