package com.dream.main.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.dream.main.DreamApplication;

import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;

/**
 * zhangyao
 * zhangyao@guoku.com
 * 15/8/26 23:47
 */
public abstract class BaseActivity extends FragmentActivity{

    private boolean isNeedRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewBinder viewBinder = createViewBinder();
        View rootView = viewBinder.inflateAndBind(getLayoutId(), initPM());
        setContentView(rootView);

        if(isNeedRegister){
            DreamApplication.getApp().eventBus().register(this);
            isNeedRegister = false;
        }

    }

    private ViewBinder createViewBinder() {
        BinderFactory reusableBinderFactory = new BinderFactoryBuilder().build();
        return reusableBinderFactory.createViewBinder(this);
    }

    public abstract int getLayoutId();

    public abstract Object initPM();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //切记在 onDestroy 的时候，取消注册。 否则会造成内存泄露 ，在Fragment 中，如果fragment 关闭也要执行此方法
        DreamApplication.getApp().eventBus().unregister(this);
    }
}