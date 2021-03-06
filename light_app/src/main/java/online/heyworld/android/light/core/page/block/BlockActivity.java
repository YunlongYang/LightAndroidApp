package online.heyworld.android.light.core.page.block;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import online.heyworld.android.light.R;
import online.heyworld.android.light.core.page.block.event.DirectGestureListener;
import online.heyworld.android.light.core.page.block.widget.BlockView;

public class BlockActivity extends AppCompatActivity {



    private BlockView blockView;
    private BlockEngine blockEngine;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("俄罗斯方块");
        setContentView(R.layout.activity_block);
        blockEngine = new BlockEngine();
        blockView =findViewById(R.id.block_view);
        blockEngine.setOnFrameReady(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int[] fullLineIndexes = blockEngine.getBlockDetector().getFullLineIndexes();
                        if(fullLineIndexes.length>0){
                            blockView.drawFullLine(blockEngine.getStaticBlockPoints(),fullLineIndexes);
                        }else{
                            blockView.drawBlock(blockEngine.getStaticBlockPoints(),blockEngine.getActiveBlock());
                        }
                    }
                });
            }
        });
        gestureDetector = new GestureDetector(this, gestureListener);
    }

    private final GestureDetector.OnGestureListener gestureListener = new DirectGestureListener() {
        @Override
        protected void direct(DIRECT direct) {
            switch (direct){
                case UP:blockEngine.getBlockController().rotate();break;
                case LEFT:blockEngine.getBlockController().left();break;
                case RIGHT:blockEngine.getBlockController().right();break;
                case DOWN:blockEngine.getBlockController().down();break;
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(blockEngine.isStarted()){
            blockEngine.resume();
        }else{
            blockEngine.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        blockEngine.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        blockEngine.stop();
    }
}
