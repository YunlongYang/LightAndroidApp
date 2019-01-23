package online.heyworld.android.light.library.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ProxyObject {
    Target target;

    public ProxyObject(Object object) {
        this.target = new ObjectTarget(object);
    }

    public ProxyObject(Class cls) {
        this.target = new ClassTarget(cls);
    }

    public void call(String name, Class[] classes, Object... args) throws Exception {
        target.call(name, classes, args);
    }

    public CallChain callChain(String name) {
        return target.callChain(name);
    }

    private static abstract class Target{
        public abstract void call(String name,Class[] classes,Object... args)throws Exception;
        public abstract CallChain callChain(String name);
    }

    public static abstract class CallChain{
        public abstract CallChain on(Class cls,Object arg);
        public abstract Object doneFor()throws Exception;
        public abstract void done()throws Exception;
    }

    private static class ObjectTarget extends Target{
        private Object obj;

        public ObjectTarget(Object obj) {
            this.obj = obj;
        }

        @Override
        public void call(String name, Class[] classes, Object... args) throws Exception {
            Method method = obj.getClass().getDeclaredMethod(name,classes);
            invoke(method,args);
        }

        @Override
        public CallChain callChain(String name) {
            return new BasicCallChain(obj.getClass(),name) {
                @Override
                public Object doneFor()throws Exception{
                    return invoke(findMethod(),getArgs());
                }

                @Override
                public void done() throws Exception{
                    invoke(findMethod(),getArgs());
                }
            };
        }

        private Object invoke(Method method, Object... args)throws Exception{
            if(Modifier.isStatic(method.getModifiers())){
               return method.invoke(null,args);
            }else{
                return method.invoke(obj,args);
            }
        }
    }

    private static class ClassTarget extends Target{
        private Class cls;

        public ClassTarget(Class cls) {
            this.cls = cls;
        }


        @Override
        public void call(String name, Class[] classes, Object... args) throws Exception {
            Method method = cls.getDeclaredMethod(name,classes);
            method.invoke(null,args);
        }

        @Override
        public CallChain callChain(String name) {
            return new BasicCallChain(cls,name) {
                @Override
                public Object doneFor()throws Exception{
                    return findMethod().invoke(null,getArgs());
                }

                @Override
                public void done() throws Exception{
                    findMethod().invoke(null,getArgs());
                }
            };
        }
    }

    private static abstract class BasicCallChain extends CallChain{
        Class cls;
        String method;
        List<Class> clsList;
        List<Object> argList;
        public BasicCallChain(Class cls, String method) {
            this.cls = cls;
            this.method = method;
            clsList = new ArrayList<>();
            argList = new ArrayList<>();
        }

        @Override
        public CallChain on(Class cls, Object arg) {
            clsList.add(cls);
            argList.add(arg);
            return this;
        }

        Method findMethod() throws Exception{
            return cls.getDeclaredMethod(method,clsList.toArray(new Class[clsList.size()]));
        }

        Object[] getArgs(){
            return argList.toArray(new Object[argList.size()]);
        }
    }
}
