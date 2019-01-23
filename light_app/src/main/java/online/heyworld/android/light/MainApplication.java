package online.heyworld.android.light;

import android.app.Application;

import online.heyworld.android.light.glance.flutter.FlutterGuide;
import online.heyworld.lightandroid.LightAndroidApplicationLike;

public class MainApplication extends Application {
    LightAndroidApplicationLike lightAndroidApplicationLike;
    @Override
    public void onCreate() {
        super.onCreate();
        lightAndroidApplicationLike = new LightAndroidApplicationLike();
        lightAndroidApplicationLike.onCreate(this);
        FlutterGuide flutterGuide = new FlutterGuide();
        if(flutterGuide.isEnable()){
            flutterGuide.init(this);
        }
    }
}
