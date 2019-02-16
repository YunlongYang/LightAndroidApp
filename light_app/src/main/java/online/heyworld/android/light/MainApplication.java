package online.heyworld.android.light;

import android.app.Application;

import online.heyworld.android.light.core.page.flutter.FlutterGuide;
import online.heyworld.android.light.core.app.service.ServiceRepo;
import online.heyworld.android.light.library.app.context.ContextProvider;
import online.heyworld.android.light.library.util.LogInitUtil;
import online.heyworld.android.light.library.util.ThreadUtils;
import online.heyworld.android.light.plugin.library.PluginLibrary;
import online.heyworld.lightandroid.LightAndroidApplicationLike;

public class MainApplication extends Application {
    LightAndroidApplicationLike lightAndroidApplicationLike;
    @Override
    public void onCreate() {
        super.onCreate();
        ServiceRepo.install(this);
        ContextProvider.install(this);
        LogInitUtil.initLog(this);
        ThreadUtils.postOnBackgroundThread(()->PluginLibrary.init(this));
        lightAndroidApplicationLike = new LightAndroidApplicationLike();
        lightAndroidApplicationLike.onCreate(this);
        FlutterGuide flutterGuide = new FlutterGuide();
        if(flutterGuide.isEnable()){
            flutterGuide.init(this);
        }
        ActivitySets.init(this);
        ServiceSets.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ServiceRepo.exit();
    }
}
