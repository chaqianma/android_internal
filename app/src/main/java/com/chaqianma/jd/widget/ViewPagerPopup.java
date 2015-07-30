package com.chaqianma.jd.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.ImagePagerAdapter;
import com.chaqianma.jd.model.UploadImgInfo;

import java.util.List;

/**
 * Created by zhangxd on 2015/7/29.
 */
public class ViewPagerPopup extends PopupWindow implements View.OnClickListener {

    private View mView;
    private OnViewPagerDialogListener listener;
    private ViewPager mPhotoViewPager;
    private List<UploadImgInfo> mUploadImgInfoList = null;
    private ImagePagerAdapter mImagePagerAdapter=null;
    public ViewPagerPopup(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.activity_img_gallery, null);
        this.setContentView(mView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPhotoViewPager = (GalleryViewPager) mView.findViewById(R.id.photoViewPager);
        mImagePagerAdapter=new ImagePagerAdapter();
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
    public void setUploadImgList(List<UploadImgInfo> uploadImgInfoList,int idx) {
        mImagePagerAdapter.setUploadedImgList(uploadImgInfoList);
        mImagePagerAdapter.refreshData();
        mPhotoViewPager.setCurrentItem(idx);
    }

    /**
     * Dialog按钮回调接口
     */
    public interface OnViewPagerDialogListener {
        void onDeletePhoto();
    }

    //设置监听器
    public void setViewPagerDialogListener(OnViewPagerDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_delete:
                listener.onDeletePhoto();
                break;
            default:
                break;
        }
        dismiss();
    }
}
