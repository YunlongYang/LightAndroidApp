package online.heyworld.android.light.glance.math.order.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.google.common.math.IntMath;
import com.google.errorprone.annotations.Var;

import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import online.heyworld.android.light.glance.math.order.ISortAlgorithm;
import online.heyworld.android.light.glance.math.order.ISortDisplay;

/**
 * Created by yunlong.yang on 2019/1/22.
 */

public class RadixSort implements ISortAlgorithm {

    private Queue<Integer>[] mRadixQueueArray;
    private int[] source;
    @Var
    private int takeSourceIndex;
    @Var
    private int putSourceIndex;
    @Var
    private int takeStackIndex;
    @Var
    private int workingRadix;
    @Var
    private boolean workDone;
    @Var
    private int maxRadix;

    @Override
    public String name() {
        return "基数排序";
    }

    @Override
    public void prepare() {
        mRadixQueueArray = new Queue[10];
        for (int i = 0; i < 10; i++) {
            mRadixQueueArray[i] = new LinkedBlockingQueue<>();
        }
        takeSourceIndex = 0;
        putSourceIndex = 0;
        takeStackIndex = 0;
        workingRadix = 0;
        maxRadix = 0;
    }

    @Override
    public void begin(int[] source) {
        this.source = source;
        workDone = false;
        int temp = Integer.MIN_VALUE;
        for (int i = 0; i < source.length; i++) {
            if(temp<source[i]){
                temp = source[i];
            }
        }
        maxRadix = IntMath.log10(temp, RoundingMode.FLOOR);
    }

    @Override
    public boolean move() {
        if(takeSourceIndex < source.length){
            int workingItem = source[takeSourceIndex];
            int radix = IntMath.pow(10,workingRadix);
            mRadixQueueArray[workingItem/radix%10].add(workingItem);
            source[takeSourceIndex] = NONE;
            takeSourceIndex++;
        }else{
            if(takeStackIndex<10) {
                while (mRadixQueueArray[takeStackIndex].isEmpty()) {
                    takeStackIndex++;
                    if (takeStackIndex == 10) {
                        break;
                    }
                }
            }
            if(takeStackIndex==10){
                takeSourceIndex = 0;
                putSourceIndex = 0;
                takeStackIndex = 0;
                workingRadix++;
                if(workingRadix>maxRadix){
                    workDone = true;
                }
            }else{
                source[putSourceIndex] = mRadixQueueArray[takeStackIndex].poll();
                putSourceIndex++;
            }
        }
        return workDone;
    }

    @Override
    public int[] result() {
        return source;
    }

    @Override
    public ISortDisplay getDisplay() {
        return sortDisplay;
    }
    private ISortDisplay sortDisplay = new ISortDisplay() {

        @Override
        public void display(Canvas canvas, int width, int height, Paint paint) {
            paint.setColor(Color.LTGRAY);
            int top = 0;
            TextDrawer textDrawer = new TextDrawer(canvas,width,height,paint);
            top += textDrawer.drawText("排序参数:\n"+getArgs() ,top ,View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("数组:\n"+getSource(),top ,View.TEXT_ALIGNMENT_TEXT_START);

            for (int i = 0; i < mRadixQueueArray.length; i++) {
                textDrawer.drawColumn(i,String.format("%4d",i),new ArrayList(mRadixQueueArray[i]));
            }
        }


        private String getSource(){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < source.length; i++) {
                if(source[i]== NONE ){
                    stringBuilder.append("[").append(String.format("%4s","")).append("]");
                }else{
                    stringBuilder.append("[").append(String.format("%4d",source[i])).append("]");
                }
                if(i%5==4){
                    stringBuilder.append("\n");
                }else {
                    stringBuilder.append(" ");
                }
            }
            return stringBuilder.toString();
        }
        private String getArgs(){
            StringBuilder stringBuilder = new StringBuilder();
            Field[] fields = RadixSort.class.getDeclaredFields();
            int count = 0;
            for(Field field : fields ){
                if(field.getAnnotation(Var.class)!=null){
                    field.setAccessible(true);
                    try {
                        stringBuilder.append(field.getName()).append(":[").append(field.get(RadixSort.this)).append("] ");
                        if(count%2==1){
                            stringBuilder.append("\n");
                        }
                        count++;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return stringBuilder.toString();
        }
    };

    public static class TextDrawer{
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

        private void drawColumn(int index,String label, List list){
            float width = mPaint.measureText("1111");
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            int left = (int) (width*index);
            int top = height -  (fontMetricsInt.bottom-fontMetricsInt.top);
            top-=drawSingleLineText(label,top,left,View.TEXT_ALIGNMENT_TEXT_START);
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
}
