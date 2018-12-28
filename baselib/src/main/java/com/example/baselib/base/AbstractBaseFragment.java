package com.example.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baselib.R;
import com.example.baselib.utils.MyProgressLoading;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractBaseFragment extends Fragment implements IView {


    public Context mContext;
    public Unbinder unbinder;
    private MyProgressLoading mLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(getLayoutID(), null);
        unbinder = ButterKnife.bind(this, rootView);
        mLoading = new MyProgressLoading(mContext, R.style.DialogStyle);
        initView(rootView);
        initDate();
        initListener();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected abstract int getLayoutID();


    protected abstract void initView(View rootView);

    protected abstract void initDate();

    protected void initListener() {
    }

    @Override
    public void showLoading() {
        mLoading.show();
    }

    @Override
    public void hideLoading() {
        mLoading.dismiss();
    }


    public void initRefreshLayout(SmartRefreshLayout refreshLayout, boolean isLoadmore) {
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        if (isLoadmore) {
            refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        } else {
            refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        }
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        //refreshLayout.setRefreshHeader(new FalsifyHeader(mContext));
//        refreshLayout.autoRefresh();
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000);
                downOnRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore();
                loadMore();
            }
        });

    }



    /**
     * 下拉刷新
     */
    public void downOnRefresh() {

    }

    /**
     * 上拉加载更多
     */
    public void loadMore() {

    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}
