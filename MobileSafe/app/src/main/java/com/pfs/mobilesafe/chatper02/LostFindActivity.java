package com.pfs.mobilesafe.chatper02;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pfs.mobilesafe.R;


public class LostFindActivity extends Activity implements View.OnClickListener
{
    private TextView mSafePhoneTV;
    private RelativeLayout mInterSetupRL;
    private SharedPreferences msharedPreferences;
    private ToggleButton mToggleButton;
    private TextView mProtectStatusTV;

    protected void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lostfind);
        msharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        if (!isSetUp())
        {
            //是否设置过向导
            startSetUp1Activity();
        }
        initView();
    }

    private boolean isSetUp()
    {
        return msharedPreferences.getBoolean("isSetUp", false);
    }

    private void initView()
    {
        TextView mTitleTV = (TextView)findViewById(R.id.tv_title);
        mTitleTV.setText("手机防盗");
        ImageView mLeftImgv = (ImageView)findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setImageResource(R.drawable.back);
        ((RelativeLayout)findViewById(R.id.rl_titlebar)).setBackgroundColor(ContextCompat.getColor(this, R.color.purple));

        mSafePhoneTV = (TextView)findViewById(R.id.tv_safephone);
        mSafePhoneTV.setText(msharedPreferences.getString("safephone", ""));
        mToggleButton = (ToggleButton)findViewById(R.id.togglebtn_lostfind);
        mInterSetupRL = (RelativeLayout)findViewById(R.id.rl_inter_setup_wizard);
        mInterSetupRL.setOnClickListener(this);
        mProtectStatusTV = (TextView)findViewById(R.id.tv_lostfind_protectstauts);
        //查询手机防盗是否开启，默认为开启
        boolean protecting = msharedPreferences.getBoolean("protecting", true);
        if (protecting)
        {
            mProtectStatusTV.setText("防盗保护已经开启");
            mToggleButton.setChecked(true);
        }
        else
        {
            mProtectStatusTV.setText("防盗保护没有开启");
            mToggleButton.setChecked(true);
        }

        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    mProtectStatusTV.setText("防盗保护已经开启");
                }
                else
                {
                    mProtectStatusTV.setText("防盗保护没有开启");
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.rl_inter_setup_wizard:
                startSetUp1Activity();
                break;
            case R.id.imgv_leftbtn:
                finish();
                break;
        }
    }

    private void startSetUp1Activity()
    {
        Intent intent = new Intent(LostFindActivity.this, SetUp1Activity.class);
        startActivity(intent);
        finish();
    }
}
