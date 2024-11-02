package com.demoapp.filedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import java.io.File;

public class FileViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);

        Uri uri = getIntent().getData();

        Util.viewFile(this,new File(uri.getPath()));
    }
}