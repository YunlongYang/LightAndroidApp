package online.heyworld.android.light.core.tech.aop.ext.context;

import android.content.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import online.heyworld.android.light.core.tech.aop.AOPProxy;
import online.heyworld.android.light.core.tech.aop.ext.context.impl.DelegateContextImpl;

/**
 * Created by yunlong.yang on 2018/12/27.
 */

public class DelegateContextUtil {
    /**
     * Create delegate context
     * @param context
     * @param delegateContextWatch for caller record log only.
     * @return
     */
    public static DelegateContext delegateContext(Context context,DelegateContextWatch delegateContextWatch){
        Object object = AOPProxy.proxy(context.getClassLoader(), new Class[]{ContextInterface.class},
                new ContextInvocationHandler(context,delegateContextWatch));
        return new DelegateContext((ContextInterface) object);
    }

    private static class ContextInvocationHandler implements InvocationHandler{
        private static final Logger logger = LoggerFactory.getLogger(ContextInvocationHandler.class);

        private final Context hostContext;
        private final ContextInterface hostContextCaller;
        private DelegateContextWatch delegateContextWatch;

        public ContextInvocationHandler(Context hostContext, DelegateContextWatch delegateContextWatch) {
            this.hostContext = hostContext;
            hostContextCaller = new DelegateContextImpl(hostContext);
            this.delegateContextWatch = delegateContextWatch;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            logger.info("invoke "+method.getName()+"("+ Arrays.toString(args)+")");
            Object obj = method.invoke(hostContextCaller,args);
            delegateContextWatch.onCall(method, args,obj);
            return obj;
        }
    }


}
