package com.chaqianma.jd.adapters;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.graphics.Bitmap;
import com.chaqianma.jd.model.UploadImgInfo;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.util.List;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by zhangxd on 2015/7/29.
 * <p/>
 * 图片查看数据源
 */
public class ImagePagerAdapter extends PagerAdapter {

    private List<UploadImgInfo> uploadedImgList;

    public ImagePagerAdapter() {

    }

    public ImagePagerAdapter(List<UploadImgInfo> uploadedImgList) {
        this.uploadedImgList = uploadedImgList;
    }

    //设置数据源
    public void setUploadedImgList(List<UploadImgInfo> uploadedImgList)
    {
        this.uploadedImgList=uploadedImgList;
    }

    //刷新数据
    public void refreshData()
    {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (uploadedImgList != null)
            return uploadedImgList.size()-1;
        return 0;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        UploadImgInfo uploadImgItem = uploadedImgList.get(position);
        String imgPath = uploadImgItem.getBigImgPath();
        if (imgPath!=null && imgPath.length()>0) {
            Bitmap img = BitmapFactory.decodeFile(imgPath);
            if (img != null) {
                photoView.setImageBitmap(img);
            } else {
                //有可能没有加载出来
                //可以添加个默认图标
            }
        } else {
            //有可能没有加载出来
            //可以添加个默认图标
        }

        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
