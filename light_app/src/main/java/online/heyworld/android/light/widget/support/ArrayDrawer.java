package online.heyworld.android.light.widget.support;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ArrayDrawer {
    Canvas canvas;
    int width;
    int height;
    Paint mPaint;
    TextDrawer textDrawer;
    int paintColor;

    public ArrayDrawer(Canvas canvas, int width, int height, Paint mPaint,TextDrawer textDrawer) {
        this.canvas = canvas;
        this.width = width;
        this.height = height;
        this.mPaint = mPaint;
        paintColor = mPaint.getColor();
        this.textDrawer = textDrawer;
    }

    public void drawArray(String[] labels,int[] source,int nowIndex,int signIndex){
        int defaultColor = Color.DKGRAY;
        int nowColor = Color.WHITE;
        int signColor = Color.LTGRAY;
        for (int i = 0; i < labels.length; i++) {
            int color = defaultColor;
            if(i == nowIndex){
                color = nowColor;
            }
            if(i == signIndex){
                color = signColor;
            }
            drawArrayItem(labels[i],source[i],i,color);
            mPaint.setColor(paintColor);
        }
       mPaint.setColor(paintColor);
    }

    private void drawArrayItem(String label,int value,int index,int color){
        float width = mPaint.measureText("1111");
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int left = (int) (width*index);
        int top = height -  (fontMetricsInt.bottom-fontMetricsInt.top);
        mPaint.setColor(color);
        int textHeight = textDrawer.drawSingleLineText(label,top,left, View.TEXT_ALIGNMENT_TEXT_START);
        top-= textHeight;
        canvas.drawRect(left+3,(1-value/1000.0f)*(height/2)+height/2,left+width-3,height-textHeight,mPaint);
    }
}
