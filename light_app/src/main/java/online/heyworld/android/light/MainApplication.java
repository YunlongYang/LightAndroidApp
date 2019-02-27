package online.heyworld.android.light;

import android.app.Application;
import android.provider.Settings;
import android.util.Log;

import online.heyworld.android.light.core.page.flutter.FlutterGuide;
import online.heyworld.android.light.core.app.service.ServiceRepo;
import online.heyworld.android.light.core.tech.ssh.SSH;
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
        lightAndroidApplicationLike = new LightAndroidApplicationLike.Builder().setLeakCanaryEnable(false).build();
        lightAndroidApplicationLike.onCreate(this);
        Log.i("HTTP_PROXY",String.valueOf(Settings.System.getString(getContentResolver(),
                Settings.System.HTTP_PROXY)));
        FlutterGuide flutterGuide = new FlutterGuide();
        if(flutterGuide.isEnable()){
            flutterGuide.init(this);
        }
        ActivitySets.init(this);
        ServiceSets.init(this);
        SSH.startServer();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ServiceRepo.exit();
    }
}
