package com.chaqianma.jd.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.DownImgInfo;
import com.chaqianma.jd.model.ImageUploadStatus;
import com.chaqianma.jd.model.SoundInfo;
import com.chaqianma.jd.model.UploadFileType;
import com.chaqianma.jd.model.UploadImgInfo;
import com.chaqianma.jd.utils.AudioRecorder;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhangxd on 2015/7/30.
 * <p/>
 * 录音数据源
 */
public class SoundGridViewAdapter extends BaseAdapter {

    private static final int RECORD_NO = 0;  //开始录音
    private static final int RECORD_ING = 1;   //录音进行中
    private static final int RECODE_ED = 2;   //录音结束
    private static int RECODE_STATE = -1;      //录音状态
    private List<SoundInfo> mSoundInfoList = null;
    private Context mContext = null;
    private iOnClickSoundListener mIonClickImgListener;
    private MediaPlayer mediaPlayer = null;
    private AudioRecorder mAudioRecorder = null;
    private Dialog soundDialog = null;
    private AnimationDrawable soundAnimation = null;
    private ImageView sound_img = null;
    private ImageView img_ok = null;
    private ImageView img_cancel = null;
    private SoundInfo soundInfo = null;

    public interface iOnClickSoundListener {
        void onStartRecord();

        void onStopRecord();
    }

    public SoundGridViewAdapter(Context context, List<SoundInfo> soundInfoList) {
        this.mContext = context;
        this.mSoundInfoList = soundInfoList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (mSoundInfoList != null)
            return mSoundInfoList.size();
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
        HolderView holderView = null;
        if (position < mSoundInfoList.size()) {
            soundInfo = mSoundInfoList.get(position);
           /* if (null == convertView) {
                holderView = new HolderView();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_img_item, null);
                ImageView imgView = (ImageView) convertView.findViewById(R.id.img_main);
                holderView.imageView = imgView;
                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }*/
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_img_item, null);
            ImageView imgView = (ImageView) convertView.findViewById(R.id.img_main);
            ImageView img_fail=(ImageView)convertView.findViewById(R.id.img_fail);
            ImageView img_success=(ImageView)convertView.findViewById(R.id.img_success);
            if(!soundInfo.isDefault())
            {
                imgView.setImageResource(R.drawable.icon_sound);
                if (soundInfo.getImgStatus() == ImageUploadStatus.SUCCESS.getValue()) {
                    img_fail.setVisibility(View.GONE);
                    img_success.setVisibility(View.VISIBLE);
                } else {
                    img_fail.setVisibility(View.VISIBLE);
                    img_success.setVisibility(View.GONE);
                }
            }
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (soundInfo.isDefault()) {
                        startRecord();
                    } else {
                        playRecord(soundInfo);
                    }
                }
            });

           /* if (soundInfo.isDefault()) {
                //默认
                imgView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (RECODE_STATE != RECORD_ING) {
                                    if (mIonClickImgListener != null) {
                                        mIonClickImgListener.onStartRecord();
                                    }
                                    RECODE_STATE = RECORD_ING;
                                    onStartRecord();
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                if (RECODE_STATE == RECORD_ING) {
                                    RECODE_STATE = RECODE_ED;
                                    if (mIonClickImgListener != null) {
                                        mIonClickImgListener.onStopRecord();
                                        onStopRecord();
                                    }
                                }
                                break;
                        }
                        return true;
                    }
                });
            } else {
                imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // if (mIonClickImgListener != null)
                        {
                            playRecord(mSoundInfoList.get(position));
                            // mIonClickImgListener.onPlayRecord(mSoundInfoList, position);
                        }
                    }
                });
            }
        }*/
        }
        return convertView;
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            try {
                mAudioRecorder.start();
            } catch (Exception e) {

            }
        }
    };

    //录音时显示Dialog
    public void showVoiceDialog() {
        if (soundDialog == null) {
            soundDialog = new Dialog(mContext, R.style.SoundDialogStyle);
            soundDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            soundDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            soundDialog.setContentView(R.layout.sound_dialog);
            soundDialog.setCancelable(false);
            sound_img = (ImageView) soundDialog.findViewById(R.id.dialog_img);
            img_ok = (ImageView) soundDialog.findViewById(R.id.img_ok);
            img_cancel = (ImageView) soundDialog.findViewById(R.id.img_cancel);
            img_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        stopRecord();
                    } catch (Exception e) {
                    }
                }
            });
            img_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mAudioRecorder.cancel();
                        //去掉文件路径
                        soundInfo.setBigImgPath(null);
                        soundDialog.dismiss();
                        if (soundInfo.iServer()) {
                            //去服务端删除文件
                            HttpClientUtil.delete(HttpRequestURL.deleteFileUrl + "", null, new JDHttpResponseHandler(mContext, new ResponseHandler() {
                                @Override
                                public void onSuccess(Object o) {
                                    mSoundInfoList.remove(soundInfo);
                                    notifyDataSetChanged();
                                }
                            }));
                        }
                    } catch (Exception e) {
                    }
                }
            });
            sound_img.setBackgroundResource(R.drawable.sound_animation);
            soundAnimation = (AnimationDrawable) sound_img.getBackground();
        }
        img_ok.setVisibility(View.VISIBLE);
        soundDialog.setCancelable(false);
        soundDialog.show();
        soundAnimation.start();
    }

    //开始录音
    private void startRecord() {
        String soundPath = Environment.getExternalStorageDirectory() + "/" + Constants.FILEDIR + "/" + System.currentTimeMillis() + "" + ".amr";
        String parentId = soundInfo.getParentId();
        parentId = "1";
        soundInfo = new SoundInfo();
        soundInfo.setIsDefault(false);
        soundInfo.setFileType(UploadFileType.SOUND.getValue());
        soundInfo.setParentTableName(Constants.USER_BASE_INFO);
        soundInfo.setParentId(parentId);  //要修改为传过来的值
        soundInfo.setBigImgPath(soundPath);
        soundInfo.setImgStatus(ImageUploadStatus.NONE.getValue());
        mAudioRecorder = new AudioRecorder(soundPath);
        try {
            mAudioRecorder.start();
        } catch (Exception e) {
        }
        //mHandler.post(mRunable);
        showVoiceDialog();
    }

    //结束录音
    private void stopRecord() {
        try {
            if (soundDialog.isShowing())
                soundDialog.dismiss();
            mAudioRecorder.stop();
            mSoundInfoList.add(0, soundInfo);
            notifyDataSetChanged();
            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
            ContentBody fileBody = new FileBody(new File(soundInfo.getBigImgPath()));
            entity.addPart("files", fileBody);
            entity.addPart("fileType", new StringBody(soundInfo.getFileType() + "", ContentType.DEFAULT_TEXT));
            entity.addPart("parentTableName", new StringBody(soundInfo.getParentTableName(), ContentType.DEFAULT_TEXT));
            entity.addPart("parentId", new StringBody("1", ContentType.DEFAULT_TEXT));
            HttpClientUtil.post(mContext, HttpRequestURL.uploadImgUrl, entity.build(), new JDHttpResponseHandler(mContext, new ResponseHandler<DownImgInfo>() {
                @Override
                public void onSuccess(DownImgInfo downImgInfo) {
                    soundInfo.setFileId(downImgInfo.getFileId());
                    soundInfo.setImgIsServer(true);
                    soundInfo.setImgStatus(ImageUploadStatus.SUCCESS.getValue());//成功
                    notifyDataSetChanged();
                }

                @Override
                public void onFailure(String data) {
                    soundInfo.setImgStatus(ImageUploadStatus.FAILURE.getValue());//失败
                    notifyDataSetChanged();
                }
            }, Class.forName(DownImgInfo.class.getName())));
        } catch (Exception e) {
            String sss="asdfsdfasdfasdfas";
            sss="asdfsdfasdfasdfas";
            sss="asdfsdfasdfasdfas";
            sss="asdfsdfasdfasdfas";
            sss="asdfsdfasdfasdfas";
        }
    }

    //播放录音
    private void playRecord(SoundInfo soundInfo) {

        if (!soundDialog.isShowing()) {
            soundDialog.show();
            img_ok.setVisibility(View.GONE);
            soundDialog.setCancelable(true);
        }
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(soundInfo.getBigImgPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    class HolderView {
        ImageView imageView;
    }
}
