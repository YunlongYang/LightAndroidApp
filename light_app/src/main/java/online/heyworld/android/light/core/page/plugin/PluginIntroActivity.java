package online.heyworld.android.light.core.page.plugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.heyworld.android.light.R;
import online.heyworld.android.light.library.listener.net.ProgressListener;
import online.heyworld.android.light.library.listener.net.ResponseListener;
import online.heyworld.android.light.library.route.ActivityRoute;
import online.heyworld.android.light.library.service.event.EventService;
import online.heyworld.android.light.library.util.InternetUtil;
import online.heyworld.android.light.library.util.ThreadUtils;
import online.heyworld.android.light.plugin.library.PluginLibrary;
import online.heyworld.android.light.widget.support.ListAdapter;

public class PluginIntroActivity extends AppCompatActivity {

    private ListView mPluginLv;
    private ListAdapter<String> mPluginAdapter;
    private List<String> dataList;
    private EditText mPluginUrlEt;
    private ProgressBar mDownloadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_intro);
        mPluginLv = findViewById(R.id.plugin_list_view);
        mPluginUrlEt =findViewById(R.id.plugin_url_et);
        mDownloadProgress = findViewById(R.id.progress_bar);
        mPluginUrlEt.setText("https://github.com/YunlongYang/Repository/raw/master/android/online/heyworld/android/light/plugin/app/light_plugin_app-debug.apk");
        dataList = new ArrayList<>();
        dataList.addAll(PluginLibrary.getAppMap().keySet());
        mPluginAdapter = new ListAdapter<String>(dataList) {
            @Override
            protected View createView() {
                return View.inflate(getApplicationContext(),R.layout.list_view_default_item,null);
            }

            @Override
            protected void drawInfo(View view, String s) {
                TextView textView = (TextView) view;
                textView.setText(s);
            }
        };
        mPluginLv.setAdapter(mPluginAdapter);
        mPluginLv.setOnItemClickListener((parent, view, position, id) -> loadPlugin(view));
    }

    public void loadPlugin(View view) {
        Map<String,Object> args = new HashMap<>();
        args.put("packageName","online.heyworld.android.light.plugin.app");
        args.put("viewName","online.heyworld.android.light.plugin.app.widget.PView");
        ActivityRoute.of(this).go("/plugin/library",args);
    }

    public void downloadPlugin(View view) {
        mDownloadProgress.setVisibility(View.VISIBLE);
        String url = mPluginUrlEt.getText().toString();
        InternetUtil.get(url)
                .listenOn(new ProgressListener() {
                    @Override
                    public void onStart(long allLength) {
                        runOnUiThread(()->mDownloadProgress.setMax((int) (allLength/1000)));
                    }

                    @Override
                    public void onProgress(long nowLength, long allLength) {
                        EventService.on("downloadPlugin","nowLength:"+nowLength+" allLength:"+allLength);
                        runOnUiThread(()->mDownloadProgress.setProgress((int) (nowLength/1000)));
                    }
                }).executeAsyncBackground(new ResponseListener() {
            @Override
            public void onResponse(InternetUtil.Response response) {
                if(response.getCode() == 200 ){
                    try {
                        File tempApkFile = new File("/sdcard/Android/tmp/app.apk");
                        Files.createParentDirs(tempApkFile);
                        Files.write(response.getBody(),tempApkFile);
                        PluginLibrary.install(getApplicationContext(),tempApkFile.getAbsolutePath());
                        tempApkFile.delete();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        ThreadUtils.postOnMainThread(()->{
                            dataList.clear();
                            dataList.addAll(PluginLibrary.getAppMap().keySet());
                            mPluginAdapter.notifyDataSetChanged();
                        });
                    }
                }else{
                    EventService.on("downloadPlugin","error:"+response);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
