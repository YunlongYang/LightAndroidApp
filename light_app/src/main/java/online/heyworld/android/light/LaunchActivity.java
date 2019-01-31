package online.heyworld.android.light;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import online.heyworld.android.light.glance.block.BlockActivity;
import online.heyworld.android.light.glance.context.LearnContextActivity;
import online.heyworld.android.light.glance.flutter.FlutterGuide;
import online.heyworld.android.light.glance.math.order.MathOrderActivity;
import online.heyworld.android.light.glance.math.order.MathOrderListActivity;
import online.heyworld.android.light.glance.plugin.PluginIntroActivity;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.library.app.activity.ReferenceActivity;
import online.heyworld.android.light.library.app.activity.ReferenceWebActivity;
import online.heyworld.android.light.library.listener.net.ResponseListener;
import online.heyworld.android.light.library.route.ActivityRoute;
import online.heyworld.android.light.library.util.InternetUtil;
import online.heyworld.android.light.library.util.LightPermissions;
import online.heyworld.android.light.library.util.SystemUtil;
import online.heyworld.android.light.plugin.ui.library.PluginLibraryActivity;
import online.heyworld.android.light.route.AppRoute;

public class LaunchActivity extends BaseCompatActivity {

    private TextView tipTv;
    private LightPermissions.PermissionSession session;
    private static Logger logger = LoggerFactory.getLogger(LaunchActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        tipTv = findViewById(R.id.heyworld_tip);
        initAppEnv(this);
        init();
    }

    private void init() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION};
        session = LightPermissions.setUp(this, Arrays.asList(permissions));
        session.onDeny(() -> showToast("应用运行需要以下权限", Toast.LENGTH_SHORT)).onGrant(() -> {
            getWelcomeTip();
            postDelayed(() -> {
                ActivityRoute.of(LaunchActivity.this).go("/main");
                ActivityRoute.of(LaunchActivity.this).back();
            }, 3000);
        });
        if (session.hadAllPermissions()) {
            session.invokeGrant();
        }
    }

    private void initAppEnv(Activity activity) {
        AppRoute.installDefault();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!session.hadAllPermissions()) {
            session.request();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        session.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void getWelcomeTip() {

        InternetUtil.FutureController futureController =
                InternetUtil.get("https://heyworld.online/welcome")
                        .executeAsyncForeground(new ResponseListener() {
                            @Override
                            public void onResponse(InternetUtil.Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(new String(response.getBody()));
                                    if (jsonObject.getInt("code") == 200) {
                                        final String data = jsonObject.getString("data");
                                        tipTv.setText(data);
                                    }
                                } catch (Exception e) {
                                    onError(e);
                                }
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

        addCancelTask(() -> futureController.cancel());
    }

}
