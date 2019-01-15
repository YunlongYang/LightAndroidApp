package online.heyworld.android.light.glance.block.util;

/**
 * Created by admin on 2019/1/10.
 */

public class BlockUtils {
    public static boolean contains(int[] collect, int item) {
        for (int cItem :
                collect) {
            if (cItem == item) {
                return true;
            }
        }
        return false;

    }
}