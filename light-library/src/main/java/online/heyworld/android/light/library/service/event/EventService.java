package online.heyworld.android.light.library.service.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import online.heyworld.android.light.library.service.event.inner.EventProcessGroup;

/**
 * Created by yunlong.yang on 2018/12/28.
 */

public class EventService {

    private static final EventProcessGroup sGroup = new EventProcessGroup();
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public static void on(String action,String value){
       logger.info("{}({})",action,value);
        sGroup.process(action, value);
    }

    public static void exception(String action,Exception e){
        logger.error(action,e);
        sGroup.process(action, e);
    }
}
