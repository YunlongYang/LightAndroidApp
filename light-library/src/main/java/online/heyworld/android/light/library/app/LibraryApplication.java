package online.heyworld.android.light.library.app;

import android.app.Application;

import online.heyworld.lightandroid.LightAndroidApplicationLike;

/**
 * Created by admin on 2018/12/27.
 */

public class LibraryApplication extends Application {
    LightAndroidApplicationLike androidApplicationLike;
    @Override
    public void onCreate() {
        super.onCreate();
        androidApplicationLike = new LightAndroidApplicationLike();
        androidApplicationLike.onCreate(this);
    }
}
