package com.demoapp.clipboarddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "SuperClipPasteMain";
    private EditText showText;
    private LinearLayout showPicture;
    private ClipboardManager mClipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        showText = findViewById(R.id.text_show_clip);
        showPicture = findViewById(R.id.show_picture);

        findViewById(R.id.print_clipboard).setOnClickListener(v -> {
            printClipboard();
        });

        findViewById(R.id.botton_clear).setOnClickListener(v -> {
            showText.setText("");
            showPicture.removeAllViews();
        });

    }

    private void showImage(ClipData clipData) {
        int itemCount = clipData.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            ClipData.Item item = clipData.getItemAt(i);
            Uri uri = item.getUri();
            if (uri != null) {
                String type = getContentResolver().getType(uri);
                if (type != null && type.contains("image/")) {
                    ImageView view = new ImageView(this);
                    view.setImageURI(uri);
                    showPicture.addView(view);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    layoutParams.height = 600;
                    layoutParams.setMargins(20, 20, 20, 20);
                }
            }
        }
    }

    private void printClipboard() {
        // 读取剪贴板
        ClipData clipData = mClipboardManager.getPrimaryClip();

        // 获取剪贴板中每个剪贴项的信息
        int itemCount = clipData.getItemCount();
        Log.d(TAG, "itemCount = " + itemCount);

        StringBuilder stringItem = new StringBuilder();
        for (int i = 0; i < itemCount; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                stringItem
                        .append("--------------------\n")
                        .append(" [ItemAt ").append(i).append("] ").append("\n")
                        .append("\t * Text:               ").append(clipData.getItemAt(i).getText()).append("\n")
                        .append("\t * HtmlText:        ").append(clipData.getItemAt(i).getHtmlText()).append("\n")
                        .append("\t * Uri:                  ").append(clipData.getItemAt(i).getUri()).append("\n")
                        .append("\t * Intent:             ").append(clipData.getItemAt(i).getIntent()).append("\n")
                        .append("\t * TextLinks:        ").append(clipData.getItemAt(i).getTextLinks()).append("\n");
            }
        }

        // 打印整个剪贴板的信息
        ClipDescription description = clipData.getDescription();
        int mimeTypeCount = description.getMimeTypeCount();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mimeTypeCount; i++) {
            if (i > 0) {
                builder.append("  |  ");
            }
            builder.append(description.getMimeType(i));
        }

        String msg = null;
        msg = "--------------------------------------------------\n"
                + "[Description]" + "\n"
                + "\t * MimeType   -->  " + builder + "\n"
                + "\t * Label   -->  " + description.getLabel() + "\n"
                + "\t * Extras  -->  " + description.getExtras() + "\n"
                + "\t * Timestamp  -->  " + description.getTimestamp() + "\n"
                + "--------------------------------------------------\n\n"
                + "[ItemData]\n"
                + stringItem + "\n"
                + "--------------------------------------------------";

        Log.d(TAG, msg);
        // 在编辑框中显示文本
        showText.setText(msg);

        // 显示图片
        showImage(clipData);
    }
}