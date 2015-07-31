package com.chaqianma.jd.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

/**
 * Created by zhangxd on 2015/7/28.
 */
public class ImageUtil {
    /**
     * 把图片转成圆角
     *
     * @param bitmap
     * @param angle  图角角度 建议0~90
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float angle) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        //final float roundPx = 90;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, angle, angle, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 处理图片 放大、缩小到合适位置
     *
     * @param newWidth
     * @param newHeight
     * @param bitmap
     * @return
     */
    public static Bitmap resizeBitmap(float newWidth, float newHeight, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth / bitmap.getWidth(), newHeight / bitmap.getHeight());
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return newBitmap;
    }

    /**
     * 旋转图片
     *
     * @param source
     * @return
     */
    public static Bitmap changeRoate(Bitmap source, boolean isHeadCamera) {
        int orientation = 90;
        Bitmap bMapRotate = null;
        if (source.getHeight() < source.getWidth()) {
            orientation = 90;
            if (isHeadCamera)
                orientation = -90;
        } else {
            orientation = 0;
        }
        if (orientation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            bMapRotate = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                    source.getHeight(), matrix, true);
        } else {
            return source;
        }
        return bMapRotate;
    }

    /**
     * 获取图片路径
     */
    public static String getPicPathFromUri(Uri uri, Activity activity) {
        String value = uri.getPath();

        if (value.startsWith("/external")) {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else {
            return value;
        }
    }

    /**
     * 读取本地的图片得到缩略图，如图片需要旋转则旋转。
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getLocalThumbImg(String path, float width, float height, String imageType) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > width) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / width);
        } else if (w < h && h > height) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / height);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(path, newOpts);
        bitmap = compressImage(bitmap, 100, imageType);//压缩好比例大小后再进行质量压缩
        int degree = readPictureDegree(path);
        bitmap = rotaingImageView(degree, bitmap);
        return bitmap;
    }

    /**
     * 图片质量压缩
     *
     * @param image
     * @return
     * @size 图片大小（kb）
     */
    public static Bitmap compressImage(Bitmap image, int size, String imageType) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (imageType.equalsIgnoreCase("png")) {
                image.compress(Bitmap.CompressFormat.PNG, 100, baos);
            } else {
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            }
            int options = 100;
            while (baos.toByteArray().length / 1024 > size) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                if (imageType.equalsIgnoreCase("png")) {
                    image.compress(Bitmap.CompressFormat.PNG, options, baos);
                } else {
                    image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                }
                options -= 10;//每次都减少10
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        if (bitmap == null)
            return null;
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 获取适应屏幕大小的图
     */
    public static Bitmap sacleBitmap(Context context, Bitmap bitmap) {
        // 适配屏幕大小
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        float aspectRatio = (float) screenWidth / (float) width;
        int scaledHeight = (int) (height * aspectRatio);
        Bitmap scaledBitmap = null;
        try {
            scaledBitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, scaledHeight, false);
        } catch (OutOfMemoryError e) {
        }
        return scaledBitmap;
    }


    /*
    * 获取缩略图
    * */
    public static Bitmap getImageThumbnail(String imgPath) {
        return getImageThumbnail(imgPath, 80, 80);
    }

    /*
    * 获取缩略图
    * */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 保存图片Bitmap到本地
     *
     * @param    tbFilePath    缩略图地址
     * @param    bitmap    位图
     */
    public static void saveBitmapFile(String imgPath, Bitmap bitmap) {
        File file = new File(imgPath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists())
            parentFile.mkdirs();
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //dp to px
    public static float dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    // 缩放图片
    public static Bitmap proportionZoom(String imgPath, int maxSquare) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bm = BitmapFactory.decodeFile(imgPath, options);
        options.inJustDecodeBounds = false;
        int height = options.outHeight;
        int width = options.outWidth;
        float scaleValue = 1;
        if (height > width) {
            scaleValue = maxSquare * 1.0f / height;
        } else {
            scaleValue = maxSquare * 1.0f / width;
        }
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleValue, scaleValue);
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(imgPath, options);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        newbm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        newbm = BitmapFactory.decodeStream(bais);
//		bm.recycle();
        bm = null;
        System.gc();

        return newbm;
    }

}
