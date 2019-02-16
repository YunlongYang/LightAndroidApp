package online.heyworld.android.light.core.tech.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by yunlong.yang on 2018/12/27.
 */

public class AOPProxy {
    public static Object proxy(ClassLoader loader,
                             Class<?>[] interfaces,
                             InvocationHandler h){
        return Proxy.newProxyInstance(loader, interfaces, h);
    }
}
