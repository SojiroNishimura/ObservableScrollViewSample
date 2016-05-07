package com.callittips.snishimura.observablescrollviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    @InjectView(R.id.image_header)
    ImageView mHeaderImage;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.scrollView)
    ObservableScrollView mScrollView;

    private int mParallaxHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        mScrollView.setScrollViewCallbacks(this);

        // Toolbarの背景色をalpha=0でセットする
        mToolbar.setBackgroundColor(
                ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));

        // 画像領域の高さを取得
        mParallaxHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.colorPrimary);

        // 現在のスクロール量からalpha値を計算する(0~1.0)
        float alpha = Math.min(1, (float) scrollY / mParallaxHeight);
        mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));

        mHeaderImage.setTranslationY(scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }
}
