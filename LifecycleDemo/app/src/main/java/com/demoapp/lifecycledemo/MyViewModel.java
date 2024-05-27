package com.demoapp.lifecycledemo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    // 创建一个 MutableLiveData 对象，并指定其泛型类型
    private MutableLiveData<String> liveData = new MutableLiveData<>();

    // 获取 LiveData 对象
    public LiveData<String> getLiveData() {
        return liveData;
    }

    // 设置 LiveData 中的数据
    public void setLiveDataValue(String value) {
        liveData.setValue(value);
    }

    // 添加观察者
    public void observeLiveData(Observer<String> observer) {
        liveData.observeForever(observer);
    }

    // 移除观察者
    public void removeLiveDataObserver(Observer<String> observer) {
        liveData.removeObserver(observer);
    }
}

