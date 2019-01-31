package online.heyworld.android.light.plugin.app.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import online.heyworld.android.light.plugin.app.R;

public class PView extends FrameLayout {

    private LinearLayout rootView;

    public PView(Context context) {
        super(context);
        init(context);
    }

    public PView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        rootView = (LinearLayout) View.inflate(context,R.layout.p_root_view,null);
        addView(rootView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        rootView.findViewById(R.id.exit_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context parentContext = getParentContext();
                if(parentContext!=null && parentContext instanceof Activity){
                    ((Activity) parentContext).finish();
                }
            }
        });
    }

    private Context getParentContext(){
        if(getParent()==null){
            return null;
        }
        return ((View)getParent()).getContext();
    }
}
