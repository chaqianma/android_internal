package com.chaqianma.jd.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.model.ImageUploadStatus;
import com.chaqianma.jd.model.UploadImgInfo;
import com.chaqianma.jd.utils.ImageUtil;

import java.util.List;

/**
 * Created by zhangxd on 2015/7/29.
 * <p/>
 * 图片
 */
public class ImgsGridViewAdapter extends BaseAdapter {

    private List<UploadImgInfo> mUploadImgInfoList = null;
    private Context mContext = null;

    private iOnClickImgListener mIonClickImgListener;

    public interface iOnClickImgListener {
        void onImgClick(List<UploadImgInfo> uploadImgInfoList,int idx);
    }

    public ImgsGridViewAdapter(Context context, List<UploadImgInfo> uploadImgInfoList) {
        this.mContext = context;
        this.mUploadImgInfoList = uploadImgInfoList;
    }

    //设置数据源
    public void setUploadImgInfoList(List<UploadImgInfo> uploadImgInfoList) {
        this.mUploadImgInfoList = uploadImgInfoList;
    }

    //刷新数据源
    public void refreshData() {
        notifyDataSetChanged();
    }

    //设置点击图片监听
    public void setOnClickImgListener(iOnClickImgListener mIonClickImgListener) {
        this.mIonClickImgListener = mIonClickImgListener;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (mUploadImgInfoList != null)
            return mUploadImgInfoList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        HolderView holderView = null;
        if (position < mUploadImgInfoList.size()) {
           /* if (null == convertView) {
                holderView = new HolderView();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_img_item, null);
                holderView.imageView = (ImageView) convertView.findViewById(R.id.img_main);
                holderView.img_fail = (ImageView) convertView.findViewById(R.id.img_fail);
                holderView.img_success = (ImageView) convertView.findViewById(R.id.img_success);
                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }*/
            //暂时不复用View
            holderView = new HolderView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_img_item, null);
            holderView.imageView = (ImageView) convertView.findViewById(R.id.img_main);
            holderView.img_fail = (ImageView) convertView.findViewById(R.id.img_fail);
            holderView.img_success = (ImageView) convertView.findViewById(R.id.img_success);
            final UploadImgInfo imgInfo = mUploadImgInfoList.get(position);
            if(!imgInfo.isDefault()) {
                if (imgInfo.getImgStatus() == ImageUploadStatus.SUCCESS.getValue()) {
                    holderView.img_fail.setVisibility(View.GONE);
                    holderView.img_success.setVisibility(View.VISIBLE);
                } else {
                    holderView.img_fail.setVisibility(View.VISIBLE);
                    holderView.img_success.setVisibility(View.GONE);
                }
            }
            holderView.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIonClickImgListener != null)
                        mIonClickImgListener.onImgClick(mUploadImgInfoList,position);
                }
            });
            ShowImgAsyncTask task = new ShowImgAsyncTask(holderView.imageView);
            task.execute(imgInfo);
        }
        return convertView;
    }

    private class ShowImgAsyncTask extends AsyncTask<UploadImgInfo, Integer, Bitmap> {
        private ImageView imageView;

        public ShowImgAsyncTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                imageView.setImageResource(R.drawable.icon_img_add);
            } else {
                imageView.setImageBitmap(result);
            }
        }

        @Override
        protected Bitmap doInBackground(UploadImgInfo... params) {
            if (params.length > 0) {
                UploadImgInfo imgInfo = params[0];
                if (imgInfo.iServer()) {
                    //服务器上图片
                } else {
                    if (imgInfo.isDefault()) {
                        //默认图片
                        return null;
                    } else {
                        //本地图片
                        return ImageUtil.getImageThumbnail(imgInfo.getSmallImgPath(), 80, 80);
                    }
                }
            }
            return null;
        }
    }

    //复用View
    class HolderView {
        ImageView imageView;
        ImageView img_success;
        ImageView img_fail;
    }
}
