package online.heyworld.android.light.library.reflect;

public class InstanceUtil {

    public static ProxyObject newInstance(String className) throws Exception {
        return new ProxyObject(Class.forName(className).newInstance());
    }

    public static ProxyObject newClassInstance(String className)throws Exception{
        return new ProxyObject(Class.forName(className));
    }
}
