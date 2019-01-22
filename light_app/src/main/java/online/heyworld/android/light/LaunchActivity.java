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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import online.heyworld.android.light.glance.block.BlockActivity;
import online.heyworld.android.light.glance.context.LearnContextActivity;
import online.heyworld.android.light.glance.math.order.MathOrderActivity;
import online.heyworld.android.light.glance.math.order.MathOrderListActivity;
import online.heyworld.android.light.glance.plugin.PluginIntroActivity;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.library.app.activity.ReferenceActivity;
import online.heyworld.android.light.library.app.activity.ReferenceWebActivity;
import online.heyworld.android.light.library.route.ActivityRoute;
import online.heyworld.android.light.library.util.LightPermissions;
import online.heyworld.android.light.plugin.ui.library.PluginLibraryActivity;

public class LaunchActivity extends BaseCompatActivity {

    private TextView tipTv;
    private LightPermissions.PermissionSession session;
    private static Logger logger = LoggerFactory.getLogger(LaunchActivity.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        tipTv = findViewById(R.id.heyworld_tip);
        initEnv(this);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityRoute.of(LaunchActivity.this).go("/main");
                ActivityRoute.of(LaunchActivity.this).back();
            }
        },3000);
        getWelcomeTip();
        logger.info("getAndroidId :" + getAndroidId(this));
    }
    public static String getAndroidId (Context context) {
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return ANDROID_ID;
    }

    private void initEnv(Activity activity){
        ActivityRoute.register("/main",MainActivity.class);
        ActivityRoute.register("/learn_context",LearnContextActivity.class);
        ActivityRoute.register("/reference",ReferenceActivity.class);
        ActivityRoute.register("/reference/web",ReferenceWebActivity.class);
        ActivityRoute.register("/plugin",PluginIntroActivity.class);
        ActivityRoute.register("/plugin/library", PluginLibraryActivity.class);
        ActivityRoute.register("/game/block", BlockActivity.class);
        ActivityRoute.register("/sort", MathOrderActivity.class);
        ActivityRoute.register("/sort_list", MathOrderListActivity.class);

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        session = LightPermissions.setUp(this, Arrays.asList(permissions));
        session.on(new Runnable() {
            @Override
            public void run() {
                showToast("应用运行需要以下权限",Toast.LENGTH_SHORT);
            }
        }, new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        session.doRequest();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        session.onRequestPermissionsResult(requestCode, permissions, grantResults,this);
    }

    private void getWelcomeTip(){
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("https://heyworld.online/welcome").get().build();
        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.getInt("code")==200){
                        final String data = jsonObject.getString("data");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tipTv.setText(data);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        addCancelTask(new Runnable() {
            @Override
            public void run() {
                call.cancel();
            }
        });
    }

}
