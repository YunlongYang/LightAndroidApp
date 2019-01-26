package online.heyworld.android.light.widget.support;

import android.graphics.Paint;

public class PaintState {
    private int color;
    private Paint.Style style;
    private float strokeWidth;

    public void save(Paint paint){
        color = paint.getColor();
        style = paint.getStyle();
        strokeWidth = paint.getStrokeWidth();
    }

    public void restore(Paint paint){
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(strokeWidth);
    }
}
