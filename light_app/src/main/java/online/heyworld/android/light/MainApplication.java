package online.heyworld.android.light;

import android.app.Application;
import android.content.Context;

import com.google.common.collect.Iterators;
import com.google.common.io.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import online.heyworld.android.light.glance.flutter.FlutterGuide;
import online.heyworld.android.light.library.app.context.ContextProvider;
import online.heyworld.android.light.library.util.LogInitUtil;
import online.heyworld.lightandroid.LightAndroidApplicationLike;

public class MainApplication extends Application {
    LightAndroidApplicationLike lightAndroidApplicationLike;
    @Override
    public void onCreate() {
        super.onCreate();
        ContextProvider.install(this);
        LogInitUtil.initLog(this);
        lightAndroidApplicationLike = new LightAndroidApplicationLike();
        lightAndroidApplicationLike.onCreate(this);
        FlutterGuide flutterGuide = new FlutterGuide();
        if(flutterGuide.isEnable()){
            flutterGuide.init(this);
        }
    }


}
