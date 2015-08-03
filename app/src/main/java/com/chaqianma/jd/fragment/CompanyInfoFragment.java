package com.chaqianma.jd.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.ImgsGridViewAdapter;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.CompanyInfo;
import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.model.UploadFileType;
import com.chaqianma.jd.model.UploadStatus;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.ImageUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDFileResponseHandler;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDToast;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/28.
 */
public class CompanyInfoFragment extends BaseFragment implements ImgsGridViewAdapter.iOnClickImgListener {
    @InjectView(R.id.linear_container)
    LinearLayout linear_container;
    //第一家企业
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
    //备注
    @InjectView(R.id.et_remark)
    EditText et_remark;
    //第二家企业
    Spinner sp_company_type_2 = null;
    //营业执照
    GridView gv_business_license_2 = null;
    //税务登记
    GridView gv_tax_registration_2 = null;
    //企业代码证
    GridView gv_company_code_2 = null;
    //其它证件
    GridView gv_other_card_2 = null;
    //经营场所
    Spinner sp_business_premises_2 = null;
    //房产价合同
    GridView gv_house_card_2 = null;
    //土地件
    GridView gv_land_card_2 = null;
    //对应企业
    Spinner sp_some_company_2 = null;


    //第三家企业
    Spinner sp_company_type_3 = null;
    //营业执照
    GridView gv_business_license_3 = null;
    //税务登记
    GridView gv_tax_registration_3 = null;
    //企业代码证
    GridView gv_company_code_3 = null;
    //其它证件
    GridView gv_other_card_3 = null;
    //经营场所
    Spinner sp_business_premises_3 = null;
    //房产价合同
    GridView gv_house_card_3 = null;
    //土地件
    GridView gv_land_card_3 = null;
    //对应企业
    Spinner sp_some_company_3 = null;
    //营业
    private ImgsGridViewAdapter mBLAdapter_1 = null;
    private List<UploadFileInfo> mBLList_1 = null;
    //税务
    private ImgsGridViewAdapter mTRAdapter_1 = null;
    private List<UploadFileInfo> mTRList_1 = null;
    //企业代码
    private ImgsGridViewAdapter mCCAdapter_1 = null;
    private List<UploadFileInfo> mCCList_1 = null;
    //其它
    private ImgsGridViewAdapter mOCAdapter_1 = null;
    private List<UploadFileInfo> mOCList_1 = null;
    //房产
    private ImgsGridViewAdapter mHCAdapter_1 = null;
    private List<UploadFileInfo> mHCList_1 = null;
    //土地
    private ImgsGridViewAdapter mLCAdapter_1 = null;
    private List<UploadFileInfo> mLCList_1 = null;

    //营业
    private ImgsGridViewAdapter mBLAdapter_2 = null;
    private List<UploadFileInfo> mBLList_2 = null;
    //税务
    private ImgsGridViewAdapter mTRAdapter_2 = null;
    private List<UploadFileInfo> mTRList_2 = null;
    //企业代码
    private ImgsGridViewAdapter mCCAdapter_2 = null;
    private List<UploadFileInfo> mCCList_2 = null;
    //其它
    private ImgsGridViewAdapter mOCAdapter_2 = null;
    private List<UploadFileInfo> mOCList_2 = null;
    //房产
    private ImgsGridViewAdapter mHCAdapter_2 = null;
    private List<UploadFileInfo> mHCList_2 = null;
    //土地
    private ImgsGridViewAdapter mLCAdapter_2 = null;
    private List<UploadFileInfo> mLCList_2 = null;


    //营业
    private ImgsGridViewAdapter mBLAdapter_3 = null;
    private List<UploadFileInfo> mBLList_3 = null;
    //税务
    private ImgsGridViewAdapter mTRAdapter_3 = null;
    private List<UploadFileInfo> mTRList_3 = null;
    //企业代码
    private ImgsGridViewAdapter mCCAdapter_3 = null;
    private List<UploadFileInfo> mCCList_3 = null;
    //其它
    private ImgsGridViewAdapter mOCAdapter_3 = null;
    private List<UploadFileInfo> mOCList_3 = null;
    //房产
    private ImgsGridViewAdapter mHCAdapter_3 = null;
    private List<UploadFileInfo> mHCList_3 = null;
    //土地
    private ImgsGridViewAdapter mLCAdapter_3 = null;
    private List<UploadFileInfo> mLCList_3 = null;

    //图片标签
    private UploadFileType fileType = UploadFileType.YY;
    //root view
    private View mView = null;
    //第二家企业是否已显示
    private boolean isCompany2Show = false;
    //第三家企业是否已显示
    private boolean isCompany3Show = false;
    private String mBorrowRequestId = null;
    //上传文件所用的Id
    private String[] mParentId = new String[3];
    //用于标识选择的是哪家企业
    private int selCompanyIdxTag = -1;
    //企业集合
    private List<CompanyInfo> mCompanyInfoList = null;
    //下拉框集合
    private ArrayList<String> companyList = new ArrayList<String>() {
        {
            add("企业1");
            add("企业2");
            add("企业3");
        }
    };

    /*
    * 添加企业
    * */
    @OnClick(R.id.img_company_add)
    void onImgAddClick(View v) {
        addCompany();
    }

    /*
    * 添加企业
    * */
    private void addCompany() {
        //第二家企业
        if (!isCompany2Show) {
            isCompany2Show = true;
            View view = ((ViewStub) mView.findViewById(R.id.stub_company_2)).inflate();
            initControlView(true);
            initGridViewData(true);
            sp_some_company_1.setEnabled(false);
            //下拉框
            companyList.remove(sp_some_company_2.getSelectedItemPosition());
            initSpinner(sp_some_company_2, companyList);
            JDAppUtil.addShowAction(view);
        } else {
            //添加第三家企业
            if (!isCompany3Show) {
                isCompany3Show = true;
                View view = ((ViewStub) mView.findViewById(R.id.stub_company_3)).inflate();
                initControlView(false);
                initGridViewData(false);
                sp_some_company_2.setEnabled(false);
                companyList.remove(sp_some_company_3.getSelectedItemPosition());
                initSpinner(sp_some_company_3, companyList);
                JDAppUtil.addShowAction(view);
            }
        }
    }

    /*
    *  初始化下拉框
    * */
    private void initSpinner(Spinner spinner, List<String> data) {
        //将可选内容与ArrayAdapter连接起来
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);
    }

    /*
    * 初始化组件
    * */
    private void initControlView(boolean is2) {
        if (is2) {
            sp_company_type_2 = (Spinner) mView.findViewById(R.id.sp_company_type_2);
            //营业执照
            gv_business_license_2 = (GridView) mView.findViewById(R.id.gv_business_license_2);
            //税务登记
            gv_tax_registration_2 = (GridView) mView.findViewById(R.id.gv_tax_registration_2);
            //企业代码证
            gv_company_code_2 = (GridView) mView.findViewById(R.id.gv_company_code_2);
            //其它证件
            gv_other_card_2 = (GridView) mView.findViewById(R.id.gv_other_card_2);
            //经营场所
            sp_business_premises_2 = (Spinner) mView.findViewById(R.id.sp_business_premises_2);
            //房产价合同
            gv_house_card_2 = (GridView) mView.findViewById(R.id.gv_house_card_2);
            //土地件
            gv_land_card_2 = (GridView) mView.findViewById(R.id.gv_land_card_2);
            //对应企业
            sp_some_company_2 = (Spinner) mView.findViewById(R.id.sp_some_company_2);
        } else {
            sp_company_type_3 = (Spinner) mView.findViewById(R.id.sp_company_type_3);
            //营业执照
            gv_business_license_3 = (GridView) mView.findViewById(R.id.gv_business_license_3);
            //税务登记
            gv_tax_registration_3 = (GridView) mView.findViewById(R.id.gv_tax_registration_3);
            //企业代码证
            gv_company_code_3 = (GridView) mView.findViewById(R.id.gv_company_code_3);
            //其它证件
            gv_other_card_3 = (GridView) mView.findViewById(R.id.gv_other_card_3);
            //经营场所
            sp_business_premises_3 = (Spinner) mView.findViewById(R.id.sp_business_premises_3);
            //房产价合同
            gv_house_card_3 = (GridView) mView.findViewById(R.id.gv_house_card_3);
            //土地件
            gv_land_card_3 = (GridView) mView.findViewById(R.id.gv_land_card_3);
            //对应企业
            sp_some_company_3 = (Spinner) mView.findViewById(R.id.sp_some_company_3);
        }
    }

    /*
    * 初始化新增企业的GridView数据
    * */
    private void initGridViewData(boolean is2) {
        if (is2) {
            //第二家企业
            {
                //营业
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(1);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.YY.getValue());
                mBLList_2.add(imgInfo);
                mBLAdapter_2 = new ImgsGridViewAdapter(getActivity(), mBLList_2);
                mBLAdapter_2.setOnClickImgListener(this);
                gv_business_license_2.setAdapter(mBLAdapter_2);
            }
            {
                //税务
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(1);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.SW.getValue());
                mTRList_2.add(imgInfo);
                mTRAdapter_2 = new ImgsGridViewAdapter(getActivity(), mTRList_2);
                mTRAdapter_2.setOnClickImgListener(this);
                gv_tax_registration_2.setAdapter(mTRAdapter_2);
            }
            {
                //企业代码
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(1);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.QYDM.getValue());
                mCCList_2.add(imgInfo);
                mCCAdapter_2 = new ImgsGridViewAdapter(getActivity(), mCCList_2);
                mCCAdapter_2.setOnClickImgListener(this);
                gv_company_code_2.setAdapter(mCCAdapter_2);
            }
            {
                //其它
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(1);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.QT.getValue());
                mOCList_2.add(imgInfo);
                mOCAdapter_2 = new ImgsGridViewAdapter(getActivity(), mOCList_2);
                mOCAdapter_2.setOnClickImgListener(this);
                gv_other_card_2.setAdapter(mOCAdapter_2);
            }
            {
                //房产
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(1);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.FC.getValue());
                mHCList_2.add(imgInfo);
                mHCAdapter_2 = new ImgsGridViewAdapter(getActivity(), mHCList_2);
                mHCAdapter_2.setOnClickImgListener(this);
                gv_house_card_2.setAdapter(mHCAdapter_2);
            }
            {
                //土地
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(1);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.TD.getValue());
                mLCList_2.add(imgInfo);
                mLCAdapter_2 = new ImgsGridViewAdapter(getActivity(), mLCList_2);
                mLCAdapter_2.setOnClickImgListener(this);
                gv_land_card_2.setAdapter(mLCAdapter_2);
            }
        } else {
            //第三家企业
            {
                //营业
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.YY.getValue());
                mBLList_3.add(imgInfo);
                mBLAdapter_3 = new ImgsGridViewAdapter(getActivity(), mBLList_3);
                mBLAdapter_3.setOnClickImgListener(this);
                gv_business_license_3.setAdapter(mBLAdapter_3);
            }
            {
                //税务
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.SW.getValue());
                mTRList_3.add(imgInfo);
                mTRAdapter_3 = new ImgsGridViewAdapter(getActivity(), mTRList_3);
                mTRAdapter_3.setOnClickImgListener(this);
                gv_tax_registration_3.setAdapter(mTRAdapter_3);
            }
            {
                //企业代码
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.QYDM.getValue());
                mCCList_3.add(imgInfo);
                mCCAdapter_3 = new ImgsGridViewAdapter(getActivity(), mCCList_3);
                mCCAdapter_3.setOnClickImgListener(this);
                gv_company_code_3.setAdapter(mCCAdapter_3);
            }
            {
                //其它
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.QT.getValue());
                mOCList_3.add(imgInfo);
                mOCAdapter_3 = new ImgsGridViewAdapter(getActivity(), mOCList_3);
                mOCAdapter_3.setOnClickImgListener(this);
                gv_other_card_3.setAdapter(mOCAdapter_3);
            }
            {
                //房产
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.FC.getValue());
                mHCList_3.add(imgInfo);
                mHCAdapter_3 = new ImgsGridViewAdapter(getActivity(), mHCList_3);
                mHCAdapter_3.setOnClickImgListener(this);
                gv_house_card_3.setAdapter(mHCAdapter_3);
            }
            {
                //土地
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.TD.getValue());
                mLCList_3.add(imgInfo);
                mLCAdapter_3 = new ImgsGridViewAdapter(getActivity(), mLCList_3);
                mLCAdapter_3.setOnClickImgListener(this);
                gv_land_card_3.setAdapter(mLCAdapter_3);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_company_info, container, false);
        ButterKnife.inject(this, mView);
        mBorrowRequestId = getBorrowRequestId();
        //初始化第一家企业
        initOneView();
        getCompanyInfo();
        sp_business_premises_1.setSelection(2);
        return mView;
    }

    //初始化集合
    private void initImageList() {
        //第一家企业
        mBLList_1 = new ArrayList<UploadFileInfo>();
        mTRList_1 = new ArrayList<UploadFileInfo>();
        mCCList_1 = new ArrayList<UploadFileInfo>();
        mOCList_1 = new ArrayList<UploadFileInfo>();
        mHCList_1 = new ArrayList<UploadFileInfo>();
        mLCList_1 = new ArrayList<UploadFileInfo>();

        //第二家企业
        mBLList_2 = new ArrayList<UploadFileInfo>();
        mTRList_2 = new ArrayList<UploadFileInfo>();
        mCCList_2 = new ArrayList<UploadFileInfo>();
        mOCList_2 = new ArrayList<UploadFileInfo>();
        mHCList_2 = new ArrayList<UploadFileInfo>();
        mLCList_2 = new ArrayList<UploadFileInfo>();

        //第三家企业
        mBLList_3 = new ArrayList<UploadFileInfo>();
        mTRList_3 = new ArrayList<UploadFileInfo>();
        mCCList_3 = new ArrayList<UploadFileInfo>();
        mOCList_3 = new ArrayList<UploadFileInfo>();
        mHCList_3 = new ArrayList<UploadFileInfo>();
        mLCList_3 = new ArrayList<UploadFileInfo>();
    }

    //初始化数据源控件
    private void initOneView() {

        //第一家企业
        {
            //营业
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.YY.getValue());
            mBLList_1.add(imgInfo);
            mBLAdapter_1 = new ImgsGridViewAdapter(getActivity(), mBLList_1);
            mBLAdapter_1.setOnClickImgListener(this);
            gv_business_license_1.setAdapter(mBLAdapter_1);
        }
        {
            //税务
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.SW.getValue());
            mTRList_1.add(imgInfo);
            mTRAdapter_1 = new ImgsGridViewAdapter(getActivity(), mTRList_1);
            mTRAdapter_1.setOnClickImgListener(this);
            gv_tax_registration_1.setAdapter(mTRAdapter_1);
        }
        {
            //企业代码
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.QYDM.getValue());
            mCCList_1.add(imgInfo);
            mCCAdapter_1 = new ImgsGridViewAdapter(getActivity(), mCCList_1);
            mCCAdapter_1.setOnClickImgListener(this);
            gv_company_code_1.setAdapter(mCCAdapter_1);
        }
        {
            //其它
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.QT.getValue());
            mOCList_1.add(imgInfo);
            mOCAdapter_1 = new ImgsGridViewAdapter(getActivity(), mOCList_1);
            mOCAdapter_1.setOnClickImgListener(this);
            gv_other_card_1.setAdapter(mOCAdapter_1);
        }
        {
            //房产
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.FC.getValue());
            mHCList_1.add(imgInfo);
            mHCAdapter_1 = new ImgsGridViewAdapter(getActivity(), mHCList_1);
            mHCAdapter_1.setOnClickImgListener(this);
            gv_house_card_1.setAdapter(mHCAdapter_1);
        }
        {
            //土地
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.TD.getValue());
            mLCList_1.add(imgInfo);
            mLCAdapter_1 = new ImgsGridViewAdapter(getActivity(), mLCList_1);
            mLCAdapter_1.setOnClickImgListener(this);
            gv_land_card_1.setAdapter(mLCAdapter_1);
        }
    }

    /*
    得到企业信息
    */
    private void getCompanyInfo() {
        String requestPath = HttpRequestURL.personalInfoUrl + "/" + Constants.BUSINESSINFO + "/" + mBorrowRequestId;
        try {
            HttpClientUtil.get(requestPath, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
                @Override
                public void onSuccess(Object o) {
                    if (o != null) {
                        try {
                            mCompanyInfoList = JSON.parseArray(o.toString(), CompanyInfo.class);
                            if (mCompanyInfoList != null) {
                                for (int i = 0; i < mCompanyInfoList.size(); i++) {
                                    CompanyInfo companyInfo = mCompanyInfoList.get(i);
                                    if (companyInfo == null)
                                        continue;
                                    mParentId[i] = companyInfo.getId();
                                    if (!JDAppUtil.isEmpty(companyInfo.getCompanyName()) || (companyInfo.getFileList() != null && companyInfo.getFileList().size() > 0)) {
                                        companyInfo.setIsValid(true);
                                        switch (i) {
                                            case 0:
                                                et_remark.setText(companyInfo.getRemark());
                                                initServerFile(companyInfo.getFileList());
                                                break;
                                            case 1:
                                                addCompany();
                                                //下载图片
                                                initServerFile(companyInfo.getFileList());
                                                break;
                                            case 2:
                                                addCompany();
                                                //下载图片
                                                initServerFile(companyInfo.getFileList());
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                }
            }));
        } catch (Exception e) {
        }
    }

    /*
   *获取服务器文件信息
    */
    private void initServerFile(List<UploadFileInfo> fileInfoList) {
        if (fileInfoList != null && fileInfoList.size() > 0) {
            for (UploadFileInfo uploadFileInfo : fileInfoList) {
                uploadFileInfo.setiServer(true);
                uploadFileInfo.setIsDefault(false);
                uploadFileInfo.setBorrowRequestId(mBorrowRequestId);
                getServerFile(uploadFileInfo);
            }
        }
    }

    /*
   * 得到服务器文件
   * */
    private void getServerFile(final UploadFileInfo fileInfo) {
        String filePath = getSaveFilePath(fileInfo);
        fileInfo.setBigImgPath(filePath);
        HttpClientUtil.get(HttpRequestURL.downLoadFileUrl + "/" + fileInfo.getFileId(), null, new JDFileResponseHandler(fileInfo, new ResponseHandler<UploadFileInfo>() {
            @Override
            public void onSuccess(UploadFileInfo uploadFileInfo) {
                uploadFileInfo.setiServer(true);
                uploadFileInfo.setStatus(UploadStatus.SUCCESS.getValue());
                addGridViewData(uploadFileInfo);
            }

            @Override
            public void onFailure(UploadFileInfo uploadFileInfo) {
                super.onFailure(uploadFileInfo);
                uploadFileInfo.setiServer(false);
                uploadFileInfo.setStatus(UploadStatus.FAILURE.getValue());
            }
        }));
    }

    //刷新数据
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {
                UploadFileInfo imgInfo = (UploadFileInfo) msg.obj;
                refreshData(imgInfo);
            }
        }
    };

    @Override
    public void onImgClick(List<UploadFileInfo> uploadImgInfoList, int idx) {
        if (idx < uploadImgInfoList.size()) {
            UploadFileInfo imgInfo = uploadImgInfoList.get(idx);
            fileType = UploadFileType.valueOf(imgInfo.getFileType());
            selCompanyIdxTag = imgInfo.getIdxTag();
            if (imgInfo.isDefault()) {
                mPopup.showAtLocation(linear_container, Gravity.BOTTOM, 0, 0);
            } else {
                mViewPagerPopup.setUploadImgList(uploadImgInfoList, idx);
                mViewPagerPopup.showAtLocation(linear_container, Gravity.BOTTOM, 0, 0);
            }
        }
    }


    //选择本地照片
    @Override
    public void onChoosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_SDK_IMGS);
    }

    //照相
    @Override
    public void onTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Constants.TEMPPATH);
        if (!file.getParentFile().exists() && !file.mkdirs()) {

        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        }
    }
    
    /*
    * 拍照回调
    * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SDK_IMGS:
                    if (data != null && data.getData() != null) {
                        Uri imgUri = data.getData();
                        ContentResolver resolver = getActivity().getContentResolver();
                        String[] pojo = {MediaStore.Images.Media.DATA};
                        Cursor cursor = null;
                        try {
                            cursor = resolver.query(imgUri, pojo, null, null, null);
                            if (cursor != null && cursor.getCount() > 0) {
                                int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                cursor.moveToFirst();
                                mHandler.post(new ImgRunable(cursor.getString(colunm_index)));
                            } else {
                                JDToast.showLongText(getActivity(), "请选择有效的图片文件夹");
                            }
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                    }
                    break;
                case REQUEST_TAKE_PHOTO:
                    mHandler.post(mRunnable);
                    break;
                default:
                    break;
            }
        }
    }


    /*
    * 提交数据
    * */
    public void saveDataSubmit() {

    }

    //删除图片
    @Override
    public void onDeletePhoto(UploadFileInfo uploadFileInfo) {
        //刷新数据源
        refreshData(uploadFileInfo);
    }

    /*
    * 往GridView里添加图片
    * */
    private void addGridViewData(UploadFileInfo imgInfo) {
        UploadFileType fType = UploadFileType.valueOf(imgInfo.getFileType());
        if (fType == UploadFileType.YY) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mBLList_1.add(0, imgInfo);
                    break;
                case 1:
                    mBLList_2.add(0, imgInfo);
                    break;
                case 2:
                    mBLList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.SW) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mTRList_1.add(0, imgInfo);
                    break;
                case 1:
                    mTRList_2.add(0, imgInfo);
                    break;
                case 2:
                    mTRList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.QYDM) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mCCList_1.add(0, imgInfo);
                    break;
                case 1:
                    mCCList_2.add(0, imgInfo);
                    break;
                case 2:
                    mCCList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }

        } else if (fType == UploadFileType.QT) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mOCList_1.add(0, imgInfo);
                    break;
                case 1:
                    mOCList_2.add(0, imgInfo);
                    break;
                case 2:
                    mOCList_2.add(0, imgInfo);
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.FC) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mHCList_1.add(0, imgInfo);
                    break;
                case 1:
                    mHCList_2.add(0, imgInfo);
                    break;
                case 2:
                    mHCList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }

        } else if (fType == UploadFileType.TD) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mLCList_1.add(0, imgInfo);
                    break;
                case 1:
                    mLCList_2.add(0, imgInfo);
                    break;
                case 2:
                    mLCList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }
        } else {

        }
        mHandler.sendMessage(mHandler.obtainMessage(0, imgInfo));
    }

    /*
    * 先这么写吧。。。 图片处理 到时与下面的整合起来
    * */

    private class ImgRunable implements Runnable {
        private String imgPath = null;

        public ImgRunable(String imgPath) {
            this.imgPath = imgPath;
        }

        @Override
        public void run() {
            String random = System.currentTimeMillis() + "";
            String smallImgPath = getFilePath(random, fileType.getValue(), false);
            String bigImgPath = getFilePath(random, fileType.getValue(), true);
            //存放大图
            Bitmap proportionBM = ImageUtil.proportionZoom(imgPath, 1024);
            if (proportionBM != null) {
                ImageUtil.saveBitmapFile(bigImgPath, proportionBM);
                proportionBM.recycle();
            }
            //存放小图
            Bitmap bitmap = ImageUtil.getLocalThumbImg(imgPath, 80, 80, "jpg");
            if (bitmap != null) {
                ImageUtil.saveBitmapFile(smallImgPath, bitmap);
                bitmap.recycle();
            }
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setiServer(false);
            imgInfo.setBigImgPath(bigImgPath);
            imgInfo.setSmallImgPath(smallImgPath);
            imgInfo.setParentTableName(Constants.BUSINESS_INFO);
            imgInfo.setIdxTag(selCompanyIdxTag);
            imgInfo.setFileType(fileType.getValue());
            //  YY(4),SW(5),QYDM(6),QT(7),FC(8),TD(9);
            addGridViewData(imgInfo);
            uploadImg(imgInfo);
        }
    }

    //保存图片
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            String random = System.currentTimeMillis() + "";
            String smallImgPath = getFilePath(random, fileType.getValue(), false);
            String bigImgPath = getFilePath(random, fileType.getValue(), true);
            //存放大图
            Bitmap proportionBM = ImageUtil.proportionZoom(Constants.TEMPPATH, 1024);
            if (proportionBM != null) {
                ImageUtil.saveBitmapFile(bigImgPath, proportionBM);
                proportionBM.recycle();
            }
            //存放小图
            Bitmap bitmap = ImageUtil.getLocalThumbImg(Constants.TEMPPATH, 80, 80, "jpg");
            if (bitmap != null) {
                ImageUtil.saveBitmapFile(smallImgPath, bitmap);
                bitmap.recycle();
            }
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setiServer(false);
            imgInfo.setBigImgPath(bigImgPath);
            imgInfo.setSmallImgPath(smallImgPath);
            imgInfo.setParentTableName(Constants.BUSINESS_INFO);
            imgInfo.setIdxTag(selCompanyIdxTag);
            imgInfo.setFileType(fileType.getValue());
            //  YY(4),SW(5),QYDM(6),QT(7),FC(8),TD(9);
            addGridViewData(imgInfo);
            uploadImg(imgInfo);
        }
    };


    //上传图片
    private void uploadImg(final UploadFileInfo fileInfo) {
        try {

            HttpClientUtil.post(getActivity(), HttpRequestURL.uploadImgUrl, getUploadEntity(fileInfo, mParentId[fileInfo.getIdxTag()], false), new JDHttpResponseHandler(getActivity(), new ResponseHandler<UploadFileInfo>() {
                @Override
                public void onSuccess(UploadFileInfo downImgInfo) {
                    fileInfo.setStatus(UploadStatus.SUCCESS.getValue());//成功
                    fileInfo.setFileId(downImgInfo.getFileId());
                    fileInfo.setiServer(true);
                    fileInfo.setFileExt(downImgInfo.getFileExt());
                    fileInfo.setUserId(downImgInfo.getUserId());
                    fileInfo.setParentTableName(downImgInfo.getParentTableName());
                    fileInfo.setParentId(downImgInfo.getParentId());
                    refreshData(fileInfo);
                }

                @Override
                public void onFailure(String data) {
                    fileInfo.setStatus(UploadStatus.FAILURE.getValue());//失败
                    refreshData(fileInfo);
                }
            }, Class.forName(UploadFileInfo.class.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 刷新数据
    * */
    private void refreshData(UploadFileInfo fileInfo) {
        UploadFileType fType = UploadFileType.valueOf(fileInfo.getFileType());
        if (fType == UploadFileType.YY) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mBLAdapter_1.refreshData();
                    break;
                case 1:
                    mBLAdapter_2.refreshData();
                    break;
                case 2:
                    mBLAdapter_3.refreshData();
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.SW) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mTRAdapter_1.refreshData();
                    break;
                case 1:
                    mTRAdapter_2.refreshData();
                    break;
                case 2:
                    mTRAdapter_3.refreshData();
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.QYDM) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mCCAdapter_1.refreshData();
                    break;
                case 1:
                    mCCAdapter_2.refreshData();
                    break;
                case 2:
                    mCCAdapter_3.refreshData();
                    break;
                default:
                    break;
            }

        } else if (fType == UploadFileType.QT) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mOCAdapter_1.refreshData();
                    break;
                case 1:
                    mOCAdapter_2.refreshData();
                    break;
                case 2:
                    mOCAdapter_3.refreshData();
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.FC) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mHCAdapter_1.refreshData();
                    break;
                case 1:
                    mHCAdapter_2.refreshData();
                    break;
                case 2:
                    mHCAdapter_3.refreshData();
                    break;
                default:
                    break;
            }

        } else if (fType == UploadFileType.TD) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mLCAdapter_1.refreshData();
                    break;
                case 1:
                    mLCAdapter_2.refreshData();
                    break;
                case 2:
                    mLCAdapter_3.refreshData();
                    break;
                default:
                    break;
            }
        } else {

        }
    }

    public static CompanyInfoFragment newInstance() {
        CompanyInfoFragment companyInfoFragment = new CompanyInfoFragment();
        return companyInfoFragment;
    }
}
