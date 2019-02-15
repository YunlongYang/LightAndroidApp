package online.heyworld.android.light.library.app.ui;

import android.app.Activity;
import android.os.Handler;

public class ActivityUiHelper {
    public static final int PLACE_TITLE = 1;

    private Handler handler;
    private String title;
    private Activity activity;

    public ActivityUiHelper(Activity activity,Handler handler) {
        this.handler = handler;
        this.activity = activity;
        title = activity.getTitle().toString();
    }

    public void tip(int place, String info, long during){
        switch (place){
            case PLACE_TITLE:
                handler.postDelayed(()->activity.setTitle(title),during);
                activity.setTitle(info);
                break;
        }

    }
}
