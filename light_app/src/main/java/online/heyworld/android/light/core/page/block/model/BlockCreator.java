package online.heyworld.android.light.core.page.block.model;

import online.heyworld.android.light.core.page.block.bean.Block;

/**
 * Created by admin on 2019/1/4.
 */

public abstract class BlockCreator {
    protected final int w;
    protected final int h;

    public BlockCreator(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public abstract Block create(int x,int y);
}
