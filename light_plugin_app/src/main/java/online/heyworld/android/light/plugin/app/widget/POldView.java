package online.heyworld.android.light.plugin.app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import online.heyworld.android.light.plugin.app.R;

/**
 * Created by admin on 2019/1/2.
 */

public class POldView extends View {
    private Paint mPaint;
    private Resources resources;
    private Handler mHandler;
    public POldView(Context context) {
        super(context);
        init();
    }

    public POldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public POldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public POldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(42);

        mHandler = new Handler(Looper.getMainLooper());
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"你点了我,我等下就关闭",Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(getContext() instanceof Activity){
                            ((Activity) getContext()).finish();
                        }
                    }
                },2000);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(100,MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        canvas.drawColor(0xFF666666);
        String text = "I am PView!";
        float textWidth = mPaint.measureText(text);
        float baseLineY = h / 2 + Math.abs(mPaint.ascent() + mPaint.descent()) / 2;
        canvas.drawText(text,(w-textWidth)/2,baseLineY,mPaint);
    }
}
