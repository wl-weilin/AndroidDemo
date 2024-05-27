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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DynamicParallel();

        findViewById(R.id.button1).setOnClickListener(v -> {
            Intent intent1 = new Intent();
            intent1.setClass(this, SecondActivity.class);
            startActivity(intent1);
        });
    }

    @OptIn(markerClass = ExperimentalWindowApi.class)
    public void DynamicParallel() {
        // 创建一个 SplitPairFilter，用于标识共享分屏的 activity
        // 第1个Activity为ListActivity，位于左侧； 第2个Activity为DetailActivity，位于左侧
        // primaryActivity表示启动方，secondaryActivity表示被启动方。只有在发起Activity和被启动Activity匹配SplitPairFilter的规则时，才会启用分屏
        SplitPairFilter filter = new SplitPairFilter(
                ComponentName.createRelative(this, ".MainActivity"),
                ComponentName.createRelative(this, ".SecondActivity"),
                null);
        // 创建过滤条件集，并将将过滤条件添加到过滤条件集
        Set<SplitPairFilter> pairFilters = new HashSet<>();
        pairFilters.add(filter);
        // 分屏的布局方式，以及primaryActivity的屏占比
        final SplitAttributes splitAttributes = new SplitAttributes.Builder()
                .setLayoutDirection(SplitAttributes.LayoutDirection.LEFT_TO_RIGHT)
                .setSplitType(SplitAttributes.SplitType.ratio(0.33f))
                .build();
        // 设置分屏规则
        SplitPairRule splitPairRule = new SplitPairRule.Builder(pairFilters)
                .setDefaultSplitAttributes(splitAttributes)
                .setMinWidthDp(840)
                .setMinSmallestWidthDp(600)
                .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ratio(1.5f))
                .setFinishPrimaryWithSecondary(SplitRule.FinishBehavior.NEVER)
                .setFinishSecondaryWithPrimary(SplitRule.FinishBehavior.ALWAYS)
                .setClearTop(false)
                .build();
        // 获取 RuleController 的单例实例并添加规则
        RuleController ruleController = RuleController.getInstance(this);
        ruleController.addRule(splitPairRule);
    }
}