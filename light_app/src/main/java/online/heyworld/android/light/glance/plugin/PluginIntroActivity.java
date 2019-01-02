package online.heyworld.android.light.glance.plugin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import online.heyworld.android.light.R;
import online.heyworld.android.light.library.route.ActivityRoute;

public class PluginIntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_intro);
    }

    public void loadPlugin(View view) {
        ActivityRoute.of(this).go("/plugin/library");
    }
}
