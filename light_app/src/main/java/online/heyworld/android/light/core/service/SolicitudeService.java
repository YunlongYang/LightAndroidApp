package online.heyworld.android.light.core.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Calendar;

import online.heyworld.android.light.library.listener.ArgListener;
import online.heyworld.android.light.library.listener.net.ResponseListener;
import online.heyworld.android.light.library.util.InternetUtil;

public class SolicitudeService extends BaseService {
    private PhoneUseTimeService phoneUseTimeService;
    @Override
    public void init() {
        phoneUseTimeService = new PhoneUseTimeService();
        phoneUseTimeService.init(context);
    }

    public InternetUtil.FutureController requestTopTip(ResponseListener responseListener){
        return InternetUtil.get("https://heyworld.online/welcome")
                .executeAsyncForeground(responseListener);
    }

    public void getTodayUseTime(ArgListener<Long> timeListener){
        phoneUseTimeService.getTodayUseTime(timeListener);
    }

    private static class PhoneUseTimeService extends BroadcastReceiver {

        public static final String KEY = PhoneUseTimeService.class.getSimpleName();
        public static final String KEY_RESET = PhoneUseTimeService.class.getSimpleName()+"Reset";

        private long lastOpenTime;
        private DataBaseService dataBaseService;
        public void init(Context context){
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            filter.addAction(KEY_RESET);
            context.registerReceiver(this, filter);
            lastOpenTime = System.currentTimeMillis();
            dataBaseService = ServiceRepo.get(DataBaseService.class);


            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent resetIntent = PendingIntent.getBroadcast(context,0xf01,new Intent(KEY_RESET),PendingIntent.FLAG_ONE_SHOT);
            Calendar calendar= Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)+1,06,0,0);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,resetIntent);
        }


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏

            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                record(System.currentTimeMillis());
                lastOpenTime = -1;
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
                lastOpenTime = System.currentTimeMillis();
            } else if(KEY_RESET.equals(action)){
                record(-1);
            }
        }

        private void record(long closeTime){
            if(closeTime == -1 ){
                dataBaseService.record(KEY,0);
            }else{
                final long during = closeTime - lastOpenTime;
                dataBaseService.query(jsonObject -> {
                    long lastDuring = jsonObject.getLongValue(KEY);
                    dataBaseService.recordWhenQuery(jsonObject,KEY,during+lastDuring);
                });
            }
        }

        public void getTodayUseTime(ArgListener<Long> timeListener) {
            dataBaseService.query(jsonObject -> {
                long lastDuring = jsonObject.getLongValue(KEY);
                long allDuring = lastOpenTime==-1?lastDuring:(System.currentTimeMillis() - lastOpenTime+lastDuring);
                timeListener.on(allDuring);
            });
        }
    }
}
