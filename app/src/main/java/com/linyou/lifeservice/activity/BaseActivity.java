package com.linyou.lifeservice.activity;


import com.lidroid.xutils.ViewUtils;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.utils.PreferenceUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public abstract class BaseActivity extends FragmentActivity
{
    
	protected static Context context;
    protected ServiceApplication application = null;

    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        application = ServiceApplication.getInstance();
        application.register(this);
        context = this;
        ViewUtils.inject(this);
        
        createView();
        
        setListeners();
        
        initDatas();

    }
    
    /**
     * 通过Class启动Activity
     * 
     * @param cls
     */
    protected void startActivityByClass(Class cls)
    {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }
    
    /**
     * 设置事件监听
     */
    abstract void setListeners();
    
    /**
     * 初始化设置
     */
    abstract void initDatas();
    
    /**
     * 初始化
     */
    abstract void createView();
    
    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
    }
    
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
    }
    
    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
    }
    
    @Override
    protected void onStop()
    {
        // TODO Auto-generated method stub
        super.onStop();
    }

    public void p(String msg) {
        if (true) {
            new AlertDialog.Builder(BaseActivity.this).setTitle("测试数据").setMessage(msg).show();
        }
    }

    public  void tt(String msg) {
        if (true) {
            Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    }
    
}
