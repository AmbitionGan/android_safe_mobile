package com.pfs.mobilesafe.chatper03.test;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

import com.pfs.mobilesafe.chatper03.db.BlackNumberDao;
import com.pfs.mobilesafe.chatper03.entity.BlackContactInfo;

import java.util.List;
import java.util.Random;

public class TestBlackNumberDao extends AndroidTestCase
{
    private Context context;

    @Override
    protected void setUp() throws Exception {
        context = getContext();
        super.setUp();
    }

    public void testAdd() throws Exception
    {
        BlackNumberDao dao = new BlackNumberDao(context);
        Random random = new Random(8979);

        for (long i=0; i<30; i++)
        {
            BlackContactInfo info = new BlackContactInfo();
            info.phoneNumber = 1350000001+i+"";
            info.contactName = "peterpan"+i;
            info.mode = random.nextInt(3)+1;
            dao.add(info);
        }
    }

    public void testDelete() throws Exception
    {
        BlackNumberDao dao = new BlackNumberDao(context);
        BlackContactInfo info = new BlackContactInfo();

        for (long i=1; i<5; i++)
        {
            info.phoneNumber=1350000001+i+"";
            dao.delete(info);

        }
    }

    public void testGetPageBlackNumber() throws Exception
    {
        BlackNumberDao dao = new BlackNumberDao(context);
        List<BlackContactInfo> list = dao.getPageBlackNumber(2, 5);
        for (int i=0; i<list.size(); i++)
        {
            Log.i("TestBlackNumberDao", list.get(i).phoneNumber);
        }
    }



}
