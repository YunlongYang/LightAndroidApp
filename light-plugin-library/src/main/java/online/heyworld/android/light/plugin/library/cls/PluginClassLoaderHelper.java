package online.heyworld.android.light.plugin.library.cls;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * Created by admin on 2019/1/2.
 */

public class PluginClassLoaderHelper {
    private final String apkPath;

    public PluginClassLoaderHelper(String apkPath) {
        this.apkPath = apkPath;
    }

    public DexClassLoader getClassLoader(ClassLoader parent){
        File appFile = new File(apkPath);
        File appWorkDir = appFile.getParentFile();
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
