package online.heyworld.android.light.widget.support;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by admin on 2019/1/22.
 */

public abstract class ListAdapter<E> extends BaseAdapter{
    private List<E> dataList;

    public ListAdapter(List<E> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = createView();
        }
        drawInfo(convertView,dataList.get(position));
        return convertView;
    }

    protected abstract View createView();
    protected abstract void drawInfo(View view, E e);
}
