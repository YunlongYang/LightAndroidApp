package online.heyworld.android.light.glance.math.order.util;

import com.google.errorprone.annotations.Var;

import java.lang.reflect.Field;

import online.heyworld.android.light.glance.math.order.ISortAlgorithm;

import static online.heyworld.android.light.glance.math.order.ISortAlgorithm.NONE_INDEX;
import static online.heyworld.android.light.glance.math.order.ISortAlgorithm.NONE_VALUE;

/**
 * Created by admin on 2019/1/22.
 */

public class SortDisplayUtil {
    public static String getSource(int[] source){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < source.length; i++) {
            if(source[i]== NONE_VALUE){
                stringBuilder.append("[").append(String.format("%4s","")).append("]");
            }else{
                stringBuilder.append("[").append(String.format("%4d",source[i])).append("]");
            }
            if(i%5==4){
                stringBuilder.append("\n");
            }else {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public static String getArgs(ISortAlgorithm sortAlgorithm){
        StringBuilder stringBuilder = new StringBuilder();
        Field[] fields = sortAlgorithm.getClass().getDeclaredFields();
        int count = 0;
        for(Field field : fields ){
            if(field.getAnnotation(Var.class)!=null){
                field.setAccessible(true);
                try {
                    stringBuilder.append(field.getName()).append(":[").append(valueOf(field.get(sortAlgorithm))).append("] ");
                    if(count%2==1){
                        stringBuilder.append("\n");
                    }
                    count++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    private static String valueOf(Object obj){
        if(obj instanceof Integer){
            int value = ((Integer) obj).intValue();
            if(NONE_VALUE == value || NONE_INDEX == value){
                return "";
            }
        }else{
            return String.valueOf(obj);
        }
        return String.valueOf(obj);
    }
}
