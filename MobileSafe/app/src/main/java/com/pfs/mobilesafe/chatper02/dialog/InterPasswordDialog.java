package com.pfs.mobilesafe.chatper02.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pfs.mobilesafe.R;

public class InterPasswordDialog extends Dialog implements View.OnClickListener
{
    private TextView mTitleTV;
    private EditText mInterET;
    private Button mOKBtn;
    private Button mCancleBtn;

    private MyCallBack myCallBack;
    private Context context;

    private InterPasswordDialog(Context context)
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
        setContentView(R.layout.inter_password_dialog);
        super.onCreate(savedInstanceState);
        intiView();
    }

    private void intiView()
    {
        mTitleTV = (TextView)findViewById(R.id.tv_interpwd_title);
        mInterET = (EditText)findViewById(R.id.et_inter_password);
        mOKBtn = (Button)findViewById(R.id.btn_comfirm);
        mCancleBtn = (Button)findViewById(R.id.btn_dismiss);

        mOKBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
    }

    //对话框标题栏
    public void setTitle(String title)
    {
        if (!TextUtils.isEmpty(title))
        {
            mTitleTV.setText(title);
        }
    }

    public String getPassword()
    {
        return mInterET.getText().toString();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_comfirm:
                myCallBack.confirm();
                break;
            case R.id.btn_dismiss:
                myCallBack.cancle();
                break;
        }
    }

    public interface MyCallBack
    {
        void confirm();
        void cancle();
    }
}
