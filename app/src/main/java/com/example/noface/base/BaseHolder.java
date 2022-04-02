package com.example.noface.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.noface.R;
import com.example.noface.utils.BitmapUtil;

import java.util.HashMap;

public class BaseHolder extends RecyclerView.ViewHolder {

    //不写死控件变量，而采用Map方式
    private HashMap<Integer, View> mViews = new HashMap<>();
    private View mRootView;

    public BaseHolder(View itemView) {
        super(itemView);
        mRootView = itemView;
    }

    /**
     * 获取控件的方法
     */
    public <T> T getView(Integer viewId) {
        //根据保存变量的类型 强转为该类型
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            //缓存
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 传入文本控件id和设置的文本值，设置文本
     */
    public BaseHolder setTextViewText(Integer viewId, String value) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(value);
        }
        return this;
    }


    /**
     * 传入文本控件id和设置的文本值，设置背景
     */
    public BaseHolder setBackground(Integer viewId, Drawable drawable) {
        View view = getView(viewId);
        if (view != null) {
            if (null == drawable) {
                view.setBackground(null);
            }
            view.setBackground(drawable);
        }
        return this;
    }

    /**
     * 传入文本控件id和设置的文本值，设置背景
     */
    public BaseHolder setBackgroundResource(Integer viewId, Integer resid) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(resid);
        }
        return this;
    }

    /**
     * 传入文本控件id和设置的文本颜色
     */
    public BaseHolder setTextViewColor(Integer viewId, Context context, int color) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTextColor(context.getResources().getColor(color));
        }
        return this;
    }

    public BaseHolder setTextViewSize(Integer viewId, Context context, int size) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTextSize(context.getResources().getDimension(size));
        }
        return this;
    }


    /**
     * 传入控件id和透明度，设置控件背景透明度并且不改变文本透明度
     */
    public BaseHolder setViewBackgroundAlpha(Integer viewId, int value) {
        View view = getView(viewId);
        if (view != null) {
            view.getBackground().mutate().setAlpha(value);
        }
        return this;
    }

    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return
     */
    public ViewGroup.LayoutParams setViewMargin(View view, int left, int right, int top, int bottom) {
        if (view == null) {
            return null;
        }

        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginParams);
        return marginParams;
    }


    /**
     * 设置图片
     */
    public BaseHolder setImageResource(Integer viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
        return this;
    }

    /**
     * 设置图片
     */
    public BaseHolder setImageResource(Integer viewId, int resID) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageResource(resID);
        }
        return this;
    }

    /**
     * 传入图片控件id和资源id，设置图片
     */
    public BaseHolder setImageResource(Integer viewId, String url) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            if (TextUtils.isEmpty(url)) {
                BitmapUtil.loadResourceBitmapToImage(mRootView.getContext(), R.drawable.myself, imageView);
            } else {
                BitmapUtil.loadBitmapToImage(mRootView.getContext(), url, imageView);
            }
        }
        return this;
    }


    public BaseHolder setViewClick(Integer viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    public BaseHolder setImageViewClick(Integer viewId, View.OnClickListener listener) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setOnClickListener(listener);
        }
        return this;
    }

    public BaseHolder setViewVisibly(Integer viewId, int visibility) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visibility);
        }
        return this;
    }


    public BaseHolder setLayoutParms(Integer viewId, LinearLayout.LayoutParams parmes) {
        View view = getView(viewId);
        if (view != null) {
            view.setLayoutParams(parmes);
        }
        return this;
    }

    public BaseHolder setViewLongClick(Integer viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(listener);
        }
        return this;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        mRootView.setOnLongClickListener(listener);
    }

    public void OnClickListener(View.OnClickListener listener) {
        mRootView.setOnClickListener(listener);
    }
}
