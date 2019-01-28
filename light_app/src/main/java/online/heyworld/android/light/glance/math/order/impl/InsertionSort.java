package online.heyworld.android.light.glance.math.order.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.google.errorprone.annotations.Var;

import java.util.ArrayList;
import java.util.List;

import online.heyworld.android.light.glance.math.order.ISortAlgorithm;
import online.heyworld.android.light.glance.math.order.ISortDisplay;
import online.heyworld.android.light.glance.math.order.util.SortDisplayUtil;
import online.heyworld.android.light.widget.support.ArrayDrawer;
import online.heyworld.android.light.widget.support.TextDrawer;

/**
 * Created by admin on 2019/1/22.
 */

public class InsertionSort implements ISortAlgorithm {

    @Var
    private int orderedCount=0;
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
    @Override
    public String name() {
        return "插入排序";
    }

    @Override
    public void prepare() {

    }

    @Override
    public void begin(int[] source) {
        this.source = source;
        orderedCount=0;
        minIndex = NONE_INDEX;
        minValue = NONE_VALUE;
        workingIndex = 0;
        workingValue = source[workingIndex];
        workDone = false;
    }

    @Override
    public boolean move() {
        if(workingIndex == source.length){
            // 遍历结束，开始处理此轮结果
            for (int shiftIndex = minIndex ; shiftIndex > orderedCount ; shiftIndex--) {
                source[shiftIndex] = source[shiftIndex-1];
            }
            source[orderedCount] = minValue;
            orderedCount++;
            if(orderedCount >= source.length ){
                workDone = true;
            }else{
                minIndex = NONE_INDEX;
                minValue = NONE_VALUE;
                workingIndex = orderedCount;
                workingValue = workingIndex>=source.length?NONE_VALUE:source[workingIndex];
            }
        }else{
            if(minValue == NONE_VALUE || minValue> workingValue){
                minIndex = workingIndex;
                minValue = workingValue;
            }
            workingIndex++;
            workingValue = workingIndex>=source.length?NONE_VALUE:source[workingIndex];
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
            TextDrawer textDrawer = new TextDrawer(canvas, width, height, paint);
            top += textDrawer.drawText("排序参数:\n"+SortDisplayUtil.getArgs(InsertionSort.this) ,top ,View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("数组:\n"+ SortDisplayUtil.getSource(source),top ,View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("注:标红部分右移，将空出的位置放最小值",top ,View.TEXT_ALIGNMENT_TEXT_START);
            ArrayDrawer arrayDrawer = new ArrayDrawer(canvas,width,height,paint,textDrawer);
            arrayDrawer.drawArray(SortDisplayUtil.genLabels(source),source,workDone?-1:workingIndex,workDone?-1:minIndex,
                    SortDisplayUtil.range(orderedCount,minIndex== NONE_INDEX?NONE_INDEX:minIndex-1));
        }
    };
}
