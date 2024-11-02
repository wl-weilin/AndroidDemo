package com.demoapp.documentviewdemo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "DocumentViewDemo";
    private static String DIR = Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOCUMENTS + "/";
    private ActivityResultLauncher<Intent> mFileChooserLauncher;
    private static final int GET_PERMISSION_REQUEST = 1;
    private static final int GRANT_PERMISSION_RESULT = 2;

    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerActivityResult();

        findViewById(R.id.open_txt).setOnClickListener(v -> {
//            String filePath = dir + "Hello.txt";
//            Util.openFileLocalActivity(this, filePath);

            Uri uri = Uri.parse("content://com.demoapp.filedemo.fileprovider/share_name/Hello.txt");
            Intent intent = new Intent();
            intent.setClass(this, FileViewActivity.class);
            intent.setData(uri);
            startActivity(intent);
        });

        findViewById(R.id.open_pdf).setOnClickListener(v -> {
//            String filePath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + "Hello.pdf";
            String filePath = DIR + "Hello.pdf";
            Log.d(TAG, filePath);
            Util.openFileLocalActivity(this, new File(filePath));
        });

        findViewById(R.id.open_word).setOnClickListener(v -> {
            String filePath = DIR + "Hello.docx";
            Util.openFileLocalActivity(this, new File(filePath));
        });

        findViewById(R.id.open_word_other).setOnClickListener(v -> {
            String filePath = DIR + "Hello.docx";
            File file = new File(filePath);
            Uri contentUri = FileProvider.getUriForFile(this, "com.demoapp.documentviewdemo.fileprovider", file);
            Log.d(TAG, contentUri.toString());

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(contentUri, "application/msword");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivity(intent);
        });

        findViewById(R.id.open_doc_chooser).setOnClickListener(v -> {
            // 打开文件选择器
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            // 设置可以选择的文件类型（MIME类型）
            // "*/*"表示可以选择任意类型的文件，具体可改为"application/pdf"、"image/*"等
            intent.setType("*/*");
            mFileChooserLauncher.launch(intent);
        });

        findViewById(R.id.get_uri_permission).setOnClickListener(v -> {
            String packageName = "com.demoapp.filedemo";
            Intent intent = new Intent();
            intent.setClassName(packageName, packageName + ".MainActivity");
            startActivityForResult(intent, GET_PERMISSION_REQUEST);
        });

        findViewById(R.id.check_uri_permission).setOnClickListener(v -> {
            Uri uri = Uri.parse("content://com.demoapp.filedemo.fileprovider/share_name/Hello.txt");
            Util.checkUriPermission(this, uri);
        });

        findViewById(R.id.button1).setOnClickListener(v -> {
            getUriInfo(mUri);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_PERMISSION_REQUEST && resultCode == GRANT_PERMISSION_RESULT) {
            Uri uri = data.getData();
            Toast.makeText(this, "uri=" + uri.toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(this, FileViewActivity.class);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    private void registerActivityResult() {
        mFileChooserLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri uri = result.getData().getData();
                        mUri = uri;
                        if (uri != null) {
//                            getUriInfo(uri);
                        }
                    }
                });
    }

    public void getUriInfo(Uri uri) {
        Log.d(TAG, "uri=" + uri.toString());
        Cursor cursor = null;
        try {

//            this.getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cursor = this.getContentResolver().query(uri,
                    null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                Log.d(TAG, "ColumnNames = " + Arrays.toString(cursor.getColumnNames()));

                final String id = cursor.getString(cursor.getColumnIndexOrThrow("document_id"));
                final String type = cursor.getString(cursor.getColumnIndexOrThrow("mime_type"));
                final String displayName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));

                Log.d(TAG, "documentId=" + id);
                Log.d(TAG, "mime_type=" + type);
                Log.d(TAG, "displayName=" + displayName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void queryData(Context context) {
        Uri table = Uri.parse("content://com.android.providers.media.documents/document/");

        Cursor cursor = null;
        try {
//            context.getContentResolver().takePersistableUriPermission(table, Intent.FLAG_GRANT_READ_URI_PERMISSION
            cursor = context.getContentResolver().query(table,
                    null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                Log.d(TAG, "ColumnNames = " + Arrays.toString(cursor.getColumnNames()));
//                final String displayName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
//                final int indexOfSize = cursor.getColumnIndexOrThrow(OpenableColumns.SIZE);
//                Log.d(TAG, "displayName=" + displayName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }

    }
}