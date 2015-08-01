package com.chaqianma.jd.fragment;

/**
 * Created by zhangxd on 2015/7/30.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.ImgsGridViewAdapter;
import com.chaqianma.jd.adapters.SoundGridViewAdapter;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.BorrowRequestInfo;
import com.chaqianma.jd.model.DownImgInfo;
import com.chaqianma.jd.model.UploadStatus;
import com.chaqianma.jd.model.UploadFileType;
import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.model.CustomerBaseInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.ImageUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDFileResponseHandler;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDToast;
import com.chaqianma.jd.widget.PhotoPopup;
import com.chaqianma.jd.widget.ViewPagerPopup;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by zhangxd on 2015/7/28.
 */
public class PersonInfoFragment extends BaseFragment implements PhotoPopup.OnDialogListener,
        ViewPagerPopup.OnViewPagerDialogListener, ImgsGridViewAdapter.iOnClickImgListener, SoundGridViewAdapter.iOnClickSoundListener {
    @InjectView(R.id.linear_container)
    LinearLayout linear_container;
    @InjectView(R.id.rg_marry)
    RadioGroup radioGroup_marry;
    @InjectView(R.id.layout_card)
    LinearLayout layout_card;
    @InjectView(R.id.rg_registered_residence)
    RadioGroup radioGroup_registered;
    @InjectView(R.id.radio_native)
    RadioButton radio_native;
    @InjectView(R.id.radio_nonlocal)
    RadioButton radio_nonlocal;
    @InjectView(R.id.layout_come_date)
    LinearLayout layout_come_date;
    @InjectView(R.id.tv_card_id)
    TextView tv_card_id;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_mobile)
    TextView tv_mobile;
    @InjectView(R.id.rg_sex)
    RadioGroup rg_sex;
    @InjectView(R.id.radio_man)
    RadioButton radio_man;
    @InjectView(R.id.radio_woman)
    RadioButton radio_woman;
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
    @InjectView(R.id.et_mark)
    EditText et_mark;
    @InjectView(R.id.gv_card_imgs)
    GridView gv_card_imgs;
    @InjectView(R.id.gv_marry_imgs)
    GridView gv_marry_imgs;
    @InjectView(R.id.gv_sound)
    GridView gv_sound;
    @InjectView(R.id.gv_mark)
    GridView gv_mark;
    @InjectView(R.id.btn_name_auth)
    Button btn_name_auth;
    private PhotoPopup mPopup;
    private ViewPagerPopup mViewPagerPopup;
    //获取SDK中的图片
    private static final int REQUEST_SDK_IMGS = 1001;
    //拍照
    private static final int REQUEST_TAKE_PHOTO = 1002;
    //刷新GridView 身份证
    private static final int CARD = 1003;
    //刷新GridView 结婚证/离婚证
    private static final int MARRY = 1004;
    //刷新GridView 录音
    private static final int SOUND = 1005;
    //刷新GridView 备注
    private static final int REMARK = 1006;
    //临时图片放置
    private String mImgTempPath = Environment.getExternalStorageDirectory() + "/" + Constants.FILEDIR + "/" + "temp.jpg";
    //图片文件夹路径
    private String mImgDirPath = Environment.getExternalStorageDirectory() + "/" + Constants.FILEDIR;//+ "/"
    //身份证数据源
    private ImgsGridViewAdapter cardImgsAdapter = null;
    //结婚证/离婚证
    private ImgsGridViewAdapter marryImgsAdapter = null;
    //录音数据源
    private SoundGridViewAdapter soundAdapter = null;
    //备注数据源
    private ImgsGridViewAdapter remarkImgsAdapter = null;
    //身份证图片集合
    private List<UploadFileInfo> cardUploadImgInfoList = null;
    //结婚证/离婚证集合
    private List<UploadFileInfo> marryUploadImgInfoList = null;
    //录音集合
    private List<UploadFileInfo> soundInfoList = null;
    //备注集合
    private List<UploadFileInfo> remarkUploadImgInfoList = null;
    //1 身份证  2 结婚证/离婚证  3 备注
    private UploadFileType fileType = UploadFileType.CARD;
    //小图
    private String smallImgPath = null;
    //大图
    private String bigImgPath = null;
    //BorrowRequest
    private String mBorrowRequestId;
    //上传文件所用的Id
    private String mParentId = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImagesList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_info, container, false);
        ButterKnife.inject(this, view);

        mPopup = new PhotoPopup(getActivity());
        mPopup.setDialogListener(this);
        mViewPagerPopup = new ViewPagerPopup(getActivity());
        mViewPagerPopup.setViewPagerDialogListener(this);
        initImgDir();
        initRadioGroup();
        initView();
        getPersonalInfo();
        return view;
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
                        JDAppUtil.addHiddenAction(layout_card);
                        break;
                    case R.id.radio_married:
                    case R.id.radio_divorce:
                        if (layout_card.getVisibility() == View.GONE) {
                            JDAppUtil.addShowAction(layout_card);
                        }
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
    * 初始化图片文件夹
    * */
    private void initImgDir() {
        if (AppData.getInstance().getBorrowRequestInfo() != null) {
            mBorrowRequestId = AppData.getInstance().getBorrowRequestInfo().getBorrowRequestId();
            mImgDirPath = mImgDirPath + "/" + mBorrowRequestId;
            File file = new File(mImgDirPath);
            if (!file.getParentFile().exists()) {
                file.mkdirs();
            }
        }
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
            //结婚证/离婚证
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIsDefault(true);
            imgInfo.setFileType(UploadFileType.MARRY.getValue());
            imgInfo.setiServer(false);
            marryUploadImgInfoList.add(imgInfo);
            marryImgsAdapter = new ImgsGridViewAdapter(getActivity(), marryUploadImgInfoList);
            gv_marry_imgs.setAdapter(marryImgsAdapter);
        }

        {
            //录音
            UploadFileInfo soundInfo = new UploadFileInfo();
            soundInfo.setIsDefault(true);
            soundInfo.setFileType(UploadFileType.SOUND.getValue());
            soundInfo.setiServer(false);
            soundInfoList.add(soundInfo);
            soundAdapter = new SoundGridViewAdapter(getActivity(), soundInfoList, mParentId);
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
            gv_mark.setAdapter(marryImgsAdapter);
        }
    }

    //获取客户基本信息
    private void getPersonalInfo() {
        String requestPath = HttpRequestURL.personalInfoUrl + "/" + Constants.PERSONALINFO + "/" + mBorrowRequestId;
        try {
            HttpClientUtil.get(requestPath, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler<CustomerBaseInfo>() {
                @Override
                public void onSuccess(CustomerBaseInfo customerBaseInfo) {
                    if (customerBaseInfo != null) {
                        mParentId = customerBaseInfo.getId();
                        tv_name.setText(customerBaseInfo.getName());
                        tv_mobile.setText(customerBaseInfo.getMobile());
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
                                et_children_count.setVisibility(View.GONE);
                            } else if (marital_status.equals("2")) {
                                radio_married.setChecked(true);
                                et_children_count.setVisibility(View.VISIBLE);
                                et_children_count.setText(customerBaseInfo.getCountChildren());
                            } else {
                                radio_divorce.setChecked(true);
                                et_children_count.setVisibility(View.VISIBLE);
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
                                tv_come_date.setText(JDAppUtil.getTimeToStr(customerBaseInfo.getComeLocalTime()));
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
                        et_mark.setText(customerBaseInfo.getRemark());
                        //初始化服务端图片
                        initServerFile(customerBaseInfo.getFileList());
                    }
                }
            }, Class.forName(CustomerBaseInfo.class.getName())));
        } catch (Exception e) {

        }
    }

    @OnClick(R.id.btn_name_auth)
    void onRealNameAuth(View v) {
        btn_name_auth.setText("认证中。。。");
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("name", "倪美华"));
        formparams.add(new BasicNameValuePair("idNumber", "320623198610022361"));
        HttpClientUtil.put(getActivity(), HttpRequestURL.realNameAuthenticationUrl, formparams, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                btn_name_auth.setText("认证成功");
                JDToast.showLongText(getActivity(), "实名认证成功");
            }

            @Override
            public void onFailure(String data) {
                super.onFailure(data);
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
                uploadFileInfo.setBorrowRequestId(mBorrowRequestId);
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
                    soundInfoList.add(0, uploadFileInfo);
                } else if (fileExt.equals("jpg")) {
                    //1.身份证  2 结婚证/离婚证  3 备注 图片
                    switch (uploadFileInfo.getFileType()) {
                        case 1:
                            cardUploadImgInfoList.add(0, uploadFileInfo);
                            cardImgsAdapter.notifyDataSetChanged();
                            break;
                        case 2:
                            marryUploadImgInfoList.add(0, uploadFileInfo);
                            marryImgsAdapter.notifyDataSetChanged();
                            break;
                        case 3:
                            uploadFileInfo.setFileType(UploadFileType.REMARK.getValue());
                            remarkUploadImgInfoList.add(0, uploadFileInfo);
                            remarkImgsAdapter.notifyDataSetChanged();
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onFailure(UploadFileInfo uploadFileInfo) {
                super.onFailure(uploadFileInfo);
                uploadFileInfo.setiServer(false);
                uploadFileInfo.setStatus(UploadStatus.FAILURE.getValue());
                switch (uploadFileInfo.getFileType()) {
                    case 1:
                        cardUploadImgInfoList.add(0, uploadFileInfo);
                        cardImgsAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        marryUploadImgInfoList.add(0, uploadFileInfo);
                        marryImgsAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        uploadFileInfo.setFileType(UploadFileType.REMARK.getValue());
                        remarkUploadImgInfoList.add(0, uploadFileInfo);
                        remarkImgsAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
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
        File file = new File(mImgTempPath);
        if (!file.getParentFile().exists() && !file.mkdirs()) {

        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        }
    }

    //删除图片
    @Override
    public void onDeletePhoto(UploadFileType fileType, int delIdx) {
        refreshData();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onImgClick(List<UploadFileInfo> uploadImgInfoList, int idx) {
        if (idx < uploadImgInfoList.size()) {
            UploadFileInfo imgInfo = uploadImgInfoList.get(idx);
            fileType = UploadFileType.valueOf(imgInfo.getFileType());
            if (imgInfo.isDefault()) {
                mPopup.showAtLocation(linear_container, Gravity.BOTTOM, 0, 0);
            } else {
                mViewPagerPopup.setUploadImgList(uploadImgInfoList, idx);
                mViewPagerPopup.showAtLocation(linear_container, Gravity.BOTTOM, 0, 0);
            }
        }
    }

    //开始录音
    @Override
    public void onStartRecord() {

    }

    //结束录音
    @Override
    public void onStopRecord() {

    }

    //拍照返回结果 ---------拍照图片 data=null
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SDK_IMGS:
                    if (data != null) {

                    }
                    break;
                case REQUEST_TAKE_PHOTO:
                    mHandler.post(mRunnable);
                    break;
            }
        }
    }

    //刷新数据
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CARD:
                    cardImgsAdapter.refreshData();
                    break;
                case MARRY:
                    marryImgsAdapter.refreshData();
                    break;
                case SOUND:
                    break;
                case REMARK:
                    remarkImgsAdapter.refreshData();
                    break;
                default:
                    break;
            }
        }
    };


    //得到保存图片地址
    private String getSaveFilePath(UploadFileInfo uploadFileInfo) {
        return mImgDirPath + "/" + uploadFileInfo.getFileType() + "_" + System.currentTimeMillis() + "." + uploadFileInfo.getFileExt();
    }

    //得到图片地址
    private String getFilePath(boolean isBigImgPath, String randomName) {
        String path = fileType.getValue() + "_" + randomName + ".jpg";
        if (!isBigImgPath)
            path = "thumb_" + path;
        return mImgDirPath + "/" + path;
    }

    //保存图片
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            String random = System.currentTimeMillis() + "";
            smallImgPath = getFilePath(false, random);
            bigImgPath = getFilePath(true, random);
            //存放大图
            Bitmap proportionBM = ImageUtil.proportionZoom(mImgTempPath, 1024);
            if (proportionBM != null) {
                ImageUtil.saveBitmapFile(bigImgPath, proportionBM);
                proportionBM.recycle();
            }
            //存放小图
            Bitmap bitmap = ImageUtil.getLocalThumbImg(mImgTempPath, 80, 80, "jpg");
            if (bitmap != null) {
                ImageUtil.saveBitmapFile(smallImgPath, bitmap);
                bitmap.recycle();
            }
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setiServer(false);
            imgInfo.setBigImgPath(bigImgPath);
            imgInfo.setSmallImgPath(smallImgPath);
            imgInfo.setParentTableName(Constants.USER_BASE_INFO);
            imgInfo.setFileType(fileType.getValue());
            if (fileType == UploadFileType.CARD) {
                cardUploadImgInfoList.add(0, imgInfo);
                cardImgsAdapter.setUploadImgInfoList(cardUploadImgInfoList);
                mHandler.sendMessage(mHandler.obtainMessage(CARD, null));
                uploadImg(imgInfo);
            } else if (fileType == UploadFileType.MARRY) {
                marryUploadImgInfoList.add(0, imgInfo);
                marryImgsAdapter.setUploadImgInfoList(marryUploadImgInfoList);
                mHandler.sendMessage(mHandler.obtainMessage(MARRY, null));
                uploadImg(imgInfo);
            } else if (fileType == UploadFileType.SOUND) {

            } else if (fileType == UploadFileType.REMARK) {
                remarkUploadImgInfoList.add(0, imgInfo);
                remarkImgsAdapter.setUploadImgInfoList(remarkUploadImgInfoList);
                mHandler.sendMessage(mHandler.obtainMessage(REMARK, null));
                uploadImg(imgInfo);
            } else {

            }
        }
    };

    //上传图片
    private void uploadImg(final UploadFileInfo imgInfo) {
        try {
            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
            ContentBody fileBody = new FileBody(new File(imgInfo.getBigImgPath()));
            entity.addPart("files", fileBody);
            int ftype = fileType.getValue();
            if (ftype == UploadFileType.REMARK.getValue())
                ftype -= 1;
            entity.addPart("fileType", new StringBody("" + ftype, ContentType.DEFAULT_TEXT));
            entity.addPart("parentTableName", new StringBody(imgInfo.getParentTableName(), ContentType.DEFAULT_TEXT));
            entity.addPart("parentId", new StringBody(mParentId, ContentType.DEFAULT_TEXT));
            HttpClientUtil.post(getActivity(), HttpRequestURL.uploadImgUrl, entity.build(), new JDHttpResponseHandler(getActivity(), new ResponseHandler<DownImgInfo>() {
                @Override
                public void onSuccess(DownImgInfo downImgInfo) {
                    imgInfo.setStatus(UploadStatus.SUCCESS.getValue());//成功
                    refreshData();
                }

                @Override
                public void onFailure(String data) {
                    imgInfo.setStatus(UploadStatus.FAILURE.getValue());//失败
                    if (fileType == UploadFileType.CARD) {

                        cardImgsAdapter.notifyDataSetChanged();
                    } else if (fileType == UploadFileType.MARRY) {

                    } else {

                    }
                }
            }, Class.forName(DownImgInfo.class.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //刷新整个数据源
    private void refreshAllData() {
        cardImgsAdapter.refreshData();
        marryImgsAdapter.refreshData();
        //soundAdapter
        remarkImgsAdapter.refreshData();
    }

    //刷新数据源
    private void refreshData() {
        if (fileType == UploadFileType.CARD) {
            cardImgsAdapter.refreshData();
        } else if (fileType == UploadFileType.MARRY) {
            marryImgsAdapter.refreshData();
        } else if (fileType == UploadFileType.SOUND) {
            //soundAdapter
        } else if (fileType == UploadFileType.REMARK) {
            remarkImgsAdapter.refreshData();
        } else {

        }
    }
}
