package online.heyworld.android.light.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

import online.heyworld.android.light.core.app.service.DataBaseService;
import online.heyworld.android.light.core.app.service.ServiceRepo;
import online.heyworld.android.light.core.app.service.SolicitudeService;

public class UserPhoneReceiver extends BroadcastReceiver {
    private DataBaseService dataBaseService;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        dataBaseService = ServiceRepo.get(DataBaseService.class);

        Log.i("UserPhoneReceiver","onReceive action:"+action+ " time:"+new Date().toString());

        if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏

        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
            record(System.currentTimeMillis());
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
            dataBaseService.query(jsonObject -> {
                JSONObject USER_PHONE_SOLICITUDE = jsonObject.getJSONObject(SolicitudeService.Key.USER_PHONE_SOLICITUDE);
                dataBaseService.record(SolicitudeService.Key.LAST_OPEN_TIME, System.currentTimeMillis());
                dataBaseService.recordWhenQuery(jsonObject,SolicitudeService.Key.USER_PHONE_SOLICITUDE,USER_PHONE_SOLICITUDE);
            });
        }
    }

    private void record(final long closeTime){
        if(closeTime == -1 ){
            dataBaseService.query(jsonObject -> {
                JSONObject USER_PHONE_SOLICITUDE = jsonObject.getJSONObject(SolicitudeService.Key.USER_PHONE_SOLICITUDE);
                USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_DURING,0);
                dataBaseService.recordWhenQuery(jsonObject,SolicitudeService.Key.USER_PHONE_SOLICITUDE,USER_PHONE_SOLICITUDE);
            });
        }else{
            dataBaseService.query(jsonObject -> {
                JSONObject USER_PHONE_SOLICITUDE = jsonObject.getJSONObject(SolicitudeService.Key.USER_PHONE_SOLICITUDE);

                long lastDuring = USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_DURING);
                long lastOpenTime = USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_OPEN_TIME);
                if(lastOpenTime <= 0 ){

                }else{
                    long during = closeTime - lastOpenTime;
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_DURING,during+lastDuring);
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_OPEN_TIME,-1);
                    dataBaseService.recordWhenQuery(jsonObject,SolicitudeService.Key.USER_PHONE_SOLICITUDE,USER_PHONE_SOLICITUDE);
                }
            });
        }
    }
}
