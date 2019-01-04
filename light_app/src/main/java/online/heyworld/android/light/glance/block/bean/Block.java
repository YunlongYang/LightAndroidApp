package online.heyworld.android.light.glance.block.bean;

import android.graphics.Point;

import java.util.HashMap;
import java.util.Map;

import online.heyworld.android.light.glance.block.widget.BlockView;

/**
 * Created by admin on 2019/1/4.
 */
public class Block {
    public Point position;
    public boolean[][] value;
    public Map<String, String> attrMap;

    private Block() {
    }

    public static Block newInstance(int w,int h) {
        Block block = new Block();
        block.position = new Point();
        block.value = new boolean[w][h];
        block.attrMap = new HashMap<>();
        return block;
    }

    public static Block newInstance(int w,int h,int x,int y) {
        Block block = new Block();
        block.position = new Point(x,y);
        block.value = new boolean[w][h];
        block.attrMap = new HashMap<>();
        return block;
    }
}
