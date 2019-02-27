package online.heyworld.android.light.library.route;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;


/**
 * Created by yunlong.yang on 2018/12/27.
 */

public class ActivityOperator implements Operator {
    private static final Logger logger = LoggerFactory.getLogger(ActivityOperator.class);
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
        logger.info("activity_go where:"+where+" , args:["+args.toString()+"]");
        Class targetClass = ActivityRoute.get(where);
        if(targetClass!=null) {
            Intent intent = new Intent(activity, targetClass);
            for (Map.Entry<String, Object> entry : args.entrySet()) {
                if(ARG_KEY_DATA.equals(entry.getKey())){
                    intent.setData(Uri.parse((String) entry.getValue()));
                }else {
                    intent.putExtra(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
            activity.startActivity(intent);
        }else{
            throw new RuntimeException("ActivityOperator go fail:not found @"+where);
        }
    }

    @Override
    public void go(String where) {
        go(where, Collections.emptyMap());
    }
}
