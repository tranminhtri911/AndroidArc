package com.example.pc.basemvp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.example.pc.basemvp.R;
import com.example.pc.basemvp.utils.FontCache;

/**
 * Default App's TextView
 * Including font type
 */
public class DTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = DTextView.class.getSimpleName();

    public DTextView(Context context) {
        super(context);
        Typeface customFont = FontCache.getTypeface("fonts/NotoSansCJKjp-Regular.ttf", context);
        setTypeface(customFont);
    }

    public DTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DTextView, defStyleAttr, 0);
        Typeface customFont;
        int str = a.getInt(R.styleable.DTextView_txtFont, 0);
        if (str == 1) {
            customFont = FontCache.getTypeface("fonts/NotoSansCJKjp-Regular.ttf", context);
        } else if (str == 2) {
            customFont = FontCache.getTypeface("fonts/NotoSansCJKjp-Bold.ttf", context);
        } else {
            customFont = FontCache.getTypeface("fonts/NotoSansCJKjp-Regular.ttf", context);
        }
        setTypeface(customFont);

        a.recycle();
    }


    public void setType(Context context, int type) {
        switch (type) {
            case Typeface.BOLD:
                setTypeface(FontCache.getTypeface("fonts/NotoSansCJKjp-Bold.ttf", context));
                break;
            case Typeface.NORMAL:
                setTypeface(FontCache.getTypeface("fonts/NotoSansCJKjp-Regular.ttf", context));
                break;
            case Typeface.BOLD_ITALIC:
            case Typeface.ITALIC:
            default:
                setTypeface(FontCache.getTypeface("fonts/NotoSansCJKjp-Regular.ttf", context));
                break;
        }
    }
}
