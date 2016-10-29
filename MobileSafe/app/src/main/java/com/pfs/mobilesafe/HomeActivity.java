package com.pfs.mobilesafe;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.pfs.mobilesafe.chatper01.HomeAdapter;
import com.pfs.mobilesafe.chatper02.LostFindActivity;
import com.pfs.mobilesafe.chatper02.dialog.InterPasswordDialog;
import com.pfs.mobilesafe.chatper02.dialog.SetUpPasswordDialog;
import com.pfs.mobilesafe.chatper02.receiver.MyDeviceAdminReceiver;
import com.pfs.mobilesafe.chatper02.utils.MD5Utils;

public class HomeActivity extends Activity
{
    private long mExitTime;
    private GridView gv_home;
    private SharedPreferences sharedPreferences;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //初始化布局
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);

        setupViewComponent();
    }

    public void setupViewComponent()
    {
        //初始化GridView
        gv_home = (GridView)findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));

        //item点击事件
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0://手机防盗
                        if (isSetUpPassword())
                        {
                            //密码对话框
                            showInterPaswdDialog();
                        }
                        else
                        {
                            //设置密码对话框
                            showSetUpPaswdDialog();
                        }
                        break;
                    case 1://通讯卫士
                        startActivity();
                        break;
                    case 2://软件管家
                        startActivity();
                        break;
                    case 3://病毒查杀
                        startActivity();
                        break;
                    case 4://缓存清理
                        startActivity();
                        break;
                    case 5://进程管理
                        startActivity();
                        break;
                    case 6://流量统计
                        startActivity();
                        break;
                    case 7://高级工具
                        startActivity();
                        break;
                    case 8://设置中心
                        startActivity();
                        break;

                }
            }
        });

        devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, MyDeviceAdminReceiver.class);
        boolean active = devicePolicyManager.isAdminActive(componentName);

        if (!active)
        {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "获取超级管理员权限");
            startActivity(intent);
        }
    }


    //设置密码对话框
    private void showSetUpPaswdDialog()
    {
        final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(HomeActivity.this);
        setUpPasswordDialog.setCallBack(new SetUpPasswordDialog.MyCallBack()
        {
            @Override
            public void ok()
            {
                String firstPwsd = setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd = setUpPasswordDialog.mAffirmET.getText().toString().trim();
                if (!TextUtils.isEmpty(firstPwsd) && TextUtils.isEmpty(affirmPwsd))
                {
                    if (firstPwsd.equals(affirmPwsd))
                    {
                        savePswd(affirmPwsd);
                        setUpPasswordDialog.dismiss();
                        showInterPaswdDialog();
                    }
                    else
                    {
                        Toast.makeText(HomeActivity.this, "两次密码不一致！", 0).show();
                    }
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "密码不能为空！", 0).show();
                }
            }

            @Override
            public void cancle()
            {
                setUpPasswordDialog.dismiss();
            }
        });

        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }

    //输入密码对话框
    private void showInterPaswdDialog()
    {
        final String password = getPassword();
        final InterPasswordDialog mInPswdDialog = new InterPasswordDialog(HomeActivity.this);
        mInPswdDialog.setCallBack(new InterPasswordDialog.MyCallBack()
        {
            @Override
            public void confirm()
            {
                if (TextUtils.isEmpty(mInPswdDialog.getPassword()))
                {
                    Toast.makeText(HomeActivity.this, "密码不能为空！",0).show();
                }
                else if (password.equals(MD5Utils.encode(mInPswdDialog.getPassword())))
                {
                    //进入防盗主页面
                    mInPswdDialog.dismiss();
                    startActivity(LostFindActivity.class);
                }
                else
                {
                    mInPswdDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "密码错误，请重新输入！",0).show();
                }
            }

            @Override
            public void cancle()
            {
                mInPswdDialog.dismiss();
            }
        });

        mInPswdDialog.setCancelable(true);
        mInPswdDialog.show();
    }

    //保存密码
    private void savePswd(String affirmPwsd)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //对密码进行加密
        editor.putString("PhoneAntiTheftPWD", MD5Utils.encode(affirmPwsd));
        editor.commit();
    }

    public void startActivity(Class<?> cls)
    {
        Intent intent = new Intent(HomeActivity.this, cls);
        startActivity(intent);
    }

    //获取密码
    private String getPassword()
    {
        String password = sharedPreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password))
        {
            return "";
        }
        return password;
    }

    //判断用户是否设置过手机防盗密码
    private boolean isSetUpPassword()
    {
        String password = sharedPreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password))
        {
            return false;
        }
        return true;
    }

    public void startActivityC(Class<?> cls)
    {
        Intent intent = new Intent(HomeActivity.this, cls);
        startActivity(intent);
    }

    //按两次退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if ((System.currentTimeMillis()-mExitTime)<2000)
            {
                System.exit(0);
            }
            else
            {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
