package com.demoapp.documentviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class FileViewActivity extends AppCompatActivity {

    private final static String TAG = "DocumentViewDemo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);

        openDocument(getIntent());
    }

    public void openDocument(Intent intent) {
        Uri uri = intent.getData();
//        Uri uri=Uri.parse("content://com.demoapp.filedemo.fileprovider/share_name/Hello.txt");
        String type = getDocuType(uri, intent.getType());
        switch (type) {
            case "txt":
                Util.txtView(this,uri);
                break;
            case "pdf":
                Util.pdfView(this,uri);
                break;
            case "word":
                Util.wordView(uri);
                break;
            default:
                break;
        }
        Log.d(TAG, "uri = " + uri);
//        String name = Util.getFileName(this, uri);
//        Log.d(TAG, "File name: " + name);
    }

    public String getDocuType(Uri uri, String type) {
        if (uri == null) {
            return null;
        }
        String file = uri.toString();
        String extension = file.substring(file.lastIndexOf(".") + 1);
        if ("txt".equals(extension) || "text/plain".equals(type)) {
            return "txt";
        }
        if ("pdf".equals(extension) || "application/pdf".equals(type)) {
            return "pdf";
        }
        if ("docx".equals(extension) || "application/msword".equals(type)) {
            return "word";
        }
        return "null";
    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}