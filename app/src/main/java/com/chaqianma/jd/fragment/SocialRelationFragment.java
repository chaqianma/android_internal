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
import com.alibaba.fastjson.JSONObject;
import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.ImgsGridViewAdapter;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.CompanyInfo;
import com.chaqianma.jd.model.ContactInfo;
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
public class SocialRelationFragment extends BaseFragment {
    @InjectView(R.id.linear_container)
    LinearLayout linear_container;
    @InjectView(R.id.img_social_add)
    ImageView img_social_add;
    @InjectView(R.id.sp_relation_type_1)
    Spinner sp_relation_type_1;
    @InjectView(R.id.gv_relation_card_1)
    GridView gv_relation_card_1;
    @InjectView(R.id.et_remark)
    EditText et_remark;
    @InjectView(R.id.et_comment)
    EditText et_comment;
    @InjectView(R.id.gv_comment)
    GridView gv_comment;
    //关系类型
    private ImgsGridViewAdapter mRCAdapter_1 = null;
    private List<UploadFileInfo> mRCList_1 = null;

    //第二个
    private ImgsGridViewAdapter mRCAdapter_2 = null;
    private List<UploadFileInfo> mRCList_2 = null;
    private Spinner sp_relation_type_2 = null;
    private GridView gv_relation_card_2 = null;

    //第三个
    private ImgsGridViewAdapter mRCAdapter_3 = null;
    private List<UploadFileInfo> mRCList_3 = null;
    private Spinner sp_relation_type_3 = null;
    private GridView gv_relation_card_3 = null;

    //第四个
    private ImgsGridViewAdapter mRCAdapter_4 = null;
    private List<UploadFileInfo> mRCList_4 = null;
    private Spinner sp_relation_type_4 = null;
    private GridView gv_relation_card_4 = null;

    //第五个
    private ImgsGridViewAdapter mRCAdapter_5 = null;
    private List<UploadFileInfo> mRCList_5 = null;
    private Spinner sp_relation_type_5 = null;
    private GridView gv_relation_card_5 = null;


    //尽职说明
    private ImgsGridViewAdapter mJZAdapter = null;
    private List<UploadFileInfo> mJZList = null;


    private View mView = null;
    private boolean isShow2 = false, isShow3 = false, isShow4 = false, isShow5 = false;
    //图片标签
    private UploadFileType fileType = UploadFileType.CARD;
    //用于标识选择的是哪个类型
    private int selIdxTag = -1;
    //父ID
    private String[] mParentId = new String[5];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_relation, container, false);
        ButterKnife.inject(this, view);
        mView = view;
        initOneView();
        getSocialRelationInfo();
        return view;
    }

    /*
    * 初始化第一个控件
    * */
    private void initOneView() {
        //第一个关系人
        {
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.CARD.getValue());
            mRCList_1.add(imgInfo);
            mRCAdapter_1 = new ImgsGridViewAdapter(getActivity(), mRCList_1);
            mRCAdapter_1.setOnClickImgListener(this);
            gv_relation_card_1.setAdapter(mRCAdapter_1);
        }
        {
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(0);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.REMARK.getValue());
            mJZList.add(imgInfo);
            mJZAdapter = new ImgsGridViewAdapter(getActivity(), mJZList);
        }
    }

    @OnClick(R.id.img_social_add)
    void onAddRcClick() {
        addRCPerson();
    }

    /*
    * 添加联系人
    * */
    private void addRCPerson() {
        if (!isShow2) {
            isShow2 = true;
            View view = ((ViewStub) mView.findViewById(R.id.stub_social_relation_2)).inflate();
            JDAppUtil.addShowAction(view);
            gv_relation_card_2 = (GridView) mView.findViewById(R.id.gv_relation_card_2);
            sp_relation_type_2 = (Spinner) mView.findViewById(R.id.sp_relation_type_2);
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIdxTag(1);
            imgInfo.setIsDefault(true);
            imgInfo.setiServer(false);
            imgInfo.setFileType(UploadFileType.CARD.getValue());
            mRCList_2.add(imgInfo);
            mRCAdapter_2 = new ImgsGridViewAdapter(getActivity(), mRCList_2);
            mRCAdapter_2.setOnClickImgListener(this);
            gv_relation_card_2.setAdapter(mRCAdapter_2);
        } else {
            if (!isShow3) {
                isShow3 = true;
                View view = ((ViewStub) mView.findViewById(R.id.stub_social_relation_3)).inflate();
                JDAppUtil.addShowAction(view);
                gv_relation_card_3 = (GridView) mView.findViewById(R.id.gv_relation_card_3);
                sp_relation_type_3 = (Spinner) mView.findViewById(R.id.sp_relation_type_3);
                UploadFileInfo imgInfo = new UploadFileInfo();
                imgInfo.setIdxTag(2);
                imgInfo.setIsDefault(true);
                imgInfo.setiServer(false);
                imgInfo.setFileType(UploadFileType.CARD.getValue());
                mRCList_3.add(imgInfo);
                mRCAdapter_3 = new ImgsGridViewAdapter(getActivity(), mRCList_3);
                mRCAdapter_3.setOnClickImgListener(this);
                gv_relation_card_3.setAdapter(mRCAdapter_3);
            } else {
                if (!isShow4) {
                    isShow4 = true;
                    View view = ((ViewStub) mView.findViewById(R.id.stub_social_relation_4)).inflate();
                    JDAppUtil.addShowAction(view);
                    gv_relation_card_4 = (GridView) mView.findViewById(R.id.gv_relation_card_4);
                    sp_relation_type_4 = (Spinner) mView.findViewById(R.id.sp_relation_type_4);
                    UploadFileInfo imgInfo = new UploadFileInfo();
                    imgInfo.setIdxTag(3);
                    imgInfo.setIsDefault(true);
                    imgInfo.setiServer(false);
                    imgInfo.setFileType(UploadFileType.CARD.getValue());
                    mRCList_4.add(imgInfo);
                    mRCAdapter_4 = new ImgsGridViewAdapter(getActivity(), mRCList_4);
                    mRCAdapter_4.setOnClickImgListener(this);
                    gv_relation_card_4.setAdapter(mRCAdapter_4);
                } else {
                    if (!isShow5) {
                        isShow5 = true;
                        View view = ((ViewStub) mView.findViewById(R.id.stub_social_relation_5)).inflate();
                        JDAppUtil.addShowAction(view);
                        gv_relation_card_5 = (GridView) mView.findViewById(R.id.gv_relation_card_5);
                        sp_relation_type_5 = (Spinner) mView.findViewById(R.id.sp_relation_type_5);
                        UploadFileInfo imgInfo = new UploadFileInfo();
                        imgInfo.setIdxTag(4);
                        imgInfo.setIsDefault(true);
                        imgInfo.setiServer(false);
                        imgInfo.setFileType(UploadFileType.CARD.getValue());
                        mRCList_5.add(imgInfo);
                        mRCAdapter_5 = new ImgsGridViewAdapter(getActivity(), mRCList_5);
                        mRCAdapter_5.setOnClickImgListener(this);
                        gv_relation_card_5.setAdapter(mRCAdapter_5);
                    }
                }
            }
        }
    }

    //初始化集合
    private void initImageList() {
        mRCList_1 = new ArrayList<UploadFileInfo>();
        mRCList_2 = new ArrayList<UploadFileInfo>();
        mRCList_3 = new ArrayList<UploadFileInfo>();
        mRCList_4 = new ArrayList<UploadFileInfo>();
        mRCList_5 = new ArrayList<UploadFileInfo>();
        mJZList = new ArrayList<UploadFileInfo>();
    }

    /*
    * 获取联系人信息
    * */
    private void getSocialRelationInfo() {
        String requestPath = HttpRequestURL.personalInfoUrl + "/" + Constants.CONTACTINFO + "/" + getBorrowRequestId();
        try {
            HttpClientUtil.get(requestPath, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
                @Override
                public void onSuccess(Object o) {
                    if (o == null)
                        return;
                    JSONObject json = JSON.parseObject(o.toString());
                    try {
                        List<ContactInfo> contactInfoList = JSON.parseArray(json.getString("contactInfoList"), ContactInfo.class);
                        if (contactInfoList != null) {
                            int size = contactInfoList.size();
                            ContactInfo contactInfo = null;
                            for (int i = 0; i < size; i++) {
                                contactInfo = contactInfoList.get(i);
                                mParentId[i] = contactInfo.getId();
                                int fileSize = -1;
                                if (contactInfo.getFileList() != null)
                                    fileSize = contactInfo.getFileList().size();
                                //判断是否是有效企业 企业名称
                                if (fileSize > 0 || !JDAppUtil.isEmpty(contactInfo.getName()) || !JDAppUtil.isEmpty(contactInfo.getRelation())) {
                                    switch (i) {
                                        case 0:
                                            et_remark.setText(contactInfo.getRemark());
                                            initServerFile(contactInfo.getFileList());
                                            break;
                                        case 1:
                                        case 2:
                                        case 3:
                                        case 4:
                                            addRCPerson();
                                            initServerFile(contactInfo.getFileList());
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
            }));
        } catch (Exception e) {
        }
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
            imgInfo.setParentTableName(Constants.CONTACT_INFO);
            imgInfo.setIdxTag(selIdxTag);
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
            imgInfo.setParentTableName(Constants.CONTACT_INFO);
            imgInfo.setIdxTag(selIdxTag);
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
        if (fType == UploadFileType.CARD) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    mRCAdapter_1.refreshData();
                    break;
                case 1:
                    mRCAdapter_2.refreshData();
                    break;
                case 2:
                    mRCAdapter_3.refreshData();
                    break;
                case 3:
                    mRCAdapter_4.refreshData();
                    break;
                case 4:
                    mRCAdapter_5.refreshData();
                    break;
                default:
                    break;
            }
        } else {

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
        if (fType == UploadFileType.CARD) {
            switch (imgInfo.getIdxTag()) {
                case 0:
                    mRCList_1.add(0, imgInfo);
                    break;
                case 1:
                    mRCList_2.add(0, imgInfo);
                    break;
                case 2:
                    mRCList_3.add(0, imgInfo);
                    break;
                case 3:
                    mRCList_4.add(0, imgInfo);
                    break;
                case 4:
                    mRCList_5.add(0, imgInfo);
                    break;
                default:
                    break;
            }
        }
        mHandler.sendMessage(mHandler.obtainMessage(0, imgInfo));
    }

    /*
        * 保存数据
        * */
    public void saveDataSubmit() {

    }

    public static SocialRelationFragment newInstance() {
        SocialRelationFragment socialRelationFragment = new SocialRelationFragment();
        return socialRelationFragment;
    }
}
