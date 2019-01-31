package online.heyworld.android.light.plugin.library.util;

import android.content.Context;

import java.io.File;
import java.io.FileFilter;

public class PluginAppPaths {

    public static File getRuntimeRootDir(Context context) {
        return ensureDirExist(new File(context.getFilesDir(),"/plugin/runtime/"));
    }

    public static File getRuntimeApk(final Context context, final String packageName){
        File[] apkFiles = getRuntimeRootDir(context).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().startsWith(packageName);
            }
        });
        for(File apkFile : apkFiles) {
            return apkFile;
        }
        return null;
    }

    public static File getRuntimeClassRootDir(File runtimeApk,String packageName){
        return ensureDirExist(new File(runtimeApk.getParent(),packageName));
    }

    public static File getRuntimeClassRootDir(Context context,String packageName){
        return ensureDirExist(new File(ensureDirExist(new File(context.getFilesDir(),"/plugin/runtime/")),packageName));
    }

    public static void clearPluginAppFiles(Context context, final String packageName){
        deleteDir(getRuntimeClassRootDir(context,packageName));
        File[] apkFiles = getRuntimeRootDir(context).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().startsWith(packageName);
            }
        });
        for(File apkFile : apkFiles) {
            apkFile.delete();
        }
    }

    private static File ensureDirExist(File dir){
        if(!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

    public static void deleteDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }
}
