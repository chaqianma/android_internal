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
import com.chaqianma.jd.adapters.SoundGridViewAdapter;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.ContactInfo;
import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.model.UploadFileType;
import com.chaqianma.jd.model.UploadStatus;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDFileResponseHandler;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDToast;

import org.apache.http.NameValuePair;
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
public class SocialRelationFragment extends BaseFragment {
    @InjectView(R.id.linear_container)
    LinearLayout linear_container;
    @InjectView(R.id.layout_relation_1)
    LinearLayout layout_relation_1;
    @InjectView(R.id.img_social_add_1)
    ImageView img_social_add_1;
    ImageView img_social_add_2;
    ImageView img_social_add_3;
    ImageView img_social_add_4;
    ImageView img_social_add_5;
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
    private boolean isShow1 = false, isShow2 = false, isShow3 = false, isShow4 = false, isShow5 = false;
    //图片标签
    private UploadFileType fileType = UploadFileType.CARD;
    //用于标识选择的是哪个类型
    private int selIdxTag = -1;
    //父ID
    private String[] mParentId = new String[5];
    //Apply_info
    private String mApplyInfoId = null;
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
            soundAdapter = new SoundGridViewAdapter(getActivity(), soundInfoList, Constants.CONTACT_INFO);
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

    @OnClick(R.id.img_social_add_1)
    void onAddRcClick() {
        addRCPerson();
    }

    /*
    * 添加联系人
    * */
    private void addRCPerson() {
        if (!isShow1) {
            if (!requiredInput())
                return;
            initView(0);
        } else if (!isShow2) {
            if (!requiredInput())
                return;
            initView(1);
        } else if (!isShow3) {
            if (!requiredInput())
                return;
            initView(2);
        } else if (!isShow4) {
            if (!requiredInput())
                return;
            initView(3);
        } else if (!isShow5) {
            if (!requiredInput())
                return;
            initView(4);
        } else {

        }
    }

    /*
    * 初始化view
    * */

    private void initView(int idxTag) {
        switch (idxTag) {
            case 0: {
                isShow1 = true;
                layout_relation_1.setVisibility(View.VISIBLE);
            }
            break;
            case 1: {
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
            }
            break;
            case 2: {
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
            }
            break;
            case 3: {
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
            }
            break;
            case 4: {
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
            break;
            default:
                break;
        }
    }

    /*
   * 添加联系人
   * */
    private void addRCPerson(final int rcIdx) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView(rcIdx);
            }
        });
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
        HttpClientUtil.get(requestPath, null, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(final Object o) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (o == null)
                                return;
                            JSONObject json = JSON.parseObject(o.toString());
                            //尽职说明
                            final JSONObject applyObj = json.getJSONObject("applyInfo");
                            mApplyInfoId = applyObj.getString("id");
                            if (applyObj != null && !applyObj.isEmpty()) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        et_comment.setText(applyObj.getString("ddDescription"));
                                        List<UploadFileInfo> uploadFileInfos = JSON.parseArray(applyObj.getString("fileList"), UploadFileInfo.class);
                                        initServerFile(uploadFileInfos, -1);
                                    }
                                });
                            }
                            //获取联系人信息
                            getContactInfoList(JSON.parseArray(json.getString("contactInfoList"), ContactInfo.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
        ));
    }

    /*
    * 获取联系人信息
    * */
    private void getContactInfoList(List<ContactInfo> contactInfoList) {
        if (contactInfoList != null) {
            int size = contactInfoList.size();
            boolean isHasContact = false;
            for (int i = 0; i < size; i++) {
                final int idx = i;
                final ContactInfo contactInfo = contactInfoList.get(i);
                mParentId[i] = contactInfo.getId();
                int fileSize = -1;
                if (contactInfo.getFileList() != null)
                    fileSize = contactInfo.getFileList().size();
                //判断是否是有效企业 企业名称
                if (fileSize > 0 || contactInfo.getRelation() != -1) {
                    isHasContact = true;
                    //初始化控件
                    addRCPerson(idx);
                    //初始化服务器文件
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initServerFile(contactInfo.getFileList(), idx);
                            switch (idx) {
                                case 0:
                                    et_remark.setText(contactInfo.getRemark());
                                    sp_relation_type_1.setSelection(contactInfo.getRelation() - 1);
                                    break;
                                case 1:
                                    sp_relation_type_2.setSelection(contactInfo.getRelation() - 1);
                                    break;
                                case 2:
                                    sp_relation_type_3.setSelection(contactInfo.getRelation() - 1);
                                    break;
                                case 3:
                                    sp_relation_type_4.setSelection(contactInfo.getRelation() - 1);
                                    break;
                                case 4:
                                    sp_relation_type_5.setSelection(contactInfo.getRelation() - 1);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });

                }
            }
            soundAdapter.setParentId(mParentId[0]);
            if (!isHasContact) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layout_relation_1.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }

    @Override
    public void onImgClick(List<UploadFileInfo> uploadImgInfoList, int idx) {
        if (idx < uploadImgInfoList.size()) {
            UploadFileInfo imgInfo = uploadImgInfoList.get(idx);
            fileType = UploadFileType.valueOf(imgInfo.getFileType());
            selIdxTag = imgInfo.getIdxTag();
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

    /*
     *获取服务器文件信息
    */
    private void initServerFile(List<UploadFileInfo> fileInfoList, int idxTag) {
        if (fileInfoList != null && fileInfoList.size() > 0) {
            for (UploadFileInfo uploadFileInfo : fileInfoList) {
                uploadFileInfo.setiServer(true);
                uploadFileInfo.setIdxTag(idxTag);
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
        });
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
                                new Thread(new BaseFragment.ImgRunable(cursor.getString(colunm_index), Constants.CONTACT_INFO, fileType, selIdxTag, new UpdateUIHandler())).start();
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
                    //mHandler.post(mRunnable);
                    new Thread(new BaseFragment.ImgRunable(Constants.TEMPPATH, Constants.CONTACT_INFO, fileType, selIdxTag, new UpdateUIHandler())).start();
                    break;
                default:
                    break;
            }
        }
    }

    /*
    * 获取ParentId
    * */
    private String getParentId(int idxTag) {
        String parentId = "";
        switch (idxTag) {
            case 0:
                parentId = mParentId[sp_relation_type_1.getSelectedItemPosition()];
                break;
            case 1:
                parentId = mParentId[sp_relation_type_2.getSelectedItemPosition()];
                break;
            case 2:
                parentId = mParentId[sp_relation_type_3.getSelectedItemPosition()];
                break;
            case 3:
                parentId = mParentId[sp_relation_type_4.getSelectedItemPosition()];
                break;
            case 4:
                parentId = mParentId[sp_relation_type_5.getSelectedItemPosition()];
                break;
            default:
                break;
        }
        return parentId;
    }

    //上传图片
    private void uploadImg(final UploadFileInfo fileInfo) {
        try {
            String parentId = null;
            if (fileType == UploadFileType.COMMENT) {
                fileInfo.setParentTableName(Constants.APPLY_INFO);
                parentId = mApplyInfoId;
            } else {
                parentId = mParentId[fileInfo.getIdxTag()];
            }
            HttpClientUtil.post(getActivity(), HttpRequestURL.uploadImgUrl, getUploadEntity(fileInfo, parentId), new JDHttpResponseHandler(getActivity(), new ResponseHandler<UploadFileInfo>() {
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
        setSpinnerEnabled(fileInfo);
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
    * 控件下拉框的可用性与否
    * */
    @Override
    public void onRefreshSpinner(UploadFileInfo fileInfo) {
        super.onRefreshSpinner(fileInfo);
        setSpinnerEnabled(fileInfo);
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
        refreshData(imgInfo);
    }

    /*
  * 必须输入判断
  * */
    private boolean requiredInput() {
        //关系类型  与关系人身份证件 备注 必填
        if (isShow1) {
            /*if (!isUploadSuccess(mRCList_1)) {
                JDToast.showLongText(getActivity(), "请上传关系人身份证件图片");
                return false;
            }*/
        }
        if (isShow2) {
           /* if (!isUploadSuccess(mRCList_2)) {
                JDToast.showLongText(getActivity(), "请上传关系人身份证件图片");
                return false;
            }*/
        }

        if (isShow3) {
            /*if (!isUploadSuccess(mRCList_3)) {
                JDToast.showLongText(getActivity(), "请上传关系人身份证件图片");
                return false;
            }*/
        }

        if (isShow4) {
           /* if (!isUploadSuccess(mRCList_4)) {
                JDToast.showLongText(getActivity(), "请上传关系人身份证件图片");
                return false;
            }*/
        }

        if (isShow5) {
           /* if (!isUploadSuccess(mRCList_5)) {
                JDToast.showLongText(getActivity(), "请上传关系人身份证件图片");
                return false;
            }*/
        }
        return true;
    }

    /*
     * 保存数据
     * */
    public void saveDataSubmit() {
        boolean isSelctedSpouse = false;
        List<ContactInfo> contactInfoList = new ArrayList<ContactInfo>();
        if (!JDAppUtil.getIsAuthSuccess()) {
            JDToast.showLongText(getActivity(), "未实名认证，不能保存数据");
            return;
        }
        if (!requiredInput())
            return;
        /*if (JDAppUtil.isEmpty(et_remark.getText().toString())) {
            JDToast.showLongText(getActivity(), "请输入备注信息");
            return;
        }*/
        ContactInfo contactInfo = null;
        if (isShow1) {
            if (sp_relation_type_1.getSelectedItem().toString().equals(mSpouse))
                isSelctedSpouse = true;

            contactInfo = new ContactInfo();
            contactInfo.setRemark(et_remark.getText().toString());
            contactInfo.setId(mParentId[0]);
            contactInfo.setRelation(sp_relation_type_1.getSelectedItemPosition() + 1);
            contactInfoList.add(contactInfo);
        }
        if (isShow2) {
            if (sp_relation_type_2.getSelectedItem().toString().equals(mSpouse))
                isSelctedSpouse = true;
            contactInfo = new ContactInfo();
            contactInfo.setRelation(sp_relation_type_2.getSelectedItemPosition() + 1);
            contactInfo.setId(mParentId[1]);
            contactInfoList.add(contactInfo);
        }
        if (isShow3) {
            if (sp_relation_type_3.getSelectedItem().toString().equals(mSpouse))
                isSelctedSpouse = true;
            contactInfo = new ContactInfo();
            contactInfo.setId(mParentId[2]);
            contactInfo.setRelation(sp_relation_type_3.getSelectedItemPosition() + 1);
            contactInfoList.add(contactInfo);
        }
        if (isShow4) {
            if (sp_relation_type_4.getSelectedItem().toString().equals(mSpouse))
                isSelctedSpouse = true;

            contactInfo = new ContactInfo();
            contactInfo.setId(mParentId[3]);
            contactInfo.setRelation(sp_relation_type_4.getSelectedItemPosition() + 1);
            contactInfoList.add(contactInfo);
        }
        if (isShow5) {
            if (sp_relation_type_5.getSelectedItem().toString().equals(mSpouse))
                isSelctedSpouse = true;

            contactInfo = new ContactInfo();
            contactInfo.setId(mParentId[4]);
            contactInfo.setRelation(sp_relation_type_5.getSelectedItemPosition() + 1);
            contactInfoList.add(contactInfo);
        }
        //必须要有一个是配偶
        if (!isSelctedSpouse) {
            JDToast.showLongText(getActivity(), "必须有一个关系类型为配偶");
            return;
        }
        // relation; // 1 配偶 2直系亲属 3合伙人 4财务 5其他
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();

        formparams.add(new BasicNameValuePair("contactInfoListJson", JSON.toJSONString(contactInfoList)));
        formparams.add(new BasicNameValuePair("ddDescription", et_comment.getText().toString()));
        HttpClientUtil.put(getActivity(), HttpRequestURL.updateSocialRelationURL + getBorrowRequestId(), formparams, new JDHttpResponseHandler(getActivity(), new ResponseHandler() {
            @Override
            public void onSuccess(Object o) {
                JDToast.showLongText(getActivity(), "社会关系信息保存成功");
            }
        }));
    }

    /*
    * 设置下拉框是否可用
    * */
    private void setSpinnerEnabled(UploadFileInfo fileInfo) {
        if (fileInfo.getFileType() == UploadFileType.CARD.getValue()) {
            switch (fileInfo.getIdxTag()) {
                case 0:
                    sp_relation_type_1.setEnabled(!isUploadSuccess(mRCList_1));
                    break;
                case 1:
                    sp_relation_type_2.setEnabled(!isUploadSuccess(mRCList_2));
                    break;
                case 2:
                    sp_relation_type_3.setEnabled(!isUploadSuccess(mRCList_3));
                    break;
                case 3:
                    sp_relation_type_4.setEnabled(!isUploadSuccess(mRCList_4));
                    break;
                case 4:
                    sp_relation_type_5.setEnabled(!isUploadSuccess(mRCList_5));
                    break;
                default:
                    break;
            }
        }
    }

    public static SocialRelationFragment newInstance() {
        SocialRelationFragment socialRelationFragment = new SocialRelationFragment();
        return socialRelationFragment;
    }
}
