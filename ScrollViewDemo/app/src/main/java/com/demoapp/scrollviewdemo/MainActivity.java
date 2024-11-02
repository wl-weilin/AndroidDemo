package com.demoapp.scrollviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItem();
    }

    public void addItem() {
        for (int i = 1; i < 50; i++) {
            // Find the LinearLayout by its ID
            LinearLayout mainLayout = findViewById(R.id.scroll_layout);

            // Create a new TextView
            TextView textView = new TextView(this);
            textView.setText("Item " + i);
            textView.setTextSize(24);
            int padding=20;
            textView.setPadding(padding,padding,padding,padding);

            // Set layout parameters for the TextView
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(layoutParams);

            // Add the TextView to the LinearLayout
            mainLayout.addView(textView);
        }

    }
}