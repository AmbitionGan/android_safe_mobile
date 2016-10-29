package com.pfs.mobilesafe.chatper02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pfs.mobilesafe.chatper02.adapter.ContactAdapter;
import com.pfs.mobilesafe.chatper02.entity.ContactInfo;
import com.pfs.mobilesafe.chatper02.utils.ContactInfoParser;
import com.pfs.mobilesafe.R;

import java.util.List;

public class ContactSelectActivity extends Activity implements View.OnClickListener
{
    private ListView mListView;
    private ContactAdapter adapter;
    private List<ContactInfo> systemContacts;

    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message message)
        {
            if (message.what == 10)
            {
                if (systemContacts != null)
                {
                    adapter = new ContactAdapter(systemContacts, ContactSelectActivity.this);
                    mListView.setAdapter(adapter);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_select);
        initView();
    }

    private void initView()
    {
        ((TextView)findViewById(R.id.tv_title)).setText("选择联系人");
        ImageView imageView = (ImageView)findViewById(R.id.imgv_leftbtn);
        imageView.setOnClickListener(this);
        imageView.setImageResource(R.drawable.back);
        //导航栏颜色
        ((TextView)findViewById(R.id.tv_title)).setTextColor(ContextCompat.getColor(this, R.color.purple));
        mListView = (ListView)findViewById(R.id.lv_contact);
        new Thread()
        {
            public void run()
            {
                systemContacts = ContactInfoParser.getSystemContact(ContactSelectActivity.this);
                systemContacts.addAll(ContactInfoParser.getSimContacts(ContactSelectActivity.this));
                handler.sendEmptyMessage(10);
            }
        }.start();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ContactInfo item = (ContactInfo)adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("phone", item.phone);
                setResult(0, intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.imgv_leftbtn)
        finish();
    }
}
