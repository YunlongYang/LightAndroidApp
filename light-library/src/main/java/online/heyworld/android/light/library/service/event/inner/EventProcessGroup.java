package online.heyworld.android.light.library.service.event.inner;

/**
 * Created by admin on 2018/12/28.
 */

public class EventProcessGroup implements EventProcess {

    private EventProcess[] eventProcesses = new EventProcess[]{};
    public EventProcessGroup() {
    }

    @Override
    public void process(String action, String value) {
        for(EventProcess eventProcess : eventProcesses){
            eventProcess.process(action, value);
        }
    }
}
