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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.ImgsGridViewAdapter;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.AssetInfo;
import com.chaqianma.jd.model.CarInfo;
import com.chaqianma.jd.model.CompanyInfo;
import com.chaqianma.jd.model.HouseInfo;
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
public class PersonalAssetsFragment extends BaseFragment {

    @InjectView(R.id.linear_container)
    LinearLayout linear_container;
    @InjectView(R.id.img_company_asset_add)
    ImageView img_company_asset_add;
    @InjectView(R.id.gv_income_1)
    GridView gv_income_1;
    @InjectView(R.id.gv_bill_1)
    GridView gv_bill_1;
    @InjectView(R.id.sp_company_1)
    Spinner sp_company_1;
    @InjectView(R.id.img_car_add)
    ImageView img_car_add;
    @InjectView(R.id.gv_license_plate_1)
    GridView gv_license_plate_1;
    @InjectView(R.id.gv_driving_license_1)
    GridView gv_driving_license_1;
    @InjectView(R.id.sp_car_1)
    Spinner sp_car_1;
    @InjectView(R.id.img_house_add)
    ImageView img_house_add;
    @InjectView(R.id.gv_house_contract_1)
    GridView gv_house_contract_1;
    @InjectView(R.id.gv_land_1)
    GridView gv_land_1;
    @InjectView(R.id.sp_house_1)
    Spinner sp_house_1;
    @InjectView(R.id.et_remark)
    EditText et_remark;

    GridView gv_income_2;
    GridView gv_bill_2;
    Spinner sp_company_2;

    GridView gv_license_plate_2;
    GridView gv_driving_license_2;
    Spinner sp_car_2;

    GridView gv_house_contract_2;
    GridView gv_land_2;
    Spinner sp_house_2;

    GridView gv_income_3;
    GridView gv_bill_3;
    Spinner sp_company_3;

    GridView gv_license_plate_3;
    GridView gv_driving_license_3;
    Spinner sp_car_3;

    GridView gv_house_contract_3;
    GridView gv_land_3;
    Spinner sp_house_3;

    //root view
    private View mView = null;
    //收入证明
    private ImgsGridViewAdapter mSYAdapter_1 = null;
    private List<UploadFileInfo> mSYList_1 = null;
    //经营性单据
    private ImgsGridViewAdapter mJYAdapter_1 = null;
    private List<UploadFileInfo> mJYList_1 = null;
    //车牌车型
    private ImgsGridViewAdapter mCPAdpter_1 = null;
    private List<UploadFileInfo> mCPList_1 = null;
    //行驶证
    private ImgsGridViewAdapter mXSAdpter_1 = null;
    private List<UploadFileInfo> mXSList_1 = null;
    //房产证/合同
    private ImgsGridViewAdapter mFCAdpter_1 = null;
    private List<UploadFileInfo> mFCList_1 = null;
    //土地证
    private ImgsGridViewAdapter mTDAdpter_1 = null;
    private List<UploadFileInfo> mTDList_1 = null;


    //收入证明
    private ImgsGridViewAdapter mSYAdapter_2 = null;
    private List<UploadFileInfo> mSYList_2 = null;
    //经营性单据
    private ImgsGridViewAdapter mJYAdapter_2 = null;
    private List<UploadFileInfo> mJYList_2 = null;
    //车牌车型
    private ImgsGridViewAdapter mCPAdpter_2 = null;
    private List<UploadFileInfo> mCPList_2 = null;
    //行驶证
    private ImgsGridViewAdapter mXSAdpter_2 = null;
    private List<UploadFileInfo> mXSList_2 = null;
    //房产证/合同
    private ImgsGridViewAdapter mFCAdpter_2 = null;
    private List<UploadFileInfo> mFCList_2 = null;
    //土地证
    private ImgsGridViewAdapter mTDAdpter_2 = null;
    private List<UploadFileInfo> mTDList_2 = null;


    //收入证明
    private ImgsGridViewAdapter mSYAdapter_3 = null;
    private List<UploadFileInfo> mSYList_3 = null;
    //经营性单据
    private ImgsGridViewAdapter mJYAdapter_3 = null;
    private List<UploadFileInfo> mJYList_3 = null;
    //车牌车型
    private ImgsGridViewAdapter mCPAdpter_3 = null;
    private List<UploadFileInfo> mCPList_3 = null;
    //行驶证
    private ImgsGridViewAdapter mXSAdpter_3 = null;
    private List<UploadFileInfo> mXSList_3 = null;
    //房产证/合同
    private ImgsGridViewAdapter mFCAdpter_3 = null;
    private List<UploadFileInfo> mFCList_3 = null;
    //土地证
    private ImgsGridViewAdapter mTDAdpter_3 = null;
    private List<UploadFileInfo> mTDList_3 = null;

    private boolean isIncome2Show = false;
    private boolean isIncome3Show = false;

    private boolean isCar2Show = false;
    private boolean isCar3Show = false;

    private boolean isHouse2Show = false;
    private boolean isHouse3Show = false;

    private String mBorrowRequestId = null;
    //用于标识选择的是哪个类型
    private int selIdxTag = -1;
    //收入
    private String[] mCompanyId = new String[3];
    //车
    private String[] mCardId = new String[3];
    //房
    private String[] mHouseId = new String[3];
    //图片标签
    private UploadFileType fileType = UploadFileType.SY;

    /*
    * 添加企业营收
    * */
    private void addCompanyIncome() {
        if (!isIncome2Show) {
            isIncome2Show = true;
            View view = ((ViewStub) mView.findViewById(R.id.stub_company_2)).inflate();
            initCompanyView(true);
            //sp_company_1.setEnabled(false);
            //下拉框
            //companyList.remove(sp_some_company_2.getSelectedItemPosition());
            //initSpinner(sp_some_company_2, companyList);
            JDAppUtil.addShowAction(view);
        } else {
            if (!isIncome3Show) {
                isIncome3Show = true;
                View view = ((ViewStub) mView.findViewById(R.id.stub_company_3)).inflate();
                initCompanyView(false);
                JDAppUtil.addShowAction(view);
            }
        }
    }

    /*
    * 添加车
    * */
    private void addCar() {
        if (!isCar2Show) {
            isCar2Show = true;
            View view = ((ViewStub) mView.findViewById(R.id.stub_car_2)).inflate();
            JDAppUtil.addShowAction(view);
            initCarView(true);
        } else {
            if (!isCar3Show) {
                isCar3Show = true;
                View view = ((ViewStub) mView.findViewById(R.id.stub_car_3)).inflate();
                JDAppUtil.addShowAction(view);
                initCarView(false);
            }
        }
    }

    /*
    * 房
    * */
    private void addHouse() {
        if (!isHouse2Show) {
            isHouse2Show = true;
            View view = ((ViewStub) mView.findViewById(R.id.stub_house_2)).inflate();
            JDAppUtil.addShowAction(view);
            initHouseView(true);
        } else {
            if (!isHouse3Show) {
                isHouse3Show = true;
                View view = ((ViewStub) mView.findViewById(R.id.stub_house_3)).inflate();
                JDAppUtil.addShowAction(view);
                initHouseView(false);
            }
        }
    }

    @OnClick(R.id.img_company_asset_add)
    void addComanyAssetClick() {
        addCompanyIncome();
    }

    @OnClick(R.id.img_car_add)
    void addCarClick() {
        addCar();
    }

    @OnClick(R.id.img_house_add)
    void addHouseClick() {
        addHouse();
    }


    /*
   * 初始企业化组件
   * */
    private void initCompanyView(boolean is2) {
        if (is2) {
            //收入证明
            gv_income_2 = (GridView) mView.findViewById(R.id.gv_income_2);
            //经营性单据
            gv_bill_2 = (GridView) mView.findViewById(R.id.gv_bill_2);
            //对应企业
            sp_company_2 = (Spinner) mView.findViewById(R.id.sp_company_2);
            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
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
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.JY.getValue());
                mJYList_2.add(imgInfo);
                mJYAdapter_2 = new ImgsGridViewAdapter(getActivity(), mJYList_2);
                mJYAdapter_2.setOnClickImgListener(this);
                gv_bill_2.setAdapter(mJYAdapter_2);
            }

        } else {
            //收入证明
            gv_income_3 = (GridView) mView.findViewById(R.id.gv_income_3);
            //经营性单据
            gv_bill_3 = (GridView) mView.findViewById(R.id.gv_bill_3);
            //对应企业
            sp_company_3 = (Spinner) mView.findViewById(R.id.sp_company_3);

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(3);
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
                imgInfo.setIdxTag(3);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.JY.getValue());
                mJYList_3.add(imgInfo);
                mJYAdapter_3 = new ImgsGridViewAdapter(getActivity(), mJYList_3);
                mJYAdapter_3.setOnClickImgListener(this);
                gv_bill_3.setAdapter(mJYAdapter_3);
            }
        }
    }

    /*
   * 初始企业化组件
   * */
    private void initCarView(boolean is2) {
        if (is2) {
            //车牌车型
            gv_license_plate_2 = (GridView) mView.findViewById(R.id.gv_license_plate_2);
            //行驶证
            gv_driving_license_2 = (GridView) mView.findViewById(R.id.gv_driving_license_2);
            //对应车辆
            sp_car_2 = (Spinner) mView.findViewById(R.id.sp_car_2);

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.CP.getValue());
                mCPList_2.add(imgInfo);
                mCPAdpter_2 = new ImgsGridViewAdapter(getActivity(), mCPList_2);
                mCPAdpter_2.setOnClickImgListener(this);
                gv_license_plate_2.setAdapter(mCPAdpter_2);
            }

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.XS.getValue());
                mXSList_2.add(imgInfo);
                mXSAdpter_2 = new ImgsGridViewAdapter(getActivity(), mXSList_2);
                mXSAdpter_2.setOnClickImgListener(this);
                gv_driving_license_2.setAdapter(mXSAdpter_2);
            }

        } else {
            //车牌车型
            gv_license_plate_3 = (GridView) mView.findViewById(R.id.gv_license_plate_3);
            //行驶证
            gv_driving_license_3 = (GridView) mView.findViewById(R.id.gv_driving_license_3);
            //对应车辆
            sp_car_3 = (Spinner) mView.findViewById(R.id.sp_car_3);

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(3);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.CP.getValue());
                mCPList_3.add(imgInfo);
                mCPAdpter_3 = new ImgsGridViewAdapter(getActivity(), mCPList_3);
                mCPAdpter_3.setOnClickImgListener(this);
                gv_license_plate_3.setAdapter(mCPAdpter_3);
            }

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(3);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.XS.getValue());
                mXSList_3.add(imgInfo);
                mXSAdpter_3 = new ImgsGridViewAdapter(getActivity(), mXSList_3);
                mXSAdpter_3.setOnClickImgListener(this);
                gv_driving_license_3.setAdapter(mXSAdpter_3);
            }
        }
    }

    /*
   * 初始企业化组件
   * */
    private void initHouseView(boolean is2) {
        if (is2) {
            //房产证/合同
            gv_house_contract_2 = (GridView) mView.findViewById(R.id.gv_house_contract_2);
            //土地证
            gv_land_2 = (GridView) mView.findViewById(R.id.gv_land_2);
            //对应房产
            sp_house_2 = (Spinner) mView.findViewById(R.id.sp_house_2);

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.FC.getValue());
                mFCList_2.add(imgInfo);
                mFCAdpter_2 = new ImgsGridViewAdapter(getActivity(), mFCList_2);
                mFCAdpter_2.setOnClickImgListener(this);
                gv_house_contract_2.setAdapter(mFCAdpter_2);
            }

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.TD.getValue());
                mTDList_2.add(imgInfo);
                mTDAdpter_2 = new ImgsGridViewAdapter(getActivity(), mTDList_2);
                mTDAdpter_2.setOnClickImgListener(this);
                gv_land_2.setAdapter(mTDAdpter_2);
            }

        } else {
            //房产证/合同
            gv_house_contract_3 = (GridView) mView.findViewById(R.id.gv_house_contract_3);
            //土地证
            gv_land_3 = (GridView) mView.findViewById(R.id.gv_land_3);
            //对应房产
            sp_house_3 = (Spinner) mView.findViewById(R.id.sp_house_3);

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(3);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.FC.getValue());
                mFCList_3.add(imgInfo);
                mFCAdpter_3 = new ImgsGridViewAdapter(getActivity(), mFCList_3);
                mFCAdpter_3.setOnClickImgListener(this);
                gv_house_contract_3.setAdapter(mFCAdpter_3);
            }

            {
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(3);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.TD.getValue());
                mTDList_3.add(imgInfo);
                mTDAdpter_3 = new ImgsGridViewAdapter(getActivity(), mTDList_3);
                mTDAdpter_3.setOnClickImgListener(this);
                gv_land_3.setAdapter(mTDAdpter_3);
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
        View view = inflater.inflate(R.layout.fragment_personal_asset, container, false);
        ButterKnife.inject(this, view);
        this.mView = view;
        mBorrowRequestId = getBorrowRequestId();
        initOneView();
        getPersonalAssetInfo();
        return view;
    }

    /*
    *得到个人资产
    */
    private void getPersonalAssetInfo() {
        String requestPath = HttpRequestURL.personalInfoUrl + "/" + Constants.PERSONALASSETSINFO + "/" + getBorrowRequestId();
        try {
            HttpClientUtil.get(requestPath, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
                @Override
                public void onSuccess(Object o) {
                    if(o==null)
                        return;
                    JSONObject json = JSON.parseObject(o.toString());
                    try {
                        List<CompanyInfo> companyInfoList = JSON.parseArray(json.getString("businessInfoList"), CompanyInfo.class);
                        if (companyInfoList != null) {
                            int size = companyInfoList.size();
                            CompanyInfo companyInfo = null;
                            for (int i = 0; i < size; i++) {
                                companyInfo = companyInfoList.get(i);
                                mCompanyId[i] = companyInfo.getId();
                                int companySize = -1;
                                if (companyInfo.getFileList() != null)
                                    companySize = companyInfo.getFileList().size();
                                //判断是否是有效企业 企业名称
                                if (companySize > 0 || !JDAppUtil.isEmpty(companyInfo.getCompanyName())) {

                                    switch (i) {
                                        case 0:
                                            et_remark.setText(companyInfo.getRemark());
                                            initServerFile(companyInfo.getFileList());
                                            break;
                                        case 1:
                                            addCompanyIncome();
                                            initServerFile(companyInfo.getFileList());
                                            break;
                                        case 2:
                                            addCompanyIncome();
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
                    AssetInfo assetInfo = json.getObject("personalAssetsInfo", AssetInfo.class);
                    if (assetInfo != null) {
                        try {
                            if (assetInfo.getPersonalAssetsCarInfoList() != null) {
                                //车
                                int size = assetInfo.getPersonalAssetsCarInfoList().size();
                                CarInfo carInfo = null;
                                for (int i = 0; i < size; i++) {
                                    carInfo = assetInfo.getPersonalAssetsCarInfoList().get(i);
                                    if (carInfo != null) {
                                        mCardId[i] = carInfo.getId();
                                        int carSize = -1;
                                        if (carInfo.getFileList() != null)
                                            carSize = carInfo.getFileList().size();
                                        //根据 车 是否有图片   车牌号码  总价 来判断是否是真实的车
                                        if (carSize > 0 || !JDAppUtil.isEmpty(carInfo.getSum_price()) || !JDAppUtil.isEmpty(carInfo.getPlate_num())) {
                                            switch (i) {
                                                case 0:
                                                    initServerFile(carInfo.getFileList());
                                                    break;
                                                case 1:
                                                    addCar();
                                                    initServerFile(carInfo.getFileList());
                                                    break;
                                                case 2:
                                                    addCar();
                                                    initServerFile(carInfo.getFileList());
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {

                        }
                        if (assetInfo.getPersonalAssetsHouseInfoList() != null) {
                            //房
                            int size = assetInfo.getPersonalAssetsHouseInfoList().size();
                            HouseInfo houseInfo = null;
                            for (int i = 0; i < size; i++) {
                                houseInfo = assetInfo.getPersonalAssetsHouseInfoList().get(i);
                                if (houseInfo != null) {
                                    mHouseId[i] = houseInfo.getId();
                                    int houseSize = -1;
                                    if (houseInfo.getFileList() != null)
                                        houseSize = houseInfo.getFileList().size();
                                    //根据 房 是否有图片   地址  面积  房产价号 来判断是否是真实的房子
                                    if (houseSize > 0 || !JDAppUtil.isEmpty(houseInfo.getArea()) || !JDAppUtil.isEmpty(houseInfo.getAddress()) || !JDAppUtil.isEmpty(houseInfo.getDeed_num())) {
                                        switch (i) {
                                            case 0:
                                                initServerFile(houseInfo.getFileList());
                                                break;
                                            case 1:
                                                addCar();
                                                initServerFile(houseInfo.getFileList());
                                                break;
                                            case 2:
                                                addCar();
                                                initServerFile(houseInfo.getFileList());
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //初始化集合
    private void initImageList() {
        mSYList_1 = new ArrayList<UploadFileInfo>();
        mJYList_1 = new ArrayList<UploadFileInfo>();
        mCPList_1 = new ArrayList<UploadFileInfo>();
        mXSList_1 = new ArrayList<UploadFileInfo>();
        mFCList_1 = new ArrayList<UploadFileInfo>();
        mTDList_1 = new ArrayList<UploadFileInfo>();

        mSYList_2 = new ArrayList<UploadFileInfo>();
        mJYList_2 = new ArrayList<UploadFileInfo>();
        mCPList_2 = new ArrayList<UploadFileInfo>();
        mXSList_2 = new ArrayList<UploadFileInfo>();
        mFCList_2 = new ArrayList<UploadFileInfo>();
        mTDList_2 = new ArrayList<UploadFileInfo>();

        mSYList_3 = new ArrayList<UploadFileInfo>();
        mJYList_3 = new ArrayList<UploadFileInfo>();
        mCPList_3 = new ArrayList<UploadFileInfo>();
        mXSList_3 = new ArrayList<UploadFileInfo>();
        mFCList_3 = new ArrayList<UploadFileInfo>();
        mTDList_3 = new ArrayList<UploadFileInfo>();
    }

    //初始化数据源控件
    private void initOneView() {
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
            imgInfo.setFileType(UploadFileType.SW.getValue());
            mJYList_1.add(imgInfo);
            mJYAdapter_1 = new ImgsGridViewAdapter(getActivity(), mJYList_1);
            mJYAdapter_1.setOnClickImgListener(this);
            gv_bill_1.setAdapter(mJYAdapter_1);
        }
        {
            //车牌车型
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.SW.getValue());
            mCPList_1.add(imgInfo);
            mCPAdpter_1 = new ImgsGridViewAdapter(getActivity(), mCPList_1);
            mCPAdpter_1.setOnClickImgListener(this);
            gv_license_plate_1.setAdapter(mCPAdpter_1);
        }
        {
            //行驶证
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.QT.getValue());
            mXSList_1.add(imgInfo);
            mXSAdpter_1 = new ImgsGridViewAdapter(getActivity(), mXSList_1);
            mXSAdpter_1.setOnClickImgListener(this);
            gv_driving_license_1.setAdapter(mXSAdpter_1);
        }
        {
            //房产证/合同
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.QT.getValue());
            mFCList_1.add(imgInfo);
            mFCAdpter_1 = new ImgsGridViewAdapter(getActivity(), mFCList_1);
            mFCAdpter_1.setOnClickImgListener(this);
            gv_house_contract_1.setAdapter(mFCAdpter_1);
        }
        {
            //土地
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.TD.getValue());
            mTDList_1.add(imgInfo);
            mTDAdpter_1 = new ImgsGridViewAdapter(getActivity(), mTDList_1);
            mTDAdpter_1.setOnClickImgListener(this);
            gv_land_1.setAdapter(mTDAdpter_1);
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
        } else if (fType == UploadFileType.CP) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mCPList_1.add(0, imgInfo);
                    break;
                case 1:
                    mCPList_2.add(0, imgInfo);
                    break;
                case 2:
                    mCPList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }

        } else if (fType == UploadFileType.XS) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mXSList_1.add(0, imgInfo);
                    break;
                case 1:
                    mXSList_2.add(0, imgInfo);
                    break;
                case 2:
                    mXSList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.FC) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mFCList_1.add(0, imgInfo);
                    break;
                case 1:
                    mFCList_2.add(0, imgInfo);
                    break;
                case 2:
                    mFCList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }

        } else if (fType == UploadFileType.TD) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mTDList_1.add(0, imgInfo);
                    break;
                case 1:
                    mTDList_2.add(0, imgInfo);
                    break;
                case 2:
                    mTDList_3.add(0, imgInfo);
                    break;
                default:
                    break;
            }
        } else {

        }
        mHandler.sendMessage(mHandler.obtainMessage(0, imgInfo));
    }

    @Override
    public void onImgClick(List<UploadFileInfo> uploadImgInfoList, int idx) {
        if (idx < uploadImgInfoList.size()) {
            UploadFileInfo imgInfo = uploadImgInfoList.get(idx);
            fileType = UploadFileType.valueOf(imgInfo.getFileType());
            selIdxTag = imgInfo.getIdxTag();
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
            imgInfo.setIdxTag(selIdxTag);
            imgInfo.setFileType(fileType.getValue());
            //  YY(4),SW(5),QYDM(6),QT(7),FC(8),TD(9);
            addGridViewData(imgInfo);
            uploadImg(imgInfo);
        }
    };

    /*
    * 得到上传文件的父id 并设置ParentTableName
    * */
    private String getParentIdAndSetParentTableName(UploadFileInfo fileInfo) {
        String parentId = null;
        UploadFileType fType = UploadFileType.valueOf(fileInfo.getFileType());
        //SY(10),JY(11),CP(12),XS(13),REMARK(16),SOUND(80)
        if (fType == UploadFileType.SY || fType == UploadFileType.JY) {
            parentId = mCompanyId[fileInfo.getIdxTag()];
            fileInfo.setParentTableName(Constants.BUSINESS_INFO);
        } else if (fType == UploadFileType.CP || fType == UploadFileType.XS) {
            parentId = mCardId[fileInfo.getIdxTag()];
            fileInfo.setParentTableName(Constants.PERSONAL_ASSERTS_CAR_INFO);
        } else if (fType == UploadFileType.REMARK || fType == UploadFileType.SOUND) {
            fileInfo.setParentTableName(Constants.PERSONAL_ASSETS_INFO);

        } else if (fType == UploadFileType.FC || fType == UploadFileType.TD) {
            parentId = mHouseId[fileInfo.getIdxTag()];
            fileInfo.setParentTableName(Constants.PERSONAL_ASSETS_HOUSE_INFO);
        } else {

        }
        return parentId;
    }

    //上传图片
    private void uploadImg(final UploadFileInfo fileInfo) {
        try {
            HttpClientUtil.post(getActivity(), HttpRequestURL.uploadImgUrl, getUploadEntity(fileInfo, getParentIdAndSetParentTableName(fileInfo)), new JDHttpResponseHandler(getActivity(), new ResponseHandler<UploadFileInfo>() {
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
            imgInfo.setIdxTag(selIdxTag);
            imgInfo.setFileType(fileType.getValue());
            addGridViewData(imgInfo);
            uploadImg(imgInfo);
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
                    mSYAdapter_2.refreshData();
                    break;
                case 2:
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
                    mJYAdapter_2.refreshData();
                    break;
                case 2:
                    mJYAdapter_3.refreshData();
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.CP) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mCPAdpter_1.refreshData();
                    break;
                case 1:
                    mCPAdpter_2.refreshData();
                    break;
                case 2:
                    mCPAdpter_3.refreshData();
                    break;
                default:
                    break;
            }

        } else if (fType == UploadFileType.XS) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mXSAdpter_1.refreshData();
                    break;
                case 1:
                    mXSAdpter_2.refreshData();
                    break;
                case 2:
                    mXSAdpter_3.refreshData();
                    break;
                default:
                    break;
            }
        } else if (fType == UploadFileType.FC) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mFCAdpter_1.refreshData();
                    break;
                case 1:
                    mFCAdpter_2.refreshData();
                    break;
                case 2:
                    mFCAdpter_3.refreshData();
                    break;
                default:
                    break;
            }

        } else if (fType == UploadFileType.TD) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mTDAdpter_1.refreshData();
                    break;
                case 1:
                    mTDAdpter_2.refreshData();
                    break;
                case 2:
                    mTDAdpter_3.refreshData();
                    break;
                default:
                    break;
            }
        } else {

        }
    }

    public static PersonalAssetsFragment newInstance() {
        PersonalAssetsFragment personalAssetsFragment = new PersonalAssetsFragment();
        return personalAssetsFragment;
    }
}
