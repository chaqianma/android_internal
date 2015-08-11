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
import com.chaqianma.jd.activity.InvestigateDetailActivity;
import com.chaqianma.jd.adapters.ImgsGridViewAdapter;
import com.chaqianma.jd.adapters.SoundGridViewAdapter;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

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
    @InjectView(R.id.gv_sound)
    GridView gv_sound;
    @InjectView(R.id.gv_mark)
    GridView gv_mark;
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

    private View mView = null;
    private boolean isShow2 = false, isShow3 = false, isShow4 = false, isShow5 = false;
    //图片标签
    private UploadFileType fileType = UploadFileType.CARD;
    //用于标识选择的是哪个类型
    private int selIdxTag = -1;
    //父ID
    private String[] mParentId = new String[5];

    //配偶说明
    private String mSpouse = "配偶";

    //备注数据源
    private ImgsGridViewAdapter remarkImgsAdapter = null;
    //备注集合
    private List<UploadFileInfo> remarkUploadImgInfoList = null;
    //录音数据源
    private SoundGridViewAdapter soundAdapter = null;
    //录音集合
    private List<UploadFileInfo> soundInfoList = null;
    //尽职数据源
    private ImgsGridViewAdapter commentImgsAdapter = null;
    //尽职集合
    private List<UploadFileInfo> commentUploadImgInfoList = null;

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
            //录音
            UploadFileInfo soundInfo = new UploadFileInfo();
            soundInfo.setIsDefault(true);
            soundInfo.setFileType(UploadFileType.SOUND.getValue());
            soundInfo.setiServer(false);
            soundInfoList.add(soundInfo);
            soundAdapter = new SoundGridViewAdapter(getActivity(), soundInfoList, "-1");
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

        //尽职说明
        {
            UploadFileInfo imgInfo = new UploadFileInfo();
            imgInfo.setIsDefault(true);
            imgInfo.setFileType(UploadFileType.COMMENT.getValue());
            imgInfo.setiServer(false);
            commentUploadImgInfoList.add(imgInfo);
            commentImgsAdapter = new ImgsGridViewAdapter(getActivity(), commentUploadImgInfoList);
            commentImgsAdapter.setOnClickImgListener(this);
            gv_comment.setAdapter(commentImgsAdapter);
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
            if (!requiredInput())
                return;
            isShow2 = true;
            ((ViewStub) mView.findViewById(R.id.stub_social_relation_2)).inflate();
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
                if (!requiredInput())
                    return;
                isShow3 = true;
                ((ViewStub) mView.findViewById(R.id.stub_social_relation_3)).inflate();
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
                    if (!requiredInput())
                        return;
                    isShow4 = true;
                    ((ViewStub) mView.findViewById(R.id.stub_social_relation_4)).inflate();
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
                        if (!requiredInput())
                            return;
                        isShow5 = true;
                        ((ViewStub) mView.findViewById(R.id.stub_social_relation_5)).inflate();
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

    /*
   * 添加联系人
   * */
    private void addRCPerson(int rcIdx) {
        switch (rcIdx) {
            case 2: {
                isShow2 = true;
                ((ViewStub) mView.findViewById(R.id.stub_social_relation_2)).inflate();
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
            }
            break;
            case 3: {
                isShow3 = true;
                ((ViewStub) mView.findViewById(R.id.stub_social_relation_3)).inflate();
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
            }
            break;
            case 4: {
                isShow4 = true;
                ((ViewStub) mView.findViewById(R.id.stub_social_relation_4)).inflate();
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
            }
            break;
            case 5: {
                isShow5 = true;
                ((ViewStub) mView.findViewById(R.id.stub_social_relation_5)).inflate();
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
            break;
            default:
                break;
        }
    }

    //初始化集合
    private void initImageList() {
        mRCList_1 = new ArrayList<UploadFileInfo>();
        mRCList_2 = new ArrayList<UploadFileInfo>();
        mRCList_3 = new ArrayList<UploadFileInfo>();
        mRCList_4 = new ArrayList<UploadFileInfo>();
        mRCList_5 = new ArrayList<UploadFileInfo>();
        remarkUploadImgInfoList = new ArrayList<UploadFileInfo>();
        soundInfoList = new ArrayList<UploadFileInfo>();
        commentUploadImgInfoList = new ArrayList<UploadFileInfo>();
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
                        //尽职说明
                        JSONObject ddObj = json.getJSONObject("applyInfo");
                        if (ddObj != null && !ddObj.isEmpty()) {
                            et_comment.setText(ddObj.getString("ddDescription"));
                            List<UploadFileInfo> uploadFileInfos = JSON.parseArray(json.getString("fileList"), UploadFileInfo.class);
                            initServerFile(uploadFileInfos);
                        }

                        List<ContactInfo> contactInfoList = JSON.parseArray(json.getString("contactInfoList"), ContactInfo.class);
                        if (contactInfoList != null) {
                            int size = contactInfoList.size();
                            ContactInfo contactInfo = null;
                            for (int i = 0; i < size; i++) {
                                contactInfo = contactInfoList.get(i);
                                mParentId[i] = contactInfo.getId();
                                //尽职说明
                                int fileSize = -1;
                                if (contactInfo.getFileList() != null)
                                    fileSize = contactInfo.getFileList().size();
                                //判断是否是有效企业 企业名称
                                if (fileSize > 0 || !JDAppUtil.isEmpty(contactInfo.getRelation())) {
                                    switch (i) {
                                        case 0:
                                            et_remark.setText(contactInfo.getRemark());
                                            initServerFile(contactInfo.getFileList());
                                            break;
                                        case 1:
                                        case 2:
                                        case 3:
                                        case 4:
                                            addRCPerson(i);
                                            initServerFile(contactInfo.getFileList());
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            soundAdapter.setParentId(mParentId[0]);
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
            String smallImgPath = getFilePath(random,fileType.getValue(), false);
            String bigImgPath = getFilePath(random,fileType.getValue(), true);
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
        } else if (fType == UploadFileType.COMMENT) {
            commentImgsAdapter.refreshData();
        } else if (fType == UploadFileType.REMARK) {
            remarkImgsAdapter.refreshData();
        } else if (fType == UploadFileType.SOUND) {
            soundAdapter.refreshData();
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
        } else if (fType == UploadFileType.COMMENT) {
            commentUploadImgInfoList.add(0, imgInfo);
        } else if (fType == UploadFileType.REMARK) {
            remarkUploadImgInfoList.add(0, imgInfo);
        } else if (fType == UploadFileType.SOUND) {
            soundInfoList.add(0, imgInfo);
        } else {

        }
        mHandler.sendMessage(mHandler.obtainMessage(0, imgInfo));
    }

    /*
  * 必须输入判断
  * */
    private boolean requiredInput() {
        //关系类型  与关系人身份证件 备注 必填
        if (!isUploadSuccess(mRCList_1)) {
            JDToast.showLongText(getActivity(), "请上传关系人身份证件图片");
            return false;
        }

        if (isShow2) {
            if (!isUploadSuccess(mRCList_2)) {
                JDToast.showLongText(getActivity(), "请上传关系人身份证件图片");
                return false;
            }
        }

        if (isShow3) {
            if (!isUploadSuccess(mRCList_3)) {
                JDToast.showLongText(getActivity(), "请上传关系人身份证件图片");
                return false;
            }
        }

        if (isShow4) {
            if (!isUploadSuccess(mRCList_4)) {
                JDToast.showLongText(getActivity(), "请上传关系人身份证件图片");
                return false;
            }
        }

        if (isShow5) {
            if (!isUploadSuccess(mRCList_5)) {
                JDToast.showLongText(getActivity(), "请上传关系人身份证件图片");
                return false;
            }
        }
        return true;
    }

    /*
     * 保存数据
     * */
    public void saveDataSubmit() {
        boolean isSelctedSpouse = false;
        List<ContactInfo> contactInfoList = new ArrayList<ContactInfo>();

       //if (!requiredInput())
       //     return;

        if (sp_relation_type_1.getSelectedItem().toString().equals(mSpouse))
            isSelctedSpouse = true;

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setRemark(et_remark.getText().toString());
        contactInfo.setRelation(sp_relation_type_1.getSelectedItemPosition() + 1 + "");
        contactInfoList.add(contactInfo);

        if (isShow2) {

            if (sp_relation_type_2.getSelectedItem().toString().equals(mSpouse))
                isSelctedSpouse = true;

            contactInfo = new ContactInfo();
            contactInfo.setRelation(sp_relation_type_2.getSelectedItemPosition() + 1 + "");
            contactInfoList.add(contactInfo);
        } else {
            if (isShow3) {
                if (sp_relation_type_3.getSelectedItem().toString().equals(mSpouse))
                    isSelctedSpouse = true;
                contactInfo = new ContactInfo();
                contactInfo.setRelation(sp_relation_type_3.getSelectedItemPosition() + 1 + "");
                contactInfoList.add(contactInfo);
            } else {
                if (isShow4) {
                    if (sp_relation_type_4.getSelectedItem().toString().equals(mSpouse))
                        isSelctedSpouse = true;

                    contactInfo = new ContactInfo();
                    contactInfo.setRelation(sp_relation_type_4.getSelectedItemPosition() + 1 + "");
                    contactInfoList.add(contactInfo);
                } else {
                    if (isShow5) {
                        if (sp_relation_type_5.getSelectedItem().toString().equals(mSpouse))
                            isSelctedSpouse = true;

                        contactInfo = new ContactInfo();
                        contactInfo.setRelation(sp_relation_type_5.getSelectedItemPosition() + 1 + "");
                        contactInfoList.add(contactInfo);
                    }
                }
            }
        }

        //必须要有一个是配偶
        if (!isSelctedSpouse) {
            JDToast.showLongText(getActivity(), "必须有一个关系类型为配偶");
            return;
        }
        // relation; // 1 配偶 2直系亲属 3合伙人 4财务 5其他
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        String ss = JSON.toJSONString(contactInfoList);
        String b = et_comment.getText().toString();
        formparams.add(new BasicNameValuePair("contactInfoListJson", ss));
        formparams.add(new BasicNameValuePair("ddDescription", b));
        HttpClientUtil.put(getActivity(), HttpRequestURL.updateSocialRelationURL + getBorrowRequestId(), formparams, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                JDToast.showLongText(getActivity(), "社会关系信息保存成功");
            }
        }));
    }

    public static SocialRelationFragment newInstance() {
        SocialRelationFragment socialRelationFragment = new SocialRelationFragment();
        return socialRelationFragment;
    }
}
