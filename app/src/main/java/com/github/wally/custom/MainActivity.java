package com.github.wally.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private boolean isOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView shrinkBtn = findViewById(R.id.shrink_btn);
        final ShrinkLayout shrinkLayout = findViewById(R.id.shrink_layout);
        shrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    //打开状态，压缩
                    int originHeight = shrinkLayout.getOrginHeight();
                    View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_view, null);
                    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    itemView.measure(w, h);
                    int singleItemHeight = itemView.getMeasuredHeight();
                    shrinkLayout.shrink(originHeight, 2 * singleItemHeight);
                } else {
                    //已经压缩了，恢复
                    shrinkLayout.restore();
                }
                //切换状态标志
                isOpen = !isOpen;
            }
        });
    }
}
