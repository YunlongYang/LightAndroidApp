package online.heyworld.android.light.core.receiver;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import online.heyworld.android.light.core.app.service.DataBaseService;
import online.heyworld.android.light.core.app.service.ServiceRepo;
import online.heyworld.android.light.core.app.service.SolicitudeService;
import online.heyworld.android.light.core.util.time.DateUtil;

public class UserPhoneReceiver extends BroadcastReceiver {

    private static final Logger logger = LoggerFactory.getLogger("SolicitudeService");

    private DataBaseService dataBaseService;

    private String mRecordDate;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏

        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
            notifyUserLeave();
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
            notifyUserCome();
        }
    }


    public void initData(Context context) {
        dataBaseService = ServiceRepo.get(DataBaseService.class);
        mRecordDate = DateUtil.getSolicitudeDateString();
        dataBaseService.query(jsonObject -> {
            JSONObject USER_PHONE_SOLICITUDE = jsonObject.getJSONObject(SolicitudeService.Key.USER_PHONE_SOLICITUDE);
            boolean changed = false;
            if(USER_PHONE_SOLICITUDE==null){
                USER_PHONE_SOLICITUDE = new JSONObject();
                if(isUserActive(context)){
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.USER_PHONE_SOLICITUDE_DATE,mRecordDate);
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_DURING,SolicitudeService.TimeValue.UNSET);
                    handleUserCome(jsonObject,USER_PHONE_SOLICITUDE);
                    return;
                }else{
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.USER_PHONE_SOLICITUDE_DATE,mRecordDate);
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_DURING,SolicitudeService.TimeValue.UNSET);
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_OPEN_TIME,SolicitudeService.TimeValue.UNSET);
                    changed = true;
                }
            }else{
                if(checkRecordDate(USER_PHONE_SOLICITUDE)){
                    long lastOpenTime = USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_OPEN_TIME);
                    if(isUserActive(context)){
                        if(lastOpenTime<=0){
                            handleUserCome(jsonObject,USER_PHONE_SOLICITUDE);
                            return;
                        }else{
                            logger.info("init data when use open before @"+lastOpenTime);
                        }
                    }else{
                        if(lastOpenTime<=0){
                            logger.info("init data on user inactive and no action before");
                        }else{
                            handleUserLeave(jsonObject,USER_PHONE_SOLICITUDE,System.currentTimeMillis(),lastOpenTime,USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_DURING));
                        }
                    }

                }else{
                    logger.info("init data on date changed");
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_DURING,SolicitudeService.TimeValue.UNSET);
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_OPEN_TIME,SolicitudeService.TimeValue.UNSET);
                    USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.USER_PHONE_SOLICITUDE_DATE,mRecordDate);
                    changed = true;
                }
            }
            if(changed){
                dataBaseService.recordWhenQuery(jsonObject,SolicitudeService.Key.USER_PHONE_SOLICITUDE,USER_PHONE_SOLICITUDE);
            }
        });
    }

    private boolean checkRecordDate(JSONObject USER_PHONE_SOLICITUDE){
        mRecordDate = DateUtil.getSolicitudeDateString();
        String recordDate = USER_PHONE_SOLICITUDE.getString(SolicitudeService.Key.USER_PHONE_SOLICITUDE_DATE);
        return mRecordDate.equals(recordDate);
    }

    public void notifyUserLeave(){
        long closeTime = System.currentTimeMillis();
        dataBaseService.query(jsonObject -> {
            JSONObject USER_PHONE_SOLICITUDE = jsonObject.getJSONObject(SolicitudeService.Key.USER_PHONE_SOLICITUDE);
            if(checkRecordDate(USER_PHONE_SOLICITUDE)){
                long lastDuring = USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_DURING);
                long lastOpenTime = USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_OPEN_TIME);
                if(lastOpenTime <= 0 ){

                }else{
                    handleUserLeave(jsonObject,USER_PHONE_SOLICITUDE,closeTime,lastOpenTime,lastDuring);
                }
            }else{
                USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_DURING,SolicitudeService.TimeValue.UNSET);
                USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_OPEN_TIME,SolicitudeService.TimeValue.UNSET);
                USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.USER_PHONE_SOLICITUDE_DATE,mRecordDate);
                dataBaseService.recordWhenQuery(jsonObject,SolicitudeService.Key.USER_PHONE_SOLICITUDE,USER_PHONE_SOLICITUDE);
            }

        });
    }

    public void notifyUserCome(){
        dataBaseService.query(jsonObject -> {
            JSONObject USER_PHONE_SOLICITUDE = jsonObject.getJSONObject(SolicitudeService.Key.USER_PHONE_SOLICITUDE);
            if(checkRecordDate(USER_PHONE_SOLICITUDE)){

            }else{
                USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_DURING,SolicitudeService.TimeValue.UNSET);
                USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.USER_PHONE_SOLICITUDE_DATE,mRecordDate);
            }
            handleUserCome(jsonObject,USER_PHONE_SOLICITUDE);
        });
    }

    public void notifyNormalCheck(Context applicationContext){
        dataBaseService.query(jsonObject -> {
            JSONObject USER_PHONE_SOLICITUDE = jsonObject.getJSONObject(SolicitudeService.Key.USER_PHONE_SOLICITUDE);
            if(checkRecordDate(USER_PHONE_SOLICITUDE)){
                long lastDuring = USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_DURING);
                long lastOpenTime = USER_PHONE_SOLICITUDE.getLongValue(SolicitudeService.Key.LAST_OPEN_TIME);
                if(lastOpenTime<=0){
                    if(isUserActive(applicationContext)){
                        handleUserCome(jsonObject,USER_PHONE_SOLICITUDE);
                    }
                }else {
                    if(!isUserActive(applicationContext)){
                        handleUserLeave(jsonObject,USER_PHONE_SOLICITUDE,System.currentTimeMillis(),lastOpenTime,lastDuring);
                    }
                }
            }else{
                USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_DURING,SolicitudeService.TimeValue.UNSET);
                USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.USER_PHONE_SOLICITUDE_DATE,mRecordDate);
                dataBaseService.recordWhenQuery(jsonObject,SolicitudeService.Key.USER_PHONE_SOLICITUDE,USER_PHONE_SOLICITUDE);
            }

        });
    }

    private void handleUserCome(JSONObject jsonObject,JSONObject USER_PHONE_SOLICITUDE){
        USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_OPEN_TIME, System.currentTimeMillis());
        logger.info("record user come");
        dataBaseService.recordWhenQuery(jsonObject,SolicitudeService.Key.USER_PHONE_SOLICITUDE,USER_PHONE_SOLICITUDE);
    }

    private void handleUserLeave(JSONObject jsonObject,JSONObject USER_PHONE_SOLICITUDE,long closeTime,long lastOpenTime,long lastDuring){
        logger.info("record user leave");
        long during = closeTime - lastOpenTime;
        USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_DURING,during+lastDuring);
        USER_PHONE_SOLICITUDE.put(SolicitudeService.Key.LAST_OPEN_TIME,SolicitudeService.TimeValue.UNSET);
        dataBaseService.recordWhenQuery(jsonObject,SolicitudeService.Key.USER_PHONE_SOLICITUDE,USER_PHONE_SOLICITUDE);
    }

    private static long createLastOpenTime(Context context){
        if(isUserActive(context)){
            return System.currentTimeMillis();
        }else{
            return SolicitudeService.TimeValue.UNSET;
        }
    }

    private static boolean isUserActive(Context context){
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean screenOn = powerManager.isScreenOn();
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean unLock = (!mKeyguardManager.inKeyguardRestrictedInputMode());
        return screenOn && unLock;
    }
}
