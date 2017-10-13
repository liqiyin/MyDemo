package com.slamli.mydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by slam.li on 2017/9/26.
 */

public class MyFragment extends Fragment {
    public int id;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        textView = (TextView) view.findViewById(R.id.text);
        String text = getArguments().getString("text");
        textView.setText(text);
        return view;
    }

    @Override
    public void onDestroyView() {
        Log.i("fragmentLife", "destoryview");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("fragmentLife", "destory");
        super.onDestroy();
    }
}
