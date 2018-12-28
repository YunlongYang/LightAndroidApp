package online.heyworld.android.light;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import online.heyworld.android.light.glance.context.LearnContextActivity;
import online.heyworld.android.light.glance.plugin.PluginIntroActivity;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.library.app.activity.ReferenceActivity;
import online.heyworld.android.light.library.app.activity.ReferenceWebActivity;
import online.heyworld.android.light.library.route.ActivityRoute;

public class LaunchActivity extends BaseCompatActivity {

    private TextView tipTv;
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
    }

    private static void initEnv(Activity activity){
        ActivityRoute.register("/main",MainActivity.class);
        ActivityRoute.register("/learn_context",LearnContextActivity.class);
        ActivityRoute.register("/reference",ReferenceActivity.class);
        ActivityRoute.register("/reference/web",ReferenceWebActivity.class);
        ActivityRoute.register("/plugin",PluginIntroActivity.class);
    }

    private void getWelcomeTip(){
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("https://heyworld.online/welcome").get().build();
        client.newCall(request).enqueue(new Callback() {
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
    }

}
