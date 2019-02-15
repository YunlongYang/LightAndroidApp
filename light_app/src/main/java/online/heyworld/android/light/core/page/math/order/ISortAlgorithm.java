package online.heyworld.android.light.core.page.math.order;

/**
 * Created by admin on 2019/1/22.
 */

public interface ISortAlgorithm {
    int NONE_VALUE = Integer.MIN_VALUE;
    int NONE_INDEX = Integer.MIN_VALUE;

    String NOT_IMPLEMENT = "此算法的演示尚未实现";
    String name();

    default boolean finished(){
        return true;
    }

    void prepare();

    void begin(int[] source);

    boolean move();

    int[] result();

    ISortDisplay getDisplay();
}
