package online.heyworld.android.light.plugin.ui.library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import online.heyworld.android.light.plugin.library.PluginLibrary;

public class PluginLibraryActivity extends AppCompatActivity {

    private ViewGroup container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_library);
        container = findViewById(R.id.plugin_container);
        try {
            PluginLibrary.install(this,"/sdcard/Android/plugin/online.heyworld.android.light.plugin.app/app.apk");
            View pView = PluginLibrary.loadView(this,"online.heyworld.android.light.plugin.app","online.heyworld.android.light.plugin.app.widget.PView");
            container.addView(pView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
