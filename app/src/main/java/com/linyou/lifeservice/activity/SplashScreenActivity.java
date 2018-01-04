package com.linyou.lifeservice.activity;

import android.os.Environment;
import android.os.Handler;

import com.lidroid.xutils.view.annotation.ContentView;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.utils.MyLog;

import java.io.File;

/**
 * 启动页面
 *
 * @author sheldon
 */
@ContentView(R.layout.activity_splash)
public class SplashScreenActivity extends BaseActivity {

    private String TAG = "SplashScreenActivity";


    @Override
    void setListeners() {

    }

    @Override
    void initDatas() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                District district = ServiceApplication.getInstance().readDistrict();

                if (null == district) {
                    startActivityByClass(DistrictSelectActivity.class);
                } else {

                    //删除缓存的Log信息 每次启动时
                    String  DATABASE_NAME="LogTanYinQing";   //数据库的存储路径
                    File sdCard = Environment.getExternalStorageDirectory();
                    String DATABASE_PATH= sdCard.getAbsolutePath()+"/"+DATABASE_NAME+"/";
                    File dbContentFile = new File(DATABASE_PATH);
                    Boolean tan=true;
                    if (dbContentFile.exists()) {
                        //dbContentFile.delete();
                        if (tan){
                            delete(dbContentFile);
                        }
                    }


                    startActivityByClass(MainActivity.class);
                }
                finish();
            }
        }, 2000);// 延时2秒
    }

    //删除一个文件夹
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }


    @Override
    void createView() {

    }


}
