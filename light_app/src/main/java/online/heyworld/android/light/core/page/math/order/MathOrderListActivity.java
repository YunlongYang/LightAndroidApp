package online.heyworld.android.light.core.page.math.order;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.heyworld.android.light.R;
import online.heyworld.android.light.library.app.activity.BaseCompatActivity;
import online.heyworld.android.light.library.reflect.ClassUtil;
import online.heyworld.android.light.library.route.ActivityRoute;
import online.heyworld.android.light.library.util.ThreadUtils;
import online.heyworld.android.light.widget.support.ListAdapter;

public class MathOrderListActivity extends BaseCompatActivity {

    private GridView mOrderGroup;
    private List<ClassUtil.ClassInfo> mClassInfoList;
    private ListAdapter<ClassUtil.ClassInfo> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_order_list);
        mClassInfoList = new ArrayList<>();


        mOrderGroup = findViewById(R.id.content_group_view);
        mAdapter = new ListAdapter<ClassUtil.ClassInfo>(mClassInfoList) {
            @Override
            protected View createView() {
               return View.inflate(context(),R.layout.list_view_default_item,null);
            }

            @Override
            protected void drawInfo(View view, ClassUtil.ClassInfo classInfo) {
                TextView textView = (TextView) view;
                textView.setText(classInfo.getSimpleName()+"\n"+getName(classInfo));
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(0,6,0,6);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            }

            private String getName(ClassUtil.ClassInfo classInfo){
                try {
                    ISortAlgorithm sortAlgorithm = (ISortAlgorithm) Class.forName(classInfo.getName()).newInstance();
                    return sortAlgorithm.name();
                }catch (Exception e){
                    return "未知";
                }
            }
        };
        mOrderGroup.setAdapter(mAdapter);
        mOrderGroup.setOnItemClickListener((parent, view, position, id) -> see(mClassInfoList.get(position)));
        mOrderGroup.setEmptyView(findViewById(R.id.empty_view));
        setTitle("排序算法列表");
        ThreadUtils.postOnBackgroundThread(()->{
            mClassInfoList.addAll(ClassUtil.getClasses(this,"online.heyworld.android.light.core.page.math.order.impl",true));
            ThreadUtils.postOnMainThread(()->mAdapter.notifyDataSetChanged());
        });
    }



    private void see(ClassUtil.ClassInfo classInfo){
        Map<String,Object> argMap = new HashMap<>();
        argMap.put("SortClass", classInfo.getName());
        ActivityRoute.of(this).go("/sort",argMap);
    }
}
