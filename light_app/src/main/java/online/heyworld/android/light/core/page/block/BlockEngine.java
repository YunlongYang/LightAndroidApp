package online.heyworld.android.light.core.page.block;

import android.os.Handler;
import android.os.HandlerThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import online.heyworld.android.light.core.page.block.bean.Block;
import online.heyworld.android.light.core.page.block.model.BlockCreatorGroup;
import online.heyworld.android.light.core.page.block.util.BlockUtils;

/**
 * Created by admin on 2019/1/3.
 */

public class BlockEngine {
    private static final Logger logger = LoggerFactory.getLogger(BlockEngine.class);
    private static Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    private Block activeBlock;
    private Handler mWorkHandler;
    private boolean running = true;
    private Runnable onFrameReady;
    private boolean[][] staticBlockPoints;
    private BlockDetector blockDetector;
    private BlockController blockController;
    private BlockCreatorGroup blockCreatorGroup;
    private boolean started;

    public BlockEngine() {
        blockCreatorGroup = new BlockCreatorGroup(4,4);
        activeBlock = blockCreatorGroup.next().create(0,0);
        staticBlockPoints = new boolean[20][30];
        blockDetector = new BlockDetector(staticBlockPoints);
        blockController = new BlockController();
        blockController.setBlock(activeBlock);
        started = false;

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

                defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
                setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.error("BlockEngine",e);
                    }
                });
                mWorkHandler.removeCallbacks(mEngine);
                mWorkHandler.postDelayed(mEngine,1000);
            }
        };
        handlerThread.start();
        started = true;
    }

    public boolean isStarted() {
        return started;
    }

    public void pause(){
        running = false;
    }

    public void resume(){
        running = true;
        mWorkHandler.removeCallbacks(mEngine);
        mWorkHandler.post(mEngine);
    }

    public void stop(){
        running = false;
        mWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                ((HandlerThread)Thread.currentThread()).quit();
                Thread.setDefaultUncaughtExceptionHandler(defaultUncaughtExceptionHandler);
                defaultUncaughtExceptionHandler = null;
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
                onNewBlockNeed();
            }
            blockController.moveDown(staticBlockPoints);
            onFrameReady.run();
        }
    }

    public BlockDetector getBlockDetector() {
        return blockDetector;
    }

    private void onNewBlockNeed(){
        Random random = new Random();
        int x= random.nextInt(20-4);
        activeBlock = blockCreatorGroup.next().create(x, 0);
        blockController.setBlock(activeBlock);
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
                    int[] fullLineIndexes = blockDetector.getFullLineIndexes();
                    if(fullLineIndexes.length>0){
                        clearFullLines(fullLineIndexes);
                    }else{
                        runBlocks();
                    }
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

    private void clearFullLines(int[] fullLineIndexes){
        boolean[][] staticBlockPointsTemp = new boolean[20][30];
        copy(staticBlockPoints,staticBlockPointsTemp,fullLineIndexes);
        copy(staticBlockPointsTemp,staticBlockPoints,new int[0]);
    }

    private static void copy(boolean[][] from,boolean[][] to,int[] exceptIndexes){
        int toIndex = 0;
        int fromIndex = 0;
        for (int i = from.length; i >=0  ; i--) {
            if(!BlockUtils.contains(exceptIndexes,i)){
                boolean[] fromLine = from[i];
                for (int j = 0; j < fromLine.length; j++) {
                    to[toIndex][j]= from[fromIndex][j];
                }
                toIndex--;
            }
            fromIndex--;
        }
    }


}
