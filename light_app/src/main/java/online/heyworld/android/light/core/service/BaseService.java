package online.heyworld.android.light.core.service;

import android.content.Context;

public abstract class BaseService {
    protected Context context;

    void install(Context context){
        this.context = context;
    }

    public abstract void init();

    public void exit(){};
}
