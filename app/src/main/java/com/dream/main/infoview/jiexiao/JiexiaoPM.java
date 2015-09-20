package com.dream.main.infoview.jiexiao;

import com.dream.bean.goodinfo.QishulistEntity;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangll on 15/9/14.
 */
@PresentationModel
public class JiexiaoPM implements HasPresentationModelChangeSupport {

    PresentationModelChangeSupport changeSupport = null;
    private List<QishulistEntity> data = new ArrayList<>();

    private boolean  loadEnable = false;

    public JiexiaoPM() {
        changeSupport = new PresentationModelChangeSupport(this);
    }

    public void setData(List<QishulistEntity> listdata) {
        if (listdata == null) return;
        data.clear();
        data.addAll(listdata);
    }

    @ItemPresentationModel(value = JiexiaoItemPM.class)
    public List<QishulistEntity> getData() {
        return data;
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }

    public boolean isLoadEnable() {
        return loadEnable;
    }


}