package online.heyworld.android.light.widget.support;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

/**
 * Created by admin on 2019/1/22.
 */

public class TextDrawer {
    Canvas canvas;
    int width;
    int height;
    Paint mPaint;

    public TextDrawer(Canvas canvas, int width, int height, Paint mPaint) {
        this.canvas = canvas;
        this.width = width;
        this.height = height;
        this.mPaint = mPaint;
    }

    public int drawText(String text,int top,int align){
        String[] lines = text.split("\n");
        int topRecord = top;
        for (String line:lines){
            topRecord += drawSingleLineText(line,topRecord,0,align);
        }
        return topRecord;
    }

    public void drawColumn(int index,String label, List list){
        float width = mPaint.measureText("1111");
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int left = (int) (width*index);
        int top = height -  (fontMetricsInt.bottom-fontMetricsInt.top);
        top-=drawSingleLineText(label,top,left, View.TEXT_ALIGNMENT_TEXT_START);
        for(Object item:list){
            top-=drawSingleLineText(item.toString(),top, (int) (left+width/2),View.TEXT_ALIGNMENT_TEXT_START);
        }
    }

    private int drawSingleLineText(String text,int top,int left,int align){
        float textWidth = mPaint.measureText(text);
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        float textHeight = fontMetricsInt.bottom-fontMetricsInt.top;
        float baseLineY = top +  textHeight/ 2 + Math.abs(mPaint.ascent() + mPaint.descent()) / 2;
        float x = 0;
        float y = 0;
        switch (align){
            case View.TEXT_ALIGNMENT_TEXT_START:
                x=left;
                break;
            case View.TEXT_ALIGNMENT_CENTER:
                x= (width-textWidth)/2;
                break;
        }
        canvas.drawText(text,x,baseLineY,mPaint);
        return (int) (fontMetricsInt.bottom-fontMetricsInt.top);
    }
}
