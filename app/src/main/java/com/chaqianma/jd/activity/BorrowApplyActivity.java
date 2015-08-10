package com.chaqianma.jd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.BorrowRequestInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.utils.SharedPreferencesUtil;
import com.chaqianma.jd.widget.JDAlertDialog;
import com.chaqianma.jd.widget.JDToast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/24.
 * <p/>
 * 尽调主页面
 */
public class BorrowApplyActivity extends BaseActivity {
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
    @InjectView(R.id.tv_begin_task)
    TextView tv_begin_task;
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
    //是否是上个页面回退
    private boolean isPreBack = false;

    @OnClick(R.id.tv_view_map)
    void onViewMap(View v) {
        //latitude  longitude
        Bundle bundle = new Bundle();
        bundle.putString(Constants.LOCATION, mLocation);
        bundle.putString(Constants.BORROWNAME, tv_name.getText().toString());
        startActivity(BaiduMapActivity.class, bundle);
    }

    @OnClick(R.id.tv_office_map)
    void onViewOfficeMap() {
        Bundle bundle = new Bundle();
        ;
        bundle.putString(Constants.LOCATION, workLocation);
        bundle.putString(Constants.BORROWNAME, tv_name.getText().toString());
        startActivity(BaiduMapActivity.class, bundle);
    }

    @OnClick(R.id.img_telephone)
    void onCallPhone(View v) {
        JDAppUtil.onCallPhone(BorrowApplyActivity.this, tv_telephone.getText().toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_apply);
        ButterKnife.inject(this);
        if (AppData.getInstance().getBorrowRequestInfo() == null) {
            getBorrowRequestInfo();
        } else {
            initData(AppData.getInstance().getBorrowRequestInfo());
            mLocation = AppData.getInstance().getBorrowRequestInfo().getLocation();
            workLocation = AppData.getInstance().getBorrowRequestInfo().getWorkLocation();
        }
    }

    /*
    * 获取数据
    * */
    private void getBorrowRequestInfo() {
        try {
            HttpClientUtil.get(HttpRequestURL.loanApplyUrl, null, new JDHttpResponseHandler(BorrowApplyActivity.this, new ResponseHandler<BorrowRequestInfo>() {
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
            tv_money.setText(borrowRequestInfo.getAmount());
            tv_date.setText(borrowRequestInfo.getLength());
            tv_purpose.setText(borrowRequestInfo.getBorrowPurpose());
            mLocation = borrowRequestInfo.getLocation();
            workLocation = borrowRequestInfo.getWorkLocation();
            getUserLocation(mLocation, false);
            getUserLocation(workLocation, true);
            tv_apply_time.setText(JDAppUtil.getTimeToStr(borrowRequestInfo.getDateline()));
            //-2请求驳回，-1 用户取消 0 新请求 1已分配 2尽调中 3审核中 4补充资料 5审核通过
            if (borrowRequestInfo.getStatus().equals("1")) {
                tv_begin_task.setVisibility(View.VISIBLE);
                tv_begin_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JDAlertDialog.showAlertDialog(BorrowApplyActivity.this, "确定转出尽调任务？", new DialogInterface.OnClickListener() {
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
                tv_begin_task.setVisibility(View.GONE);
            }

            /*
            * 提交尽调任务报告
            * */
            tv_finish_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JDAlertDialog.showAlertDialog(BorrowApplyActivity.this, "确定提交尽调任务报告吗？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HttpClientUtil.put(HttpRequestURL.finishTaskUrl, null, new JDHttpResponseHandler(BorrowApplyActivity.this, new ResponseHandler() {
                                @Override
                                public void onSuccess(Object o) {
                                    JDToast.showShortText(BorrowApplyActivity.this, "提交尽调任务报告成功");
                                    AppData.getInstance().clearBorrowRequestData();
                                    btn_borrow.setEnabled(false);
                                    Intent intent = new Intent();
                                    AppData.getInstance().getUserInfo().setIsBusy("0");
                                    //singleTask
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setClass(BorrowApplyActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
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
            if (borrowRequestInfo.getStatus().equals("2")) {
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
        HttpClientUtil.put(HttpRequestURL.transformUrl, null, new JDHttpResponseHandler(BorrowApplyActivity.this, new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                JDToast.showShortText(BorrowApplyActivity.this, "任务转出成功");
                btn_borrow.setEnabled(false);
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.btn_borrow)
    void onBeginCheck() {
        JDToast.showLongText(BorrowApplyActivity.this, "借款页面加载布局有点慢，请等待！！！");
        boolean isFinish = getTaskTag();
        if (!isCanClickOnce) {
            isCanClickOnce = true;
            if (!isShouldRequest) {
                isCanClickOnce = false;
                setTaskTag(false);
                startActivity(InvestigateDetailActivity.class);
            } else {
                HttpClientUtil.put(HttpRequestURL.beginCheckUrl, null, new JDHttpResponseHandler(BorrowApplyActivity.this, new ResponseHandler() {
                    @Override
                    public void onSuccess(Object o) {
                        startActivity(InvestigateDetailActivity.class);
                        isCanClickOnce = false;
                        setTaskTag(false);
                        isPreBack = true;
                        tv_begin_task.setVisibility(View.GONE);
                        btn_borrow.setText("进入尽调");
                        isShouldRequest = false;
                    }
                }));
            }
        } else {
            JDToast.showLongText(BorrowApplyActivity.this, "不要点了，借款页面加载页面有点慢，请等待！！！");
        }
    }

    /*
    * 获取任务状态
    * */
    private boolean getTaskTag() {
        return SharedPreferencesUtil.getShareBoolean(BorrowApplyActivity.this, Constants.ISTASKFinishTAG);
    }

    /*
    * 保存任务状态
    * */
    private void setTaskTag(boolean isFinish) {
        SharedPreferencesUtil.setShareBoolean(BorrowApplyActivity.this, Constants.ISTASKFinishTAG, isFinish);
    }

    //获取用户的地址
    private void getUserLocation(String location, final boolean isOffice) {
        if (location != null && location.length() > 0 && location.indexOf(",") >= 0) {
            try {
                String[] arrs = location.split(",");
                //经度 纬度
                LatLng latLng = new LatLng(Double.parseDouble(arrs[1]), Double.parseDouble(arrs[0]));
                GeoCoder geoCoder = GeoCoder.newInstance();
                OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
                    // 反地理编码查询结果回调函数
                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                        if (result == null
                                || result.error != SearchResult.ERRORNO.NO_ERROR) {
                            // 没有检测到结果
                        }
                        if (isOffice)
                            tv_office.setText(result.getAddress());
                        else
                            tv_address.setText(result.getAddress());
                    }

                    // 地理编码查询结果回调函数
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult result) {
                        if (result == null
                                || result.error != SearchResult.ERRORNO.NO_ERROR) {
                            // 没有检测到结果
                        }
                    }
                };
                // 设置地理编码检索监听者
                geoCoder.setOnGetGeoCodeResultListener(listener);
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isBack) {
            super.onBackPressed();
        } else {
            isBack = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isBack = false;
                }
            }, 5 * 1000);
            JDToast.showShortText(BorrowApplyActivity.this, "再按一次退出系统");
        }
    }
}
