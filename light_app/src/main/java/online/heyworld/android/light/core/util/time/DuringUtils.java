package online.heyworld.android.light.core.util.time;

public class DuringUtils {
    public static String duringToString(long duration) {
        long during = duration;
        long s = during/1000%60;
        long m = during/1000/60%60;
        long h = during/1000/60/60;

        StringBuilder stringBuilder = new StringBuilder();
        if(h>0){
            stringBuilder.append(h).append("小时");
        }
        if(m>0){
            stringBuilder.append(m).append("分钟");
        }

        if(h<=0 && s>0){
            stringBuilder.append(s).append("秒");
        }
        return stringBuilder.toString();
    }
}
