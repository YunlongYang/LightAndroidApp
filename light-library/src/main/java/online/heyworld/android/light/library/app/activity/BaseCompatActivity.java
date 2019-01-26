package online.heyworld.android.light.library.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/12/27.
 */

public class BaseCompatActivity extends AppCompatActivity {

    protected Handler mHandler;
    protected List<Runnable> mCancelTaskList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        mCancelTaskList = new ArrayList<>();
    }

    protected void showToast(String text,int duration){
        Toast.makeText(this,text,duration).show();
    }

    protected boolean post(Runnable r) {
        return mHandler.post(r);
    }

    protected boolean postDelayed(Runnable r, long delayMillis) {
        return mHandler.postDelayed(r, delayMillis);
    }

    protected void addCancelTask(Runnable runnable){
        synchronized (mCancelTaskList) {
            mCancelTaskList.add(runnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mCancelTaskList){
            for (Runnable runnable : mCancelTaskList){
                runnable.run();
            }
        }
        mCancelTaskList.clear();
    }

    protected void removeCallbacks(Runnable r) {
        mHandler.removeCallbacks(r);
    }

    protected Context context(){
        return this;
    }
}
