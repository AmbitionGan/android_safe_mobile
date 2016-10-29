package com.pfs.mobilesafe.chatper03;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.pfs.mobilesafe.R;
import com.pfs.mobilesafe.chatper03.adapter.BlackContactAdapter;
import com.pfs.mobilesafe.chatper03.db.BlackNumberDao;
import com.pfs.mobilesafe.chatper03.entity.BlackContactInfo;

import java.util.ArrayList;
import java.util.List;

public class SecurityPhoneActivity extends Activity implements View.OnClickListener
{
    //有黑名单
    private FrameLayout mHaveBlackNumber;
    //没有黑名单
    private FrameLayout mNoBlackNumber;

    private BlackNumberDao dao;
    private ListView mListView;
    private int pageNumber = 0;
    private int pageSize = 15;
    private int totalNumber;

    private List<BlackContactInfo> pageBlackNumber = new ArrayList<BlackContactInfo>();
    private BlackContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_securityphone);
        initView();
        fillData();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (totalNumber != dao.getTotalNumber())
        {
            if (dao.getTotalNumber()>0)
            {
                mHaveBlackNumber.setVisibility(View.VISIBLE);
                mNoBlackNumber.setVisibility(View.GONE);
            }
        }
    }
}
