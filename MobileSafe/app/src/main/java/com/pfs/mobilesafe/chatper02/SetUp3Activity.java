package com.pfs.mobilesafe.chatper02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.pfs.mobilesafe.R;


public class SetUp3Activity extends BaseSetUpActivity implements View.OnClickListener
{
    private EditText mInputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initView();
    }

    @Override
    public void showNext()
    {
        String safePhone = mInputPhone.getText().toString().trim();
        if (TextUtils.isEmpty(safePhone))
        {
            Toast.makeText(this, "请输入安全号码",0).show();
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("safephone", safePhone);
        editor.commit();
        startActivityAndFinishSelf(SetUp4Activity.class);
    }

    @Override
    public void showPre()
    {
        startActivityAndFinishSelf(SetUp2Activity.class);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId()==R.id.btn_addcontact)
            startActivityForResult(new Intent(this, ContactSelectActivity.class),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null) {
            String phone = data.getStringExtra("phone");
            mInputPhone.setText(phone);
        }
    }

    public void initView()
    {
        ((RadioButton)findViewById(R.id.rb_third)).setChecked(true);
        findViewById(R.id.btn_addcontact).setOnClickListener(this);
        mInputPhone = (EditText)findViewById(R.id.et_inputphone);
        String safephone = sharedPreferences.getString("safephone", null);
        if (!TextUtils.isEmpty(safephone))
        {
            mInputPhone.setText(safephone);
        }
    }
}
