package com.demoapp.binderserverdemo;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private int mId;
    private String mName;

    // 必须要有无参构造函数，因为如果aidl中使用out修饰该对象则会调用无参构造函数
    public Person() {
    }

    public Person(int id, String name) {
        this.mId = id;
        this.mName = name;
    }

    // toString()不是必须的
    public String toString() {
        return "Person{" +
                "id=" + mId + ", " +
                "name=" + mName +
                "}";
    }

    // Person中不包含文件描述符，返回0
    @Override
    public int describeContents() {
        return 0;
    }

    // 将成员写入到Parcel out
    // 如果有非基本类型的对象需要写入，该对象也应该实现Parcelable，并调用"对象.writeToParcel()"
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mName);
    }

    public void readFromParcel(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
    }

    // CREATOR接口是从Parcel中解析出对象
    public static final Parcelable.Creator<Person> CREATOR
            = new Parcelable.Creator<Person>() {
        // 从Parcel中解析出对象，实际调用的是Person(Parcel in)构造函数
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    // 具体从Parcel解析对象的构造函数
    private Person(Parcel in) {
        readFromParcel(in);
    }
}
