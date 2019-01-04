package online.heyworld.android.light.glance.block.model.impl;

import online.heyworld.android.light.glance.block.bean.Block;
import online.heyworld.android.light.glance.block.model.BlockCreator;

/**
 * Created by admin on 2019/1/4.
 */

public class RectCreator extends BlockCreator {
    public RectCreator(int w, int h) {
        super(w, h);
    }

    @Override
    public Block create(int x, int y) {
        Block activeBlock = Block.newInstance(w,h,x,y);
        activeBlock.value[0][0] = true;
        activeBlock.value[0][1] = true;
        activeBlock.value[1][0] = true;
        activeBlock.value[1][1] = true;
        return activeBlock;
    }
}
