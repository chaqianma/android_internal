package com.chaqianma.jd.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.ImagePagerAdapter;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.UploadFileType;
import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangxd on 2015/7/29.
 */
public class ViewPagerPopup extends PopupWindow implements View.OnClickListener {

    private View mView;
    private Context mContext;
    private OnViewPagerDialogListener listener;
    private ViewPager mPhotoViewPager;
    private List<UploadFileInfo> mUploadImgInfoList = null;
    private ImagePagerAdapter mImagePagerAdapter = null;

    public ViewPagerPopup(Context context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.activity_img_gallery, null);
        this.setContentView(mView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPhotoViewPager = (GalleryViewPager) mView.findViewById(R.id.photoViewPager);
        mImagePagerAdapter = new ImagePagerAdapter();
        mPhotoViewPager.setAdapter(mImagePagerAdapter);
        Button btn_delete = (Button) mView.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable(0xb0000000);//0xb0000000
        // 设置SelectPicPopupWindow弹出窗体的背景
        //this.setBackgroundDrawable(dw);//半透明颜色
    }

    //设置数据源
    public void setUploadImgList(List<UploadFileInfo> uploadImgInfoList, int idx) {
        this.mUploadImgInfoList = uploadImgInfoList;
        mImagePagerAdapter.setUploadedImgList(uploadImgInfoList);
        mImagePagerAdapter.refreshData();
        mPhotoViewPager.setCurrentItem(idx);
    }

    /**
     * Dialog按钮回调接口
     */
    public interface OnViewPagerDialogListener {
        void onDeletePhoto(UploadFileType fileType, int delIdx);
    }

    //设置监听器
    public void setViewPagerDialogListener(OnViewPagerDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                try {
                    final int curIdx=mPhotoViewPager.getCurrentItem();
                    if(curIdx>=mUploadImgInfoList.size())
                        return;
                    final UploadFileInfo uploadImgInfo = mUploadImgInfoList.get(curIdx);
                    if (mUploadImgInfoList.size() == 0 && uploadImgInfo.isDefault()) {
                        JDToast.showLongText(mContext, "该张是默认图片，不能删除,有时间把默认排除");
                        this.dismiss();
                        return;
                    }
                    if (uploadImgInfo != null && !uploadImgInfo.isDefault()) {
                        JDAlertDialog.showAlertDialog(mContext, "确定要删除吗？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mUploadImgInfoList.remove(uploadImgInfo);
                                //删除图片
                                HashMap<String, Object> argMaps = new HashMap<String, Object>();
                                argMaps.put("delete", uploadImgInfo.getFileId());
                                HttpClientUtil.delete(HttpRequestURL.deleteFileUrl, argMaps, new JDHttpResponseHandler(mContext, new ResponseHandler() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        JDToast.showLongText(mContext, "图片删除成功");
                                    }
                                }));
                                if(mUploadImgInfoList.size()==0) {
                                    ViewPagerPopup.this.dismiss();
                                }else{
                                    mImagePagerAdapter.notifyDataSetChanged();
                                    listener.onDeletePhoto(UploadFileType.valueOf(uploadImgInfo.getFileType()), curIdx);
                                }
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    } else {
                        JDToast.showLongText(mContext, "默认图片不能删除，先提示出来，有时间把默认排除");
                    }
                } catch (Exception e) {

                }
                break;
            default:
                break;
        }
    }
}
