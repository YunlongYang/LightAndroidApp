package online.heyworld.android.light.glance.math.order.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.google.errorprone.annotations.Var;

import online.heyworld.android.light.glance.math.order.ISortAlgorithm;
import online.heyworld.android.light.glance.math.order.ISortDisplay;
import online.heyworld.android.light.glance.math.order.util.SortDisplayUtil;
import online.heyworld.android.light.widget.support.ArrayDrawer;
import online.heyworld.android.light.widget.support.TextDrawer;

/**
 * 选择排序(Selection-sort)是一种简单直观的排序算法。它的工作原理：首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
 */

public class SelectionSort implements ISortAlgorithm {

    @Var
    private int cycleIndex;
    @Var
    private boolean workDone;

    @Var
    private int minIndex;
    @Var
    private int minValue;

    @Var
    private int workingIndex;
    @Var
    private int workingValue;

    private int[] source;
    private boolean cycleDone;
    @Override
    public String name() {
        return "选择排序";
    }

    @Override
    public void prepare() {
        minIndex = NONE_INDEX;
        minValue = NONE_VALUE;
        cycleIndex = 0;
        workDone = false;
        cycleDone = false;
    }

    @Override
    public void begin(int[] source) {
        this.source = source;

    }

    private String[] genLabels(){
        String[] labels = new String[source.length];
        for (int i = 0; i <source.length; i++) {
            labels[i] = String.valueOf(source[i]);
        }
        return labels;
    }

    @Override
    public boolean move() {
        if(workingIndex == source.length){
            if(minValue != NONE_VALUE){
                source[minIndex] = source[cycleIndex];
                source[cycleIndex] = minValue;
            }
            cycleIndex++;
            workingIndex = cycleIndex;
            minIndex = NONE_INDEX;
            minValue = NONE_VALUE;
            if(cycleIndex == source.length){
                workDone = true;
            }
        }else{
            workingValue = source[workingIndex];
            if(minValue == NONE_VALUE){
                minValue = workingValue;
                minIndex = workingIndex;
            }else{
                if(minValue>workingValue){
                    minValue = workingValue;
                    minIndex = workingIndex;
                }
            }
            workingIndex++;
        }
        checkCycleDone();
        return workDone;
    }

    private void checkCycleDone(){
        cycleDone = workingIndex == source.length;
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
            TextDrawer textDrawer = new TextDrawer(canvas, width, height, paint);
            top += textDrawer.drawText("排序参数:\n"+ SortDisplayUtil.getArgs(SelectionSort.this) ,top , View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("数组:\n"+ SortDisplayUtil.getSource(source),top ,View.TEXT_ALIGNMENT_TEXT_START);
            ArrayDrawer arrayDrawer = new ArrayDrawer(canvas, width, height, paint,textDrawer);
            arrayDrawer.drawArray(genLabels(),source,workingIndex,minIndex);
            if((!workDone) && cycleDone){
                arrayDrawer.drawConnect(source,cycleIndex,minIndex,Color.LTGRAY);
            }
        }
    };
}
