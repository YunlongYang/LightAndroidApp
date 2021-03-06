package online.heyworld.android.light.core.page.math.order.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import online.heyworld.android.light.core.page.math.order.ISortAlgorithm;
import online.heyworld.android.light.core.page.math.order.ISortDisplay;
import online.heyworld.android.light.widget.support.TextDrawer;

/**
 * Created by admin on 2019/1/22.
 */

public class CountingSort implements ISortAlgorithm {

    private int[] source;
    private int[] countArray;
    @Override
    public String name() {
        return "计数排序";
    }

    @Override
    public void prepare() {

    }

    @Override
    public void begin(int[] source) {
        countArray = new int[1000];
        for (int i = 0; i < countArray.length; i++) {
            countArray[i] = 0;
        }
        this.source = source;
    }

    @Override
    public boolean move() {
        for (int i = 0; i < source.length; i++) {
            countArray[source[i]]++;
        }
        int index =0 ;
        for (int i = 0; i < countArray.length; i++) {
            int times =countArray[i];
            while (times>0){
                source[index] = i;
                index++;
                times--;
            }
        }
        return true;
    }

    @Override
    public boolean finished() {
        return false;
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
            top += textDrawer.drawText(NOT_IMPLEMENT, top, View.TEXT_ALIGNMENT_TEXT_START);
//            top += textDrawer.drawText("排序参数:\n"+SortDisplayUtil.getArgs(BubbleSort.this) ,top ,View.TEXT_ALIGNMENT_TEXT_START);
//            top += textDrawer.drawText("数组:\n"+ SortDisplayUtil.getSource(source),top ,View.TEXT_ALIGNMENT_TEXT_START);
//            top += textDrawer.drawText("动作:\n"+ action,top ,View.TEXT_ALIGNMENT_TEXT_START);
        }
    };
}
