package online.heyworld.android.light.core.page.block.model;

import java.util.Iterator;
import java.util.Random;

import online.heyworld.android.light.core.page.block.model.impl.ConvexCreator;
import online.heyworld.android.light.core.page.block.model.impl.LadderCreator;
import online.heyworld.android.light.core.page.block.model.impl.LineCreator;
import online.heyworld.android.light.core.page.block.model.impl.RectCreator;

/**
 * Created by admin on 2019/1/4.
 */

public class BlockCreatorGroup implements Iterator<BlockCreator>{

    private final int w;
    private final int h;
    private final BlockCreator[] blockCreators;


    public BlockCreatorGroup(int w, int h) {
        this.w = w;
        this.h = h;
        blockCreators = new BlockCreator[]{
                new LineCreator(w, h),
                new RectCreator(w,h),
                new LadderCreator(w, h),
                new ConvexCreator(w,h)
        };
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public BlockCreator next() {
        return blockCreators[nextIndex()];
    }

    private int nextIndex(){
        return new Random().nextInt(blockCreators.length);
    }
}
