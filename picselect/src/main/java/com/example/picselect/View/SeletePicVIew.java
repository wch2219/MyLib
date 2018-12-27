package com.example.picselect.View;

import com.example.baselibs.base.IView;
import com.example.picselect.presenter.Image;

import java.util.List;
import java.util.TreeMap;

public interface SeletePicVIew extends IView {

    void backClassList( List<TreeMap<String,List<Image>>> datas);
}
