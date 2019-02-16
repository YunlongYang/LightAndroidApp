package online.heyworld.android.light;

import android.app.Application;
import android.content.Intent;

import online.heyworld.android.light.core.service.UserPhoneService;

public class ServiceSets {
    public static void init(Application application) {
        Intent service = new Intent(application,UserPhoneService.class);
        application.startService(service);
    }
}
