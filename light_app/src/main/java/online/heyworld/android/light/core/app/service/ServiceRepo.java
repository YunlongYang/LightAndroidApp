package online.heyworld.android.light.core.app.service;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class ServiceRepo {

    private static final Impl sImpl = new Impl();

    public static void install(Context context) {
        sImpl.install(context);
    }

    public static void exit(){
        sImpl.exit();
    }

    public static <T extends BaseService> T get(Class<T> cls) {
        return sImpl.get(cls);
    }


    private static class Impl{
        private Map<String,BaseService> serviceMap = new HashMap<>();
        private Context context;

        public void install(Context context) {
            this.context = context;
        }

        public <T extends BaseService> T get(Class<T> cls){
            return ensure(cls);
        }

        private <T extends BaseService> T ensure(Class<T> cls){
            BaseService service = serviceMap.get(cls.getName());
            if(service==null){
                try {
                    service = cls.newInstance();
                    service.install(context);
                    service.init();
                    serviceMap.put(cls.getName(),service);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            return (T) service;
        }

        public void exit() {
            for(BaseService service : serviceMap.values()){
                service.exit();
            }
        }
    }

}
