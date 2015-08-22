package com.chaqianma.jd.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.DownImgInfo;
import com.chaqianma.jd.model.UploadStatus;
import com.chaqianma.jd.model.UploadFileInfo;
import com.chaqianma.jd.model.UploadFileType;
import com.chaqianma.jd.utils.AudioRecorder;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.widget.JDAlertDialog;
import com.chaqianma.jd.widget.JDToast;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.w3c.dom.Text;

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
    private List<UploadFileInfo> mSoundInfoList = null;
    private Context mContext = null;
    private iOnClickSoundListener mIonClickImgListener;
    private MediaPlayer mediaPlayer = null;
    private AudioRecorder mAudioRecorder = null;
    private Dialog soundDialog = null;
    private AnimationDrawable soundAnimation = null;
    private ImageView sound_img = null;
    private ImageView img_ok = null;
    private ImageView img_cancel = null;
    private ImageView img_delete = null;
    //private UploadFileInfo soundInfo = null;
    private String mParentId = "1";
    private String mParentTableName = null;
    private String mSoundPath = null;
    private TextView tv_record_status = null;

    public interface iOnClickSoundListener {
        void onStartRecord();

        void onStopRecord();
    }

    public SoundGridViewAdapter(Context context, List<UploadFileInfo> soundInfoList) {
        this(context, soundInfoList, null);
    }


    public SoundGridViewAdapter(Context context, List<UploadFileInfo> soundInfoList, String parentTableName) {
        this.mContext = context;
        this.mSoundInfoList = soundInfoList;
        this.mParentTableName = parentTableName;
    }

    public void setParentId(String parentId) {
        this.mParentId = parentId;
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
            UploadFileInfo fileInfo = mSoundInfoList.get(position);
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
            final ImageView imgView = (ImageView) convertView.findViewById(R.id.img_main);
            ImageView img_fail = (ImageView) convertView.findViewById(R.id.img_fail);
            ImageView img_success = (ImageView) convertView.findViewById(R.id.img_success);
            if (!fileInfo.isDefault()) {
                imgView.setImageResource(R.drawable.icon_sound);
                if (fileInfo.getStatus() == UploadStatus.SUCCESS.getValue()) {
                    img_fail.setVisibility(View.GONE);
                    img_success.setVisibility(View.VISIBLE);
                } else {
                    img_fail.setVisibility(View.VISIBLE);
                    img_success.setVisibility(View.GONE);
                }
            }
            imgView.setTag(fileInfo);
            imgView.setLongClickable(true);
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() instanceof UploadFileInfo) {
                        UploadFileInfo uploadFileInfo = (UploadFileInfo) v.getTag();
                        if (uploadFileInfo.isDefault()) {
                            if (mSoundInfoList.size() >= 11) {
                                JDToast.showLongText(mContext,"最多只能上传10段录音");
                                return;
                            }
                            startRecord();
                        } else {
                            playRecord(uploadFileInfo);
                        }
                    }
                }
            });
            imgView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    JDAlertDialog.showAlertDialog(mContext, "确定删除录音吗？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (imgView.getTag() instanceof UploadFileInfo) {
                                final UploadFileInfo soundInfo = (UploadFileInfo) imgView.getTag();
                                try {//删除正在收听的录音
                                    if (mediaPlayer != null)
                                        mediaPlayer.release();
                                    mediaPlayer = null;
                                    if (soundInfo == null)
                                        return;
                                    if (soundInfo.iServer()) {
                                        //去服务端删除文件
                                        HttpClientUtil.delete(HttpRequestURL.deleteFileUrl + soundInfo.getFileId(), null, new JDHttpResponseHandler(mContext, new ResponseHandler() {
                                            @Override
                                            public void onSuccess(Object o) {
                                                JDToast.showLongText(mContext, "录音删除成功");
                                                mSoundInfoList.remove(soundInfo);
                                                notifyDataSetChanged();
                                            }
                                        }));
                                    } else {
                                        if (!soundInfo.isDefault()) {
                                            mSoundInfoList.remove(soundInfo);
                                            notifyDataSetChanged();
                                        }
                                    }
                                    soundDialog.dismiss();
                                } catch (Exception e) {
                                }
                            }
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dialog.cancel();
                        }
                    });
                    //不执行短按操作
                    return true;
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
            img_delete = (ImageView) soundDialog.findViewById(R.id.img_delete);
            img_cancel = (ImageView) soundDialog.findViewById(R.id.img_cancel);
            tv_record_status = (TextView) soundDialog.findViewById(R.id.tv_record_status);
            //设置监听
            soundDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //通过判断 img_delete 状态
                    if (img_delete.getVisibility() == View.VISIBLE) {
                        //显示 说明 收听状态   关闭收听
                        if (mediaPlayer == null)
                            return;
                        mediaPlayer.release();
                        mediaPlayer = null;
                    } else {
                    }
                }
            });
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
                        //mAudioRecorder.stop();
                        mAudioRecorder.cancel();
                        soundDialog.dismiss();
                        //if (soundInfo == null) {
                        //    return;
                        //}
                    } catch (Exception e) {
                    }
                }
            });

            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                    } catch (Exception e) {
                    }
                }
            });
            sound_img.setBackgroundResource(R.drawable.sound_animation);
            soundAnimation = (AnimationDrawable) sound_img.getBackground();
        }
    }


    //刷新数据源
    public void refreshData() {
        notifyDataSetChanged();
    }

    //开始录音
    private void startRecord() {
        mSoundPath = Environment.getExternalStorageDirectory() + "/" + Constants.FILEDIR + "/" + System.currentTimeMillis() + "" + ".amr";
        mAudioRecorder = new AudioRecorder(mSoundPath);
        try {
            mAudioRecorder.start();
        } catch (Exception e) {
        }
        //mHandler.post(mRunable);
        showVoiceDialog();
        setVisible(true);
        soundDialog.setCancelable(false);
        soundDialog.show();
        //soundAnimation.start();
    }

    //结束录音
    private void stopRecord() {
        try {
            if (soundDialog.isShowing())
                soundDialog.dismiss();
            mAudioRecorder.stop();
            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
            ContentBody fileBody = new FileBody(new File(mSoundPath));
            entity.addPart("files", fileBody);
            entity.addPart("fileType", new StringBody(UploadFileType.REMARK.getValue() + "", ContentType.DEFAULT_TEXT));
            entity.addPart("parentTableName", new StringBody(mParentTableName, ContentType.DEFAULT_TEXT));
            entity.addPart("parentId", new StringBody(mParentId, ContentType.DEFAULT_TEXT));
            HttpClientUtil.post(mContext, HttpRequestURL.uploadImgUrl, entity.build(), new JDHttpResponseHandler(mContext, new ResponseHandler<UploadFileInfo>() {
                @Override
                public void onSuccess(UploadFileInfo fileInfo) {
                    UploadFileInfo soundInfo = new UploadFileInfo();
                    soundInfo.setIsDefault(false);
                    soundInfo.setFileType(UploadFileType.SOUND.getValue());
                    soundInfo.setParentTableName(mParentTableName);
                    soundInfo.setBigImgPath(mSoundPath);
                    soundInfo.setStatus(UploadStatus.SUCCESS.getValue());
                    soundInfo.setFileId(fileInfo.getFileId());
                    soundInfo.setiServer(true);
                    soundInfo.setFileId(fileInfo.getFileId());
                    soundInfo.setParentId(fileInfo.getParentId());
                    mSoundInfoList.add(0, soundInfo);
                    notifyDataSetChanged();
                }

                @Override
                public void onFailure(String data) {
                    UploadFileInfo soundInfo = new UploadFileInfo();
                    soundInfo.setStatus(UploadStatus.FAILURE.getValue());//失败
                    soundInfo.setBigImgPath(mSoundPath);
                    soundInfo.setiServer(false);
                    mSoundInfoList.add(0, soundInfo);
                    notifyDataSetChanged();
                }
            }, Class.forName(UploadFileInfo.class.getName())));
        } catch (Exception e) {
            JDToast.showLongText(mContext, "录音失败，请重新录音");
        }
    }

    //播放录音
    private void playRecord(UploadFileInfo uploadFileInfo) {
        showVoiceDialog();
        if (!soundDialog.isShowing()) {
            soundDialog.show();
            soundDialog.setCancelable(true);
            setVisible(false);
        }
        try {
            if (mediaPlayer == null)
                mediaPlayer = new MediaPlayer();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        } catch (Exception e) {

        }
        try {
            mediaPlayer.setDataSource(uploadFileInfo.getBigImgPath());
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


    //设置控件显示与否
    private void setVisible(boolean isRecord) {
        if (isRecord) {
            //录音中。。。
            img_ok.setVisibility(View.VISIBLE);
            img_cancel.setVisibility(View.VISIBLE);
            img_delete.setVisibility(View.GONE);
            tv_record_status.setText("录音中...");
        } else {
            //收听中。。。
            img_ok.setVisibility(View.GONE);
            img_cancel.setVisibility(View.GONE);
            img_delete.setVisibility(View.GONE);
            tv_record_status.setText("收听中...");
        }
    }

    class HolderView {
        ImageView imageView;
    }
}
