package online.heyworld.android.light;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import online.heyworld.android.light.glance.context.LearnContextActivity;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.library.route.ActivityRoute;

public class LaunchActivity extends BaseCompatActivity {

    private TextView tipTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        tipTv = findViewById(R.id.heyworld_tip);
        tipTv.setText("你好，工程师");
        initEnv(this);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityRoute.of(LaunchActivity.this).go("/main");
                ActivityRoute.of(LaunchActivity.this).back();
            }
        },3000);
    }

    private static void initEnv(Activity activity){
        ActivityRoute.register("/main",MainActivity.class);
        ActivityRoute.register("/learn_context",LearnContextActivity.class);
    }

}
