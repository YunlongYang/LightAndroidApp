package online.heyworld.android.light.glance.math.order;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import online.heyworld.android.light.R;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.widget.DisplayView;

public class MathOrderActivity extends BaseCompatActivity {
    private ISortAlgorithm sortAlgorithm;
    private Handler mHandler;
    private DisplayView displayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_order);
        try {
            sortAlgorithm = (ISortAlgorithm) Class.forName(getIntent().getStringExtra("SortClass")).newInstance();
            init();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"算法未找到",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void init(){
        sortAlgorithm.prepare();
        sortAlgorithm.begin(new SourceCreator(10,1000).create());
        displayView = findViewById(R.id.display_view);
        displayView.setDisplay(new DisplayView.Display() {
            @Override
            public void init(Context context, Paint paint) {
                Typeface face = Typeface.createFromAsset(context.getAssets(),"MONACO.TTF");
                paint.setTypeface(face);
            }

            @Override
            public void display(Canvas canvas, int width, int height, Paint paint) {
                sortAlgorithm.getDisplay().display(canvas, width, height, paint);
            }
        });
        setTitle(sortAlgorithm.name());
        mHandler = new Handler();
    }



    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(step,2000);
        addCancelTask(new Runnable() {
            @Override
            public void run() {
                mHandler.removeCallbacks(step);
            }
        });
    }

    private final Runnable step = new Runnable() {
        @Override
        public void run() {
            if(sortAlgorithm.move()){
                displayView.invalidate();
            }else{
                mHandler.postDelayed(step,1000);
                displayView.invalidate();
            }
        }
    };
}
