package online.heyworld.android.light.core.page.context;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.heyworld.android.light.R;
import online.heyworld.android.light.core.tech.aop.ext.context.DelegateContextUtil;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.library.util.RecyclerViewUtil;
import online.heyworld.android.light.widget.LightView;

public class LearnContextActivity extends BaseCompatActivity {
    private ViewGroup testViewContainer;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ContextCallCounter> logList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_context);
        testViewContainer = findViewById(R.id.container);
        logList = new ArrayList<>();
        LightView lightView = new LightView(DelegateContextUtil.delegateContext(this, (method, args, result) -> {
            ContextCallCounter contextCallCounter = ContextCallCounter.Pool.get(method);
            contextCallCounter.call();
            if(!logList.contains(contextCallCounter)){
                logList.add(contextCallCounter);
            }
            removeCallbacks(refreshLogTask);
            postDelayed(refreshLogTask,200);
        }));
        testViewContainer.addView(lightView);
        recyclerView = findViewById(R.id.recycler_view);
        adapter = RecyclerViewUtil.install(recyclerView,logList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ContextCallCounter.Pool.clear();
    }

    private final Runnable refreshLogTask = new Runnable() {
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
        }
    };

    private static class ContextCallCounter{
        public static class Pool {
            private static Map<String,ContextCallCounter> sPool = new HashMap<>();
            public static ContextCallCounter get(Method method){
                if(!sPool.containsKey(method.getName())){
                    sPool.put(method.getName(),new ContextCallCounter(method));
                }
                return sPool.get(method.getName());
            }

            public static void clear(){
                sPool.clear();
            }
        }


        Method method;
        int times;

        public ContextCallCounter(Method method) {
            this.method = method;
            times =0;
        }

        public void call(){
            times++;
        }

        @Override
        public String toString() {
            return method.getName()+ "\t\tcalled<"+times+">";
        }
    }


}
