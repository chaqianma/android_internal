package com.chaqianma.jd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.RepaymentInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/8/12.
 */
public class RepaymentDetailActivity extends BaseActivity {

    @InjectView(R.id.tv_id)
    TextView tv_id;
    @InjectView(R.id.tv_status)
    TextView tv_status;
    @InjectView(R.id.tv_borrowName)
    TextView tv_borrowName;
    @InjectView(R.id.tv_phone)
    TextView tv_phone;
    @InjectView(R.id.tv_repayment_money)
    TextView tv_repayment_money;
    @InjectView(R.id.tv_repayment_date)
    TextView tv_repayment_date;
    @InjectView(R.id.tv_office_addr)
    TextView tv_office_addr;
    @InjectView(R.id.tv_repayment_count)
    TextView tv_repayment_count;
    @InjectView(R.id.img_repayment_count)
    ImageView img_repayment_count;
    private String repaymentId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment_detail);
        ButterKnife.inject(this);
        setTopBarState("催收任务详情", true, false);
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.TOVALUEKEY)) {
            repaymentId = intent.getStringExtra(Constants.TOVALUEKEY);
            getRepaymentDetailInfo();
        }
    }

    /*
    * 获取催收详情
    * */
    private void getRepaymentDetailInfo() {
        try {
            HttpClientUtil.get(HttpRequestURL.getRepaymentDetail + repaymentId, null, new JDHttpResponseHandler(RepaymentDetailActivity.this, new ResponseHandler<RepaymentInfo>() {
                @Override
                public void onSuccess(RepaymentInfo repaymentInfo) {
                    if (repaymentInfo != null) {
                        tv_id.setText(repaymentInfo.getId());
                        tv_status.setText(repaymentInfo.getDescStatus());
                        //tv_borrowName.setText(repaymentInfo.getB);
                        tv_phone.setText(repaymentInfo.getId());
                        tv_repayment_money.setText(repaymentInfo.getMoney());
                        tv_repayment_date.setText(JDAppUtil.getStrDateTime(repaymentInfo.getRepaymentDateline()));
                        tv_office_addr.setText(repaymentInfo.getId());
                        tv_repayment_count.setText(repaymentInfo.getDunningCount());
                    }
                }
            }, Class.forName(RepaymentInfo.class.getName())));
        } catch (Exception e) {

        }
    }

    /*
    * 添加催收次数
    * */
    @OnClick(R.id.img_repayment_count)
    void addRepaymentCount(View v) {
        HttpClientUtil.put(HttpRequestURL.getRepaymentDetail + repaymentId, null, new JDHttpResponseHandler(RepaymentDetailActivity.this, new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                if (o != null) {
                    tv_repayment_count.setText(o.toString());
                }
            }
        }));
    }
}
