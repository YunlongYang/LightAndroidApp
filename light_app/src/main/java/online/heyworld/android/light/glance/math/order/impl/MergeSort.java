package online.heyworld.android.light.glance.math.order.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.google.errorprone.annotations.Var;

import online.heyworld.android.light.glance.math.order.ISortAlgorithm;
import online.heyworld.android.light.glance.math.order.ISortDisplay;
import online.heyworld.android.light.glance.math.order.util.SortDisplayUtil;
import online.heyworld.android.light.library.util.CopyUtil;
import online.heyworld.android.light.widget.support.ArrayDrawer;
import online.heyworld.android.light.widget.support.TextDrawer;

/**
 * Created by admin on 2019/1/22.
 */

public class MergeSort implements ISortAlgorithm {

    private int[] source;
    private int[] compareSource;

    @Var
    private int unitSize;
    @Var
    private int unitIndex;
    @Var
    private int fillItemIndex;
    @Var
    private int firstItemIndex;
    @Var
    private int secondItemIndex;
    @Var
    private boolean workDone;

    @Override
    public String name() {
        return "归并排序";
    }

    @Override
    public void prepare() {

    }

    @Override
    public void begin(int[] source) {
        this.source = source;
        compareSource = new int[source.length];
        for (int i = 0; i < compareSource.length; i++) {
            compareSource[i] = source[i];
        }
        unitSize = 2;
        unitIndex = 0;
        firstItemIndex = 0;
        secondItemIndex = 0;
        workDone = false;
    }

    boolean moved = false;
    private void beforeMove(){
        if(!moved){
            takeOutUnit();
        }
    }

    @Override
    public boolean move() {
        beforeMove();
        moved = true;
        int firstBase = unitIndex*unitSize;
        int secondBase = firstBase+unitSize/2;

        if((firstItemIndex>=unitSize/2 && secondItemIndex>=unitSize/2) || fillItemIndex >= source.length){
            if(fillItemIndex >= source.length){
                if(unitSize >= source.length){
                    workDone = true;
                }else {
                    unitSize *=2;
                    unitIndex = 0;
                    firstItemIndex = 0;
                    secondItemIndex = 0;
                    fillItemIndex = 0;
                    CopyUtil.copy(source,compareSource);
                    takeOutUnit();
                }
            }else{
                unitIndex++;
                firstItemIndex = 0;
                secondItemIndex = 0;
                fillItemIndex = unitIndex*unitSize;
                takeOutUnit();
            }
        }else{
            if(firstItemIndex< unitSize/2 && (
                    // 第二组元素不能超过自己的单元范围
                    secondItemIndex >= unitSize/2 ||
                    // 第二组元素不能超过数组长度范围
                    (secondBase+secondItemIndex)>= compareSource.length ||
                    compareSource[firstBase+firstItemIndex]<compareSource[secondBase+secondItemIndex])){
                source[fillItemIndex] = compareSource[firstBase+firstItemIndex];
                firstItemIndex++;
            }else{
                source[fillItemIndex] = compareSource[secondBase+secondItemIndex];
                secondItemIndex++;
            }
            fillItemIndex++;
        }
        return workDone;
    }

    private void takeOutUnit(){
        int firstBase = unitIndex*unitSize;
        int secondBase = firstBase+unitSize/2;
        for (int i = firstBase; i < (firstBase+unitSize) && i<source.length; i++) {
            source[i] = NONE_VALUE;
        }
    }


    @Override
    public int[] result() {
        return new int[0];
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
            top += textDrawer.drawText("排序参数:\n"+SortDisplayUtil.getArgs(MergeSort.this) ,top ,View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("数组:\n"+ SortDisplayUtil.getSource(source),top ,View.TEXT_ALIGNMENT_TEXT_START);

            top += textDrawer.drawText("单元A数组:"+
                    SortDisplayUtil.getSource(SortDisplayUtil.sub(compareSource,unitIndex * unitSize+firstItemIndex ,Math.min(source.length-1,(unitIndex +1)* unitSize-1-unitSize/2))),top ,View.TEXT_ALIGNMENT_TEXT_START);

            top += textDrawer.drawText("单元B数组: "+ SortDisplayUtil.getSource(SortDisplayUtil.sub(compareSource,
                    unitIndex * unitSize+unitSize/2+secondItemIndex,
                    Math.min(source.length-1,(unitIndex +1)* unitSize-1))),top ,View.TEXT_ALIGNMENT_TEXT_START);
//            top += textDrawer.drawText("动作:\n"+ action,top ,View.TEXT_ALIGNMENT_TEXT_START);

            ArrayDrawer arrayDrawer = new ArrayDrawer(canvas,width,height,paint,textDrawer);
            if(!workDone){
                arrayDrawer.drawArray(SortDisplayUtil.genLabels(source),source,-1,-1,
                        SortDisplayUtil.range(unitIndex * unitSize,(unitIndex +1)* unitSize-1));
            }else{
                arrayDrawer.drawArray(SortDisplayUtil.genLabels(source),source,-1,-1);
            }

        }
    };
}
