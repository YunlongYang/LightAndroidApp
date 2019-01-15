package online.heyworld.android.light.glance.block.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import online.heyworld.android.light.glance.block.bean.Block;

/**
 * Created by admin on 2019/1/3.
 */

public class BlockView extends View {
    int blockRowCount = 20;
    int blockColumnCount = 30;


    static final int BLOCK_WIDTH = 4;
    private Block blockActive;
    private boolean[][] blockStatic;
    private int[] fullLineIndexes;

    private Paint blockPaint;
    private Paint fullLineBlockPaint;

    public BlockView(Context context) {
        super(context);
        init();
    }

    public BlockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BlockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        blockPaint = new Paint();
        blockPaint.setColor(Color.LTGRAY);
        fullLineIndexes = new int[0];

        fullLineBlockPaint = new Paint();
        fullLineBlockPaint.setColor(Color.WHITE);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();

        int unitWidth = w/blockRowCount;
        int unitHeight = unitWidth;
        drawStaticBlock(canvas,blockStatic,unitWidth,unitHeight);
        drawBlock(canvas,blockActive,unitWidth,unitHeight);
    }

    private void drawStaticBlock(Canvas canvas, boolean[][] blockStatic, int unitWidth, int unitHeight) {
        if(blockStatic==null) return;
        Rect rect = new Rect();
        for(int r=0;r<blockRowCount;r++) {
            for (int c = 0; c < blockColumnCount; c++) {
                if(fullLineIndexes.length>0  && isInFullLine(r)){
                    rect.left = r*unitWidth;
                    rect.top = c*unitHeight;
                    rect.right = (r+1)*unitWidth;
                    rect.bottom = (c+1)*unitHeight;
                    canvas.drawRect(rect,fullLineBlockPaint);
                }else if (blockStatic[r][c]) {
                    rect.left = r*unitWidth+2;
                    rect.top = c*unitHeight+2;
                    rect.right = (r+1)*unitWidth-2;
                    rect.bottom = (c+1)*unitHeight-2;
                    canvas.drawRect(rect,blockPaint);
                }
            }
        }
    }

    private boolean isInFullLine(int lineIndex){
        for(int index : fullLineIndexes){
            if(index == lineIndex){
                return true;
            }
        }
        return false;
    }

    public void drawBlock(boolean[][] bg,Block activeBlock){
       this.blockStatic = bg;
       this.blockActive = activeBlock;
        fullLineIndexes = new int[0];
        invalidate();
    }

    public void drawFullLine(boolean[][] bg,int[] fullLineIndexes){
        this.blockStatic = bg;
        this.blockActive = null;
        this.fullLineIndexes = fullLineIndexes;
        invalidate();
    }



    private void drawBlock(Canvas canvas,Block block,int unitWidth,int unitHeight){
        if(block==null) return;
        int left = block.position.x*unitWidth;
        int top = block.position.y*unitHeight;
        Rect rect = new Rect();
        for(int r=0;r<BLOCK_WIDTH;r++){
            for (int c = 0; c < BLOCK_WIDTH; c++) {
                if(block.value[r][c]){
                    rect.left = left+r*unitWidth+2;
                    rect.top = top+c*unitHeight+2;
                    rect.right = left+(r+1)*unitWidth-2;
                    rect.bottom = top+(c+1)*unitHeight-2;
                    canvas.drawRect(rect,blockPaint);
                }
            }
        }
    }

}
