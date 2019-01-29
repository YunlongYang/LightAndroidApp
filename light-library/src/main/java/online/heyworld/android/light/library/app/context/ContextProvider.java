package online.heyworld.android.light.library.app.context;

import android.content.Context;

public class ContextProvider {

    private static Context sContext;

    public static void install(Context context) {
        sContext = context.getApplicationContext();
    }

    public static Context get(){
        return sContext;
    }
}
