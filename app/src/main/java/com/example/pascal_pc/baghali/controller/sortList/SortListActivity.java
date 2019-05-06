package com.example.pascal_pc.baghali.controller.sortList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pascal_pc.baghali.R;

public class SortListActivity extends AppCompatActivity {

    private static final String REQ_KEY ="request_key.com.example.pascal_pc.baghali.controller.sortList";

    public static Intent newIntent(Context context,int reqId){
        Intent intent = new Intent(context,SortListActivity.class);
        intent.putExtra(REQ_KEY,reqId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);

        int reqId=getIntent().getIntExtra(REQ_KEY,1);

        FragmentManager fragmentManager=getSupportFragmentManager();
        if(fragmentManager.findFragmentById(R.id.sort_list_container)==null){
            fragmentManager.beginTransaction()
                    .replace(R.id.sort_list_container,SortListFragment.newInstance(reqId))
                    .commit();
        }
    }
}
