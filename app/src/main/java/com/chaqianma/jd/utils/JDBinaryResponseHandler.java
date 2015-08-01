package com.chaqianma.jd.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.chaqianma.jd.model.UploadFileInfo;
import com.loopj.android.http.BinaryHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zhangxd on 2015/7/31.
 */
public class JDBinaryResponseHandler extends BinaryHttpResponseHandler {

    private ResponseHandler handler = null;

    private UploadFileInfo fileInfo = null;

    public JDBinaryResponseHandler() {
        super();
    }

    public JDBinaryResponseHandler(UploadFileInfo fileInfo, ResponseHandler handler) {
        //super( new String[] { "image/jpeg;charset=utf-8", "image/jpeg;charset=utf-8"});
        //super( new String[] {"*/*", "application/pdf", "image/png", "image/jpeg"});
        super(new String[]{".*"});
        this.handler = handler;
        this.fileInfo = fileInfo;
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        // TODO Auto-generated method stub
        int length = bytes.length;

        try {
            InputStream is = new ByteArrayInputStream(bytes);
            FileOutputStream os = new FileOutputStream(fileInfo.getBigImgPath());
            byte[] b = new byte[1024];
            int len = 0;
            //开始读取
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            //完毕关闭
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        File file = new File(fileInfo.getBigImgPath());
        // 压缩格式
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        // 压缩比例
        int quality = 100;
        try {
            // 若存在则删除
            if (file.exists())
                file.delete();
            // 创建文件
            file.createNewFile();
            //
            OutputStream stream = new FileOutputStream(file);
            // 压缩输出
            bmp.compress(format, quality, stream);
            // 关闭
            stream.close();
            handler.onSuccess(fileInfo);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        throwable.printStackTrace();
        Log.e("NMH", "onFailure!" + throwable.getMessage());
        for (Header header : headers) {
            Log.i("NMH", header.getName() + " / " + header.getValue());
        }
        handler.onFailure(fileInfo);
    }
}
