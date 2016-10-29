package com.pfs.mobilesafe.chatper02.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;

public class GPSLocationService extends Service
{
    private LocationManager lm;
    private Mylistener listener;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        listener = new Mylistener();
        Criteria criteria = new Criteria();
        //获取准确的位置
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(true);
        String name = lm.getBestProvider(criteria, true);
        System.out.println("最好的位置提供者："+name);
        lm.requestLocationUpdates(name, 0, 0, listener);
    }

    private class Mylistener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location)
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("accuracy"+location.getAccuracy()+"\n");
            stringBuilder.append("speed:"+location.getSpeed()+"\n");
            stringBuilder.append("jingdu:"+location.getLongitude()+"\n");
            stringBuilder.append("weidu"+location.getLatitude()+"\n");

            String result = stringBuilder.toString();
            SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            String safenumber = sharedPreferences.getString("safephone", "");
            SmsManager.getDefault().sendTextMessage(safenumber, null, result, null, null);
            stopSelf();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lm.removeUpdates(listener);
        listener = null;
    }
}
