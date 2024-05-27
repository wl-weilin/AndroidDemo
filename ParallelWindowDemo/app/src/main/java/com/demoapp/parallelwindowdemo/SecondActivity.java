package com.demoapp.parallelwindowdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.LayoutDirection;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.window.core.ExperimentalWindowApi;
import androidx.window.embedding.EmbeddingAspectRatio;
import androidx.window.embedding.RuleController;
import androidx.window.embedding.SplitAttributes;
import androidx.window.embedding.SplitController;
import androidx.window.embedding.SplitPairFilter;
import androidx.window.embedding.SplitPairRule;
import androidx.window.embedding.SplitRule;

import java.util.HashSet;
import java.util.Set;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DynamicParallel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.button1).setOnClickListener(v -> {
            Intent intent1 = new Intent();
            intent1.setClass(this, ThirdActivity.class);
            startActivity(intent1);
        });
    }

    @OptIn(markerClass = ExperimentalWindowApi.class)
    public void DynamicParallel() {
        SplitPairFilter filter = new SplitPairFilter(
                ComponentName.createRelative(this, ".SecondActivity"),
                ComponentName.createRelative(this, ".ThirdActivity"),
                null);
        Set<SplitPairFilter> pairFilters = new HashSet<>();
        pairFilters.add(filter);
        final SplitAttributes splitAttributes = new SplitAttributes.Builder()
                .setLayoutDirection(SplitAttributes.LayoutDirection.LEFT_TO_RIGHT)
                .setSplitType(SplitAttributes.SplitType.ratio(0.33f))
                .build();
        SplitPairRule splitPairRule = new SplitPairRule.Builder(pairFilters)
                .setDefaultSplitAttributes(splitAttributes)
                .setMinWidthDp(840)
                .setMinSmallestWidthDp(600)
                .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ratio(1.5f))
                .setFinishPrimaryWithSecondary(SplitRule.FinishBehavior.NEVER)
                .setFinishSecondaryWithPrimary(SplitRule.FinishBehavior.ALWAYS)
                .setClearTop(false)
                .build();
        RuleController ruleController = RuleController.getInstance(this);
        ruleController.addRule(splitPairRule);
    }
}