package online.heyworld.android.light.core.tech.aop.ext.context;

import java.lang.reflect.Method;

/**
 * Created by admin on 2018/12/27.
 */

public interface DelegateContextWatch {
    void onCall(Method method, Object[] args, Object result);
}
