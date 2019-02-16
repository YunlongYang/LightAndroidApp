package online.heyworld.android.light.plugin.library.context;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.view.LayoutInflater;

import online.heyworld.android.light.plugin.library.PluginLibrary;

public class PluginContext extends ContextWrapper {

    private String packageName;
    private Resources resources;
    public LayoutInflater mInflater;
    private Resources.Theme mTheme;
    private ClassLoader classLoader;

    public PluginContext(Activity activity, String packageName) {
        this(activity.getBaseContext(),activity.getResources(),packageName,activity.getClassLoader());
    }


    public PluginContext(Context base, Resources resources,String packageName,ClassLoader classLoader) {
        super(base);
        this.classLoader = PluginLibrary.getAppMap().get(packageName).getPluginClassLoaderHelper().getClassLoader(classLoader);
        this.resources = PluginLibrary.getAppMap().get(packageName).getPluginResourcesHelper().getResources(resources);
        this.packageName = packageName;
        this.classLoader = classLoader;
    }


    @Override
    public Object getSystemService(String name) {
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mInflater == null) {
                LayoutInflater systemLayoutInflater = LayoutInflater.from(getBaseContext());
                mInflater = systemLayoutInflater.cloneInContext(this);
            }
            return mInflater;
        }
        return getBaseContext().getSystemService(name);
    }

    @Override
    public Resources getResources() {
        return resources;
    }

    @Override
    public Resources.Theme getTheme() {
        if(mTheme==null){
            mTheme = getResources().newTheme();
            mTheme.setTo(getBaseContext().getTheme());
        }
        return mTheme;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

//    @Override
//    public void startActivity(Intent intent) {
//        PM.startActivity(getBaseContext(),intent);
//    }
//
//    @Override
//    public ComponentName startService(Intent service) {
//
//        return PM.startService(getBaseContext(),service);
//    }
}
