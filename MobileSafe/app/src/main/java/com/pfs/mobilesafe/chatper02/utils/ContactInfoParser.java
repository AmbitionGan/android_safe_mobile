package com.pfs.mobilesafe.chatper02.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.pfs.mobilesafe.chatper02.entity.ContactInfo;

import java.util.ArrayList;
import java.util.List;

public class ContactInfoParser
{
    public static List<ContactInfo> getSystemContact(Context context) {
        ContentResolver resolver = context.getContentResolver();
        //查询raw_contacts表，取出联系人id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri datauri = Uri.parse("content://com.android.contacts/data");
        List<ContactInfo> infos = new ArrayList<ContactInfo>();
        Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);

        while (cursor.moveToNext())
        {
            String id = cursor.getString(0);
            if (id != null)
            {
                System.out.println("联系人id：" + id);
                ContactInfo info = new ContactInfo();
                info.id = id;
                //根据联系人的id查询data表（系统API并不是去查询打表而是去查询data表的视图）
                Cursor dataCursor = resolver.query(datauri, new String[]
                        {"dara1", "mimetype"}, "raw_contact_id=?", new String[]{id}, null);

                while (dataCursor.moveToNext())
                {
                    String data1 = dataCursor.getString(0);
                    String mimetype = dataCursor.getString(1);

                    if ("vnd.android.cursor.item/name".equals(mimetype))
                    {
                        System.out.println("姓名=" + data1);
                        info.name = data1;
                    } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype))
                    {
                        System.out.println("电话=" + data1);
                        info.phone = data1;
                    }
                }

                infos.add(info);
                dataCursor.close();
            }
        }

        cursor.close();
        return infos;
    }

    public static List<ContactInfo> getSimContacts(Context context)
    {
        Uri uri = Uri.parse("content://icc/adn");
        List<ContactInfo> infos = new ArrayList<ContactInfo>();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                ContactInfo info = new ContactInfo();
                int nameFiledColumnIndex = cursor.getColumnIndex("name");
                info.name = cursor.getString(nameFiledColumnIndex);
                int numberFiledColumnIndex = cursor.getColumnIndex("number");
                info.phone = cursor.getString(numberFiledColumnIndex);
                infos.add(info);
            }
        }
        cursor.close();
        return infos;
    }
}

