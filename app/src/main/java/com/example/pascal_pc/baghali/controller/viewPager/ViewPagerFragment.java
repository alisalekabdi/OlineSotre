package com.example.pascal_pc.baghali.controller.viewPager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pascal_pc.baghali.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {

    private static final String POSITION_KEY="position_key";

    public static ViewPagerFragment newInstance(int position) {

        Bundle args = new Bundle();

        args.putInt(POSITION_KEY,position);
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView mSpecialImgView;
    private int mPosition;
    private int[] myImgList=new int[]{R.drawable.slider_img_zero,R.drawable.slider_img_one,
            R.drawable.slider_img_two ,R.drawable.slider_img_three,R.drawable.slider_img_four};

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition=getArguments().getInt(POSITION_KEY,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        mSpecialImgView =view.findViewById(R.id.special_img_view);
        mSpecialImgView.setImageResource(myImgList[mPosition]);
        return view;
    }

}
