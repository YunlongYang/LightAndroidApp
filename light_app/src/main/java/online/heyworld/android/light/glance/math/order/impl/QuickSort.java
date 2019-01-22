package online.heyworld.android.light.glance.math.order.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import online.heyworld.android.light.glance.math.order.ISortAlgorithm;
import online.heyworld.android.light.glance.math.order.ISortDisplay;
import online.heyworld.android.light.widget.support.TextDrawer;

/**
 * Created by admin on 2019/1/22.
 */

public class QuickSort implements ISortAlgorithm {
    @Override
    public String name() {
        return "快速排序";
    }

    @Override
    public void prepare() {

    }

    @Override
    public void begin(int[] source) {

    }

    @Override
    public boolean move() {
        return true;
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
            top += textDrawer.drawText("正在编写中" ,top , View.TEXT_ALIGNMENT_TEXT_START);
//            top += textDrawer.drawText("排序参数:\n"+SortDisplayUtil.getArgs(BubbleSort.this) ,top ,View.TEXT_ALIGNMENT_TEXT_START);
//            top += textDrawer.drawText("数组:\n"+ SortDisplayUtil.getSource(source),top ,View.TEXT_ALIGNMENT_TEXT_START);
//            top += textDrawer.drawText("动作:\n"+ action,top ,View.TEXT_ALIGNMENT_TEXT_START);
        }
    };
}
