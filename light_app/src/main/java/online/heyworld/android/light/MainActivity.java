package online.heyworld.android.light;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import online.heyworld.android.light.library.route.ActivityRoute;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void viewContext(View view) {
//        Map<String,Object> arg = new HashMap<>();
//        arg.put("title",getResources().getString(R.string.resources_intro_title));
//        arg.put("content",getResources().getString(R.string.resources_intro));
//        ActivityRoute.of(this).go("/reference",arg);
        ActivityRoute.of(this).go("/plugin");
    }

    public void viewFlutterCompare(View view) {
        Toast.makeText(this,"正在写作中...",Toast.LENGTH_SHORT).show();
    }


    public void playBlock(View view){
        ActivityRoute.of(this).go("/game/block");

    }
}
