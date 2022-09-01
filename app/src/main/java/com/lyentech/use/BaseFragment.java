package com.lyentech.use;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.lyentech.sdk.DeviceIdUtil;
import com.lyentech.sdk.GreeNp;
import com.lyentech.sdk.MMKVUtil;
import com.lyentech.sdk.NpConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        TextView tvUuid = (TextView) view.findViewById(R.id.tv_uuid_value);
        //tvUuid.setText(DeviceIdUtil.getUniqueId(getActivity()));
        tvUuid.setText(DeviceIdUtil.a(getActivity()));
        EditText appKey = (EditText) view.findViewById(R.id.et_appkey_value);
        appKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //GreeNp.setAppKey(editable.toString());
                GreeNp.a(editable.toString());
            }
        });
        //appKey.setText(MMKVUtil.getStr(NpConfig.PUBLIC_APP));
        appKey.setText(MMKVUtil.d(NpConfig.d));
        TextView pf = (TextView) view.findViewById(R.id.tv_pf_value);
        //pf.setText(DeviceIdUtil.getPlatForm());
        pf.setText(DeviceIdUtil.a());
        TextView sys = (TextView) view.findViewById(R.id.tv_sys_value);
        sys.setText("Android " + Build.VERSION.RELEASE);
        TextView br = (TextView) view.findViewById(R.id.tv_br_value);
        br.setText(Build.BRAND);
        TextView brv = (TextView) view.findViewById(R.id.tv_brv_value);
        brv.setText(Build.MODEL);
        TextView sr = (TextView) view.findViewById(R.id.tv_sr_value);
        int w = 1080, h = 1920;
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        w = dm.widthPixels;
        h = dm.heightPixels;
        sr.setText(w + "x" + h);
        EditText u = (EditText) view.findViewById(R.id.et_u_value);
        EditText rf = (EditText) view.findViewById(R.id.et_rf_value);
        TextView rnd = (TextView) view.findViewById(R.id.tv_rnd_value);
        Spinner tp = (Spinner) view.findViewById(R.id.sp_tp_value);
        Button btUpload = (Button) view.findViewById(R.id.bt_tp_upload);
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String des = u.getText().toString();
                String src = rf.getText().toString();
                String tpValue = tp.getSelectedItem().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date time = new Date(System.currentTimeMillis());
                rnd.setText(simpleDateFormat.format(time));
                GreeNp.trackEvent(des ,src ,tpValue ,null ,null);
                Toast.makeText(getContext(),"已上报",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}