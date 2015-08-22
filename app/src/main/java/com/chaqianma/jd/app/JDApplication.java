package com.chaqianma.jd.app;

import android.app.Application;
import android.app.Notification;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.utils.FileUtil;
import com.chaqianma.jd.utils.JPushUtil;
import com.chaqianma.jd.utils.SharedPreferencesUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by zhangxd on 2015/7/15.
 * Application
 */
public class JDApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileUtil.deleteTempFile();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        JPushInterface.setDebugMode(Constants.DEBUG); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush
        SDKInitializer.initialize(this);//初始化百度地图
        JPushUtil.setPushNotificationBuilder(getApplicationContext());
        //初始化ImageLoader
        initImageLoader();
    }

    //设置别名
    public void setAlias() {
        //设置别名
        JPushInterface.setAliasAndTags(getApplicationContext(), AppData.getInstance().getUserInfo().getMobile(), null, mAliasCallback);
    }

    //取消别名 "" （空字符串）表示取消之前的设置。
    public void cancelAlias()
    {
        JPushInterface.setAliasAndTags(getApplicationContext(), "", null, mAliasCallback);
    }

    /*
    * 初始化ImageLoader
    * */
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                        //.discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                        //.discCacheSize(50 * 1024 * 1024)
                        //.discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        //.discCacheFileCount(100) //缓存的文件数量
                        //.discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }

    // AliasCallback
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    break;
                case 6002:
                    break;
                default:
            }
        }
    };
}
