package com.chaqianma.jd.fragment;

/**
 * Created by zhangxd on 2015/7/30.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.InvestigateDetailActivity;
import com.chaqianma.jd.adapters.ImgsGridViewAdapter;
import com.chaqianma.jd.adapters.SoundGridViewAdapter;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.DownImgInfo;
import com.chaqianma.jd.model.UploadStatus;
import com.chaqianma.jd.model.UploadFileType;
import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.model.CustomerBaseInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDFileResponseHandler;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDToast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by zhangxd on 2015/7/28.
 */
public class PersonInfoFragment extends BaseFragment {
    @InjectView(R.id.linear_container)
    LinearLayout linear_container;
    @InjectView(R.id.rg_marry)
    RadioGroup radioGroup_marry;
    @InjectView(R.id.layout_marry)
    LinearLayout layout_marry;
    @InjectView(R.id.layout_divorce)
    LinearLayout layout_divorce;
    @InjectView(R.id.layout_child)
    LinearLayout layout_child;
    @InjectView(R.id.rg_registered_residence)
    RadioGroup radioGroup_registered;
    @InjectView(R.id.radio_native)
    RadioButton radio_native;
    @InjectView(R.id.radio_nonlocal)
    RadioButton radio_nonlocal;
    @InjectView(R.id.layout_come_date)
    LinearLayout layout_come_date;
    @InjectView(R.id.et_card_id)
    EditText et_card_id;
    @InjectView(R.id.et_name)
    EditText et_name;
    @InjectView(R.id.et_mobile)
    EditText et_mobile;
    @InjectView(R.id.rg_sex)
    RadioGroup rg_sex;
    @InjectView(R.id.radio_man)
    RadioButton radio_man;
    @InjectView(R.id.radio_woman)
    RadioButton radio_woman;
    @InjectView(R.id.rg_marry)
    RadioGroup rg_marry;
    @InjectView(R.id.radio_single)
    RadioButton radio_single;
    @InjectView(R.id.radio_married)
    RadioButton radio_married;
    @InjectView(R.id.radio_divorce)
    RadioButton radio_divorce;
    @InjectView(R.id.et_children_count)
    EditText et_children_count;
    @InjectView(R.id.tv_come_date)
    TextView tv_come_date;
    @InjectView(R.id.rg_agriculture)
    RadioGroup rg_agriculture;
    @InjectView(R.id.radio_yes)
    RadioButton radio_yes;
    @InjectView(R.id.radio_no)
    RadioButton radio_no;
    @InjectView(R.id.et_remark)
    EditText et_remark;
    @InjectView(R.id.gv_card_imgs)
    GridView gv_card_imgs;
    @InjectView(R.id.gv_marry_imgs)
    GridView gv_marry_imgs;
    @InjectView(R.id.gv_divorce_imgs)
    GridView gv_divorce_imgs;
    @InjectView(R.id.gv_sound)
    GridView gv_sound;
    @InjectView(R.id.gv_mark)
    GridView gv_mark;
    @InjectView(R.id.btn_name_auth)
    Button btn_name_auth;
    @InjectView(R.id.gv_driving)
    GridView gv_driving;
    @InjectView(R.id.gv_passport)
    GridView gv_passport;
    //刷新GridView 身份证
    private static final int CARD = 1003;
    //刷新GridView 结婚证/离婚证
    private static final int MARRY = 1004;
    //刷新GridView 录音
    private static final int SOUND = 1005;
    //刷新GridView 离异
    private static final int SINGLE = 1006;
    //刷新GridView 备注
    private static final int REMARK = 1007;
    //身份证数据源
    private ImgsGridViewAdapter cardImgsAdapter = null;
    //驾照
    private ImgsGridViewAdapter driveImgsAdapter = null;
    //护照
    private ImgsGridViewAdapter passportImgsAdapter = null;
    //结婚证
    private ImgsGridViewAdapter marryImgsAdapter = null;
    //离婚证
    private ImgsGridViewAdapter divorceImgsAdapter = null;
    //录音数据源
    private SoundGridViewAdapter soundAdapter = null;
    //备注数据源
    private ImgsGridViewAdapter remarkImgsAdapter = null;
    //身份证图片集合
    private List<UploadFileInfo> cardUploadImgInfoList = null;
    //护照
    private List<UploadFileInfo> passportUploadImgInfoList = null;
    //驾照
    private List<UploadFileInfo> driveUploadImgInfoList = null;
    //结婚证
    private List<UploadFileInfo> marryUploadImgInfoList = null;
    //离婚证
    private List<UploadFileInfo> divorceUploadImgInfoList = null;
    //录音集合
    private List<UploadFileInfo> soundInfoList = null;
    //备注集合
    private List<UploadFileInfo> remarkUploadImgInfoList = null;
    //用于标识图片类型
    private UploadFileType fileType = UploadFileType.CARD;
    //日期弹出框
    private DatePickerDialog datePickerDialog = null;
    //上传文件所用的Id
    private String mParentId = null;
    //用于实名认证
    private boolean mIsAuthSuccess = false;
    //用于只加载一次
    private boolean hasLoadedOnce = false;
    private int year, month, day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImagesList();
        initDate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_info, container, false);
        ButterKnife.inject(this, view);
        initRadioGroup();
        initView();
        getPersonalInfo();
        return view;
    }

    /*
    * 设置未婚、结婚、离异 显示与否
    */
    private void setLayoutMarryVisible(int idx) {
        switch (idx) {
            case 1:
                layout_child.setVisibility(View.GONE);
                layout_marry.setVisibility(View.GONE);
                layout_divorce.setVisibility(View.GONE);
                break;
            case 2:
                layout_divorce.setVisibility(View.GONE);
                layout_marry.setVisibility(View.VISIBLE);
                layout_child.setVisibility(View.VISIBLE);
                break;
            case 3:
                layout_marry.setVisibility(View.GONE);
                layout_divorce.setVisibility(View.VISIBLE);
                layout_child.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /*
    * 初始化RadioGroup
    * */
    private void initRadioGroup() {
        //结婚
        radioGroup_marry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_single:
                        setLayoutMarryVisible(1);
                        break;
                    case R.id.radio_married:
                        setLayoutMarryVisible(2);
                        break;
                    case R.id.radio_divorce:
                        setLayoutMarryVisible(3);
                        break;
                    default:
                        break;
                }
            }
        });
        //户口
        radioGroup_registered.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_native:
                        JDAppUtil.addHiddenAction(layout_come_date);
                        break;
                    case R.id.radio_nonlocal:
                        JDAppUtil.addShowAction(layout_come_date);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /*
         * 初始化图片集合
        * */
    private void initImagesList() {
        fileType = UploadFileType.NONE;
        cardUploadImgInfoList = new ArrayList<UploadFileInfo>();
        marryUploadImgInfoList = new ArrayList<UploadFileInfo>();
        soundInfoList = new ArrayList<UploadFileInfo>();
        remarkUploadImgInfoList = new ArrayList<UploadFileInfo>();
        divorceUploadImgInfoList = new ArrayList<UploadFileInfo>();
        driveUploadImgInfoList = new ArrayList<UploadFileInfo>();
        passportUploadImgInfoList = new ArrayList<UploadFileInfo>();
    }

    /*
    * 初始化图片控件
    * */
    private void initView() {
        {
            //身份证
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.CARD.getValue());
            cardUploadImgInfoList.add(imgInfo);
            cardImgsAdapter = new ImgsGridViewAdapter(getActivity(), cardUploadImgInfoList);
            cardImgsAdapter.setOnClickImgListener(this);
            gv_card_imgs.setAdapter(cardImgsAdapter);
        }

        {
            //护照
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.HZ.getValue());
            passportUploadImgInfoList.add(imgInfo);
            passportImgsAdapter = new ImgsGridViewAdapter(getActivity(), passportUploadImgInfoList);
            passportImgsAdapter.setOnClickImgListener(this);
            gv_passport.setAdapter(passportImgsAdapter);
        }

        {
            //驾照
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.JZ.getValue());
            driveUploadImgInfoList.add(imgInfo);
            driveImgsAdapter = new ImgsGridViewAdapter(getActivity(), driveUploadImgInfoList);
            driveImgsAdapter.setOnClickImgListener(this);
            gv_driving.setAdapter(driveImgsAdapter);
        }

        {
            //结婚证
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIsDefault(true);
            imgInfo.setFileType(UploadFileType.MARRY.getValue());
            imgInfo.setiServer(false);
            marryUploadImgInfoList.add(imgInfo);
            marryImgsAdapter = new ImgsGridViewAdapter(getActivity(), marryUploadImgInfoList);
            marryImgsAdapter.setOnClickImgListener(this);
            gv_marry_imgs.setAdapter(marryImgsAdapter);
        }

        {
            //离婚证
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.SINGLE.getValue());
            divorceUploadImgInfoList.add(imgInfo);
            divorceImgsAdapter = new ImgsGridViewAdapter(getActivity(), divorceUploadImgInfoList);
            divorceImgsAdapter.setOnClickImgListener(this);
            gv_divorce_imgs.setAdapter(divorceImgsAdapter);
        }

        {
            //录音
            UploadFileInfo soundInfo = new UploadFileInfo();
            soundInfo.setIsDefault(true);
            soundInfo.setFileType(UploadFileType.SOUND.getValue());
            soundInfo.setiServer(false);
            soundInfoList.add(soundInfo);
            soundAdapter = new SoundGridViewAdapter(getActivity(), soundInfoList, Constants.USER_BASE_INFO);
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
    * 初始化日期
    * */
    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    /*
    * 来本地日期
    * */
    @OnClick(R.id.tv_come_date)
    void onComeDateClick() {
        datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                PersonInfoFragment.this.year = year;
                month = monthOfYear;
                day = dayOfMonth;
                tv_come_date.setText(year + "-" + String.format("%2d", monthOfYear + 1) + "-" + String.format("%2d", dayOfMonth));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    //获取客户基本信息
    private void getPersonalInfo() {
        String requestPath = HttpRequestURL.personalInfoUrl + "/" + Constants.PERSONALINFO + "/" + getBorrowRequestId();
        try {
            HttpClientUtil.get(requestPath, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler<CustomerBaseInfo>() {
                @Override
                public void onSuccess(final CustomerBaseInfo customerBaseInfo) {
                    try {
                        if (customerBaseInfo != null) {
                            mParentId = customerBaseInfo.getId();
                            soundAdapter.setParentId(mParentId);
                            et_card_id.setText(customerBaseInfo.getIdCardNumber());
                            et_name.setText(customerBaseInfo.getName());
                            if(!JDAppUtil.isEmpty(customerBaseInfo.getIdCardNumber()))
                                btn_name_auth.setEnabled(false);
                            et_mobile.setText(customerBaseInfo.getMobile());
                            if (customerBaseInfo.getGender() != null) {
                                if (customerBaseInfo.getGender().equals("1"))
                                    radio_man.setChecked(true);
                                else
                                    radio_woman.setChecked(true);
                            }
                            //已婚，离异
                            if (customerBaseInfo.getMaritalStatus() != null) {
                                String marital_status = customerBaseInfo.getMaritalStatus();
                                if (marital_status.equals("1")) {
                                    radio_single.setChecked(true);
                                    setLayoutMarryVisible(1);
                                } else if (marital_status.equals("2")) {
                                    radio_married.setChecked(true);
                                    setLayoutMarryVisible(2);
                                    et_children_count.setText(customerBaseInfo.getCountChildren());
                                } else {
                                    radio_divorce.setChecked(true);
                                    setLayoutMarryVisible(3);
                                    et_children_count.setText(customerBaseInfo.getCountChildren());
                                }
                            }
                            //户口 本地  外地 household_type

                            if (customerBaseInfo.getHouseholdType() != null) {
                                if (customerBaseInfo.getHouseholdType().equals("1")) {
                                    layout_come_date.setVisibility(View.GONE);
                                    radio_native.setChecked(true);
                                } else {
                                    layout_come_date.setVisibility(View.VISIBLE);
                                    radio_nonlocal.setChecked(true);
                                    //来本地日期
                                    tv_come_date.setText(JDAppUtil.getTimeYMD(customerBaseInfo.getComeLocalTime()));
                                }
                            }
                            //是否是农业户口
                            if (customerBaseInfo.getIsAgriculturalHousehold() != null) {
                                if (customerBaseInfo.getIsAgriculturalHousehold().equals("1"))
                                    radio_yes.setChecked(true);
                                else
                                    radio_no.setChecked(true);
                            }
                            //备注
                            et_remark.setText(customerBaseInfo.getRemark());

                            //初始化服务端图片
                            initServerFile(customerBaseInfo.getFileList());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, Class.forName(CustomerBaseInfo.class.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_name_auth)
    void onRealNameAuth(View v) {
        String name = et_name.getText().toString();
        String card_id = et_card_id.getText().toString();
        if (JDAppUtil.isEmpty(card_id)) {
            JDToast.showLongText(getActivity(), "请输入身份证号");
            return;
        }
        if (JDAppUtil.isEmpty(name)) {
            JDToast.showLongText(getActivity(), "请输入姓名");
            return;
        }
        btn_name_auth.setText("认证中...");
        HashMap<String, Object> argMaps = new HashMap<String, Object>();
        argMaps.put("name", name);
        argMaps.put("idNumber", card_id);
        HttpClientUtil.post(HttpRequestURL.realNameAuthenticationUrl, argMaps, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                mIsAuthSuccess = true;
                btn_name_auth.setEnabled(false);
                btn_name_auth.setText("认证成功");
                JDToast.showLongText(getActivity(), "实名认证成功");
            }

            @Override
            public void onFailure(String data) {
                super.onFailure(data);
                mIsAuthSuccess = false;
                btn_name_auth.setText("认验失败");
            }
        }));
    }

    /*
    *获取服务器文件信息
     */
    private void initServerFile(List<UploadFileInfo> fileInfoList) {
        if (fileInfoList != null && fileInfoList.size() > 0) {
            for (UploadFileInfo uploadFileInfo : fileInfoList) {
                uploadFileInfo.setiServer(true);
                uploadFileInfo.setIsDefault(false);
                uploadFileInfo.setBorrowRequestId(getBorrowRequestId());
                getServerFile(uploadFileInfo);
            }
        }
    }

    /*
    * 得到服务器文件
    * */
    private void getServerFile(UploadFileInfo fileInfo) {
        String filePath = getSaveFilePath(fileInfo);
        fileInfo.setBigImgPath(filePath);
        HttpClientUtil.get(HttpRequestURL.downLoadFileUrl + "/" + fileInfo.getFileId(), null, new JDFileResponseHandler(fileInfo, new ResponseHandler<UploadFileInfo>() {
            @Override
            public void onSuccess(UploadFileInfo uploadFileInfo) {
                uploadFileInfo.setiServer(true);
                uploadFileInfo.setStatus(UploadStatus.SUCCESS.getValue());
                String fileExt = uploadFileInfo.getFileExt();
                if (fileExt.equals("amr")) {
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

    public static PersonInfoFragment newInstance() {
        PersonInfoFragment personInfoFragment = new PersonInfoFragment();
        return personInfoFragment;
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

    //删除图片
    @Override
    public void onDeletePhoto(UploadFileInfo uploadFileInfo) {
        //刷新数据源
        refreshData(uploadFileInfo);
    }

    @Override
    public void onImgClick(List<UploadFileInfo> uploadImgInfoList, int idx) {
        if (idx < uploadImgInfoList.size()) {
            UploadFileInfo imgInfo = uploadImgInfoList.get(idx);
            fileType = UploadFileType.valueOf(imgInfo.getFileType());
            if (imgInfo.isDefault()) {
               /* if (!mIsAuthSuccess) {
                    JDToast.showLongText(getActivity(), "未实名认证，不能上传图片");
                    return;
                }*/
                mPopup.showAtLocation(linear_container, Gravity.BOTTOM, 0, 0);
            } else {
                mViewPagerPopup.setUploadImgList(uploadImgInfoList, idx);
                mViewPagerPopup.showAtLocation(linear_container, Gravity.BOTTOM, 0, 0);
            }
        }
    }

    //拍照返回结果 ---------拍照图片 data=null
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
                                new Thread(new ImgRunable(cursor.getString(colunm_index), Constants.USER_BASE_INFO, fileType, new UpdateUIHandler())).start();
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
                    new Thread(new ImgRunable(Constants.TEMPPATH, Constants.USER_BASE_INFO, fileType, new UpdateUIHandler())).start();
                    break;
            }
        }
    }

    /*
    * 刷新GridView数据与上传文件
    * */
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
    * 添加到GridView
    * */
    private void addGridViewData(UploadFileInfo imgInfo) {
        UploadFileType fType = UploadFileType.valueOf(imgInfo.getFileType());
        if (fType == UploadFileType.CARD) {
            cardUploadImgInfoList.add(0, imgInfo);
            cardImgsAdapter.refreshData();
        } else if (fType == UploadFileType.SOUND) {
            soundInfoList.add(0, imgInfo);
            soundAdapter.refreshData();
        } else if (fType == UploadFileType.MARRY) {
            //结婚
            marryUploadImgInfoList.add(0, imgInfo);
            marryImgsAdapter.refreshData();
        } else if (fType == UploadFileType.SINGLE) {
            //离婚
            divorceUploadImgInfoList.add(0, imgInfo);
            divorceImgsAdapter.refreshData();
        } else if (fType == UploadFileType.REMARK) {
            remarkUploadImgInfoList.add(0, imgInfo);
            remarkImgsAdapter.refreshData();
        } else if (fType == UploadFileType.JZ) {
            //驾照
            driveUploadImgInfoList.add(0, imgInfo);
            driveImgsAdapter.refreshData();
        } else if (fType == UploadFileType.HZ) {
            //护照
            passportUploadImgInfoList.add(0, imgInfo);
            passportImgsAdapter.refreshData();
        } else {

        }
    }

    //上传图片
    private void uploadImg(final UploadFileInfo fileInfo) {
        try {
            HttpClientUtil.post(getActivity(), HttpRequestURL.uploadImgUrl, getUploadEntity(fileInfo, mParentId), new JDHttpResponseHandler(getActivity(), new ResponseHandler<DownImgInfo>() {
                @Override
                public void onSuccess(DownImgInfo downImgInfo) {
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
                }
            }, Class.forName(DownImgInfo.class.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 保持数据
    * */
    public void saveDataSubmit() {
        if (!mIsAuthSuccess) {
            JDToast.showLongText(getActivity(), "请进行实名认证");
            return;
        }
        //身份证号
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        HashMap<String, Object> argMaps = new HashMap<String, Object>();
        String card_id = et_card_id.getText().toString().trim();
        if (card_id != null && card_id.length() > 0) {
            formparams.add(new BasicNameValuePair("idCardNumber", card_id));
        } else {
            JDToast.showLongText(getActivity(), "请输入身份证号");
            return;
        }
        //姓名
        String name = et_name.getText().toString().trim();
        if (name != null && name.length() > 0) {
            formparams.add(new BasicNameValuePair("name", name));
        } else {
            JDToast.showLongText(getActivity(), "请输入姓名");
            return;
        }
        //身份证
        if (!isUploadSuccess(cardUploadImgInfoList)) {
            JDToast.showLongText(getActivity(), "请上传身份证图片");
            return;
        }
        //手机号
        String mobile = et_mobile.getText().toString();
        char c = mobile.charAt(0);
        boolean ss = c == '1';
        if (!JDAppUtil.isEmpty(mobile) && mobile.length() == 11 && mobile.charAt(0) == '1') {
            formparams.add(new BasicNameValuePair("mobile", mobile));
        } else {
            JDToast.showLongText(getActivity(), "请输入正确的手机号码");
            return;
        }
        //数据  1男2女
        formparams.add(new BasicNameValuePair("gender", radio_man.isChecked() ? "1" : "2"));
        //婚姻状况 1. 未婚 2. 已婚 3离异
        formparams.add(new BasicNameValuePair("maritalStatus", radio_single.isChecked() ? "1" : (radio_married.isChecked() ? "2" : "3")));
        //子女数
        if (radio_married.isChecked() || radio_divorce.isChecked()) {
            if (radio_married.isChecked()) {
                if (!isUploadSuccess(marryUploadImgInfoList)) {
                    JDToast.showLongText(getActivity(), "请上传结婚证书");
                    return;
                }
            }
            if (radio_divorce.isChecked()) {
                if (!isUploadSuccess(divorceUploadImgInfoList)) {
                    JDToast.showLongText(getActivity(), "请上传离婚证书");
                    return;
                }
            }
            String childCount = et_children_count.getText().toString().trim();
            if (childCount != null && childCount.length() > 0) {
                formparams.add(new BasicNameValuePair("countChildren", childCount));
            } else {
                JDToast.showLongText(getActivity(), "已婚/离异必须要输入子女数");
                return;
            }
        }
        //户口 户口类型 1、本地户口 2外地户口
        formparams.add(new BasicNameValuePair("householdType", radio_native.isChecked() ? "1" : "2"));
        //外地过来日期
        if (radio_nonlocal.isChecked()) {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String come_date = tv_come_date.getText().toString();
            if (come_date != null && come_date.length() > 0) {
                try {
                    Date date = sdf.parse("" + year + "-" + (month + 1) + "-" + day + " 00:00:00");
                    formparams.add(new BasicNameValuePair("comeLocalTime", date.getTime() + ""));
                } catch (Exception e) {

                }
            }
        }
        //是否农业户口 1是 0否
        formparams.add(new BasicNameValuePair("isAgriculturalHousehold", radio_yes.isChecked() ? "1" : "0"));
        //备注
        String remark = et_remark.getText().toString().trim();
        if (!JDAppUtil.isEmpty(remark) || soundInfoList.size() > 0 || remarkUploadImgInfoList.size() > 0) {
            formparams.add(new BasicNameValuePair("remark", remark));
        } else {
            JDToast.showLongText(getActivity(), "备注不能为空");
        }

        HttpClientUtil.put(getActivity(), HttpRequestURL.updatePersonUrl + mParentId, formparams, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                JDToast.showLongText(getActivity(), "个人信息保存成功");
                if (getActivity() instanceof InvestigateDetailActivity)
                    ((InvestigateDetailActivity) getActivity()).gotoNext();
            }
        }));
    }

    //刷新数据源
    private void refreshData(UploadFileInfo uploadFileInfo) {
        UploadFileType fType = UploadFileType.valueOf(uploadFileInfo.getFileType());
        if (fType == UploadFileType.CARD) {
            cardImgsAdapter.refreshData();
        } else if (fType == UploadFileType.MARRY) {
            marryImgsAdapter.refreshData();
        } else if (fType == UploadFileType.SINGLE) {
            divorceImgsAdapter.refreshData();
        } else if (fType == UploadFileType.REMARK) {
            remarkImgsAdapter.refreshData();
        } else if (fType == UploadFileType.HZ) {
            passportImgsAdapter.refreshData();
        } else if (fType == UploadFileType.JZ) {
            driveImgsAdapter.refreshData();
        } else {

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !hasLoadedOnce) {
            //getPersonalInfo();
            hasLoadedOnce = true;
        }
    }
}
