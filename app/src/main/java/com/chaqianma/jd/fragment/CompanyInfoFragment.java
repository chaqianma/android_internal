package com.chaqianma.jd.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
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
import com.chaqianma.jd.DBHelper.ImageTable;
import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.InvestigateDetailActivity;
import com.chaqianma.jd.adapters.ImgsGridViewAdapter;
import com.chaqianma.jd.adapters.SoundGridViewAdapter;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.CompanyInfo;
import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.model.UploadFileType;
import com.chaqianma.jd.model.UploadStatus;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDFileResponseHandler;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDAlertDialog;
import com.chaqianma.jd.widget.JDToast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
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
    @InjectView(R.id.img_company_add_1)
    ImageView img_company_add_1;
    @InjectView(R.id.img_company_delete_1)
    ImageView img_company_delete_1;
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
    @InjectView(R.id.gv_sound)
    GridView gv_sound;
    @InjectView(R.id.gv_mark)
    GridView gv_mark;
    @InjectView(R.id.layout_company_1)
    LinearLayout layout_company_1;
    @InjectView(R.id.gv_income_1)
    GridView gv_income_1;
    @InjectView(R.id.gv_bill_1)
    GridView gv_bill_1;
    //第二家企业
    ImageView img_company_delete_2 = null;
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
    //收入证明
    GridView gv_income_2 = null;
    //经营性单据
    GridView gv_bill_2 = null;
    //第三家企业
    ImageView img_company_delete_3 = null;
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
    //收入证明
    GridView gv_income_3 = null;
    //经营性单据
    GridView gv_bill_3 = null;
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
    private boolean isCompany1Show = false;
    //第二家企业是否已显示
    private boolean isCompany2Show = false;
    //第三家企业是否已显示
    private boolean isCompany3Show = false;
    private String mBorrowRequestId = null;
    //上传文件所用的Id
    private String[] mParentId = new String[3];
    //用于标识选择的是哪家企业
    private int selCompanyIdxTag = -1;
    //下拉框集合  无聊的代码
    private ArrayList<String> companyList1 = new ArrayList<String>() {
        {
            add("企业a");
            add("企业b");
            add("企业c");
        }
    };
    private ArrayList<String> companyList2 = null;
    private ArrayList<String> companyList3 = null;
    //备注数据源
    private ImgsGridViewAdapter remarkImgsAdapter = null;
    //备注集合
    private List<UploadFileInfo> remarkUploadImgInfoList = null;
    //录音数据源
    private SoundGridViewAdapter soundAdapter = null;
    //录音集合
    private List<UploadFileInfo> soundInfoList = null;
    //用于只加载一次
    private boolean hasLoadedOnce = false;
    //收入证明
    private ImgsGridViewAdapter mSYAdapter_1 = null;
    private List<UploadFileInfo> mSYList_1 = null;
    //经营性单据
    private ImgsGridViewAdapter mJYAdapter_1 = null;
    private List<UploadFileInfo> mJYList_1 = null;
    //收入证明
    private ImgsGridViewAdapter mSYAdapter_2 = null;
    private List<UploadFileInfo> mSYList_2 = null;
    //经营性单据
    private ImgsGridViewAdapter mJYAdapter_2 = null;
    private List<UploadFileInfo> mJYList_2 = null;
    //收入证明
    private ImgsGridViewAdapter mSYAdapter_3 = null;
    private List<UploadFileInfo> mSYList_3 = null;
    //经营性单据
    private ImgsGridViewAdapter mJYAdapter_3 = null;
    private List<UploadFileInfo> mJYList_3 = null;

    LinearLayout layout_company_2;
    LinearLayout layout_company_3;

    /*
    * 添加企业
    * */
    @OnClick(R.id.img_company_add_1)
    void onImgAddClick(View v) {
        addCompany();
    }

    /*
    * 删除企业
    * */
    @OnClick(R.id.img_company_delete_1)
    void onDeleteCompany(View v) {
        //删除企业
        if (!isCompany2Show && !isCompany3Show) {
            JDToast.showLongText(getActivity(), "企业不能全部删除，必须保留一家企业");
        } else {
            deleteCompany(getParentId(0), v);
        }
    }

    /*
    * 添加企业
    * */
    private void addCompany() {
        //第一家企业
        if (!isCompany1Show) {
            if (!requiredInput())
                return;
            isCompany1Show = true;
            layout_company_1.setVisibility(View.VISIBLE);
            companyList1 = (ArrayList) Constants.COMPANYLIST.clone();
            //下拉框
            if (isCompany2Show) {
                sp_some_company_2.setEnabled(false);
                companyList1.remove(sp_some_company_2.getSelectedItem());
            }
            if (isCompany3Show) {
                sp_some_company_3.setEnabled(false);
                companyList1.remove(sp_some_company_3.getSelectedItem());
            }
            initSpinner(sp_some_company_1, companyList1);
            sp_some_company_1.setSelection(0);
            sp_some_company_1.setEnabled(true);
        } else if (!isCompany2Show) {
            //第二家企业
            if (!requiredInput())
                return;
            isCompany2Show = true;
            if (layout_company_2 != null) {
                layout_company_2.setVisibility(View.VISIBLE);
            } else {
                ((ViewStub) mView.findViewById(R.id.stub_company_2)).inflate();
                initControlView(true);
                initGridViewData(true);
            }
            companyList2 = (ArrayList) Constants.COMPANYLIST.clone();
            //下拉框
            if (isCompany1Show) {
                sp_some_company_1.setEnabled(false);
                companyList2.remove(sp_some_company_1.getSelectedItem());
            }
            if (isCompany3Show) {
                sp_some_company_3.setEnabled(false);
                companyList2.remove(sp_some_company_3.getSelectedItem());
            }
            initSpinner(sp_some_company_2, companyList2);
            sp_some_company_2.setSelection(0);
            sp_some_company_2.setEnabled(true);
        } else {
            //添加第三家企业
            if (!isCompany3Show) {
                if (!requiredInput())
                    return;
                isCompany3Show = true;
                if (layout_company_3 != null) {
                    layout_company_3.setVisibility(View.VISIBLE);
                } else {
                    ((ViewStub) mView.findViewById(R.id.stub_company_3)).inflate();
                    layout_company_3 = (LinearLayout) mView.findViewById(R.id.layout_company_3);
                    initControlView(false);
                    initGridViewData(false);
                }
                companyList3 = (ArrayList) Constants.COMPANYLIST.clone();
                //下拉框
                if (isCompany1Show) {
                    sp_some_company_1.setEnabled(false);
                    companyList3.remove(sp_some_company_1.getSelectedItem());
                }
                if (isCompany2Show) {
                    sp_some_company_2.setEnabled(false);
                    companyList3.remove(sp_some_company_2.getSelectedItem());
                }
                initSpinner(sp_some_company_3, companyList3);
                sp_some_company_3.setSelection(0);
                sp_some_company_3.setEnabled(true);
            }
        }
    }

    /*
   * 添加企业
   * */
    private void addCompany(int showIdx) {
        switch (showIdx) {
            case 0:
                isCompany1Show = true;
                layout_company_1.setVisibility(View.VISIBLE);
                sp_some_company_1.setEnabled(false);
                sp_some_company_1.setSelection(0);
                break;
            case 1:
                isCompany2Show = true;
                ((ViewStub) mView.findViewById(R.id.stub_company_2)).inflate();
                layout_company_2 = (LinearLayout) mView.findViewById(R.id.layout_company_2);
                initControlView(true);
                initGridViewData(true);
                //下拉框
                initSpinner(sp_some_company_2, Constants.COMPANYLIST);
                sp_some_company_2.setSelection(1);
                sp_some_company_2.setEnabled(false);
                break;
            case 2:
                isCompany3Show = true;
                ((ViewStub) mView.findViewById(R.id.stub_company_3)).inflate();
                layout_company_3 = (LinearLayout) mView.findViewById(R.id.layout_company_3);
                initControlView(false);
                initGridViewData(false);
                sp_some_company_3.setEnabled(false);
                initSpinner(sp_some_company_3, Constants.COMPANYLIST);
                sp_some_company_3.setSelection(2);
                break;
            default:
                break;
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
            layout_company_2 = (LinearLayout) mView.findViewById(R.id.layout_company_2);
            img_company_delete_2 = (ImageView) mView.findViewById(R.id.img_company_delete_2);
            img_company_delete_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除企业
                    if (!isCompany1Show && !isCompany3Show) {
                        JDToast.showLongText(getActivity(), "企业不能全部删除，必须保留一家企业");
                    } else {
                        deleteCompany(getParentId(1), v);
                    }
                }
            });
            sp_company_type_2 = (Spinner) mView.findViewById(R.id.sp_company_type_2);
            //收入证明
            gv_income_2 = (GridView) mView.findViewById(R.id.gv_income_2);
            //经营性单据
            gv_bill_2 = (GridView) mView.findViewById(R.id.gv_bill_2);
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
            img_company_delete_3 = (ImageView) mView.findViewById(R.id.img_company_delete_3);
            img_company_delete_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除企业
                    if (!isCompany1Show && !isCompany2Show) {
                        JDToast.showLongText(getActivity(), "企业不能全部删除，必须保留一家企业");
                    } else {
                        deleteCompany(getParentId(2), v);
                    }
                }
            });
            sp_company_type_3 = (Spinner) mView.findViewById(R.id.sp_company_type_3);
            gv_income_3 = (GridView) mView.findViewById(R.id.gv_income_3);
            //经营性单据
            gv_bill_3 = (GridView) mView.findViewById(R.id.gv_bill_3);
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
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(1);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.SY.getValue());
                mSYList_2.add(imgInfo);
                mSYAdapter_2 = new ImgsGridViewAdapter(getActivity(), mSYList_2);
                mSYAdapter_2.setOnClickImgListener(this);
                gv_income_2.setAdapter(mSYAdapter_2);
            }

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(1);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.JY.getValue());
                mJYList_2.add(imgInfo);
                mJYAdapter_2 = new ImgsGridViewAdapter(getActivity(), mJYList_2);
                mJYAdapter_2.setOnClickImgListener(this);
                gv_bill_2.setAdapter(mJYAdapter_2);
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
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.SY.getValue());
                mSYList_3.add(imgInfo);
                mSYAdapter_3 = new ImgsGridViewAdapter(getActivity(), mSYList_3);
                mSYAdapter_3.setOnClickImgListener(this);
                gv_income_3.setAdapter(mSYAdapter_3);
            }

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.JY.getValue());
                mJYList_3.add(imgInfo);
                mJYAdapter_3 = new ImgsGridViewAdapter(getActivity(), mJYList_3);
                mJYAdapter_3.setOnClickImgListener(this);
                gv_bill_3.setAdapter(mJYAdapter_3);
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
        //getCompanyInfo();
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
        mSYList_1 = new ArrayList<UploadFileInfo>();
        mJYList_1 = new ArrayList<UploadFileInfo>();
        //第二家企业
        mBLList_2 = new ArrayList<UploadFileInfo>();
        mTRList_2 = new ArrayList<UploadFileInfo>();
        mCCList_2 = new ArrayList<UploadFileInfo>();
        mOCList_2 = new ArrayList<UploadFileInfo>();
        mHCList_2 = new ArrayList<UploadFileInfo>();
        mLCList_2 = new ArrayList<UploadFileInfo>();
        mSYList_2 = new ArrayList<UploadFileInfo>();
        mJYList_2 = new ArrayList<UploadFileInfo>();
        //第三家企业
        mBLList_3 = new ArrayList<UploadFileInfo>();
        mTRList_3 = new ArrayList<UploadFileInfo>();
        mCCList_3 = new ArrayList<UploadFileInfo>();
        mOCList_3 = new ArrayList<UploadFileInfo>();
        mHCList_3 = new ArrayList<UploadFileInfo>();
        mLCList_3 = new ArrayList<UploadFileInfo>();
        mSYList_3 = new ArrayList<UploadFileInfo>();
        mJYList_3 = new ArrayList<UploadFileInfo>();
        //备注
        remarkUploadImgInfoList = new ArrayList<UploadFileInfo>();
        soundInfoList = new ArrayList<UploadFileInfo>();
    }

    //初始化数据源控件
    private void initOneView() {

        //第一家企业
        {
            //收入证明
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.SY.getValue());
            mSYList_1.add(imgInfo);
            mSYAdapter_1 = new ImgsGridViewAdapter(getActivity(), mSYList_1);
            mSYAdapter_1.setOnClickImgListener(this);
            gv_income_1.setAdapter(mSYAdapter_1);
        }
        {
            //经营性单据
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.JY.getValue());
            mJYList_1.add(imgInfo);
            mJYAdapter_1 = new ImgsGridViewAdapter(getActivity(), mJYList_1);
            mJYAdapter_1.setOnClickImgListener(this);
            gv_bill_1.setAdapter(mJYAdapter_1);
        }
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

        {
            //录音
            UploadFileInfo soundInfo = new UploadFileInfo();
            soundInfo.setIsDefault(true);
            soundInfo.setFileType(UploadFileType.SOUND.getValue());
            soundInfo.setiServer(false);
            soundInfoList.add(soundInfo);
            soundAdapter = new SoundGridViewAdapter(getActivity(), soundInfoList, Constants.BUSINESS_INFO);
            gv_sound.setAdapter(soundAdapter);
        }

        {
            //备注
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIsDefault(true);
            imgInfo.setFileType(UploadFileType.REMARK.getValue());
            imgInfo.setiServer(false);
            remarkUploadImgInfoList.add(imgInfo);
            remarkImgsAdapter = new ImgsGridViewAdapter(getActivity(), remarkUploadImgInfoList);
            remarkImgsAdapter.setOnClickImgListener(this);
            gv_mark.setAdapter(remarkImgsAdapter);
        }
    }

    /*
    * 设置企业是否有效
    * */
    private boolean isCompanyVaild(CompanyInfo companyInfo) {
        //
        if (!JDAppUtil.isEmpty(companyInfo.getOrganizationType()) && !JDAppUtil.isEmpty(companyInfo.getBusinessPremises())
                || (companyInfo.getFileList() != null && companyInfo.getFileList().size() > 0)) {
            companyInfo.setIsValid(true);
            return true;
        }
        return false;
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
                            List<CompanyInfo> mCompanyInfoList = JSON.parseArray(o.toString(), CompanyInfo.class);
                            if (mCompanyInfoList != null) {
                                boolean isHasCompany = false;
                                for (int i = 0; i < mCompanyInfoList.size(); i++) {
                                    CompanyInfo companyInfo = mCompanyInfoList.get(i);
                                    if (companyInfo == null)
                                        continue;
                                    mParentId[i] = companyInfo.getId();
                                    if (isCompanyVaild(companyInfo)) {
                                        isHasCompany = true;
                                        int orgType = 0;
                                        if (!JDAppUtil.isEmpty(companyInfo.getOrganizationType()))
                                            orgType = Integer.parseInt(companyInfo.getOrganizationType()) - 1;
                                        int busPremises = 0;
                                        if (!JDAppUtil.isEmpty(companyInfo.getBusinessPremises()))
                                            busPremises = Integer.parseInt(companyInfo.getBusinessPremises()) - 1;
                                        //组织类型
                                        switch (i) {
                                            case 0:
                                                addCompany(0);
                                                sp_company_type_1.setSelection(orgType);
                                                sp_business_premises_1.setSelection(busPremises);
                                                if (!JDAppUtil.isEmpty(companyInfo.getRemark()))
                                                    et_remark.setText(companyInfo.getRemark());
                                                initServerFile(companyInfo.getFileList(), i);
                                                break;
                                            case 1:
                                                addCompany(1);
                                                sp_company_type_2.setSelection(orgType);
                                                sp_business_premises_2.setSelection(busPremises);
                                                if (!JDAppUtil.isEmpty(companyInfo.getRemark()))
                                                    et_remark.setText(companyInfo.getRemark());
                                                //下载图片
                                                initServerFile(companyInfo.getFileList(), i);
                                                break;
                                            case 2:
                                                addCompany(2);
                                                sp_company_type_3.setSelection(orgType);
                                                sp_business_premises_3.setSelection(busPremises);
                                                if (!JDAppUtil.isEmpty(companyInfo.getRemark()))
                                                    et_remark.setText(companyInfo.getRemark());
                                                //下载图片
                                                initServerFile(companyInfo.getFileList(), i);
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }
                                if (!isHasCompany) {
                                    isCompany1Show = true;
                                    layout_company_1.setVisibility(View.VISIBLE);
                                }
                                //设置录音parentId,parentTableName
                                soundAdapter.setParentId(mParentId[0]);
                            }
                        } catch (Exception e) {
                            String msg = e.getMessage();
                        }
                    }
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
   *获取服务器文件信息
    */
    private void initServerFile(List<UploadFileInfo> fileInfoList, int idxTag) {
        if (fileInfoList != null && fileInfoList.size() > 0) {
            for (UploadFileInfo uploadFileInfo : fileInfoList) {
                uploadFileInfo.setiServer(true);
                uploadFileInfo.setIdxTag(idxTag);
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
                if (uploadFileInfo.getFileExt().equals("amr")) {
                    uploadFileInfo.setFileType(UploadFileType.SOUND.getValue());
                }
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

    /*
    * 删除企业
    * */
    private void deleteCompany(final String parentId, final View v) {
        JDAlertDialog.showAlertDialog(getActivity(), "确认删除该企业吗？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HttpClientUtil.put(HttpRequestURL.deleteCompany + parentId, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
                    @Override
                    public void onSuccess(Object o) {
                        switch (v.getId()) {
                            case R.id.img_company_delete_1:
                                isCompany1Show = false;
                                JDAppUtil.addHiddenAction(layout_company_1);
                                initCompanyData(0, sp_some_company_1.getSelectedItem().toString());
                                break;
                            case R.id.img_company_delete_2:
                                isCompany2Show = false;
                                JDAppUtil.addHiddenAction(layout_company_2);
                                initCompanyData(1, sp_some_company_2.getSelectedItem().toString());
                                break;
                            case R.id.img_company_delete_3:
                                isCompany3Show = false;
                                JDAppUtil.addHiddenAction(layout_company_3);
                                initCompanyData(2, sp_some_company_3.getSelectedItem().toString());
                                break;
                            default:
                                break;
                        }
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


    /**
     * 补齐下拉框
     */
    private void initCompanyData(int idx, String itemName) {
        switch (idx) {
            case 0:
                if (isCompany2Show) {
                    companyList2.add(itemName);
                    String selItemd = sp_some_company_2.getSelectedItem().toString();
                    sortList(companyList2);
                    initSpinner(sp_some_company_2, companyList2);
                    if (!sp_some_company_2.isEnabled())
                        sp_some_company_2.setSelection(companyList2.indexOf(selItemd));
                }
                if (isCompany3Show) {
                    companyList3.add(itemName);
                    String selItemd = sp_some_company_3.getSelectedItem().toString();
                    sortList(companyList3);
                    initSpinner(sp_some_company_3, companyList3);
                    if (!sp_some_company_3.isEnabled())
                        sp_some_company_3.setSelection(companyList3.indexOf(selItemd));
                }
                removeImgList(mSYList_1, mSYAdapter_1);
                removeImgList(mJYList_1, mJYAdapter_1);
                removeImgList(mBLList_1, mBLAdapter_1);
                removeImgList(mTRList_1, mTRAdapter_1);
                removeImgList(mCCList_1, mCCAdapter_1);
                removeImgList(mOCList_1, mOCAdapter_1);
                removeImgList(mHCList_1, mHCAdapter_1);
                removeImgList(mLCList_1, mLCAdapter_1);
                break;
            case 1:
                if (isCompany1Show) {
                    companyList1.add(itemName);
                    String selItemd = sp_some_company_1.getSelectedItem().toString();
                    sortList(companyList1);
                    initSpinner(sp_some_company_1, companyList1);
                    if (!sp_some_company_1.isEnabled())
                        sp_some_company_1.setSelection(companyList1.indexOf(selItemd));
                }
                if (isCompany3Show) {
                    companyList3.add(itemName);
                    String selItemd = sp_some_company_3.getSelectedItem().toString();
                    sortList(companyList3);
                    initSpinner(sp_some_company_3, companyList3);
                    if (!sp_some_company_3.isEnabled())
                        sp_some_company_3.setSelection(companyList3.indexOf(selItemd));
                }
                removeImgList(mSYList_2, mSYAdapter_2);
                removeImgList(mJYList_2, mJYAdapter_2);
                removeImgList(mBLList_2, mBLAdapter_2);
                removeImgList(mTRList_2, mTRAdapter_2);
                removeImgList(mCCList_2, mCCAdapter_2);
                removeImgList(mOCList_2, mOCAdapter_2);
                removeImgList(mHCList_2, mHCAdapter_2);
                removeImgList(mLCList_2, mLCAdapter_2);
                break;
            case 2:
                if (isCompany1Show) {
                    companyList1.add(itemName);
                    String selItemd = sp_some_company_1.getSelectedItem().toString();
                    sortList(companyList1);
                    initSpinner(sp_some_company_1, companyList1);
                    if (!sp_some_company_1.isEnabled())
                        sp_some_company_1.setSelection(companyList1.indexOf(selItemd));
                }
                if (isCompany2Show) {
                    companyList2.add(itemName);
                    String selItemd = sp_some_company_2.getSelectedItem().toString();
                    sortList(companyList2);
                    initSpinner(sp_some_company_2, companyList2);
                    if (!sp_some_company_2.isEnabled())
                        sp_some_company_2.setSelection(companyList2.indexOf(selItemd));
                }
                removeImgList(mSYList_3, mSYAdapter_3);
                removeImgList(mJYList_3, mJYAdapter_3);
                removeImgList(mBLList_3, mBLAdapter_3);
                removeImgList(mTRList_3, mTRAdapter_3);
                removeImgList(mCCList_3, mCCAdapter_3);
                removeImgList(mOCList_3, mOCAdapter_3);
                removeImgList(mHCList_3, mHCAdapter_3);
                removeImgList(mLCList_3, mLCAdapter_3);
                break;
            default:
                break;
        }
    }

    /**
     * 对集合排序
     *
     * @param arrs
     */
    private void sortList(List<String> arrs) {
        Collections.sort(arrs, Collator.getInstance(java.util.Locale.CHINA));
    }

    /**
     * 删除时把图片列表中的除默认图片的其它图片 删除
     */
    private void removeImgList(List<UploadFileInfo> uploadFileInfos, ImgsGridViewAdapter imgAdapter) {
        if (uploadFileInfos != null) {
            int size = uploadFileInfos.size();
            if (size == 1 && uploadFileInfos.get(0).isDefault()) {
                return;
            }
            UploadFileInfo uploadFileInfo = null;
            for (int i = size - 1; i >= 0; i--) {
                uploadFileInfo = uploadFileInfos.get(i);
                if (!uploadFileInfo.isDefault())
                    uploadFileInfos.remove(uploadFileInfo);
            }
            if (imgAdapter != null)
                imgAdapter.refreshData();
        }
    }

    @Override
    public void onImgClick(List<UploadFileInfo> uploadImgInfoList, int idx) {
        if (idx < uploadImgInfoList.size()) {
            UploadFileInfo imgInfo = uploadImgInfoList.get(idx);
            fileType = UploadFileType.valueOf(imgInfo.getFileType());
            selCompanyIdxTag = imgInfo.getIdxTag();
            if (imgInfo.isDefault()) {
                if (!JDAppUtil.getIsAuthSuccess()) {
                    JDToast.showLongText(getActivity(), "未实名认证，不能上传图片");
                    return;
                }
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
                                new Thread(new BaseFragment.ImgRunable(cursor.getString(colunm_index), Constants.BUSINESS_INFO, fileType, selCompanyIdxTag, new UpdateUIHandler())).start();
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
                    new Thread(new BaseFragment.ImgRunable(Constants.TEMPPATH, Constants.BUSINESS_INFO, fileType, selCompanyIdxTag, new UpdateUIHandler())).start();
                    break;
                default:
                    break;
            }
        }
    }

    //删除图片
    @Override
    public void onDeletePhoto(UploadFileInfo uploadFileInfo) {
        //刷新数据源
        refreshData(uploadFileInfo);
    }

    //删除下拉框控件状态
    @Override
    public void onRefreshSpinner(UploadFileInfo fileInfo) {
        super.onRefreshSpinner(fileInfo);
        setSpinnerEnabled(fileInfo.getIdxTag());
    }

    /*
         * 刷新GridView数据与上传文件
        */
    private class UpdateUIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {
                UploadFileInfo fileInfo = (UploadFileInfo) msg.obj;
                addGridViewData(fileInfo);
                uploadImg(fileInfo);
            }
        }
    }

    /*
    * 往GridView里添加图片
    * */
    private void addGridViewData(UploadFileInfo imgInfo) {
        UploadFileType fType = UploadFileType.valueOf(imgInfo.getFileType());
        if (fType == UploadFileType.SY) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mSYList_1.add(0, imgInfo);
                    break;
                case 1:
                    mSYList_2.add(0, imgInfo);
                    break;
                case 2:
                    mSYList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.JY) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mJYList_1.add(0, imgInfo);
                    break;
                case 1:
                    mJYList_2.add(0, imgInfo);
                    break;
                case 2:
                    mJYList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.YY) {
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
                    mOCList_3.add(0, imgInfo);
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
        } else if (fType == UploadFileType.REMARK) {
            remarkUploadImgInfoList.add(0, imgInfo);
        } else if (fType == UploadFileType.SOUND) {
            soundInfoList.add(0, imgInfo);
        } else {
        }
        refreshData(imgInfo);
    }

    /*
    * 获取ParentId
    * */
    private String getParentId(int idxTag) {
        String parentId = "0";
        switch (idxTag) {
            case 0:
                parentId = mParentId[Constants.COMPANYLIST.indexOf(sp_some_company_1.getSelectedItem().toString())];
                break;
            case 1:
                parentId = mParentId[Constants.COMPANYLIST.indexOf(sp_some_company_2.getSelectedItem().toString())];
                break;
            case 2:
                parentId = mParentId[Constants.COMPANYLIST.indexOf(sp_some_company_3.getSelectedItem().toString())];
                break;
        }
        return parentId;
    }

    //上传图片
    private void uploadImg(final UploadFileInfo fileInfo) {
        try {
            HttpClientUtil.post(getActivity(), HttpRequestURL.uploadImgUrl, getUploadEntity(fileInfo, getParentId(fileInfo.getIdxTag())), new JDHttpResponseHandler(getActivity(), new ResponseHandler<UploadFileInfo>() {
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
        if (fType == UploadFileType.SY) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mSYAdapter_1.refreshData();
                    break;
                case 1:
                    if (mSYAdapter_2 != null)
                        mSYAdapter_2.refreshData();
                    break;
                case 2:
                    if (mSYAdapter_3 != null)
                        mSYAdapter_3.refreshData();
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.JY) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mJYAdapter_1.refreshData();
                    break;
                case 1:
                    if (mJYAdapter_2 != null)
                        mJYAdapter_2.refreshData();
                    break;
                case 2:
                    if (mJYAdapter_3 != null)
                        mJYAdapter_3.refreshData();
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.YY) {
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
        } else if (fType == UploadFileType.REMARK) {
            remarkImgsAdapter.refreshData();
        } else if (fType == UploadFileType.SOUND) {
            soundAdapter.refreshData();
        } else {

        }
        setSpinnerEnabled(fileInfo.getIdxTag());
    }

    /*
    * 设置下拉框是否可用
    * */
    private void setSpinnerEnabled(int fileIdxTag) {
        switch (fileIdxTag) {
            case 0:
                sp_some_company_1.setEnabled(!(isUploadSuccess(mSYList_1) || isUploadSuccess(mJYList_1) || isUploadSuccess(mBLList_1) || isUploadSuccess(mTRList_1) || isUploadSuccess(mCCList_1) || isUploadSuccess(mOCList_1) || isUploadSuccess(mHCList_1) || isUploadSuccess(mLCList_1)));
                break;
            case 1:
                sp_some_company_2.setEnabled(!(isUploadSuccess(mSYList_2) || isUploadSuccess(mJYList_2) || isUploadSuccess(mBLList_2) || isUploadSuccess(mTRList_2) || isUploadSuccess(mCCList_2) || isUploadSuccess(mOCList_2) || isUploadSuccess(mHCList_2) || isUploadSuccess(mLCList_2)));
                break;
            case 2:
                sp_some_company_3.setEnabled(!(isUploadSuccess(mSYList_3) || isUploadSuccess(mJYList_3) || isUploadSuccess(mBLList_3) || isUploadSuccess(mTRList_3) || isUploadSuccess(mCCList_3) || isUploadSuccess(mOCList_3) || isUploadSuccess(mHCList_3) || isUploadSuccess(mLCList_3)));
                break;
        }
    }

    /*
    * 必须输入判断
    * */
    private boolean requiredInput() {
        if (isCompany1Show) {
            /*if (!isUploadSuccess(mTRList_1)) {
                JDToast.showLongText(getActivity(), "请上传税务登记图片");
                return false;
            }
            if (!isUploadSuccess(mCCList_1)) {
                JDToast.showLongText(getActivity(), "请上传企业代码图片");
                return false;
            }
            if (!isUploadSuccess(mOCList_1)) {
                JDToast.showLongText(getActivity(), "请上传其它证件图片");
                return false;
            }
            if (!isUploadSuccess(mHCList_1)) {
                JDToast.showLongText(getActivity(), "请上传房产证合同图片");
                return false;
            }
            if (!isUploadSuccess(mLCList_1)) {
                JDToast.showLongText(getActivity(), "请上传土地证图片");
                return false;
            }*/

            if (!isUploadSuccess(mSYList_1) && !isUploadSuccess(mJYList_1) && !isUploadSuccess(mBLList_1) && !isUploadSuccess(mTRList_1) && !isUploadSuccess(mCCList_1)
                    && !isUploadSuccess(mOCList_1) && !isUploadSuccess(mHCList_1) &&
                    !isUploadSuccess(mLCList_1)) {
                JDToast.showLongText(getActivity(), "请上传图片");
                return false;
            }
        }
        if (isCompany2Show) {
            /*if (!isUploadSuccess(mTRList_2)) {
                JDToast.showLongText(getActivity(), "请上传税务登记图片");
                return false;
            }
            if (!isUploadSuccess(mCCList_2)) {
                JDToast.showLongText(getActivity(), "请上传企业代码图片");
                return false;
            }
            if (!isUploadSuccess(mOCList_2)) {
                JDToast.showLongText(getActivity(), "请上传其它证件图片");
                return false;
            }
            if (!isUploadSuccess(mHCList_2)) {
                JDToast.showLongText(getActivity(), "请上传房产证合同图片");
                return false;
            }
            if (!isUploadSuccess(mLCList_2)) {
                JDToast.showLongText(getActivity(), "请上传土地证图片");
                return false;
            }*/

            if (!isUploadSuccess(mSYList_2) && !isUploadSuccess(mJYList_2) && !isUploadSuccess(mBLList_2) && !isUploadSuccess(mTRList_2) && !isUploadSuccess(mCCList_2)
                    && !isUploadSuccess(mOCList_2) && !isUploadSuccess(mHCList_2) &&
                    !isUploadSuccess(mLCList_2)) {
                JDToast.showLongText(getActivity(), "请上传图片");
                return false;
            }
        }

        if (isCompany3Show) {
           /* if (!isUploadSuccess(mTRList_3)) {
                JDToast.showLongText(getActivity(), "请上传税务登记图片");
                return false;
            }
            if (!isUploadSuccess(mCCList_3)) {
                JDToast.showLongText(getActivity(), "请上传企业代码图片");
                return false;
            }
            if (!isUploadSuccess(mOCList_3)) {
                JDToast.showLongText(getActivity(), "请上传其它证件图片");
                return false;
            }
            if (!isUploadSuccess(mHCList_3)) {
                JDToast.showLongText(getActivity(), "请上传房产证合同图片");
                return false;
            }
            if (!isUploadSuccess(mLCList_3)) {
                JDToast.showLongText(getActivity(), "请上传土地证图片");
                return false;
            }*/

            if (!isUploadSuccess(mSYList_3) && !isUploadSuccess(mJYList_3) && !isUploadSuccess(mBLList_3) && !isUploadSuccess(mTRList_3) && !isUploadSuccess(mCCList_3)
                    && !isUploadSuccess(mOCList_3) && !isUploadSuccess(mHCList_3) &&
                    !isUploadSuccess(mLCList_3)) {
                JDToast.showLongText(getActivity(), "请上传图片");
                return false;
            }
        }
        return true;
    }

    /*
    * 设置企业索引值
    * */
    private int getSelectedIdx(String code) {
        int selIdx = 0;
        if (!JDAppUtil.isEmpty(code)) {
            if (code.equals("a")) {
                selIdx = 0;
            } else if (code.equals("b")) {
                selIdx = 1;
            } else if (code.equals("c")) {
                selIdx = 2;
            } else {

            }
        }
        return selIdx;
    }

    /*
    * 获取企业对应编码
    * */
    private String getSelectedCompanyCode(String selectedItem) {
        if (selectedItem.equals("企业a"))
            return "a";
        else if (selectedItem.equals("企业b"))
            return "b";
        else if (selectedItem.equals("企业c"))
            return "c";
        return "";
    }

    /*
    * 保存数据
    * */
    public void saveDataSubmit() {
        //组织类型 1有限责任公司  2个体商户 3其他
        //经营场所 1自由 2租赁 3其他
        List<CompanyInfo> companyInfoList = new ArrayList<CompanyInfo>();
        if (!JDAppUtil.getIsAuthSuccess()) {
            JDToast.showLongText(getActivity(), "未实名认证，不能保存数据");
            return;
        }
        //必填验证
        if (!requiredInput())
            return;
        CompanyInfo companyInfo = null;
        if (isCompany1Show) {
            companyInfo = new CompanyInfo();
            companyInfo.setOrganizationType(sp_company_type_1.getSelectedItemPosition() + 1 + "");
            companyInfo.setBusinessPremises(sp_business_premises_1.getSelectedItemPosition() + 1 + "");
            companyInfo.setCode(getSelectedCompanyCode(sp_some_company_1.getSelectedItem().toString()));
            companyInfo.setId(mParentId[Constants.COMPANYLIST.indexOf(sp_some_company_1.getSelectedItem().toString())]);
            //companyInfo.setId(mParentId[0]);
            companyInfo.setRemark(this.et_remark.getText().toString());
            companyInfoList.add(companyInfo);
        }
        if (isCompany2Show) {
            companyInfo = new CompanyInfo();
            companyInfo.setOrganizationType(sp_company_type_2.getSelectedItemPosition() + 1 + "");
            companyInfo.setBusinessPremises(sp_business_premises_2.getSelectedItemPosition() + 1 + "");
            companyInfo.setCode(getSelectedCompanyCode(sp_some_company_2.getSelectedItem().toString()));
            companyInfo.setId(mParentId[Constants.COMPANYLIST.indexOf(sp_some_company_2.getSelectedItem().toString())]);
            //companyInfo.setId(mParentId[1]);
            companyInfoList.add(companyInfo);
        }
        if (isCompany3Show) {
            companyInfo = new CompanyInfo();
            companyInfo.setOrganizationType(sp_company_type_3.getSelectedItemPosition() + 1 + "");
            companyInfo.setBusinessPremises(sp_business_premises_3.getSelectedItemPosition() + 1 + "");
            companyInfo.setCode(getSelectedCompanyCode(sp_some_company_3.getSelectedItem().toString()));
            companyInfo.setId(mParentId[Constants.COMPANYLIST.indexOf(sp_some_company_3.getSelectedItem().toString())]);
            //companyInfo.setId(mParentId[2]);
            companyInfoList.add(companyInfo);
        }
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        String ss = JSON.toJSONString(companyInfoList);
        formparams.add(new BasicNameValuePair("businessInfoListJson", JSON.toJSONString(companyInfoList)));
        HttpClientUtil.put(getActivity(), HttpRequestURL.updateBusinessInfoUrl, formparams, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                JDToast.showLongText(getActivity(), "企业信息保存成功");
                if (getActivity() instanceof InvestigateDetailActivity)
                    ((InvestigateDetailActivity) getActivity()).gotoNext();
            }
        }));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible() && isVisibleToUser && !hasLoadedOnce) {
            hasLoadedOnce = true;
            getCompanyInfo();
        }
    }

    public static CompanyInfoFragment newInstance() {
        CompanyInfoFragment companyInfoFragment = new CompanyInfoFragment();
        return companyInfoFragment;
    }
}
