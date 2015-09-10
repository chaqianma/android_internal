package com.chaqianma.jd.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.GaiDeMapActivity;
import com.chaqianma.jd.activity.InvestigateDetailActivity;
import com.chaqianma.jd.activity.MainActivity;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.BorrowRequestInfo;
import com.chaqianma.jd.utils.FileUtil;
import com.chaqianma.jd.utils.GDGeoCodeUtil;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDAlertDialog;
import com.chaqianma.jd.widget.JDToast;

import java.util.Timer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/24.
 */
public class BorrowApplyFragment extends BaseFragment {
    @InjectView(R.id.tv_id)
    TextView tv_id;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_telephone)
    TextView tv_telephone;
    @InjectView(R.id.tv_money)
    TextView tv_money;
    @InjectView(R.id.tv_date)
    TextView tv_date;
    @InjectView(R.id.tv_purpose)
    TextView tv_purpose;
    @InjectView(R.id.tv_address)
    TextView tv_address;
    @InjectView(R.id.tv_apply_time)
    TextView tv_apply_time;
    @InjectView(R.id.btn_transfer)
    Button btn_transfer;
    @InjectView(R.id.tv_finish_task)
    TextView tv_finish_task;
    @InjectView(R.id.btn_borrow)
    Button btn_borrow;
    @InjectView(R.id.tv_office)
    TextView tv_office;
    @InjectView(R.id.tv_office_map)
    TextView tv_office_map;
    private Timer timer = new Timer();
    //回退判断
    private boolean isBack = false;
    private String mLocation = null;
    //办公地址
    private String workLocation = null;
    //是否需要请求服务端
    private boolean isShouldRequest = true;
    //用于点击判断
    private boolean isCanClickOnce = false;

    @OnClick(R.id.tv_view_map)
    void onViewMap(View v) {
        //latitude  longitude
        Bundle bundle = new Bundle();
        /*if (!JDAppUtil.isEmpty(mLocation) && mLocation.indexOf(",") > -1) {
            bundle.putString(Constants.LOCATION, mLocation.split(",")[1] + "," + mLocation.split(",")[0]);
        }*/
        bundle.putString(Constants.LOCATION, mLocation);
        bundle.putString(Constants.BORROWNAME, tv_name.getText().toString());
        startActivity(GaiDeMapActivity.class, bundle);
    }

    @OnClick(R.id.tv_office_map)
    void onViewOfficeMap() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.LOCATION, workLocation);
        bundle.putString(Constants.BORROWNAME, tv_name.getText().toString());
        startActivity(GaiDeMapActivity.class, bundle);
    }

    @OnClick(R.id.img_telephone)
    void onCallPhone(View v) {
        JDAppUtil.onCallPhone(getActivity(), tv_telephone.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        FileUtil.deleteTempFile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_borrow_apply, container, false);
        ButterKnife.inject(this, view);
        setTitle("首页", false);
        if (AppData.getInstance().getBorrowRequestInfo() == null) {
            getBorrowRequestInfo();
        } else {
            initData(AppData.getInstance().getBorrowRequestInfo());
            mLocation = AppData.getInstance().getBorrowRequestInfo().getLocation();
            workLocation = AppData.getInstance().getBorrowRequestInfo().getWorkLocation();
        }
        return view;
    }

    /*
     * 获取数据
     * */
    private void getBorrowRequestInfo() {
        try {
            HttpClientUtil.get(HttpRequestURL.loanApplyUrl, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler<BorrowRequestInfo>() {
                @Override
                public void onSuccess(BorrowRequestInfo borrowRequestInfo) {
                    initData(borrowRequestInfo);
                }
            }, Class.forName(BorrowRequestInfo.class.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 初始化数据
    * */
    private void initData(BorrowRequestInfo borrowRequestInfo) {
        if (borrowRequestInfo != null) {
            tv_id.setText(borrowRequestInfo.getBorrowRequestId());
            tv_name.setText(borrowRequestInfo.getName());
            tv_telephone.setText(borrowRequestInfo.getMobile());
            if (!JDAppUtil.isEmpty(borrowRequestInfo.getAmount())) {
                try {
                    tv_money.setText(JDAppUtil.money2Format(borrowRequestInfo.getAmount()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tv_date.setText(borrowRequestInfo.getLength());
            tv_purpose.setText(borrowRequestInfo.getBorrowPurposeStr());
            mLocation = borrowRequestInfo.getLocation();
            workLocation = borrowRequestInfo.getWorkLocation();
            /*if (!JDAppUtil.isEmpty(mLocation) && mLocation.indexOf(",") > -1) {
                new GeoCoderUtil(tv_address, mLocation.split(",")[1] + "," + mLocation.split(",")[0]);
            }*/
            new GDGeoCodeUtil(getActivity(),tv_address, mLocation);
            new GDGeoCodeUtil(getActivity(),tv_office, workLocation);
            tv_apply_time.setText(JDAppUtil.getTimeToStr(borrowRequestInfo.getDateline()));
            //-2请求驳回，-1 用户取消 0 新请求 1已分配 2尽调中 3审核中 4补充资料 5审核通过

            /*借款申请状态： -3弃标 -2驳回补充资料 -1用户取消 0待预审 1待分配 2已分配 3尽调中 4录入中
                    * 5初审中 6复审中 7会审中 8主管审核中 9合同签订中 10生成标的中 11已生成标的
                    * 20、账单日 21、还款逾期*/
            if (borrowRequestInfo.getStatus().equals("2")) {
                tv_finish_task.setVisibility(View.GONE);
                btn_transfer.setVisibility(View.VISIBLE);
                btn_transfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JDAlertDialog.showAlertDialog(getActivity(), "确定转出尽调任务？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                transformTask();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        });
                    }
                });
            } else {
                tv_finish_task.setVisibility(View.VISIBLE);
                btn_transfer.setVisibility(View.GONE);
            }

            /*
            * 提交尽调任务报告
            * */
            tv_finish_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JDAlertDialog.showAlertDialog(getActivity(), "确定提交尽调任务报告吗？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HttpClientUtil.put(HttpRequestURL.finishTaskUrl, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
                                @Override
                                public void onSuccess(Object o) {
                                    JDToast.showShortText(getActivity(), "提交尽调任务报告成功");
                                    AppData.getInstance().clearBorrowRequestData();
                                    AppData.getInstance().getUserInfo().setIsBusy("0");
                                    btn_borrow.setEnabled(false);
                                    //去除本地保存的removePaymentId
                                    //SharedPreferencesUtil.removePaymentId(getActivity());
                                    ((MainActivity) getActivity()).setShowFragment(0);
                                    //删除本地文件
                                    FileUtil.deleteTempFile();
                                }
                            }));

                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dialog.cancel();
                        }
                    });
                }
            });
            btn_borrow.setText("开始尽调");
            if (borrowRequestInfo.getStatus().equals("3")) {
                isShouldRequest = false;
                btn_borrow.setText("进入尽调");
            }
            AppData.getInstance().setBorrowRequestInfo(borrowRequestInfo);
        }
    }

    /*
    * 转出任务
    * */
    private void transformTask() {
        HttpClientUtil.put(HttpRequestURL.transformUrl, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                JDToast.showShortText(getActivity(), "任务转出成功");
                AppData.getInstance().clearBorrowRequestData();
                AppData.getInstance().getUserInfo().setIsBusy("0");
                ((MainActivity) getActivity()).setShowFragment(0);
                btn_borrow.setEnabled(false);
            }
        }));
    }

    @OnClick(R.id.btn_borrow)
    void onBeginCheck() {
        if (!JDAppUtil.existSDCard()) {
            JDToast.showLongText(getActivity(), "请安装SD卡");
            return;
        }
        if (JDAppUtil.getSDFreeSize() <= 100l) {
            JDToast.showLongText(getActivity(), "SD容量小于100MB，请清理");
            return;
        }
        JDToast.showLongText(getActivity(), "借款页面加载布局有点慢，请等待！！！");
        if (!isCanClickOnce) {
            isCanClickOnce = true;
            if (!isShouldRequest) {
                isCanClickOnce = false;
                startActivity(InvestigateDetailActivity.class);
            } else {
                HttpClientUtil.put(HttpRequestURL.beginCheckUrl, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
                    @Override
                    public void onSuccess(Object o) {
                        startActivity(InvestigateDetailActivity.class);
                        isCanClickOnce = false;
                        btn_transfer.setVisibility(View.GONE);
                        btn_borrow.setText("进入尽调");
                        isShouldRequest = false;
                    }
                }));
            }
        } else {
            JDToast.showLongText(getActivity(), "不要点了，借款页面加载页面有点慢，请等待！！！");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setTitle("首页", false);
        }
    }


    public static BorrowApplyFragment newInstance() {
        BorrowApplyFragment borrowApplyFragment = new BorrowApplyFragment();
        return borrowApplyFragment;
    }
}
