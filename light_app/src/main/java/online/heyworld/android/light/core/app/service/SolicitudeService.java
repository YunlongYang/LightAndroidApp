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

        String LAST_OPEN_TIME = "LAST_OPEN_TIME";

        String LAST_DURING = "LAST_DURING";
    }

    @Override
    public void init() {
        initData();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(new UserPhoneReceiver(),filter);
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

    private void initData(){
        final DataBaseService dataBaseService = ServiceRepo.get(DataBaseService.class);
        dataBaseService.query(jsonObject -> {
            JSONObject USER_PHONE_SOLICITUDE = jsonObject.getJSONObject(SolicitudeService.Key.USER_PHONE_SOLICITUDE);
            boolean changed = false;
            if(USER_PHONE_SOLICITUDE==null){
                USER_PHONE_SOLICITUDE = new JSONObject();
                USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_DURING,0);
                USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_OPEN_TIME,System.currentTimeMillis());
                changed = true;
            }else{
                if(USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_OPEN_TIME)<=0){
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_OPEN_TIME,System.currentTimeMillis());
                    changed = true;
                }
            }
            if(changed){
                dataBaseService.recordWhenQuery(jsonObject,SolicitudeService.Key.USER_PHONE_SOLICITUDE,USER_PHONE_SOLICITUDE);
            }
        });
    }
}
