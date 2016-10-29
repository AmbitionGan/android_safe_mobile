package com.pfs.mobilesafe.chatper02;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.pfs.mobilesafe.R;

public class SetUp2Activity extends BaseSetUpActivity implements View.OnClickListener
{
    private TelephonyManager telephonyManager;
    private Button mBindSIMBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        initView();
    }

    private void initView()
    {
        ((RadioButton)findViewById(R.id.rb_second)).setChecked(true);
        mBindSIMBtn = (Button)findViewById(R.id.btn_bind_sim);
        mBindSIMBtn.setOnClickListener(this);

        if (isBind())
        {
            mBindSIMBtn.setEnabled(false);
        }
        else
        {
            mBindSIMBtn.setEnabled(true);
        }
    }

    private boolean isBind()
    {
        String simString = sharedPreferences.getString("sim", null);
        if (TextUtils.isEmpty(simString))
        {
            return false;
        }
        return true;
    }

    @Override
    public void showNext()
    {
        if (!isBind())
        {
            Toast.makeText(this, "您还没有绑定SIM卡！", 0).show();
            return;
        }
        startActivityAndFinishSelf(SetUp3Activity.class);
    }

    @Override
    public void showPre()
    {
        startActivityAndFinishSelf(SetUp1Activity.class);
    }

    @Override
    public void onClick(View v)
    {

        if (v.getId()==R.id.btn_bind_sim)
            bindSIM();
    }

    //绑定SIM
    private void bindSIM()
    {
        if (!isBind())
        {
            String simSerialNumber = telephonyManager.getSimSerialNumber();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("sim", simSerialNumber);
            editor.commit();
            Toast.makeText(this, "SIM卡绑定成功！", 0).show();
            mBindSIMBtn.setEnabled(false);
        }
        else
        {
            Toast.makeText(this, "SIM已经绑定！", 0).show();
            mBindSIMBtn.setEnabled(false);
        }
    }

}
