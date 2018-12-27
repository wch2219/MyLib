package com.example.picselect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.baselibs.base.BaseRecyclerAdapter;
import com.example.baselibs.base.CommonViewHolder;
import com.example.baselibs.utils.GlideUtils;
import com.example.picselect.R;
import com.example.picselect.activity.PicListActivity;
import com.example.picselect.presenter.Image;

import java.util.List;
import java.util.TreeMap;

public class SeleteClassPicAdapter extends BaseRecyclerAdapter<TreeMap<String,List<Image>>> {
    public SeleteClassPicAdapter(Context context, List<TreeMap<String, List<Image>>> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    protected void bindData(CommonViewHolder holder, TreeMap<String, List<Image>> data, int position) {
        final String key = data.firstKey();

        List<Image> images = data.get(key);
        GlideUtils.show(mContext, (ImageView) holder.getView(R.id.iv_pic),images.get(0).getPath());
        holder.setText(R.id.tv_num,images.size()+"");
        holder.setText(R.id.tv_name,key);
        holder.setOnClickListener(R.id.ll_root, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,PicListActivity.class).putExtra("ParentUrl",key));
            }
        });
    }


}
