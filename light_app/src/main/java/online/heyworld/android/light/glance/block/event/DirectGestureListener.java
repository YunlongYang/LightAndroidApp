package online.heyworld.android.light.glance.block.event;

import android.view.GestureDetector;
import android.view.MotionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by admin on 2019/1/4.
 */

public abstract class DirectGestureListener implements GestureDetector.OnGestureListener {
    private static final Logger logger = LoggerFactory.getLogger("DirectGesture");

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        logger.info("onFling velocityX:{} velocityY:{}",velocityX,velocityY);
        if(Math.abs(velocityX)>Math.abs(velocityY)){
            if(velocityX<0){
                direct(DIRECT.LEFT);
            }else{
                direct(DIRECT.RIGHT);
            }
            return true;
        }else{
            if(velocityY<0){
                direct(DIRECT.UP);
            }else{
                direct(DIRECT.DOWN);
            }
            return true;
        }
    }

    protected abstract void direct(DIRECT direct);

    public enum DIRECT{
        UP,LEFT,RIGHT, DOWN
    }
}
