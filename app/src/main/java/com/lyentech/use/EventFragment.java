package com.lyentech.use;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyentech.sdk.GreeNp;

import org.json.JSONObject;

import java.util.ArrayList;

public class EventFragment extends Fragment {
    private int evvCount = 1;
    private ArrayList<String> evvResult = new ArrayList<>();
    private ArrayList<String> attrValueResult = new ArrayList<>();
    private ArrayList<String> attrResult = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        evvResult.add("");
        attrValueResult.add("");
        attrResult.add("文本");

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        EditText search = (EditText) view.findViewById(R.id.et_search_value);
        CountView searchCount = (CountView) view.findViewById(R.id.search_count_value);
        Button btSearch = (Button) view.findViewById(R.id.bt_search_upload);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < searchCount.getCount(); i++){
                    GreeNp.trackSearch(search.getText().toString());
                }
                Toast.makeText(getContext(),"已上报",Toast.LENGTH_SHORT).show();
            }
        });

        EditText ev = (EditText) view.findViewById(R.id.et_ev_value);
        CountView evCount = (CountView) view.findViewById(R.id.ev_count_value);
        RecyclerView rvEvv = (RecyclerView) view.findViewById(R.id.rv_evv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvEvv.setLayoutManager(layoutManager);
        EvvAdapter adapter = new EvvAdapter(evvCount);
        adapter.addTextChangedListener(new EvvAdapter.textChangedListener() {
            @Override
            public void afterEvvChanged(String evv, int position) {
                evvResult.set(position, evv);
            }

            @Override
            public void afterAttributeValueChanged(String attrValue, int position) {
                attrValueResult.set(position, attrValue);
            }

            @Override
            public void afterAttributeChanged(String attr, int position) {
                attrResult.set(position, attr);
            }
        });
        rvEvv.setAdapter(adapter);
        TextView evvAdd = (TextView) view.findViewById(R.id.tv_add_evv);
        evvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evvResult.add("");
                attrValueResult.add("");
                attrResult.add("文本");
                evvCount += 1;
                adapter.setCount(evvCount,true);
                adapter.notifyDataSetChanged();
            }
        });
        TextView evvSub = (TextView) view.findViewById(R.id.tv_sub_evv);
        evvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (evvCount > 1) {
                    evvResult.remove(evvResult.size() - 1);
                    attrValueResult.remove(attrValueResult.size() - 1);
                    attrResult.remove(attrResult.size() - 1);
                    evvCount -= 1;
                    adapter.setCount(evvCount,false);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        Button btEv = (Button) view.findViewById(R.id.bt_ev_upload);
        btEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject js = new JSONObject();
                for (int i = 0; i < evvCount; i++){
                    switch (attrResult.get(i)) {
                        case "文本":
                            try {
                                js.put(evvResult.get(i), attrValueResult.get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "数值":
                            try {
                                js.put(evvResult.get(i), Integer.valueOf(attrValueResult.get(i)).intValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "是非":
                            try {
                                switch (attrValueResult.get(i)) {
                                    case "是":
                                    case "1":
                                        js.put(evvResult.get(i), true);
                                        break;
                                    case "非":
                                    case "0":
                                        js.put(evvResult.get(i), false);
                                        break;
                                    default:
                                        js.put(evvResult.get(i), Boolean.valueOf(attrValueResult.get(i)).booleanValue());
                                        break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
                for (int i = 0; i < evCount.getCount(); i++){
                    GreeNp.trackEvent(ev.getText().toString(), js.toString());
                }
                Toast.makeText(getContext(),"已上报",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private static class EvvAdapter extends RecyclerView.Adapter<EvvAdapter.ViewHolder> {
        private int count;
        private boolean add = false;

        EvvAdapter(int count) {
            this.count = count;
        }

        public void setCount(int count, boolean add){
            this.count = count;
            this.add = add;
        }

        private interface textChangedListener {
            void afterEvvChanged(String evv, int position);
            void afterAttributeValueChanged(String attrValue, int position);
            void afterAttributeChanged(String attr, int position);
        }

        private textChangedListener listener;

        public void addTextChangedListener(textChangedListener listener){
            this.listener = listener;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evv_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (add && position == count - 1) {
                holder.evv.setText("");
                holder.attributeValue.setText("");
                holder.attribute.setSelection(0);
            }
            holder.evv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    listener.afterEvvChanged(editable.toString(), holder.getAdapterPosition());
                }
            });
            holder.attributeValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    listener.afterAttributeValueChanged(editable.toString(), holder.getAdapterPosition());
                }
            });
            holder.attribute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    holder.attributeValue.setText("");
                    listener.afterAttributeChanged(holder.attribute.getSelectedItem().toString(), holder.getAdapterPosition());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return count;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final EditText evv;
            private final EditText attributeValue;
            private final Spinner attribute;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                evv = itemView.findViewById(R.id.et_evv_value);
                attributeValue = itemView.findViewById(R.id.et_attribute_value);
                attribute = itemView.findViewById(R.id.sp_attribute);
            }
        }
    }
}