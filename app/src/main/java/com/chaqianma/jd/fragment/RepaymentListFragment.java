package com.chaqianma.jd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.RepaymentDetailActivity;
import com.chaqianma.jd.adapters.RepaymentListAdapter;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.RepaymentInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.PullToRefreshView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/8/17.
 */
public class RepaymentListFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    @InjectView(R.id.refreshView)
    PullToRefreshView view_refresh;
    @InjectView(R.id.list_repayment)
    ListView list_repayment;
    //数据源
    private RepaymentListAdapter mRepaymentListAdapter = null;
    private List<RepaymentInfo> mRepaymentInfoList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_repayment_list, container, false);
        ButterKnife.inject(this, view);
        initView();
        setTitle("催收任务", false);
        getRepaymentList();
        return view;
    }

    /*
   * 初始化刷新View
   * */
    private void initView() {
        view_refresh.setOnHeaderRefreshListener(this);
        view_refresh.setOnFooterRefreshListener(this);
        //view_refresh.onHeaderRefreshComplete();
        //view_refresh.onFooterRefreshComplete();
        view_refresh.setEnablePullLoadMoreDataStatus(false);
        view_refresh.setEnablePullTorefresh(true);
        list_repayment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < mRepaymentInfoList.size()) {
                    RepaymentInfo repaymentInfo = mRepaymentInfoList.get(position);
                    startActivity(RepaymentDetailActivity.class, repaymentInfo.getId());
                }
            }
        });
    }

    /*
    * 获取催款数据
    * */
    private void getRepaymentList() {
        if(mRepaymentInfoList!=null) {
            mRepaymentInfoList.clear();
            if(mRepaymentListAdapter!=null)
                mRepaymentListAdapter.notifyDataSetChanged();
        }
        HttpClientUtil.get(HttpRequestURL.getRepaymentList,null,new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                if (o != null) {
                    mRepaymentInfoList = JSON.parseArray(o.toString(), RepaymentInfo.class);
                    if (mRepaymentInfoList != null) {
                        mRepaymentListAdapter = new RepaymentListAdapter(getActivity(), mRepaymentInfoList);
                        list_repayment.setAdapter(mRepaymentListAdapter);
                        view_refresh.onHeaderRefreshComplete();
                    }
                }
            }

            @Override
            public void onFailure(String data) {
                super.onFailure(data);
            }
        }));
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        view_refresh.onFooterRefreshComplete();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        getRepaymentList();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setTitle("催收任务", false);
        }
    }

    public static RepaymentListFragment newInstance()
    {
        RepaymentListFragment repaymentListFragment = new RepaymentListFragment();
        return repaymentListFragment;
    }
}
