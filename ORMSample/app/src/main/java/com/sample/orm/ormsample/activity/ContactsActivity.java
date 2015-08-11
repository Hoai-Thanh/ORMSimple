package com.sample.orm.ormsample.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.sample.orm.ormsample.R;
import com.sample.orm.ormsample.fragment.DetailFragment;

/**
 * Created by Thanh Nguyen on 8/9/2015.
 */
public class ContactsActivity extends FragmentActivity {
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orm_list_contact_activity);

        detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.activityMain_frmDetail);
    }
}
