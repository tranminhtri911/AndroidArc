package com.example.pc.basemvp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.example.pc.basemvp.R;

public class RoundImageView extends AppCompatImageView {

    private float mCornerRadius = 0;
    private RectF mRectF;
    private Path mPath;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RoundRectImageView);
        if (array != null) {
            mCornerRadius = array.getDimensionPixelSize(R.styleable.RoundRectImageView_cornerRadius, 0);
        }
    }

    public void setCornerRadius(float cornerRadius) {
        mCornerRadius = cornerRadius;
        mPath = null;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRectF == null) {
            mRectF = new RectF(0, 0, this.getWidth(), this.getHeight());
        }
        if (mPath == null) {
            mPath = new Path();
            mPath.addRoundRect(mRectF, mCornerRadius, mCornerRadius, Path.Direction.CW);
        }
        canvas.clipPath(mPath);
        super.onDraw(canvas);
    }
}
