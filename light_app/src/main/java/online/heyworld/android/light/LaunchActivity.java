package online.heyworld.android.light;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import online.heyworld.android.light.core.app.service.ServiceRepo;
import online.heyworld.android.light.core.app.service.SolicitudeService;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.library.listener.net.ResponseListener;
import online.heyworld.android.light.library.route.ActivityRoute;
import online.heyworld.android.light.library.util.InternetUtil;
import online.heyworld.android.light.library.util.LightPermissions;
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
                ServiceRepo.get(SolicitudeService.class).requestTopTip(new ResponseListener() {
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
