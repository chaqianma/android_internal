package com.chaqianma.jd.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.activity.MainActivity;
import com.chaqianma.jd.adapters.ImgsGridViewAdapter;
import com.chaqianma.jd.adapters.SoundGridViewAdapter;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.model.UploadFileType;
import com.chaqianma.jd.model.UploadStatus;
import com.chaqianma.jd.widget.PhotoPopup;
import com.chaqianma.jd.widget.ViewPagerPopup;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.util.List;

/**
 * Created by zhangxd on 2015/7/21.
 */
public class BaseFragment extends Fragment implements PhotoPopup.OnDialogListener,
        ViewPagerPopup.OnViewPagerDialogListener, ImgsGridViewAdapter.iOnClickImgListener, SoundGridViewAdapter.iOnClickSoundListener {

    private String mBorrowRequestId = null;

    protected PhotoPopup mPopup;
    protected ViewPagerPopup mViewPagerPopup;
    //获取SDK中的图片
    protected static final int REQUEST_SDK_IMGS = 1001;
    //拍照
    protected static final int REQUEST_TAKE_PHOTO = 1002;
    //图片存放路径
    protected String mImgDirPath = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImgDir();
        mPopup = new PhotoPopup(getActivity());
        mPopup.setDialogListener(this);
        mViewPagerPopup = new ViewPagerPopup(getActivity());
        mViewPagerPopup.setViewPagerDialogListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void setTitle(String title) {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null) {
            View view = fragmentActivity.findViewById(R.id.top_title);
            if (view != null)
                ((TextView) view).setText(title);
        }
    }

    protected void setShowFragment(String fragmentTag, boolean isAddToBackStack) {
        /*FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof MainActivity) {
            MainActivity activity = (MainActivity) fragmentActivity;
            activity.setShowFragment(fragmentTag, isAddToBackStack);
        }*/
    }

    protected void startActivity(Class<?> toClass) {
        startActivity(toClass, "");
    }

    protected void startActivity(Class<?> toClass, String value) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), toClass);
        if (value != null && value.length() > 0) {
            intent.putExtra(Constants.TOVALUEKEY, value);
        }
        startActivity(intent);
    }

    protected String getBorrowRequestId() {
        if (AppData.getInstance().getBorrowRequestInfo() != null)
            mBorrowRequestId = AppData.getInstance().getBorrowRequestInfo().getBorrowRequestId();
        return mBorrowRequestId;
    }

    protected void startActivity(Class<?> toClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), toClass);
        if (bundle != null) {
            intent.putExtra(Constants.TOVALUEKEY, bundle);
        }
        startActivity(intent);
    }

    /*
    * 初始化图片文件夹
    * */
    private void initImgDir() {
        if (AppData.getInstance().getBorrowRequestInfo() != null) {
            mBorrowRequestId = AppData.getInstance().getBorrowRequestInfo().getBorrowRequestId();
            mImgDirPath = Constants.DIRPATH + "/" + getBorrowRequestId();
            File file = new File(mImgDirPath);
            if (!file.getParentFile().exists()) {
                file.mkdirs();
            }
        }
    }

    //得到保存图片地址
    protected String getSaveFilePath(UploadFileInfo uploadFileInfo) {
        return mImgDirPath + "/" + uploadFileInfo.getFileType() + "_" + System.currentTimeMillis() + "." + uploadFileInfo.getFileExt();
    }

    //得到图片地址
    protected String getFilePath(String randomName, int fileType, boolean isBigImgPath) {
        String path = fileType + "_" + randomName + ".jpg";
        if (!isBigImgPath)
            path = "thumb_" + path;
        return mImgDirPath + "/" + path;
    }

    /*
    * 获取上传文件 entity
    * */
    protected HttpEntity getUploadEntity(UploadFileInfo fileInfo, String parentId) {
        return getUploadEntity(fileInfo, parentId, true);
    }

    /*
    * 获取上传文件 entity  排除个人信息的 remak 与 YY 的冲突
    * */
    protected HttpEntity getUploadEntity(UploadFileInfo fileInfo, String parentId, boolean isPerson) {
        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
        ContentBody fileBody = new FileBody(new File(fileInfo.getBigImgPath()));
        entity.addPart("files", fileBody);
        int ftype = fileInfo.getFileType();
        if (ftype == UploadFileType.SOUND.getValue())
            ftype = UploadFileType.REMARK.getValue();
        entity.addPart("fileType", new StringBody("" + ftype, ContentType.DEFAULT_TEXT));
        entity.addPart("parentTableName", new StringBody(fileInfo.getParentTableName(), ContentType.DEFAULT_TEXT));
        entity.addPart("parentId", new StringBody(parentId, ContentType.DEFAULT_TEXT));
        return entity.build();
    }

    /*
 * 判断是否有上传成功图片
 * */
    protected boolean isUploadSuccess(List<UploadFileInfo> uploadFileInfoList) {
        boolean isSuccess = false;
        if (uploadFileInfoList != null) {
            int size = uploadFileInfoList.size();
            UploadFileInfo uploadFileInfo = null;
            for (int i = 0; i < size; i++) {
                uploadFileInfo = uploadFileInfoList.get(i);
                //uploadFileInfo.iServer() &&
                if (uploadFileInfo.getStatus() == UploadStatus.SUCCESS.getValue()) {
                    isSuccess = true;
                    break;
                }
            }
        }
        return isSuccess;
    }

    //选择本地照片
    @Override
    public void onChoosePhoto() {

    }

    //照相
    @Override
    public void onTakePhoto() {

    }

    //删除图片
    @Override
    public void onDeletePhoto(UploadFileInfo uploadFileInfo) {
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onImgClick(List<UploadFileInfo> uploadImgInfoList, int idx) {

    }

    //开始录音
    @Override
    public void onStartRecord() {

    }

    //结束录音
    @Override
    public void onStopRecord() {

    }
}
