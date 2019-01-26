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
            top += textDrawer.drawText("限制条件" ,top , View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("1、输入的线性表的元素属于有限偏序集S" ,top , View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("1、输入的线性表的元素属于有限偏序集S；" ,top , View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("计算过程" ,top , View.TEXT_ALIGNMENT_TEXT_START);
            top += textDrawer.drawText("对于给定的输入序列中的每一个元素x，确定该序列中值小于x的元素的个数（此处并非比较各元素的大小，而是通过对元素值的计数和计数值的累加来确定）。一旦有了这个信息，就可以将x直接存放到最终的输出序列的正确位置上。" ,top , View.TEXT_ALIGNMENT_TEXT_START);
//            top += textDrawer.drawText("排序参数:\n"+SortDisplayUtil.getArgs(BubbleSort.this) ,top ,View.TEXT_ALIGNMENT_TEXT_START);
//            top += textDrawer.drawText("数组:\n"+ SortDisplayUtil.getSource(source),top ,View.TEXT_ALIGNMENT_TEXT_START);
//            top += textDrawer.drawText("动作:\n"+ action,top ,View.TEXT_ALIGNMENT_TEXT_START);
        }
    };
}
