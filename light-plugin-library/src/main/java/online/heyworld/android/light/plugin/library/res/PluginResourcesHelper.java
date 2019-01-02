package online.heyworld.android.light.plugin.library.res;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

/**
 * Created by admin on 2019/1/2.
 */

public class PluginResourcesHelper {
    String apkPath;

    public PluginResourcesHelper(String apkPath) {
        this.apkPath = apkPath;
    }

    public Resources getResources(final Resources parent){
        AssetManager assetManager = bindAssetManager(apkPath);
        Resources resources = new Resources(assetManager,
                parent.getDisplayMetrics(),
                parent.getConfiguration()){

            @Override
            public int getIdentifier(String name, String defType, String defPackage) {
                try {
                    return parent.getIdentifier(name, defType, defPackage);
                }catch (Exception e){
                    return super.getIdentifier(name, defType, defPackage);
                }
            }
        };
        return resources;
    }

    private static AssetManager bindAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            try {
                Object result = AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
                        assetManager, apkPath);
                Log.i("PluginResources","bindAssetManager result:"+result);
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return assetManager;
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }
}
