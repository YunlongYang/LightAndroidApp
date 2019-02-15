package online.heyworld.android.light.core.page.math.order.util;

import com.google.errorprone.annotations.Var;

import java.lang.reflect.Field;

import online.heyworld.android.light.core.page.math.order.ISortAlgorithm;

import static online.heyworld.android.light.core.page.math.order.ISortAlgorithm.NONE_INDEX;
import static online.heyworld.android.light.core.page.math.order.ISortAlgorithm.NONE_VALUE;

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

    public static String[] genLabels(int[] source){
        String[] labels = new String[source.length];
        for (int i = 0; i <source.length; i++) {
            if(source[i] == NONE_VALUE ){
                labels[i] = " ";
            }else{
                labels[i] = String.valueOf(source[i]);
            }
        }
        return labels;
    }

    public static boolean contains(int item,int[] checkArray){
        for (int check: checkArray){
            if(check == item){
                return true;
            }
        }
        return false;
    }

    public static int[] range(int start,int end){
        if(end<start || start == NONE_INDEX || end == NONE_INDEX){
            return new int[0];
        }
        int[] result = new int[end-start+1];
        for (int i = 0; i < result.length; i++) {
            result[i] = start+i;
        }
        return result;
    }

    public static int[] sub(int[] source,int start,int end){
        if(end<start || start == NONE_INDEX || end == NONE_INDEX
                || start>= source.length ){
            return new int[0];
        }

        int[] result = new int[end-start+1];
        for (int i = 0; i < result.length; i++) {
            if(start+i>=source.length){
                break;
            }
            result[i] = source[start+i];
        }
        return result;
    }
}
