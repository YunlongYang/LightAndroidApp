package online.heyworld.android.light.plugin.library;

import android.content.Context;
import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import online.heyworld.android.light.plugin.library.cls.PluginClassLoaderHelper;
import online.heyworld.android.light.plugin.library.res.PluginResourcesHelper;
import online.heyworld.android.light.plugin.library.util.ApkHelper;

/**
 * Created by yunlong.yang on 2019/1/2.
 */

public class PluginLibrary {
    private static final Logger logger = LoggerFactory.getLogger(PluginLibrary.class);
    private static Map<String,PluginApp> sAppMap = new HashMap<>();

    public static void install(Context context,String apkPath)throws Exception{
        ApkHelper apkHelper = new ApkHelper(apkPath,context);
        File runApkPath = new File(context.getFilesDir(),"/plugin/runtime/"+apkHelper.getPackageName()+"-"+apkHelper.getVersionName()+".apk");
        if(!runApkPath.getParentFile().exists()){
            runApkPath.getParentFile().mkdirs();
        }
        copy(new File(apkPath),runApkPath);
        installByRunApk(runApkPath,context);
    }

    public static void init(Context context){
        File runTimeDir = new File(context.getFilesDir(),"/plugin/runtime/");
        if(runTimeDir.exists()){
            for(File file : runTimeDir.listFiles()){
                if(file.isFile()){
                    installByRunApk(file,context);
                }
            }
        }
    }

    private static void installByRunApk(File runApkPath,Context context){
        makeSureDirs(runApkPath);
        ApkHelper apkHelper = new ApkHelper(runApkPath.getAbsolutePath(),context);
        String apkPath = runApkPath.getAbsolutePath();
        logger.info("---Start Install Plugin---");
        logger.info("- path: {} -",runApkPath.getAbsolutePath());
        logger.info("- package:{} -",apkHelper.getPackageName());
        logger.info("- version:{} -",apkHelper.getVersion());
        logger.info("- versionName:{} -",apkHelper.getVersionName());
        PluginResourcesHelper pluginResourcesHelper = new PluginResourcesHelper(apkPath);
        PluginClassLoaderHelper pluginClassLoaderHelper = new PluginClassLoaderHelper(apkPath,apkHelper.getPackageName());
        sAppMap.put(apkHelper.getPackageName(),new PluginApp(apkPath,apkHelper,pluginResourcesHelper,pluginClassLoaderHelper));
    }

    public static Map<String, PluginApp> getAppMap() {
        return sAppMap;
    }

    public static View loadView(Context context, String packageName, String viewClsName)throws Exception{
        Class viewClass = sAppMap.get(packageName).pluginClassLoaderHelper.getClassLoader(context.getClassLoader()).loadClass(viewClsName);
        Constructor constructor = viewClass.getConstructor(Context.class);
        Context pContext = context;
        View pView = (View) constructor.newInstance(pContext);
        return pView;
    }

    private static void makeSureDirs(File file) {
        File pfile = file.getParentFile();
        if (!pfile.exists()) {
            pfile.mkdirs();
        }
    }

    private static void copy(File src,File dest)throws Exception{
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(src).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }

    }

    private static class PluginApp{
        String apkPath;
        ApkHelper apkHelper;
        PluginResourcesHelper pluginResourcesHelper;
        PluginClassLoaderHelper pluginClassLoaderHelper;

        public PluginApp(String apkPath, ApkHelper apkHelper, PluginResourcesHelper pluginResourcesHelper, PluginClassLoaderHelper pluginClassLoaderHelper) {
            this.apkPath = apkPath;
            this.apkHelper = apkHelper;
            this.pluginResourcesHelper = pluginResourcesHelper;
            this.pluginClassLoaderHelper = pluginClassLoaderHelper;
        }
    }
}
