package online.heyworld.android.light.plugin.library.cls;

import java.io.File;

import dalvik.system.DexClassLoader;
import online.heyworld.android.light.plugin.library.util.PluginAppPaths;

/**
 * Created by admin on 2019/1/2.
 */

public class PluginClassLoaderHelper {
    private final String apkPath;
    private final String packageName;

    public PluginClassLoaderHelper(String apkPath,String packageName) {
        this.apkPath = apkPath;
        this.packageName = packageName;
    }

    public DexClassLoader getClassLoader(ClassLoader parent){
        File appFile = new File(apkPath);
        File appWorkDir = PluginAppPaths.getRuntimeClassRootDir(appFile,packageName);
        File optDir = new File(appWorkDir,"opt");
        if(!optDir.exists()){
            optDir.mkdirs();
        }

        File libDir = new File(appWorkDir,"lib");
        if(!libDir.exists()){
            libDir.mkdirs();
        }
        return new DexClassLoader(appFile.getAbsolutePath(),
                optDir.getAbsolutePath(), libDir.getAbsolutePath(), parent);
    }
}
