package online.heyworld.android.light.library.route;

import android.app.Activity;
import android.content.Intent;

import java.util.Collections;
import java.util.Map;

import online.heyworld.android.light.library.service.event.EventService;

/**
 * Created by yunlong.yang on 2018/12/27.
 */

public class ActivityOperator implements Operator {
    private Activity activity;

    public ActivityOperator(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void back() {
        activity.finish();
    }

    @Override
    public void go(String where, Map<String, Object> args) {
        EventService.on("activity_go","where:"+where+" , args:["+args.toString()+"]");
        Class targetClass = ActivityRoute.get(where);
        if(targetClass!=null) {
            Intent intent = new Intent(activity, targetClass);
            for (Map.Entry<String, Object> entry : args.entrySet()) {
                intent.putExtra(entry.getKey(), String.valueOf(entry.getValue()));
            }
            activity.startActivity(intent);
        }else{
            throw new RuntimeException("ActivityOperator go fail:not found @"+where);
        }
    }

    @Override
    public void go(String where) {
        go(where, Collections.<String,Object>emptyMap());
    }
}
