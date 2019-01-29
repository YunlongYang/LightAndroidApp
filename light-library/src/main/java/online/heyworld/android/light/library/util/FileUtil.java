package online.heyworld.android.light.library.util;

import java.io.File;

public class FileUtil {
    public static File file(String parent,String... child){
        File parentFile = new File(parent);
        File childFile = parentFile;
        for (String item: child){
            childFile = new File(childFile,item);
        }
        return childFile;
    }
}
