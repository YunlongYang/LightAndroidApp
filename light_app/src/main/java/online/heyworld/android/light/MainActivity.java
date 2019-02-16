package online.heyworld.android.light;

import android.Manifest;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import online.heyworld.android.light.core.page.flutter.FlutterGuide;
import online.heyworld.android.light.core.app.service.ServiceRepo;
import online.heyworld.android.light.core.app.service.SolicitudeService;
import online.heyworld.android.light.core.util.time.DuringUtils;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.library.app.ui.ActivityUiHelper;
import online.heyworld.android.light.library.route.ActivityRoute;
import online.heyworld.android.light.library.util.LightPermissions;

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
//        Map<String,Object> arg = new HashMap<>();
//        arg.put("title",getResources().getString(R.string.resources_intro_title));
//        arg.put("content",getResources().getString(R.string.resources_intro));
//        ActivityRoute.of(this).go("/reference",arg);
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
}
