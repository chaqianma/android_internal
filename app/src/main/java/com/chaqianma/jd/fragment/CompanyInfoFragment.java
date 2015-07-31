package com.chaqianma.jd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.CustomerBaseInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxd on 2015/7/28.
 */
public class CompanyInfoFragment extends BaseFragment {
    private String mBorrowRequestId = null;

    @InjectView(R.id.img_company_add)
    ImageView img_company_add;
    @InjectView(R.id.sp_company_type_1)
    Spinner sp_company_type_1;
    //营业执照
    @InjectView(R.id.gv_business_license_1)
    GridView gv_business_license_1;
    //税务登记
    @InjectView(R.id.gv_tax_registration_1)
    GridView gv_tax_registration_1;
    //企业代码证
    @InjectView(R.id.gv_company_code_1)
    GridView gv_company_code_1;
    //其它证件
    @InjectView(R.id.gv_other_card_1)
    GridView gv_other_card_1;
    //经营场所
    @InjectView(R.id.sp_business_premises_1)
    Spinner sp_business_premises_1;
    //房产价合同
    @InjectView(R.id.gv_house_card_1)
    GridView gv_house_card_1;
    //土地件
    @InjectView(R.id.gv_land_card_1)
    GridView gv_land_card_1;
    //对应企业
    @InjectView(R.id.sp_some_company_1)
    Spinner sp_some_company_1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_info, container, false);
        ButterKnife.inject(this, view);

        ViewStub stud=(ViewStub)view.findViewById(R.id.stub_import);
        View v=stud.inflate();
        Spinner sp=(Spinner)v.findViewById(R.id.sp_company_type_1);
        getCompanyInfo();
        return view;
    }


    /*
    得到企业信息
    */
    private void getCompanyInfo() {
        if (AppData.getInstance().getBorrowRequestInfo() != null)
            mBorrowRequestId = AppData.getInstance().getBorrowRequestInfo().getBorrowRequestId();
        mBorrowRequestId="23";
        String requestPath = HttpRequestURL.personalInfoUrl + "/" + Constants.BUSINESSINFO + "/" + mBorrowRequestId;
        try {
            HttpClientUtil.get(requestPath, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
                @Override
                public void onSuccess(Object o) {
                    String sss = "23423423423";
                    sss = "23423423423";
                    sss = "23423423423";
                }
            }));
        } catch (Exception e) {
        }
    }

    public static CompanyInfoFragment newInstance() {
        CompanyInfoFragment companyInfoFragment = new CompanyInfoFragment();
        return companyInfoFragment;
    }
}
