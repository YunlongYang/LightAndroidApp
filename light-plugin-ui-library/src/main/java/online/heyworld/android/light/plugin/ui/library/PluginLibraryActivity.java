package online.heyworld.android.light.plugin.ui.library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import online.heyworld.android.light.plugin.library.PluginLibrary;
import online.heyworld.android.light.plugin.library.context.PluginContext;

public class PluginLibraryActivity extends AppCompatActivity {

    private ViewGroup container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_library);
        container = findViewById(R.id.plugin_container);
        try {
            View pView = PluginLibrary.loadView(new PluginContext(this,"online.heyworld.android.light.plugin.app"),"online.heyworld.android.light.plugin.app","online.heyworld.android.light.plugin.app.widget.PView");
            container.addView(pView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
