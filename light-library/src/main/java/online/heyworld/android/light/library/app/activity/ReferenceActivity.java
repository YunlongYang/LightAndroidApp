package online.heyworld.android.light.library.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import online.heyworld.android.light.library.R;

public class ReferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("title"));
        TextView tv = findViewById(R.id.reference_content_tv);
        tv.setText(getIntent().getStringExtra("content"));
    }
}
