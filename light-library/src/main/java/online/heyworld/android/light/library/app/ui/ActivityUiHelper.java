package online.heyworld.android.light.library.app.ui;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

public class ActivityUiHelper {
    public static final int PLACE_TITLE = 1;
    public static final int PLACE_TOAST = 2;

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
                handler.removeCallbacks(recoverTask);
                handler.postDelayed(recoverTask,during);
                activity.setTitle(info);
                break;
            case PLACE_TOAST:
                Toast.makeText(activity,info, (int) during).show();
                break;
        }
    }

    private Runnable recoverTask = ()->activity.setTitle(title);
}
