package online.heyworld.android.light.widget.support;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.View;

import online.heyworld.android.light.glance.math.order.ISortAlgorithm;
import online.heyworld.android.light.glance.math.order.util.SortDisplayUtil;

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
        int defaultColor = Color.GRAY;
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

    public void drawArray(String[] labels,int[] source,int nowIndex,int signIndex, int[] shiftIndexes){
        int defaultColor = Color.GRAY;
        int nowColor = Color.WHITE;
        int signColor = Color.LTGRAY;
        int shiftColorColor = Color.RED;
        for (int i = 0; i < labels.length; i++) {
            int color = defaultColor;

            if(SortDisplayUtil.contains(i,shiftIndexes)){
                color = shiftColorColor;
            }

            if(i == signIndex){
                color = signColor;
            }

            if(i == nowIndex){
                color = nowColor;
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
        if(value == ISortAlgorithm.NONE_VALUE){

        }else{
            canvas.drawRect(left+3,(1-value/1000.0f)*(height/3)+height/2,left+width-3,height-textHeight,mPaint);
        }
    }

    public void drawConnect(int[] source,int firstIndex,int nextIndex,int color){
        Point firstPoint = getItemPoint(source[firstIndex],firstIndex);
        Point nextPoint = getItemPoint(source[nextIndex],nextIndex);
        PaintState paintState = new PaintState();
        paintState.save(mPaint);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        Path path = new Path();
        path.moveTo(firstPoint.x,firstPoint.y);
        path.quadTo((firstPoint.x+nextPoint.x)/2,Math.min(firstPoint.y,nextPoint.y)-10,nextPoint.x,nextPoint.y);
        canvas.drawPath(path,mPaint);
        paintState.restore(mPaint);
    }



    private Point getItemPoint(int value,int index){
        float width = mPaint.measureText("1111");
        int left = (int) (width*index);
        return new Point((int)(left+width/2),(int)((1-value/1000.0f)*(height/3)+height/2));
    }


}
