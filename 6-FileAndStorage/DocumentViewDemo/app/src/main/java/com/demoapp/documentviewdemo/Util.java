package com.demoapp.documentviewdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Util {
    final static String TAG = "DocumentViewDemo";

    public static void openFileLocalActivity(Context context, File file) {
        if (!file.exists()) {
            Toast.makeText(context, file.getName() + "不存在", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent();
        intent.setClass(context, FileViewActivity.class);
        intent.setData(Uri.fromFile(file));
        context.startActivity(intent);
    }

    public static void txtView(Activity activity, Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView textView = activity.findViewById(R.id.txt_content);
        textView.setVisibility(View.VISIBLE);
        textView.setText(stringBuilder.toString());
        Toast.makeText(activity, "开始浏览：" + getFileName(activity, uri), Toast.LENGTH_SHORT).show();
    }

    public static void pdfView(Activity activity, Uri uri) {
        ParcelFileDescriptor fileDescriptor = null;
        PdfRenderer pdfRenderer = null;
        PdfRenderer.Page currentPage = null;
        try {
            // 打开文件描述符
            fileDescriptor = activity.getContentResolver().openFileDescriptor(uri, "r");
            if (fileDescriptor != null) {
                // 创建 PdfRenderer
                pdfRenderer = new PdfRenderer(fileDescriptor);
                // 打开第一页 PDF 页并渲染到 ImageView
                currentPage = pdfRenderer.openPage(0);

                Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
                currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                // 渲染页面到 ImageView
                ImageView imageView = activity.findViewById(R.id.pdf_image_view);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);

                currentPage.close();
                pdfRenderer.close();
                fileDescriptor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void wordView(Uri uri) {
        Log.d(TAG, uri.getPath());
        FileInputStream fis = null;
        try {
//            File file = new File(filePath);
            fis = new FileInputStream(uri.getPath());

            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph paragraph : paragraphs) {
                // 输出段落文本
                Log.d(TAG, paragraph.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getFileName(Context context, Uri uri) {
        Log.d(TAG, "uri=" + uri.toString());
        boolean res = DocumentsContract.isDocumentUri(context, uri);
        if (!res) {
            Toast.makeText(context, "非DocumentsProvider类型", Toast.LENGTH_SHORT).show();
        }

        Cursor cursor = null;
        String displayName = null;
        try {
            cursor = context.getContentResolver().query(uri,
                    null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                displayName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));

            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }
        Log.d(TAG, "displayName=" + displayName);
        return displayName;
    }

    public static void checkUriPermission(Context context, Uri uri) {
        int isGranted = context.checkUriPermission(uri, Process.myPid(), Process.myUid(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (isGranted == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "已授权", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "未授权", Toast.LENGTH_SHORT).show();
        }
    }
}
