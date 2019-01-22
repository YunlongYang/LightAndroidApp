package online.heyworld.android.light.glance.math.order;

/**
 * Created by admin on 2019/1/22.
 */

public interface ISortAlgorithm {
    int NONE_VALUE = Integer.MIN_VALUE;
    int NONE_INDEX = Integer.MIN_VALUE;
    String name();
    void prepare();
    void begin(int[] source);
    boolean move();
    int[] result();
    ISortDisplay getDisplay();
}
