package online.heyworld.android.light.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import online.heyworld.android.light.R;

/**
 * Created by yunlong.yang on 2018/12/27.
 */

public class LightView extends View {
    private Paint mPaint;
    private Resources resources;
    public LightView(Context context) {
        super(context);
        init();
    }

    public LightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(42);

        resources = getResources();
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
        canvas.drawColor(resources.getColor(R.color.colorPrimaryLight));
        String text = "I am LightView!";
        float textWidth = mPaint.measureText(text);
        float baseLineY = h / 2 + Math.abs(mPaint.ascent() + mPaint.descent()) / 2;
        canvas.drawText(text,(w-textWidth)/2,baseLineY,mPaint);
    }
}
