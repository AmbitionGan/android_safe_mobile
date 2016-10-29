package com.pfs.mobilesafe.chatper02.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pfs.mobilesafe.chatper02.entity.ContactInfo;
import com.pfs.mobilesafe.R;

import java.util.List;

public class ContactAdapter extends BaseAdapter
{
    private List<ContactInfo> contactInfos;
    private Context context;

    public ContactAdapter(List<ContactInfo> contactInfos, Context context)
    {
        super();
        this.contactInfos = contactInfos;
        this.contactInfos = contactInfos;
    }

    @Override
    public int getCount()
    {
        return contactInfos.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView ==null)
        {
            convertView = View.inflate(context, R.layout.item_list_contact_select, null);
            holder = new ViewHolder();
            holder.mNameTV = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mPhoneTV = (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder
    {
        TextView mNameTV;
        TextView mPhoneTV;
    }
}
