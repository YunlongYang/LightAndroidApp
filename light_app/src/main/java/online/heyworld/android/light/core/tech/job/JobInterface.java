package online.heyworld.android.light.core.tech.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import online.heyworld.android.light.core.service.UserPhoneService;

public class JobInterface {

    private static final Logger logger = LoggerFactory.getLogger(JobInterface.class);

    public static final int USER_PHONE_SERVICE_ID = 0x100;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public static void startJob(Context context, Class<? extends JobService> jobServiceClass) {
        logger.info("startJob "+jobServiceClass);
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(context, jobServiceClass);
        JobInfo.Builder builder = new JobInfo.Builder(getJobId(jobServiceClass), componentName);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPeriodic(1000*60*10);
//        String delay = mDelayEditText.getText().toString();
//        if (delay != null && !TextUtils.isEmpty(delay)) {
//            //设置JobService执行的最小延时时间
//            builder.setMinimumLatency(Long.valueOf(delay) * 1000);
//        }
//        String deadline = mDeadlineEditText.getText().toString();
//        if (deadline != null && !TextUtils.isEmpty(deadline)) {
//            //设置JobService执行的最晚时间
//            builder.setOverrideDeadline(Long.valueOf(deadline) * 1000);
//        }
//        boolean requiresUnmetered = mWiFiConnectivityRadioButton.isChecked();
//        boolean requiresAnyConnectivity = mAnyConnectivityRadioButton.isChecked();
//        //设置执行的网络条件
//        if (requiresUnmetered) {
//            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
//        } else if (requiresAnyConnectivity) {
//            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//        }
//        builder.setRequiresDeviceIdle(mRequiresIdleCheckbox.isChecked());//是否要求设备为idle状态
//        builder.setRequiresCharging(mRequiresChargingCheckBox.isChecked());//是否要设备为充电状态

        scheduler.schedule(builder.build());
    }

    private static int getJobId(Class<? extends JobService> jobServiceClass){
        if(jobServiceClass.getName().equals(UserPhoneService.class.getName())){
            return USER_PHONE_SERVICE_ID;
        }else {
            return -1;
        }
    }
}
