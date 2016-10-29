package com.pfs.mobilesafe.chatper02.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pfs.mobilesafe.MainActivity;


public class BootCompleteReceiver extends BroadcastReceiver
{
    private static final String TAG = BootCompleteReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        ((MainActivity)context.getApplicationContext()).correctSIM();
    }
}
