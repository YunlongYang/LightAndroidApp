package online.heyworld.android.light.glance.block;

import android.os.Handler;
import android.os.HandlerThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import online.heyworld.android.light.glance.block.bean.Block;
import online.heyworld.android.light.glance.block.model.BlockCreatorGroup;

/**
 * Created by admin on 2019/1/3.
 */

public class BlockEngine {
    private static final Logger logger = LoggerFactory.getLogger(BlockEngine.class);

    private Block activeBlock;
    private Handler mWorkHandler;
    private boolean running = true;
    private Runnable onFrameReady;
    private boolean[][] staticBlockPoints;
    private BlockDetector blockDetector;
    private BlockController blockController;
    private boolean handleDraw = false;
    private BlockCreatorGroup blockCreatorGroup;

    public BlockEngine() {
        blockCreatorGroup = new BlockCreatorGroup(4,4);
        activeBlock = blockCreatorGroup.next().create(0,0);
        staticBlockPoints = new boolean[20][30];
        blockDetector = new BlockDetector(staticBlockPoints);
        blockController = new BlockController();
        blockController.setBlock(activeBlock);

    }

    public void setOnFrameReady(Runnable onFrameReady) {
        this.onFrameReady = onFrameReady;
    }

    public void start(){
        HandlerThread handlerThread = new HandlerThread("BlockEngine"){
            @Override
            protected void onLooperPrepared() {
                super.onLooperPrepared();
                mWorkHandler = new Handler(getLooper());
                setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.error("BlockEngine",e);
                    }
                });
                mWorkHandler.postDelayed(mEngine,1000);
            }
        };
        handlerThread.start();
    }

    public void stop(){
        running = false;
        mWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                ((HandlerThread)Thread.currentThread()).quit();
            }
        });
    }

    public BlockController getBlockController() {
        return blockController;
    }

    private void runBlocks(){
        if(activeBlock!=null) {
            if (blockDetector.shouldFraze(activeBlock, blockController.tryMove(activeBlock, staticBlockPoints))) {
                onBlockFraze(activeBlock);
                activeBlock = blockCreatorGroup.next().create(0, 0);
                blockController.setBlock(activeBlock);
            }
            blockController.moveDown(staticBlockPoints);
            onFrameReady.run();
        }
    }

    public Block getActiveBlock() {
        return activeBlock;
    }

    public boolean[][] getStaticBlockPoints() {
        return staticBlockPoints;
    }

    private final Runnable mEngine = new Runnable() {
        @Override
        public void run() {
            if (running){
                if(engineReq>=10){
                    engineReq =0;
                    runBlocks();
                }else{
                    runRefresh();
                }
                mWorkHandler.postDelayed(mEngine,60);
            }
        }
    };

    private int engineReq = 0;

    private void runRefresh(){
        if(activeBlock.attrMap.containsKey("changed")){
            activeBlock.attrMap.remove("changed");
            blockController.move(staticBlockPoints);
            onFrameReady.run();
        }
        engineReq++;
    }

    private void onBlockFraze(Block block){
        int x = block.position.x;
        int y = block.position.y;
        for(int i=0;i<=3;i++){
            for(int j=0;j<=3;j++){
                if(block.value[i][j]){
                    staticBlockPoints[x+i][y+j] = staticBlockPoints[x+i][y+j] | block.value[i][j];
                }
            }
        }
    }
}
