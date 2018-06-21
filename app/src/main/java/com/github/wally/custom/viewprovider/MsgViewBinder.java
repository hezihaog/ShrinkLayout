package com.github.wally.custom.viewprovider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
        holder.mMsgOne.setText(msg.getTitle());
        holder.mMsgTwo.setText(msg.getTitle());
        holder.mMsgThird.setText(msg.getTitle());
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
                    View itemView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.item_msg, null);
                    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    itemView.measure(w, h);
                    int singleItemHeight = itemView.getMeasuredHeight();
                    holder.mShrinkLayout.shrink(originHeight, 2 * singleItemHeight);
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ShrinkLayout mShrinkLayout;
        private final TextView mMsgOne;
        private final TextView mMsgTwo;
        private final TextView mMsgThird;
        private final FrameLayout mBtnLayout;

        ViewHolder(View itemView) {
            super(itemView);
            mBtnLayout = itemView.findViewById(R.id.btn_layout);
            mShrinkLayout = itemView.findViewById(R.id.shrink_layout);
            mMsgOne = itemView.findViewById(R.id.msg_one);
            mMsgTwo = itemView.findViewById(R.id.msg_two);
            mMsgThird = itemView.findViewById(R.id.msg_third);
        }
    }
}
