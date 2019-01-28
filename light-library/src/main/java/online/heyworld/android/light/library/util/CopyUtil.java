package online.heyworld.android.light.library.util;

public class CopyUtil {
    public static void copy(int[]source, int[] dest){
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
    }
}
