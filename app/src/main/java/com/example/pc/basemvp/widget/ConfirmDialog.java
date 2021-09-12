package com.example.pc.basemvp.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.example.pc.basemvp.R;
import java.util.Objects;

public class ConfirmDialog extends Dialog {

    public ConfirmDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_confirm);
    }

    public ConfirmDialog setTitle(String pTitle) {
        ((TextView) findViewById(R.id.tv_dialog_message_title)).setText(pTitle);
        return this;
    }

    public ConfirmDialog setContent(String pContent) {
        ((TextView) findViewById(R.id.tv_dialog_message_content)).setText(pContent);
        return this;
    }

    public ConfirmDialog setNegativeLabel(String label) {
        ((TextView) findViewById(R.id.btn_dialog_cancel)).setText(label);
        return this;
    }

    public ConfirmDialog setPositiveLabel(String label) {
        ((TextView) findViewById(R.id.btn_dialog_ok)).setText(label);
        return this;
    }

    public ConfirmDialog setNegativeListener(View.OnClickListener negativeListener) {
        findViewById(R.id.btn_dialog_cancel).setOnClickListener(view -> dismiss());
        return this;
    }

    public ConfirmDialog setPositiveListener(View.OnClickListener positiveListener) {
        findViewById(R.id.btn_dialog_ok).setOnClickListener(positiveListener);
        return this;
    }
}

