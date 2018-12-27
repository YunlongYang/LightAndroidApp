package online.heyworld.android.light.library.route;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yunlong.yang on 2018/12/27.
 */

public class ActivityRoute {

    private static final Map<String,Class<? extends Activity>> sRouteMap = new HashMap<>();

    public static Operator of(Activity activity){
         return new ActivityOperator(activity);
    }

    public static void register(String where,Class<? extends Activity> activityClass){
        sRouteMap.put(where,activityClass);
    }

    static Class<? extends Activity> get(String where){
        return sRouteMap.get(where);
    }
}
