package com.dream.main.seach;

import com.dream.bean.SeachGood;
import com.dream.main.DreamApplication;
import com.dream.net.NetResponse;
import com.dream.net.business.ProtocolUrl;
import com.dream.util.ToastUtil;
import com.dream.views.xviews.XLoadEvent;

import org.apache.commons.lang.StringUtils;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.widget.adapterview.ItemClickEvent;
import org.robobinding.widget.view.ClickEvent;

import java.util.ArrayList;
import java.util.HashMap;

import control.annotation.Subcriber;
import eb.eventbus.ThreadMode;

/**
 * Created by yangll on 15/9/9.
 */
@PresentationModel
public class SeachPM implements HasPresentationModelChangeSupport {

    private boolean loadEnable = false;
    private ArrayList<SeachGood> seachdata = new ArrayList<>();
    private String input;


    PresentationModelChangeSupport changeSupport = null;
    private final String SEACHTAG = "SEACHTAG";

    private SeachEmptyPM emptyPM  ;

    int page = 1;
    int count = 20;
    int total = 0 ;

    public SeachPM() {
        changeSupport = new PresentationModelChangeSupport(this);
        DreamApplication.getApp().eventBus().register(this);
    }

    private void getSeach(){
        if(StringUtils.isBlank(input)){
            ToastUtil.show("请输入搜索内容");
            return;
        }
        HashMap<String , Object> params = new HashMap<>();
        params.put("key",input);
        params.put("page",page);
        params.put("size",count);
        DreamApplication.getApp().getDreamNet().netJsonPost(SEACHTAG, ProtocolUrl.SEACH, params);
    }

    public SeachEmptyPM getEmptyPM() {
        return emptyPM = new SeachEmptyPM();
    }

    //搜索按钮
    public void seach(ClickEvent event){
        getSeach();
    }

    public void onload(XLoadEvent event){
        if(page * count >= total){
            setLoadEnable(false);
        }else{
            if(!loadEnable)setLoadEnable(true);
            page++;
            getSeach();
        }
    }

    @Subcriber(tag = SEACHTAG , threadMode = ThreadMode.MainThread)
    public void respHandler(NetResponse response){
        if(response.getRespType() == NetResponse.SUCCESS){

        }else{
            ToastUtil.show("获取结果失败");
        }
    }

    @ItemPresentationModel(value = SeachItemPM.class)
    public ArrayList<SeachGood> getSeachdata() {
        return seachdata;
    }

    public boolean isLoadEnable() {
        return loadEnable;
    }

    public void setLoadEnable(boolean loadEnable) {
        this.loadEnable = loadEnable;
        changeSupport.firePropertyChange("loadEnable");
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    //点击搜索结果
    public void clickItem(ItemClickEvent clickEvent){

    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }
}