package online.heyworld.android.light.glance.math.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.heyworld.android.light.R;
import online.heyworld.android.light.library.reflect.ClassUtil;
import online.heyworld.android.light.library.route.ActivityRoute;
import online.heyworld.android.light.widget.support.ListAdapter;

public class MathOrderListActivity extends AppCompatActivity {

    private ListView mOrderLv;
    private List<ClassUtil.ClassInfo> classInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_order_list);
        classInfoList = new ArrayList<>();
        classInfoList.addAll(ClassUtil.getClasses(this,"online.heyworld.android.light.glance.math.order.impl",true));
        mOrderLv = findViewById(R.id.content_list_view);
        mOrderLv.setAdapter(new ListAdapter<ClassUtil.ClassInfo>(classInfoList) {
            @Override
            protected View createView() {
                return new TextView(MathOrderListActivity.this);
            }

            @Override
            protected void drawInfo(View view, ClassUtil.ClassInfo classInfo) {
                TextView textView = (TextView) view;
                textView.setText(classInfo.getSimpleName());
            }
        });
        mOrderLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                see(classInfoList.get(position));
            }
        });
    }



    private void see(ClassUtil.ClassInfo classInfo){
        Map<String,Object> argMap = new HashMap<>();
        argMap.put("SortClass", classInfo.getName());
        ActivityRoute.of(this).go("/sort",argMap);
    }
}
