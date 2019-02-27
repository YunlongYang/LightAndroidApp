package online.heyworld.android.light;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;

import online.heyworld.android.light.core.app.service.ServiceRepo;
import online.heyworld.android.light.core.app.service.SolicitudeService;
import online.heyworld.android.light.core.page.flutter.FlutterGuide;
import online.heyworld.android.light.core.util.time.DuringUtils;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.library.app.ui.ActivityUiHelper;
import online.heyworld.android.light.library.route.ActivityRoute;
import online.heyworld.android.light.library.route.Operator;

public class MainActivity extends BaseCompatActivity {

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = getTitle().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        postDelayed(() -> showUseTime(), 2000);

    }

    private void showUseTime() {
        ServiceRepo.get(SolicitudeService.class)
                .getTodayUseTime(
                        aLong -> post(() ->
                                        activityUiHelper.tip(
                                                ActivityUiHelper.PLACE_TITLE,
                                                "今日使用手机" + DuringUtils.duringToString(aLong.longValue()),
                                                3000)));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void viewContext(View view) {
        ActivityRoute.of(this).go("/plugin");
    }

    public void viewFlutterCompare(View view) {
        if (new FlutterGuide().isEnable()) {
            ActivityRoute.of(this).go("/flutter");
        } else {
            Toast.makeText(this, "Flutter未启用，请参考ReadMe文件启用", Toast.LENGTH_SHORT).show();
        }
    }


    public void playBlock(View view) {
        ActivityRoute.of(this).go("/game/block");

    }

    public void sortAlgorithm(View view) {
        ActivityRoute.of(this).go("/sort_list");
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void openWebView(View view) {
        ActivityRoute.of(this).go("/web",Collections.singletonMap(Operator.ARG_KEY_DATA,"https://github.com/login"));
//        ActivityRoute.of(this).go("/web",Collections.singletonMap(Operator.ARG_KEY_DATA,"https://html5test.com/"));
    }
}
