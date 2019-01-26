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
 * Created by admin on 2019/1/22.
 */

public class BubbleSort implements ISortAlgorithm {
    @Var
    private int workingIndex;
    @Var
    private int cycleIndex;
    @Var
    private boolean workDone;
    @Var
    private int now;
    @Var
    private int next;
    @Var
    private boolean movedAtCycle;

    private int[] source;
    private String action;
    @Override
    public String name() {
        return "冒泡排序";
    }

    @Override
    public void prepare() {
        workingIndex = 0;
        cycleIndex = 0;
        workDone =false;
        movedAtCycle = false;
    }

    @Override
    public void begin(int[] source) {
        this.source = source;
    }

    @Override
    public boolean move() {
        StringBuilder stringBuilder = new StringBuilder();
        if(workingIndex<(source.length-1-cycleIndex)){
            now = source[workingIndex];
            next = source[workingIndex+1];
            if(now > next ){
                source[workingIndex] = source[workingIndex+1];
                source[workingIndex+1] = now;
                movedAtCycle = true;
            }else{
            }
            workingIndex++;
        }else{
            workingIndex = 0;
            cycleIndex++;

            if(cycleIndex>=source.length || (!movedAtCycle)){
                if((!movedAtCycle) && cycleIndex< (source.length-1)){
                    stringBuilder.append("排序完成.[循环内未移动元素]");
                }else{
                    stringBuilder.append("排序完成.[遍历完成]");
                }
                workDone = true;
            }else{
                movedAtCycle = false;
            }
        }
        action = stringBuilder.toString();
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
            top += textDrawer.drawText("排序参数:\n"+SortDisplayUtil.getArgs(BubbleSort.this) ,top ,View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("数组:\n"+ SortDisplayUtil.getSource(source),top ,View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("提示: "+ String.valueOf(action==null?"":action),top ,View.TEXT_ALIGNMENT_TEXT_START);
            ArrayDrawer arrayDrawer = new ArrayDrawer(canvas,width,height,paint,textDrawer);
            arrayDrawer.drawArray(SortDisplayUtil.genLabels(source),source,workingIndex,workingIndex+1);
        }
    };
}
