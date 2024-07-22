package com.demoapp.listviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private MyAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        // 创建适配器并绑定到ListView
        customAdapter = new MyAdapter(getStringData(), this);
        listView.setAdapter(customAdapter);
    }

    public List<String> getStringData(){
        List<String> dataList = new ArrayList<>();
        dataList.add("Item 1");
        dataList.add("Item 2");
        dataList.add("Item 3");
        return dataList;
    }
}