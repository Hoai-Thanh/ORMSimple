package com.sample.orm.ormsample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.orm.ormsample.R;

/**
 * Created by Thanh Nguyen on 8/9/2015.
 */
public class DetailFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orm_list_detail_fragment, container, false);
        return view;
    }
}
