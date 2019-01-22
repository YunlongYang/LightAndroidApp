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

public class DisplayView extends View {
    private Paint mPaint;
    private Resources resources;
    private Display mDisplay;
    public DisplayView(Context context) {
        super(context);
        init();
    }

    public DisplayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DisplayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DisplayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(42);

        resources = getResources();
    }

    public void setDisplay(Display mDisplay) {
        this.mDisplay = mDisplay;
        mDisplay.init(getContext(),mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mDisplay!=null){
            mDisplay.display(canvas,getWidth(),getHeight(),mPaint);
        }
    }

    public interface Display{
        void init(Context context,Paint paint);
        void display(Canvas canvas, int width, int height, Paint paint);
    }
}
