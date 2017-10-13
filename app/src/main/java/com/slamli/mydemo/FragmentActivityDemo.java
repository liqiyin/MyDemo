package com.slamli.mydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by slam.li on 2017/9/26.
 */

public class FragmentActivityDemo extends AppCompatActivity {
    FragmentManager fragmentManager;
    int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        addFragment("1");
        id = addFragment("2");
        addFragment("3");
        addFragment("4");
        addFragment("5");
    }

    private int addFragment(String text) {
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int[] anims = getAnimationResIds();
        transaction.setCustomAnimations(anims[0], anims[1], anims[2], anims[3]);
        return transaction.add(R.id.layout_main, fragment)
                .addToBackStack(text)
                .commit();
    }

    private int[] getAnimationResIds() {
        return new int[]{R.anim.slide_in_left, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out_right};
    }

    @Override
    public void onBackPressed() {
        fragmentManager.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
