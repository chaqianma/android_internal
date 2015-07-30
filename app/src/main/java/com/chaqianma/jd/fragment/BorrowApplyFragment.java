package com.chaqianma.jd.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.BaiduMapActivity;
import com.chaqianma.jd.activity.InvestigateDetailActivity;
import com.chaqianma.jd.activity.MainActivity;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.BorrowRequestInfo;
import com.chaqianma.jd.model.UserInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDAlertDialog;
import com.chaqianma.jd.widget.JDToast;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.w3c.dom.Text;

import java.security.Timestamp;
import java.util.HashMap;

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

    @InjectView(R.id.tv_check)
    TextView tv_check;
    @InjectView(R.id.btn_borrow)
    Button btn_borrow;
    private String mLocation = null;

    @OnClick(R.id.tv_view_map)
    void onViewMap(View v) {
        //latitude  longitude
        Bundle bundle = new Bundle();
        mLocation = "118.996925,32.142425";
        bundle.putString(Constants.LOCATION, mLocation);
        bundle.putString(Constants.BORROWNAME, tv_name.getText().toString());
        startActivity(BaiduMapActivity.class, bundle);
    }

    @OnClick(R.id.img_telephone)
    void onCallPhone(View v) {
        JDAppUtil.onCallPhone(getActivity(), tv_telephone.getText().toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow_apply, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    private void initData() {
        try {
            HttpClientUtil.get(HttpRequestURL.loanApplyUrl, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler<BorrowRequestInfo>() {
                @Override
                public void onSuccess(BorrowRequestInfo borrowRequestInfo) {
                    if (borrowRequestInfo != null) {
                        tv_id.setText(borrowRequestInfo.getBorrowRequestId());
                        tv_name.setText(borrowRequestInfo.getName());
                        tv_telephone.setText(borrowRequestInfo.getMobile());
                        tv_money.setText(borrowRequestInfo.getAmount());
                        tv_date.setText(borrowRequestInfo.getLength());
                        tv_purpose.setText(borrowRequestInfo.getBorrowPurpose());
                        mLocation = borrowRequestInfo.getLocation();
                        mLocation = "118.996925,32.142425";
                        getUserLocation(mLocation);
                        tv_apply_time.setText(JDAppUtil.getStrDateTime(borrowRequestInfo.getDateline()));
                        //-2请求驳回，-1 用户取消 0 新请求 1已分配 2尽调中 3审核中 4补充资料 5审核通过
                        if (borrowRequestInfo.getStatus().equals("1")) {
                            tv_check.setVisibility(View.VISIBLE);
                            tv_check.setOnClickListener(new View.OnClickListener() {
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
                            tv_check.setVisibility(View.GONE);
                        }
                        AppData.getInstance().setBorrowRequestInfo(borrowRequestInfo);
                    }
                }
            }, Class.forName(BorrowRequestInfo.class.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transformTask() {
        HttpClientUtil.put(HttpRequestURL.transformUrl, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                JDToast.showShortText(getActivity(), "任务转出成功");
                btn_borrow.setEnabled(false);
            }
        }));
    }

    @OnClick(R.id.btn_borrow)
    void onBeginCheck() {
        HttpClientUtil.put(HttpRequestURL.beginCheckUrl, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                startActivity(InvestigateDetailActivity.class);
            }
        }));
    }

    //获取用户的地址
    public void getUserLocation(String location) {
        if (location != null && location.length() > 0 && location.indexOf(",") >= 0) {
            try {
                String [] arrs=location.split(",");
                //经度 纬度
                LatLng latLng = new LatLng( Double.parseDouble(arrs[1]), Double.parseDouble(arrs[0]));
                GeoCoder geoCoder = GeoCoder.newInstance();
                OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
                    // 反地理编码查询结果回调函数
                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                        if (result == null
                                || result.error != SearchResult.ERRORNO.NO_ERROR) {
                            // 没有检测到结果
                        }
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

    public static BorrowApplyFragment newInstance() {
        BorrowApplyFragment borrowApplyFragment = new BorrowApplyFragment();
        return borrowApplyFragment;
    }
}
