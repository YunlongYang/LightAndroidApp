package online.heyworld.android.light.library.util;

public class AndroidVarUtil {
    public static int requestCodeForPermissions(Object obj) {
        return Math.abs(obj.hashCode()%10000);
    }
}
