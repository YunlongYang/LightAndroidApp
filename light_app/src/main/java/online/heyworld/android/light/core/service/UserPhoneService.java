package online.heyworld.android.light.core.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

import online.heyworld.android.light.core.app.service.ServiceRepo;
import online.heyworld.android.light.core.app.service.SolicitudeService;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class UserPhoneService extends JobService {

    private SolicitudeService solicitudeService;

    @Override
    public boolean onStartJob(JobParameters params) {
        solicitudeService.getUserPhoneReceiver().notifyNormalCheck(getApplicationContext());
        jobFinished(params,false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        solicitudeService = ServiceRepo.get(SolicitudeService.class);
    }
}
