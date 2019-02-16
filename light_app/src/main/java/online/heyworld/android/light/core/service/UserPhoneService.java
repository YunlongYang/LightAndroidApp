package online.heyworld.android.light.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import online.heyworld.android.light.core.app.service.ServiceRepo;
import online.heyworld.android.light.core.app.service.SolicitudeService;

public class UserPhoneService extends Service {

    SolicitudeService solicitudeService;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        solicitudeService = ServiceRepo.get(SolicitudeService.class);
    }
}
