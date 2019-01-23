package online.heyworld.android.light.library.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ProxyObject {
    Target target;

    public ProxyObject(Object object) {
        this.target = new ObjectTarget(object);
    }

    public ProxyObject(Class cls) {
        this.target = new ClassTarget(cls);
    }

    public void call(String name, Object... args)throws Exception {
        target.call(name, args);
    }

    public void call(String name, Class[] classes, Object... args) throws Exception {
        target.call(name, classes, args);
    }

    private static abstract class Target{
        public abstract void call(String name,Object... args)throws Exception;
        public abstract void call(String name,Class[] classes,Object... args)throws Exception;
    }

    private static class ObjectTarget extends Target{
        private Object obj;

        public ObjectTarget(Object obj) {
            this.obj = obj;
        }

        @Override
        public void call(String name, Object... args)throws Exception {
            Method method = obj.getClass().getDeclaredMethod(name,asClasses(args));
            if(Modifier.isStatic(method.getModifiers())){
                method.invoke(null,args);
            }else{
                method.invoke(obj,args);
            }
        }

        @Override
        public void call(String name, Class[] classes, Object... args) throws Exception {

        }
    }

    private static class ClassTarget extends Target{
        private Class cls;

        public ClassTarget(Class cls) {
            this.cls = cls;
        }

        @Override
        public void call(String name, Object... args)throws Exception {
            Method method = cls.getDeclaredMethod(name,asClasses(args));
            method.invoke(null,args);
        }

        @Override
        public void call(String name, Class[] classes, Object... args) throws Exception {
            Method method = cls.getDeclaredMethod(name,classes);
            method.invoke(null,args);
        }
    }

    private static Class[] asClasses(Object... args){
        Class[] classes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }
        return classes;
    }
}
