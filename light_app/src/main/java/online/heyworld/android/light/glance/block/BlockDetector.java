package online.heyworld.android.light.glance.block;

import android.graphics.Point;

import online.heyworld.android.light.glance.block.bean.Block;

/**
 * Created by admin on 2019/1/3.
 */

public class BlockDetector {
    private final boolean[][] staticBg;

    public BlockDetector(boolean[][] staticBg) {
        this.staticBg = staticBg;
    }

    public boolean shouldFraze(Block activeBlock, Point nextPoint){
        int x = nextPoint.x;
        int y = nextPoint.y;

        if(y<0 || y>=30){
            return true;
        }

        for(int i=0;i<=3;i++){
            for(int j=0;j<=3;j++){
                if(activeBlock.value[i][j]  ){
                    if(y+j>=30){
                        return true;
                    }

                    if(x+i<0 || x+i>=20){
                        return false;
                    }

                    if(staticBg [x+i][y+j]){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
