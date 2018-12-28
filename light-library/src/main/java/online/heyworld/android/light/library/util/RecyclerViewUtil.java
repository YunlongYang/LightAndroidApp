package online.heyworld.android.light.library.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import online.heyworld.android.light.library.R;

/**
 * Created by yunlong.yang on 2018/12/27.
 */

public class RecyclerViewUtil {
    public static RecyclerView.Adapter install(RecyclerView recyclerView,List dataList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        BaseAdapter baseAdapter = new BaseAdapter(dataList);
        recyclerView.setAdapter(baseAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        return baseAdapter;
    }

    public static class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder>{
        private List dataList;

        public BaseAdapter(List dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return BaseViewHolder.newInstance(parent.getContext());
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            holder.setData(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder{

        public static BaseViewHolder newInstance(Context context) {
            TextView textView = (TextView) View.inflate(context, R.layout.recycler_item,null);
            BaseViewHolder fragment = new BaseViewHolder(textView);
            return fragment;
        }

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(Object object){
            if( itemView instanceof TextView){
                ((TextView) itemView).setText(object.toString());
            }
        }
    }

}
