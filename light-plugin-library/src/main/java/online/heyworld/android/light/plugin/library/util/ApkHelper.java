package online.heyworld.android.light.plugin.library.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by yunlong.yang on 2019/1/2.
 */

public class ApkHelper {
    String apkPath;
    Context context;
    ApplicationInfo applicationInfo;
    PackageInfo packageInfo;

    public ApkHelper(String apkPath, Context context) {
        this.apkPath = apkPath;
        this.context = context;
        init();
    }

    private void init(){
        PackageManager pm = context.getPackageManager();
        packageInfo = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            applicationInfo = packageInfo.applicationInfo;
        }
    }

    public String getPackageName(){
        return applicationInfo.packageName;
    }

    public int getVersion(){
        return packageInfo.versionCode;
    }

    public String getVersionName(){
        return packageInfo.versionName;
    }


}
