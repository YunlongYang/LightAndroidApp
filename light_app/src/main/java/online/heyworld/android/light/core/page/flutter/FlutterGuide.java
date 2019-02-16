package online.heyworld.android.light.core.page.flutter;

import android.app.Activity;
import android.content.Context;

import online.heyworld.android.light.library.reflect.InstanceUtil;

public class FlutterGuide {

    public boolean isEnable(){
        try {
            Class.forName("online.heyworld.android.light.flutter.library.MainActivity");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Class<? extends Activity> getLaunchActivity(){
        try {
            return Class.forName("online.heyworld.android.light.flutter.library.MainActivity").asSubclass(Activity.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void init(Context context){
        try {
            InstanceUtil.newClassInstance("io.flutter.support.ApplicationSupport")
                    .callChain("init")
                    .on(Context.class,context)
                    .done();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
