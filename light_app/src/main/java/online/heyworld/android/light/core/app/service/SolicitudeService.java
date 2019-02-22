package online.heyworld.android.light.core.app.service;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import online.heyworld.android.light.core.receiver.UserPhoneReceiver;
import online.heyworld.android.light.library.listener.ArgListener;
import online.heyworld.android.light.library.listener.net.ResponseListener;
import online.heyworld.android.light.library.util.InternetUtil;

public class SolicitudeService extends BaseService {
    private static final String TAG = SolicitudeService.class.getName();

    public interface Key{
        String USER_PHONE_SOLICITUDE = "USER_PHONE_SOLICITUDE";

        String USER_PHONE_SOLICITUDE_DATE = "USER_PHONE_SOLICITUDE_DATE";

        String LAST_OPEN_TIME = "LAST_OPEN_TIME";

        String LAST_DURING = "LAST_DURING";
    }

    public interface TimeValue{
        long UNSET = 0;
    }

    private UserPhoneReceiver userPhoneReceiver;

    @Override
    public void init() {
        userPhoneReceiver = new UserPhoneReceiver();
        userPhoneReceiver.initData(context);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(userPhoneReceiver,filter);
    }

    public InternetUtil.FutureController requestTopTip(ResponseListener responseListener){
        return InternetUtil.get("https://heyworld.online/welcome")
                .executeAsyncForeground(responseListener);
    }

    public void getTodayUseTime(ArgListener<Long> timeListener){
        ServiceRepo.get(DataBaseService.class).query(jsonObject -> {
            JSONObject USER_PHONE_SOLICITUDE = jsonObject.getJSONObject(SolicitudeService.Key.USER_PHONE_SOLICITUDE);
            if(USER_PHONE_SOLICITUDE!=null){
                long lastDuring = USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_DURING);
                long lastOpenTime = USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_OPEN_TIME);
                long allDuring = lastOpenTime<=0?lastDuring:(System.currentTimeMillis() - lastOpenTime+lastDuring);
                Log.d(TAG,"getTodayUseTime lastDuring:"+lastDuring+" lastOpenTime:"+lastOpenTime+" allDuring:"+allDuring);
                timeListener.on(allDuring);
            }
        });
    }

    public UserPhoneReceiver getUserPhoneReceiver() {
        return userPhoneReceiver;
    }
}
