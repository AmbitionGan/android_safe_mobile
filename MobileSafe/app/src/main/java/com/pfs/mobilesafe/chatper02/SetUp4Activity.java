package com.pfs.mobilesafe.chatper02;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pfs.mobilesafe.R;

public class SetUp4Activity extends BaseSetUpActivity
{
    private TextView mStatusTV;
    private ToggleButton mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        initView();
    }

    private void initView()
    {
        ((RadioButton)findViewById(R.id.rb_four)).setChecked(true);
        mStatusTV = (TextView)findViewById(R.id.tv_setup4_status);
        mToggleButton = (ToggleButton)findViewById(R.id.togglebtn_securityfunction);
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    mStatusTV.setText("防盗保护已开启");
                }
                else
                {
                    mStatusTV.setText("防盗保护没有开启");
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("protecting", isChecked);
                editor.commit();
            }
        });

        boolean protecting = sharedPreferences.getBoolean("protecting", true);
        if (protecting)
        {
            mStatusTV.setText("防盗保护已开启");
            mToggleButton.setChecked(true);
        }
        else
        {

            mToggleButton.setChecked(false);
        }
    }

    @Override
    public void showNext()
    {
        //跳转至防盗保护页面
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isSetUp", true);
        editor.commit();
        startActivityAndFinishSelf(LostFindActivity.class);
    }

    @Override
    public void showPre()
    {
        startActivityAndFinishSelf(SetUp3Activity.class);
    }
}
