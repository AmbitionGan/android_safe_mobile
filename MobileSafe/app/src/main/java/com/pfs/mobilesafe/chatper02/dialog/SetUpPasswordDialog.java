package com.pfs.mobilesafe.chatper02.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pfs.mobilesafe.R;


public class SetUpPasswordDialog extends Dialog implements View.OnClickListener
{
    private TextView mTitleTV;
    private EditText mFirstPWDET;
    private EditText mAffirmET;

    private MyCallBack myCallBack;

    private SetUpPasswordDialog(Context context)
    {
        //自定义对话框样式
        super(context, R.style.dialog_custom);
    }

    public void setCallBack(MyCallBack myCallBack)
    {
        this.myCallBack = myCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.setup_password_dialog);
        super.onCreate(savedInstanceState);
        intiView();
    }

    private void intiView()
    {
        mTitleTV = (TextView)findViewById(R.id.tv_setuppwd_title);
        mFirstPWDET = (EditText)findViewById(R.id.et_firstpwd);
        mAffirmET = (EditText)findViewById(R.id.et_affirm_password);

        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancle).setOnClickListener(this);
    }

    //对话框标题栏
    public void setTitle(String title)
    {
        if (!TextUtils.isEmpty(title))
        {
            mTitleTV.setText(title);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_ok:
                myCallBack.ok();
                break;
            case R.id.btn_cancle:
                myCallBack.cancle();
                break;
        }
    }

    public interface MyCallBack
    {
        void ok();
        void cancle();
    }
}
