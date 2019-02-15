package online.heyworld.android.light.core.page.math.order;

import java.util.Random;

/**
 * Created by admin on 2019/1/22.
 */

public class SourceCreator {
    private int count;
    private int max;

    public SourceCreator(int count, int max) {
        this.count = count;
        this.max = max;
    }

    public int[] create(){
        int[] result = new int[count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            result[i] = random.nextInt(max);
        }
        return result;
    }
}
