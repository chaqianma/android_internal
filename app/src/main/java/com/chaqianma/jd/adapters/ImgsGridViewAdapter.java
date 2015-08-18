package com.chaqianma.jd.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.UploadStatus;
import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.ImageUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDAlertDialog;
import com.chaqianma.jd.widget.JDToast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangxd on 2015/7/29.
 * <p/>
 * 图片
 */
public class ImgsGridViewAdapter extends BaseAdapter {

    private List<UploadFileInfo> mUploadImgInfoList = null;
    private Context mContext = null;
    private ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4),
            new ThreadPoolExecutor.DiscardOldestPolicy());
    private iOnClickImgListener mIonClickImgListener;
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.icon_img_add)            //加载图片时的图片
                    //.showImageForEmptyUri(R.drawable.ic_empty)         //没有图片资源时的默认图片
                    //.showImageOnFail(R.drawable.ic_error)              //加载失败时的图片
            .cacheInMemory(false)                               //启用内存缓存
            .cacheOnDisk(false)                                 //启用外存缓存
            .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                    //.displayer(new RoundedBitmapDisplayer(20))         //设置显示风格这里是圆角矩形
            .build();

    public interface iOnClickImgListener {
        void onImgClick(List<UploadFileInfo> uploadImgInfoList, int idx);
    }

    public ImgsGridViewAdapter(Context context, List<UploadFileInfo> uploadImgInfoList) {
        this.mContext = context;
        this.mUploadImgInfoList = uploadImgInfoList;
    }

    //设置数据源
    public void setUploadImgInfoList(List<UploadFileInfo> uploadImgInfoList) {
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

    /*
   * 删除图片
   * */
    private void deleteFile(UploadFileInfo fileInfo) {
        try {
            if (!JDAppUtil.isEmpty(fileInfo.getBigImgPath())) {
                File bigfile = new File(fileInfo.getBigImgPath());
                if (bigfile.exists())
                    bigfile.delete();
            }
            if (!JDAppUtil.isEmpty(fileInfo.getSmallImgPath())) {
                File smallfile = new File(fileInfo.getSmallImgPath());
                if (smallfile.exists())
                    smallfile.delete();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        HolderView holderView = null;
        if (position < mUploadImgInfoList.size()) {
            if (null == convertView) {
                holderView = new HolderView();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_img_item, null);
                holderView.imageView = (ImageView) convertView.findViewById(R.id.img_main);
                holderView.img_fail = (ImageView) convertView.findViewById(R.id.img_fail);
                holderView.img_success = (ImageView) convertView.findViewById(R.id.img_success);
                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }
            //暂时不复用View
            //holderView = new HolderView();
            //convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_img_item, null);
            //holderView.imageView = (ImageView) convertView.findViewById(R.id.img_main);
            //holderView.img_fail = (ImageView) convertView.findViewById(R.id.img_fail);
            //holderView.img_success = (ImageView) convertView.findViewById(R.id.img_success);
            try{
                final UploadFileInfo imgInfo = mUploadImgInfoList.get(position);
                holderView.img_fail.setVisibility(View.GONE);
                holderView.img_success.setVisibility(View.GONE);
                if (!imgInfo.isDefault()) {
                    if (imgInfo.getStatus() == UploadStatus.SUCCESS.getValue()) {
                        holderView.img_fail.setVisibility(View.GONE);
                        holderView.img_success.setVisibility(View.VISIBLE);
                    } else {
                        holderView.img_fail.setVisibility(View.VISIBLE);
                        holderView.img_success.setVisibility(View.GONE);
                    }
                }
                holderView.imageView.setOnClickListener(null);
                holderView.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mIonClickImgListener != null)
                            mIonClickImgListener.onImgClick(mUploadImgInfoList, position);
                    }
                });
                holderView.imageView.setLongClickable(true);
                holderView.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        JDAlertDialog.showAlertDialog(mContext, "确定删除该张图片吗？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(imgInfo.isDefault())
                                {
                                    JDToast.showLongText(mContext,"默认图片不能删除");
                                    return;
                                }
                                if (imgInfo.iServer()) {
                                    //去服务端删除
                                    HttpClientUtil.delete(HttpRequestURL.deleteFileUrl + imgInfo.getFileId(), null, new JDHttpResponseHandler(mContext, new ResponseHandler() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            mUploadImgInfoList.remove(imgInfo);
                                            deleteFile(imgInfo);
                                            refreshData();
                                            JDToast.showLongText(mContext, "图片删除成功");
                                        }
                                        @Override
                                        public void onFailure(String data) {
                                            super.onFailure(data);
                                            JDToast.showLongText(mContext, "图片删除出错");
                                        }
                                    }));
                                } else {
                                    //本地删除
                                    mUploadImgInfoList.remove(imgInfo);
                                    deleteFile(imgInfo);
                                    refreshData();
                                    JDToast.showLongText(mContext, "图片删除成功");
                                }
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        });
                        return true;
                    }
                });
                holderView.imageView.setImageDrawable(null);
                holderView.imageView.setImageBitmap(null);
                mImageLoader.displayImage("file:///" + imgInfo.getBigImgPath(), holderView.imageView, options);
            }catch (Exception e)
            {
                String ss="s";
            }

            //ShowImgAsyncTask task=new ShowImgAsyncTask(holderView.imageView);
            //task.execute(imgInfo);
        }
        return convertView;
    }


    private class ImgViewHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    private class ImgViewRunable implements Runnable {

        private UploadFileInfo mUploadFileInfo = null;
        private ImageView mImageView = null;

        public ImgViewRunable(UploadFileInfo uploadFileInfo, ImageView imageView) {
            this.mUploadFileInfo = uploadFileInfo;
            this.mImageView = imageView;
        }

        @Override
        public void run() {
            if (mUploadFileInfo == null)
                return;
            if (mUploadFileInfo.iServer()) {
                //服务器上图片 获取流
                try {
                    mImageView.post(new Runnable() {
                        @Override
                        public void run() {
                            mImageView.setImageBitmap(BitmapFactory.decodeFile(mUploadFileInfo.getBigImgPath()));
                        }
                    });
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    mImageView.post(new Runnable() {
                        @Override
                        public void run() {
                            String s = "1";
                            mImageView.setImageResource(R.drawable.icon_img_add);
                        }
                    });
                }
            } else {
                if (mUploadFileInfo.isDefault()) {
                    //默认图片
                    mImageView.post(new Runnable() {
                        @Override
                        public void run() {
                            String s = "1";
                            mImageView.setImageResource(R.drawable.icon_img_add);
                        }
                    });
                } else {
                    //本地图片
                    mImageView.post(new Runnable() {
                        @Override
                        public void run() {
                            mImageView.setImageBitmap(ImageUtil.getImageThumbnail(mUploadFileInfo.getSmallImgPath(), 80, 80));
                        }
                    });
                }
            }
        }
    }


    private class ShowImgAsyncTask extends AsyncTask<UploadFileInfo, Integer, Bitmap> {
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
        protected Bitmap doInBackground(UploadFileInfo... params) {
            //Looper.prepare();
            if (params.length > 0) {
                UploadFileInfo fileInfo = params[0];
                if (fileInfo.iServer()) {
                    //服务器上图片 获取流
                    try {
                        // Looper.loop();
                        return BitmapFactory.decodeFile(fileInfo.getBigImgPath());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        // Looper.loop();
                        return null;
                    }
                } else {
                    if (fileInfo.isDefault()) {
                        //默认图片
                        // Looper.loop();
                        return null;
                    } else {
                        //本地图片
                        //Looper.loop();
                        return ImageUtil.getImageThumbnail(fileInfo.getSmallImgPath(), 80, 80);
                    }
                }
            }
            //Looper.loop();
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
