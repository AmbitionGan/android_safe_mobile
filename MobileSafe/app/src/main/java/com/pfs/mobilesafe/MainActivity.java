package com.pfs.mobilesafe;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        correctSIM();
    }

    public void correctSIM()
    {
        //检查SIM是否发生变化
        SharedPreferences sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        //获取防盗保护的状态
        boolean protecting = sharedPreferences.getBoolean("protecting", true);

        if (protecting)
        {
            //得到绑定的sim卡串号
            String bindsim = sharedPreferences.getString("sim", "");
            //得到现在的sim卡串号
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String realsim = telephonyManager.getSimSerialNumber();

            if (bindsim.equals(realsim))
            {
                Log.i("", "SIM卡未发生变化！");
            }
            else
            {
                Log.i("", "SIM卡发生变化");
                //某些版本的系统可能无法发生短信成功
                String safenumber = sharedPreferences.getString("safephone","");
                if (!TextUtils.isEmpty(safenumber))
                {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(safenumber,null,"您绑定的手机SIM卡被更换！",null,null);
                }
            }
        }
    }
}
