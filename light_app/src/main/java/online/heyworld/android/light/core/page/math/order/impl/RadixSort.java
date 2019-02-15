package online.heyworld.android.light.core.page.math.order.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.google.common.math.IntMath;
import com.google.errorprone.annotations.Var;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import online.heyworld.android.light.core.page.math.order.ISortAlgorithm;
import online.heyworld.android.light.core.page.math.order.ISortDisplay;
import online.heyworld.android.light.core.page.math.order.util.SortDisplayUtil;
import online.heyworld.android.light.widget.support.TextDrawer;

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
            source[takeSourceIndex] = NONE_VALUE;
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
            top += textDrawer.drawText("排序参数:\n"+SortDisplayUtil.getArgs(RadixSort.this) ,top ,View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("数组:\n"+ SortDisplayUtil.getSource(source),top ,View.TEXT_ALIGNMENT_TEXT_START);

            for (int i = 0; i < mRadixQueueArray.length; i++) {
                textDrawer.drawColumn(i,String.format("%4d",i),new ArrayList(mRadixQueueArray[i]));
            }
        }
    };
}
