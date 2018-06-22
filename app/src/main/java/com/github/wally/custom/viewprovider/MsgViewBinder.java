package com.github.wally.custom.viewprovider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.wally.custom.R;
import com.github.wally.custom.ShrinkLayout;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Package: com.github.wally.custom.viewprovider
 * FileName: TextViewBinder
 * Date: on 2018/6/21  上午9:36
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class MsgViewBinder extends ItemViewBinder<Msg, MsgViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_list_view, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull Msg msg) {
        ((TextView) holder.mMsgOneLayout.findViewById(R.id.sub_item_text)).setText(msg.getTitle());
        ((TextView) holder.mMsgTwoLayout.findViewById(R.id.sub_item_text)).setText(msg.getTitle());
        ((TextView) holder.mMsgThirdLayout.findViewById(R.id.sub_item_text)).setText(msg.getTitle());
        holder.mBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = holder.itemView.getTag(R.id.item_is_open);
                boolean isOpen;
                if (tag != null) {
                    isOpen = (boolean) tag;
                } else {
                    //默认为开
                    isOpen = true;
                }
                if (isOpen) {
                    //打开状态，压缩
                    int originHeight = holder.mShrinkLayout.getOriginHeight();
                    View itemView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.item_msg, holder.mParentLayout, false);
                    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    itemView.measure(w, h);
                    //加上Margin值
                    int singleItemHeight = itemView.getMeasuredHeight();
                    int totalMargin = ((ViewGroup.MarginLayoutParams) itemView.getLayoutParams()).topMargin +
                            ((ViewGroup.MarginLayoutParams) itemView.getLayoutParams()).bottomMargin;
                    int sumHeight = singleItemHeight + totalMargin;
                    holder.mShrinkLayout.shrink(originHeight, sumHeight);
                } else {
                    //已经压缩了，恢复
                    holder.mShrinkLayout.restore();
                }
                //切换状态标志
                isOpen = !isOpen;
                holder.itemView.setTag(R.id.item_is_open, isOpen);
            }
        });
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static int spToPixel(Context context, float spValue) {
        final float fontScale = getDisplayMetrics(context).scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        return displaymetrics;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ShrinkLayout mShrinkLayout;
        private final LinearLayout mMsgOneLayout;
        private final LinearLayout mMsgTwoLayout;
        private final LinearLayout mMsgThirdLayout;
        private final FrameLayout mBtnLayout;
        private final LinearLayout mParentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            mBtnLayout = itemView.findViewById(R.id.btn_layout);
            mShrinkLayout = itemView.findViewById(R.id.shrink_layout);
            mParentLayout = itemView.findViewById(R.id.parent_layout);
            mMsgOneLayout = itemView.findViewById(R.id.msg_one_layout);
            mMsgTwoLayout = itemView.findViewById(R.id.msg_two_layout);
            mMsgThirdLayout = itemView.findViewById(R.id.msg_third_layout);
        }
    }
}
