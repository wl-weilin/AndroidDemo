package com.demoapp.lifecycledemo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class User extends BaseObservable {
    public String name;

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }
    
    public String getName(){
        return name;
    }
}
