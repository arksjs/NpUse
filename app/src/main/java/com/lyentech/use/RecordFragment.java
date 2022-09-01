package com.lyentech.use;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyentech.sdk.NpCount;

import java.util.ArrayList;
import java.util.Collections;

public class RecordFragment extends Fragment {
    private ArrayList<String> recordList = new ArrayList<>();
    private RecordAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_record);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        //recordList.addAll(NpCount.getAll());
        recordList.addAll(NpCount.b());
        Collections.reverse(recordList);
        adapter = new RecordAdapter(recordList);
        rv.setAdapter(adapter);
        TextView tvClear = (TextView) view.findViewById(R.id.tv_clear);
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NpCount.removeAll();
                NpCount.a();
                recordList.clear();
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            recordList.clear();
            //recordList.addAll(NpCount.getAll());
            recordList.addAll(NpCount.b());
            Collections.reverse(recordList);
            adapter.notifyDataSetChanged();
        }
    }

    private static class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
        private final ArrayList<String> recordList;

        RecordAdapter(ArrayList<String> recordList) {
            this.recordList = recordList;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String record = recordList.get(position);
            holder.tvRecord.setText(record);
            holder.tvCount.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return recordList == null ? 0 : recordList.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvCount;
            private final TextView tvRecord;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvCount = itemView.findViewById(R.id.tv_record_count);
                tvRecord = itemView.findViewById(R.id.tv_record);
            }
        }
    }
}